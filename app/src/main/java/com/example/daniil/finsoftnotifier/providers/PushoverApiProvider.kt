package com.example.daniil.finsoftnotifier.providers

import com.example.daniil.finsoftnotifier.api.PushoverApiService
import com.example.daniil.finsoftnotifier.entities.PushoverMessage
import com.google.gson.JsonObject
import retrofit2.Call

class PushoverApiProvider private constructor() {

    private lateinit var pushoverApiService : PushoverApiService


    fun injectApiProvider(pushoverApiService: PushoverApiService){
        this.pushoverApiService = pushoverApiService
    }

    fun sendMessage(pushoverMessage: PushoverMessage): Call<JsonObject> {
        return pushoverApiService.sendMessage(pushoverMessage)

    }

    companion object {
        val instance: PushoverApiProvider by lazy {
            PushoverApiProvider()
        }
    }



}