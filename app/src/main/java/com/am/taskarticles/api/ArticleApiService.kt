package com.am.taskarticles.api

import com.am.taskarticles.di.DaggerApiComponent
import com.am.taskarticles.model.ResponseBase
import javax.inject.Inject

class ArticleApiService {
    @Inject
    lateinit var api:ArticleApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    suspend fun getApiForAllArticles(key:String): ResponseBase {
        return api.getAllArticles(key)
    }

}