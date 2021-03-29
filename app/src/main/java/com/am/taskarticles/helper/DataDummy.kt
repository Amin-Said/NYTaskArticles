package com.am.taskarticles.helper

import com.am.taskarticles.model.Article
import com.am.taskarticles.model.Media
import com.am.taskarticles.model.MediaMetadata
import com.am.taskarticles.model.ResponseBase

object DataDummy {
    fun generateDummyDataResponse(): ResponseBase {

        val mediaMetadata = MediaMetadata("format",100,"url image",100)
        val mediaMetadataList = listOf(mediaMetadata,mediaMetadata,mediaMetadata)
        val media = Media(1,"caption","NY Times",mediaMetadataList,"sub","type")
        val mediaList = listOf(media)
        val article = Article("details","adx",1
            ,"by author","col",null,null
            ,null,1,mediaList,"section"
            ,null,null,"29-03-2021"
            ,"sport","NY Times","football"
            ,"Mo Salah is the best player","news","now","uri","url")
        val articleList = listOf(article,article,article)

        return  ResponseBase("ok","NY Times",1,articleList)

    }

    fun generateResponseWithEmptyResult(): ResponseBase{
        return ResponseBase("ok", "", 1, emptyList())
    }
}