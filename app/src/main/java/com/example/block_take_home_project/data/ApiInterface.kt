package com.example.block_take_home_project.data

import com.example.block_take_home_project.model.Employees
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

// An interface that utilizes Moshi and Retrofit for basic network calls
interface ApiInterface {

    @GET("employees.json")
    fun getEmployees() : Call<Employees>

    @GET("employees_malformed.json")
    fun getMalformedEmployees(): Call<Employees>

    @GET("employees_empty.json")
    fun getEmptyEmployees(): Call<Employees>

    companion object {
        val BASE_URL = "https://s3.amazonaws.com/sq-mobile-interview/"

        fun create(): ApiInterface {
            val moshi = Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}