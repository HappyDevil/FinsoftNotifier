package com.example.daniil.finsoftnotifier.constants

import com.example.daniil.finsoftnotifier.helpers.toMonthNumber
import com.example.daniil.finsoftnotifier.helpers.toYearNumberString
import java.util.*

val DEFAULT_YEAR = Date().toYearNumberString()
val DEFAULT_MONTH  = Date().toMonthNumber()
const val DEFAULT_MONTH_NAME = "Январь"


val MONTH_CONVERT = mapOf(1 to "Январь",
        2 to "Февраль",
        3 to "Март",
        4 to "Апрель",
        5 to "Май",
        6 to "Июнь",
        7 to "Июль",
        8 to "Август",
        9 to "Сентябрь",
        10 to "Октябрь",
        11 to "Ноябрь",
        12 to "Декабрь")



