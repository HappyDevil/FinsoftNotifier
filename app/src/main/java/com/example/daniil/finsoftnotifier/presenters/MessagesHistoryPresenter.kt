package com.example.daniil.finsoftnotifier.presenters

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.daniil.finsoftnotifier.constants.NotificationMessageType
import com.example.daniil.finsoftnotifier.entities.YearMonth
import com.example.daniil.finsoftnotifier.entities.YearMonthData
import com.example.daniil.finsoftnotifier.models.PushoverMessageModel
import com.example.daniil.finsoftnotifier.providers.NotifierMessagesProvider
import com.example.daniil.finsoftnotifier.views.MessagesHistoryView


@InjectViewState
class MessagesHistoryPresenter(yearMonth: YearMonth, dataType: Int) : MvpPresenter<MessagesHistoryView>() {

    private val TAG : String = this.javaClass.name

    private val messagesProvider = NotifierMessagesProvider.instance
    private val curDataModel : List<PushoverMessageModel>

    init{
        val yearMonthData = messagesProvider.statsDate[yearMonth] ?: YearMonthData()

        curDataModel = when(dataType){
            NotificationMessageType.SCHEDULED.value -> {
                yearMonthData.scheduleNotifications.map { PushoverMessageModel(it) }
            }
            NotificationMessageType.SEND.value -> {
                yearMonthData.sendNotifications.map { PushoverMessageModel(it) }
            }
            else ->{
                Log.e(TAG,"DataType error")
                listOf()
            }
        }

        viewState.injectData(curDataModel)
    }


}