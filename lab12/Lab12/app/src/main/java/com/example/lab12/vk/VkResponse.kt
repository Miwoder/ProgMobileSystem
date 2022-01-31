package com.example.lab12.vk

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

class VkResponse {
    @SerializedName("response")
    var response: Response? = null
}

class Response{
    @SerializedName("count")
    var count: Int? = null
    @SerializedName("items")
    var items = ArrayList<Item>()
}

class Item{
    @SerializedName("first_name")
    var firstName: String? = null
    @SerializedName("last_name")
    var lastName: String? = null
    @SerializedName("id")
    var id: Int? = null
    @SerializedName("online")
    var online: Int? = null
    @SerializedName("track_code")
    var trackCode: String? = null
}