package com.example.tsuandroid.retrofit

import com.example.tsuandroid.BuildConfig
import com.example.tsuandroid.retrofit.models.ElementFromApi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import com.google.gson.GsonBuilder

interface ApiElement {

    @GET(BuildConfig.API_URL)
    fun getElements(): Deferred<List<ElementFromApi>>

    companion object {
        operator fun invoke(): ApiElement {
            val gson = GsonBuilder()
                .create()

            return Retrofit.Builder()
               // .client(okHttpClient)
                .baseUrl("https://timursamigulin.github.io/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiElement::class.java)
        }
    }
}