package by.govoronok.myapplication.vk

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface VkService {
    @GET("method/friends.get?")
    fun getVkFriendsData(@Query("fields") fields: String,
                              @Query("access_token") accessToken: String,
                              @Query("v") v: String ): Call<VkResponse>//version
}