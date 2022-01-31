package com.example.lab12

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab12.vk.Item
import com.example.lab12.vk.VkResponse
import com.example.lab12.vk.VkService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class VkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vk)
        var recyclerView: RecyclerView? = findViewById(R.id.list)
        var errorTextView: TextView? = findViewById(R.id.error)
        var amountTextView: TextView? = findViewById(R.id.friendsNumber)
        var friends = ArrayList<Item>()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(VkService::class.java)
        val call = service.getVkFriendsData(fields, accessToken, version)
        call.enqueue(object : Callback<VkResponse> {//делаем запрос
            override fun onResponse(call: Call<VkResponse>, response: Response<VkResponse>) {
                if (response.isSuccessful) {
                    val vkResponse = response.body()!!
                    for (item in vkResponse.response!!.items) {
                        friends.add(item)
                    }
                    var adapter = CustomAdapter(baseContext, friends)
                    recyclerView!!.adapter = adapter
                    amountTextView!!.text = "Total number of friends: " + vkResponse.response!!.count
                }
            }

            override fun onFailure(call: Call<VkResponse>, t: Throwable) {
                errorTextView!!.text = t.message
            }
        })

    }

    companion object {
        var baseUrl = "https://api.vk.com/"
        var accessToken =
            ""
        var fields = "online"
        var version = "5.81"
    }
}