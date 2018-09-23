package com.example.daniil.finsoftnotifier.presenters

import android.content.Intent
import android.os.Bundle
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.daniil.finsoftnotifier.constants.*
import com.example.daniil.finsoftnotifier.entities.PushoverMessageDB
import com.example.daniil.finsoftnotifier.entities.YearMonth
import com.example.daniil.finsoftnotifier.entities.YearMonthData
import com.example.daniil.finsoftnotifier.helpers.*
import com.example.daniil.finsoftnotifier.models.StatsModel
import com.example.daniil.finsoftnotifier.providers.NotifierMessagesProvider
import com.example.daniil.finsoftnotifier.providers.mockProviders.MockNotifierMessagesProvider
import com.example.daniil.finsoftnotifier.views.StatsView
import java.util.*


@InjectViewState
class StatsPresenter : MvpPresenter<StatsView>() {


    private val statsModel: StatsModel by lazy {
        val modelLazy = StatsModel()
        modelLazy.statsDataValue.set(YearMonthData())
        modelLazy.statsDataKey.set(YearMonth(DEFAULT_YEAR, DEFAULT_MONTH))
        modelLazy
    }

    private val messagesProvider = NotifierMessagesProvider.instance
    private val statsDate: SortedMap<YearMonth, YearMonthData>
    private var curYearMonthPosition: Int = 0
    private lateinit var curYearMonth: YearMonth
    private lateinit var curYearMonthData: YearMonthData

    init {
        statsDate = messagesProvider.statsDate
        updateCurYearMonthPosition()
        updateModel()
        viewState.injectData(statsModel)
    }

    fun nextYearMonth() {
        if (curYearMonthPosition < statsDate.keys.size - 1) {
            curYearMonthPosition++
            updateModel()
        }
    }

    fun prevYearMonth() {
        if (curYearMonthPosition > 0) {
            curYearMonthPosition--
            updateModel()
        }
    }

    private fun updateModel() {
        if(statsDate.size>1)
            when (curYearMonthPosition) {
                0 -> viewState.startPosition()
                statsDate.size - 1 -> viewState.endPosition()
                else -> viewState.simplePosition()
            }
        else viewState.oneElementPosition()


        val iterator = statsDate.iterator()
        var curData: MutableMap.MutableEntry<YearMonth, YearMonthData>? = null
        for (i in 0..curYearMonthPosition) {
            if (iterator.hasNext()) {
                curData = iterator.next()
            }
        }
        curYearMonth = curData?.key ?: YearMonth(DEFAULT_YEAR, DEFAULT_MONTH)
        curYearMonthData = curData?.value ?: YearMonthData()



        statsModel.statsDataKey.set(curYearMonth)
        statsModel.statsDataValue.set(curYearMonthData)
    }

    private fun updateCurYearMonthPosition() {
        val curDate = Date()
        curYearMonthPosition = 0
        val curYearMonth = curDate.toYearMonth()
        if (statsDate.keys.contains(curYearMonth)) {
            val datesIterator = statsDate.keys.iterator()
            while(datesIterator.hasNext()){
                val nextYearMonth = datesIterator.next()
                if (nextYearMonth == curYearMonth)
                    return
                else curYearMonthPosition++
            }
        }
    }

    fun fullySendIntent(intent: Intent) {
        intent.putExtra(YEAR_MONTH_DATA,curYearMonth)
        intent.putExtra(DATA_TYPE, NotificationMessageType.SEND.value)
    }

    fun fullyScheduledIntent(intent: Intent) {
        intent.putExtra(YEAR_MONTH_DATA,curYearMonth)
        intent.putExtra(DATA_TYPE, NotificationMessageType.SCHEDULED.value)
    }
}