package com.example.daniil.finsoftnotifier.views

import android.widget.Button
import android.widget.EditText
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.daniil.finsoftnotifier.models.PushoverMessageModel

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface SendingView : MvpView {
    @StateStrategyType(value = SkipStrategy::class)
    fun sendNotif()

    @StateStrategyType(value = SkipStrategy::class)
    fun finishSend()

    fun injectData(pushoverMessageModel: PushoverMessageModel)

    fun required(text_field: String)
}