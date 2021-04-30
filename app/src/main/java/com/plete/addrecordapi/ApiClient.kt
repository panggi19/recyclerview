package com.plete.addrecordapi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    var BaseURL: String = "http://zalia.id"
    var retrofit: Retrofit? = null

    fun getApiClient() : Retrofit? {
        if (retrofit == null){
            retrofit = Retrofit.Builder()
                .baseUrl(BaseURL)
                .addConverterFactory(GsonConverterFactory.create()).build()
        }
        return retrofit
    }
}