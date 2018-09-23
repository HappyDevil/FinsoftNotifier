package com.example.daniil.finsoftnotifier.views

import com.arellomobile.mvp.MvpView
import com.example.daniil.finsoftnotifier.models.PushoverMessageModel

interface MessagesHistoryView : MvpView {
    fun injectData(messagesHistoryModel: List<PushoverMessageModel>)
}