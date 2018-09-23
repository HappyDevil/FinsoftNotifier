package com.example.daniil.finsoftnotifier.entities


data class YearMonthData(var sendNotifications : List<PushoverMessageDB> = listOf(),
                         var scheduleNotifications : List<PushoverMessageDB> = listOf())


