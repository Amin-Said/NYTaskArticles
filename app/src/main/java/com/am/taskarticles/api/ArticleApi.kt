package com.am.taskarticles.api

import com.am.taskarticles.helper.ConfigHelper.BASE_RETURN
import com.am.taskarticles.helper.ConfigHelper.KEY_PARAM
import com.am.taskarticles.model.ResponseBase
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleApi {
    @GET(BASE_RETURN)
    fun getAllArticles(@Query(KEY_PARAM) key:String?): Single<ResponseBase>

}