package by.govoronok.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.govoronok.myapplication.vk.Item
import by.govoronok.myapplication.vk.VkResponse
import by.govoronok.myapplication.vk.VkService
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
            "a5a72d078d33225d27762cfc6555ab61c12c28d143d76a6d84b174a550311e5399668e43c897eb1f06215"
        var fields = "online"
        var version = "5.81"
    }
}