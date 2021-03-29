package com.am.taskarticles.di

import com.am.taskarticles.api.ArticleApiService
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(service:ArticleApiService)
}