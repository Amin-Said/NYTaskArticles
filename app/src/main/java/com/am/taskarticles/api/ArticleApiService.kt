package com.am.taskarticles.api

import com.am.taskarticles.di.DaggerApiComponent
import com.am.taskarticles.model.ResponseBase
import io.reactivex.Single
import javax.inject.Inject

class ArticleApiService {
    @Inject
    lateinit var api:ArticleApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getApiForAllArticles(key:String): Single<ResponseBase> {
        return api.getAllArticles(key)
    }

}