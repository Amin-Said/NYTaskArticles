package com.am.taskarticles.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.am.taskarticles.api.ArticleApiService
import com.am.taskarticles.di.DaggerViewModelComponent
import com.am.taskarticles.helper.EspressoIdlingResource
import com.am.taskarticles.model.Article
import com.am.taskarticles.model.ResponseBase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class MainViewModel :ViewModel{

    constructor()

    constructor(test:Boolean=true){
        injected = true
    }

    private var injected = false

    val loadError by lazy { MutableLiveData<Boolean>() }
    val noData by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }
    val articles by lazy { MutableLiveData<List<Article>>() }

    private val disposable = CompositeDisposable()

    @Inject
    lateinit var apiService : ArticleApiService

    private fun inject() {
        if (!injected){
            DaggerViewModelComponent.create().inject(this)
        }
    }

    fun loadData(key:String) {
        loading.value = true
        noData.value = false
        loadError.value = false
        inject()
        getArticles(key)
    }

    private fun getArticles(key:String){
        if (!injected){
            EspressoIdlingResource.increment()
        }
        disposable.add(
            apiService.getApiForAllArticles(key)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ResponseBase>() {
                    override fun onSuccess(dataResponse: ResponseBase) {
                        if (dataResponse.results.isNullOrEmpty()) {
                            noData.value = true
                            loadError.value = false
                            loading.value = false
                            articles.value = null
                        } else {
                            loadError.value = false
                            loading.value = false
                            articles.value = dataResponse.results

                        }
                        if (!injected){
                            EspressoIdlingResource.decrement()
                        }


                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        loadError.value = true
                        loading.value = false
                        noData.value = false
                    }

                })
        )

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}