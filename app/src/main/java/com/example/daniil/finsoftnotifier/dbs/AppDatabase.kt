package com.example.daniil.finsoftnotifier.dbs

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.daniil.finsoftnotifier.daos.PushoverMessageDao
import com.example.daniil.finsoftnotifier.entities.PushoverMessageDB


@Database(entities = arrayOf(PushoverMessageDB::class), version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pushoverMessageDao(): PushoverMessageDao
}