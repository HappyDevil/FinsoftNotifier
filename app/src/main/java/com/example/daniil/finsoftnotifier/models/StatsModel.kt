package com.example.daniil.finsoftnotifier.models

import android.databinding.ObservableField
import com.example.daniil.finsoftnotifier.entities.YearMonth
import com.example.daniil.finsoftnotifier.entities.YearMonthData
import kotlin.reflect.KProperty

class StatsModel {
    val statsDataValue : ObservableField<YearMonthData> = ObservableField()
    val statsDataKey : ObservableField<YearMonth> = ObservableField()
}