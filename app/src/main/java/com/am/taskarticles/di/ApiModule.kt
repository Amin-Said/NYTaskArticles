package com.am.taskarticles.di

import com.am.taskarticles.api.ArticleApi
import com.am.taskarticles.api.ArticleApiService
import com.am.taskarticles.helper.ConfigHelper
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
open class ApiModule {

    @Provides
    fun provideArticleApi():ArticleApi{
        return Retrofit.Builder().baseUrl(ConfigHelper.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(ArticleApi::class.java)

    }

    @Provides
    open fun provideArticleApiService():ArticleApiService{
        return ArticleApiService()
    }
}