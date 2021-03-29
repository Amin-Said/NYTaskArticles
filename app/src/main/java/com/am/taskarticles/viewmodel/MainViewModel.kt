package com.am.taskarticles.viewmodel

import androidx.lifecycle.*
import com.am.taskarticles.api.ArticleApiService
import com.am.taskarticles.di.DaggerViewModelComponent
import com.am.taskarticles.helper.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import javax.inject.Inject

class MainViewModel : ViewModel {

    constructor()

    constructor(test: Boolean = true) {
        injected = test
    }

    private var injected = false

    @Inject
    lateinit var apiService: ArticleApiService

    private fun inject() {
        if (!injected) {
            DaggerViewModelComponent.create().inject(this)
        }
    }


    fun getResponse(key:String) = liveData(Dispatchers.IO) {
        if (!injected) {
            inject()
        }
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiService.getApiForAllArticles(key)))
        }catch (exception:Exception){
            emit(Resource.error(data = null,error = exception))
        }

    }

}