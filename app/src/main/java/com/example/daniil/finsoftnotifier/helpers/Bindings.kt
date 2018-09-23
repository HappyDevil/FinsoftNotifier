package com.example.daniil.finsoftnotifier.helpers

import android.databinding.BindingConversion
import com.example.daniil.finsoftnotifier.entities.PushoverMessageDB
import android.databinding.BindingAdapter
import android.text.format.DateFormat
import android.widget.Button
import android.widget.TextView
import java.util.*


@BindingConversion
fun convertHobbiesToString(pushoverMessagesDB : List<PushoverMessageDB>): String {
    return pushoverMessagesDB.size.toString()
}

@BindingConversion
fun convertDateToString(date: Date): String {
    return DateFormat.format("dd.MM.yy HH:mm", date) as String
}

@BindingAdapter("app:send")
fun setSendedMessages(button: Button, value: String) {
    val s = "$value отправлено"
    button.text = s
}

@BindingAdapter("app:unixTimestamp")
fun setUnixTimeStamp(textView: TextView, value: Long) {
    textView.text = convertDateToString(value.unixStampToDate())
}

@BindingAdapter("app:scheduled")
fun setScheduledMessages(button: Button, value: String) {
    val s = "$value отложено"
    button.text = s
}