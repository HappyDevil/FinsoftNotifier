package com.example.daniil.finsoftnotifier.api

import com.example.daniil.finsoftnotifier.entities.PushoverMessage
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST



interface PushoverApiService {

    @POST("/1/messages.json")
    fun sendMessage(@Body pushoverMessage: PushoverMessage): Call<JsonObject>
}