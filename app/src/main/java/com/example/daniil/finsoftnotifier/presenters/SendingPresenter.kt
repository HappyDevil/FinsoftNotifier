package com.example.daniil.finsoftnotifier.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.daniil.finsoftnotifier.NotifierApp
import com.example.daniil.finsoftnotifier.constants.APP_FIELD
import com.example.daniil.finsoftnotifier.constants.TEXT_FIELD
import com.example.daniil.finsoftnotifier.constants.USER_FIELD
import com.example.daniil.finsoftnotifier.helpers.toUnixTimeStamp
import com.example.daniil.finsoftnotifier.models.PushoverMessageModel
import com.example.daniil.finsoftnotifier.providers.NotifierMessagesProvider
import com.example.daniil.finsoftnotifier.providers.PushoverApiProvider
import com.example.daniil.finsoftnotifier.views.SendingView
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

@InjectViewState
class SendingPresenter : MvpPresenter<SendingView>() {
    private val messagesProvider = NotifierMessagesProvider.instance
    private val pushoverApiProvider = PushoverApiProvider.instance

    private val pushoverMessageModel: PushoverMessageModel by lazy {
        val modelLazy = PushoverMessageModel()
        modelLazy.messageModel.set(messagesProvider.getDefaultMessage())
        modelLazy
    }

    init {
        viewState.injectData(pushoverMessageModel)
    }

    fun sendNotif() {
        val pushoverMessage = pushoverMessageModel.messageModel.get()

        if (pushoverMessage != null) {
            with(pushoverMessage.apiData) {
                var isOk = true

                if (this.user.isEmpty()) {
                    isOk = false
                    viewState.required(USER_FIELD)
                }

                if (this.token.isEmpty()) {
                    isOk = false
                    viewState.required(APP_FIELD)
                }

                if (this.message.isEmpty()) {
                    isOk = false
                    viewState.required(TEXT_FIELD)
                }

                if (isOk) {
                    viewState.sendNotif()
                    val registerUser = pushoverApiProvider.sendMessage(this)
                    registerUser.enqueue(object : Callback<JsonObject> {
                        override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                            viewState.finishSend()
                        }

                        override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                            val curDate = Date()
                            if (pushoverMessage.apiData.sendingTimeStamp == null)
                                pushoverMessage.apiData.sendingTimeStamp = curDate.toUnixTimeStamp()
                            pushoverMessage.createDate = curDate
                            messagesProvider.insert(pushoverMessage)
                            viewState.finishSend()
                        }

                    })
                }
            }
        }
    }

    fun setSendDate(time: Date?) {
        pushoverMessageModel.messageModel.get()?.apiData?.sendingTimeStamp = time?.toUnixTimeStamp()
    }

    fun setMessage(message: String?) {
        pushoverMessageModel.messageModel.get()?.apiData?.message = message ?: ""
    }
}