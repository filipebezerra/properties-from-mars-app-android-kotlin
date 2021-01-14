package dev.filipebezerra.app.propertiesfrommars.datasource.remote

import retrofit2.Call
import retrofit2.http.GET

const val MARS_BASE_API_URL = "https://mars.udacity.com/"

interface MarsApiService {
    @GET("realestate") suspend fun getProperties(): List<MarsPropertyNetwork>
}