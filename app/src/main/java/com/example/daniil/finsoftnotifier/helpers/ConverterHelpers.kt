package com.example.daniil.finsoftnotifier.helpers

import android.text.format.DateFormat
import com.example.daniil.finsoftnotifier.constants.DEFAULT_MONTH_NAME
import com.example.daniil.finsoftnotifier.constants.MONTH_CONVERT
import com.example.daniil.finsoftnotifier.entities.YearMonth
import java.util.*


fun Long.unixStampToDate() : Date  = Date(this * 1000)


fun Date?.toMonthNumber() : Int  =  (DateFormat.format("MM", this) as String).toInt()
fun Date?.toDayNumber() : Int  =  (DateFormat.format("dd", this) as String).toInt()
fun Date?.toHourNumber() : Int  =  (DateFormat.format("HH", this) as String).toInt()
fun Date?.toMinuteNumber() : Int  =  (DateFormat.format("mm", this) as String).toInt()
fun Date?.toYearNumberString() : String  =  (DateFormat.format("yyyy", this) as String)
fun Date?.toYearNumber() : Int  =  toYearNumberString().toInt()

fun Date?.toYearMonth() : YearMonth  {
    val monthNumber = this.toMonthNumber()
    val yearNumber = this.toYearNumberString()
    return YearMonth(yearNumber, monthNumber)
}


fun Date.toUnixTimeStamp() : Long = time / 1000



fun getMonthName(i : Int) : String {
    return MONTH_CONVERT[i] ?: DEFAULT_MONTH_NAME
}