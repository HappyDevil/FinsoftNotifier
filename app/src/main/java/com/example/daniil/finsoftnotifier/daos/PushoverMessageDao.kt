package com.example.daniil.finsoftnotifier.daos

import android.arch.persistence.room.*
import com.example.daniil.finsoftnotifier.entities.PushoverMessageDB


@Dao
interface PushoverMessageDao {
    @Query("SELECT * FROM pushoverMessage")
    fun getAll(): List<PushoverMessageDB>

    @Query("SELECT * FROM pushoverMessage WHERE id = :id")
    fun getById(id: Long): PushoverMessageDB

    @Insert
    fun insert(pushoverMessage: PushoverMessageDB) : Long

    @Update
    fun update(pushoverMessage: PushoverMessageDB)

    @Delete
    fun delete(pushoverMessage: PushoverMessageDB)

}