package com.example.daniil.finsoftnotifier.providers

import com.example.daniil.finsoftnotifier.daos.PushoverMessageDao
import com.example.daniil.finsoftnotifier.entities.PushoverMessageDB
import com.example.daniil.finsoftnotifier.entities.YearMonth
import com.example.daniil.finsoftnotifier.entities.YearMonthData
import com.example.daniil.finsoftnotifier.helpers.toUnixTimeStamp
import com.example.daniil.finsoftnotifier.helpers.toYearMonth
import com.example.daniil.finsoftnotifier.helpers.unixStampToDate
import java.util.*

class NotifierMessagesProvider private constructor() {

    private lateinit var dbProvider: PushoverMessageDao
    lateinit var statsDate: SortedMap<YearMonth, YearMonthData>
    private lateinit var allPushoverMessages: List<PushoverMessageDB>

    companion object {
        val instance: NotifierMessagesProvider by lazy {
            NotifierMessagesProvider()
        }
    }

    fun injectDbProvider(dbProvider: PushoverMessageDao) {
        this.dbProvider = dbProvider
        statsDate = sortedMapOf()
        allPushoverMessages = this.dbProvider.getAll()
        recalculateData()
    }

    private fun recalculateData() {
        val curDate = Date()
        val curUnixTimeStamp = curDate.toUnixTimeStamp()
        statsDate.clear()
        allPushoverMessages.forEach {
            val pushoverMessageDB = it
            val sendingTimeStamp = pushoverMessageDB.apiData.sendingTimeStamp

            if (sendingTimeStamp != null) {
                val toDate = sendingTimeStamp.unixStampToDate()
                val isSended = sendingTimeStamp <= curUnixTimeStamp

                val yearMonth = toDate.toYearMonth()

                val yearMonthData: YearMonthData?

                if (statsDate.containsKey(yearMonth)) {
                    yearMonthData = statsDate[yearMonth]
                } else {
                    yearMonthData = YearMonthData()
                    statsDate[yearMonth] = yearMonthData
                }

                yearMonthData?.apply {
                    if (isSended)
                        sendNotifications += pushoverMessageDB
                    else
                        scheduleNotifications += pushoverMessageDB
                }
            }
        }
    }

    fun insert(pushoverMessage: PushoverMessageDB) {
        val newID = dbProvider.insert(pushoverMessage)
        pushoverMessage.id = newID
        allPushoverMessages += pushoverMessage
        recalculateData()
    }

    fun getDefaultMessage(): PushoverMessageDB {
        return PushoverMessageDB(token = "aaaym6n214st7zwqmbjosghh44xhr8",
                user = "",
                message = "hhh")
    }

    fun findById(id: Long?): PushoverMessageDB? {
        return allPushoverMessages.first { it.id == id }
    }
}