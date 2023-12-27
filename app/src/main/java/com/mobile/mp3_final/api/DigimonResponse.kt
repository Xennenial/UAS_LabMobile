package com.mobile.mp3_final.api

data class Response(
    val response: List<ResponseItem>
)

data class ResponseItem(
    val img: String,
    val level: String,
    val name: String
)