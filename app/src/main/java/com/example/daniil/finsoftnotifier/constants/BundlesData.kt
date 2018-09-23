package com.example.daniil.finsoftnotifier.constants


const val DATA_TYPE = "dataType"
const val YEAR_MONTH_DATA = "yearMonthData"
const val MESSAGE = "message"
const val ID_MESSAGE = "id_message"


enum class NotificationMessageType(val value: Int) {
    SEND(0),
    SCHEDULED(1)
}