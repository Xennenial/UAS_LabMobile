package com.mobile.mp3_final.api

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("api/digimon")
    fun getAgents(): Call<List<ResponseItem>>
}