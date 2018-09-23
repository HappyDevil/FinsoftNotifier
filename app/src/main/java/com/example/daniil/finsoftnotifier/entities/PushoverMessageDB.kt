package com.example.daniil.finsoftnotifier.entities

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import com.example.daniil.finsoftnotifier.dbConverters.Converters
import com.google.gson.annotations.Expose
import java.util.*

@Entity(tableName = "pushoverMessage")
@TypeConverters(Converters::class)
data class PushoverMessageDB(@Embedded var apiData: PushoverMessage,
                             @PrimaryKey(autoGenerate = true) var id: Long? = null,
                             var createDate: Date = Date()){

    constructor(token: String,
                user: String,
                message: String,
                attachment: String? = null,
                device: String? = null,
                title: String? = null,
                url: String? = null,
                url_title: String? = null,
                priority: Int? = null,
                sound: String? = null,
                sendingTimeStamp: Long? = null,
                id : Long? = null,
                createDate: Date = Date()) : this(PushoverMessage(token, user, message, attachment, device, title, url, url_title, priority, sound, sendingTimeStamp),id,createDate)
}