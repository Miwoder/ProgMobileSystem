package com.example.lab12.weather

import retrofit2.Call;
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("data/2.5/weather?")
    fun getCurrentWeatherData(@Query("q") q: String,//город
                              @Query("appid") app_id: String): Call<WeatherResponse>
}