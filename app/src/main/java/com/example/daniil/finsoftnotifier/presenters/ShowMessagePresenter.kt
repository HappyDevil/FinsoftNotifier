package com.example.daniil.finsoftnotifier.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.daniil.finsoftnotifier.models.PushoverMessageModel
import com.example.daniil.finsoftnotifier.providers.NotifierMessagesProvider
import com.example.daniil.finsoftnotifier.views.ShowMessageView

@InjectViewState
class ShowMessagePresenter(id: Long?) : MvpPresenter<ShowMessageView>() {
    private val messagesProvider = NotifierMessagesProvider.instance

    private val pushoverMessageModel: PushoverMessageModel

    init {
        val messageDBwithID = messagesProvider.findById(id)
        pushoverMessageModel = PushoverMessageModel(messageDBwithID ?: messagesProvider.getDefaultMessage())
        viewState.injectData(pushoverMessageModel)
    }
}