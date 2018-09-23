package com.example.daniil.finsoftnotifier

import com.example.daniil.finsoftnotifier.dbs.AppDatabase
import android.arch.persistence.room.Room
import android.app.Application
import com.example.daniil.finsoftnotifier.api.PushoverApiService
import com.example.daniil.finsoftnotifier.providers.NotifierMessagesProvider
import com.example.daniil.finsoftnotifier.providers.PushoverApiProvider
import com.example.daniil.finsoftnotifier.providers.mockProviders.MockNotifierMessagesProvider
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val PUSHOVER_API = "https://api.pushover.net/"

class NotifierApp : Application() {

    private lateinit var database: AppDatabase
    private lateinit var retrofitPushover: Retrofit
    private lateinit var pushoverApiService: PushoverApiService

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, AppDatabase::class.java, "pushoverDB")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()

        val client = OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .build()

        retrofitPushover = Retrofit.Builder()
                .client(client)
                .baseUrl(PUSHOVER_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build()


        pushoverApiService = retrofitPushover.create(PushoverApiService::class.java)


//        NotifierMessagesProvider.instance.injectDbProvider(MockNotifierMessagesProvider.instance)
        NotifierMessagesProvider.instance.injectDbProvider(database.pushoverMessageDao())
        PushoverApiProvider.instance.injectApiProvider(pushoverApiService)
    }
}