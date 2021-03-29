package com.am.taskarticles

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.am.taskarticles.api.ArticleApiService
import com.am.taskarticles.di.DaggerViewModelComponent
import com.am.taskarticles.model.Article
import com.am.taskarticles.model.ResponseBase
import com.am.taskarticles.viewmodel.MainViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MainViewModelTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var articleApiService: ArticleApiService

    private var mainViewModel = MainViewModel(true)

    private val key = "testKey"

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)

       DaggerViewModelComponent.builder()
            .apiModule(ApiModuleTest(articleApiService))
            .build()
            .inject(mainViewModel)
    }

    @Before
    fun setupRXSchedulers(){
        val immediate = object :Scheduler(){
            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker({ it.run() },true)
            }
        }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler{ immediate }
    }

    @Test
    fun getArticlesSuccess(){

        val article = Article("details",null,null
            ,null,null,null,null
            ,null,null,null,null
            ,null,null,null
            ,null,null,null
            ,null,null,null,null,null)
        val articleList = listOf(article)

        val response =  ResponseBase("ok","ok",1,articleList)

        val testSingle = Single.just(response)

        Mockito.`when`(articleApiService.getApiForAllArticles(key)).thenReturn(testSingle)

        mainViewModel.loadData(key)

        Assert.assertEquals(1,mainViewModel.articles.value?.size)
        Assert.assertEquals(false,mainViewModel.loadError.value)
        Assert.assertEquals(false,mainViewModel.loading.value)



    }

    @Test
    fun getArticlesSuccessWithNoArticlesData(){

        val response =  ResponseBase("ok","",1, emptyList())

        val testSingle = Single.just(response)

        Mockito.`when`(articleApiService.getApiForAllArticles(key)).thenReturn(testSingle)

        mainViewModel.loadData(key)

        Assert.assertEquals(null,mainViewModel.articles.value)
        Assert.assertEquals(false,mainViewModel.loadError.value)
        Assert.assertEquals(false,mainViewModel.loading.value)
        Assert.assertEquals(true,mainViewModel.noData.value)


    }

    @Test
    fun getArticlesFailure(){

        val testSingle = Single.error<ResponseBase>(Throwable())

        Mockito.`when`(articleApiService.getApiForAllArticles(key)).thenReturn(testSingle)

        mainViewModel.loadData(key)

        Assert.assertEquals(null,mainViewModel.articles.value)
        Assert.assertEquals(true,mainViewModel.loadError.value)
        Assert.assertEquals(false,mainViewModel.loading.value)

    }




}