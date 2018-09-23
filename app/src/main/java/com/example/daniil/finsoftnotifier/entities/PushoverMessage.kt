package com.example.daniil.finsoftnotifier.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PushoverMessage(var token : String,
                      var user : String,
                      var message : String,
                      var attachment : String? = null,
                      var device : String? = null,
                      var title  : String? = null,
                      var url : String? = null,
                      var url_title  : String? = null,
                      var priority : Int? = null,
                      var sound : String? = null,
                      @SerializedName("timestamp") var sendingTimeStamp : Long? = null)