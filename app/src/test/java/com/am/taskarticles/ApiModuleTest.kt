package com.am.taskarticles

import com.am.taskarticles.api.ArticleApiService
import com.am.taskarticles.di.ApiModule

class ApiModuleTest(private val mockService:ArticleApiService):ApiModule() {
    override fun provideArticleApiService(): ArticleApiService {
        return mockService
    }
}