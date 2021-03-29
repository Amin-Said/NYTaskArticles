package com.am.taskarticles.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.am.taskarticles.TestCoroutineRule
import com.am.taskarticles.api.ArticleApiService
import com.am.taskarticles.di.ApiModuleTest
import com.am.taskarticles.di.DaggerViewModelComponent
import com.am.taskarticles.helper.DataDummy
import com.am.taskarticles.helper.Resource
import com.am.taskarticles.model.ResponseBase
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    @Mock
    lateinit var articleApiService: ArticleApiService

    @Mock
    private lateinit var observer: Observer<Resource<ResponseBase>>

    private var mainViewModel = MainViewModel(true)

    private val key = "testKey"

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        DaggerViewModelComponent.builder()
            .apiModule(ApiModuleTest(articleApiService))
            .build()
            .inject(mainViewModel)

        mainViewModel.getResponse(key).observeForever(observer)
    }

    @Test
    fun getArticlesSuccess() {
        testCoroutineRule.runBlockingTest {

            val response = DataDummy.generateDummyDataResponse()

            Mockito.`when`(articleApiService.getApiForAllArticles(key)).thenReturn(response)


            verify(observer).onChanged(Resource.loading(null))
            verify(observer).onChanged(Resource.success(response))

        }

    }

    @Test
    fun getArticlesSuccessWithNoArticlesData() {

        testCoroutineRule.runBlockingTest {
            Mockito.`when`(articleApiService.getApiForAllArticles(key)).thenReturn(DataDummy.generateResponseWithEmptyResult())
            Assert.assertEquals(0,articleApiService.getApiForAllArticles(key).results.size)

        }

    }



}