package com.am.taskarticles.di

import com.am.taskarticles.api.ArticleApiService

class ApiModuleTest(private val mockService:ArticleApiService):ApiModule() {
    override fun provideArticleApiService(): ArticleApiService {
        return mockService
    }
}