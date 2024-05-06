package com.example.twit.data.network.utils


import com.google.gson.annotations.SerializedName
import java.util.Date

data class ApiResponse<T>(
    @SerializedName("message")
    var messages: String = "",
    @SerializedName("error")
    var error: String = "",
    @SerializedName("Response")
    var response: T? = null,
    @SerializedName("Total")
    var total: Int? = null,
    @SerializedName("Code")
    var code: Int? = null,
    @SerializedName("Last")
    var last: Date? = null
)