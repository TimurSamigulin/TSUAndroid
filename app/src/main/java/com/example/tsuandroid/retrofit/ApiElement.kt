package com.example.tsuandroid.retrofit

import com.example.tsuandroid.retrofit.models.DriveElement
import com.example.tsuandroid.retrofit.models.ElementFromApi
import com.example.tsuandroid.retrofit.models.Message
import com.example.tsuandroid.room.entity.Element
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query
import com.google.gson.GsonBuilder
import com.google.gson.Gson
import retrofit2.http.Headers


//"https://drive.google.com/file/d/1MgiNj_3gZW1cRgmB8owbZRKzo3QXpOaO/view?usp=sharing"

interface ApiElement {

    @Headers("Content-Type: application/json")
    @GET("https://drive.google.com/file/d/1MgiNj_3gZW1cRgmB8owbZRKzo3QXpOaO/view?usp=sharing")
    fun getElementsFromDrive(): Deferred<DriveElement>

    @GET("octocat")
    fun getElementsFromGit(): Deferred<String>

    @GET("el.json")
    fun getElements(): Deferred<List<ElementFromApi>>

    companion object {
        operator fun invoke(): ApiElement {
            /*val requestInterceptor = Interceptor {chain ->

                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("usp", "sharing")
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build()*/

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