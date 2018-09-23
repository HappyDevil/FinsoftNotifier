package com.example.daniil.finsoftnotifier.dbConverters

import android.arch.persistence.room.TypeConverter
import com.example.daniil.finsoftnotifier.helpers.unixStampToDate
import java.util.*


class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.unixStampToDate()
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return (date?.time)
    }
}