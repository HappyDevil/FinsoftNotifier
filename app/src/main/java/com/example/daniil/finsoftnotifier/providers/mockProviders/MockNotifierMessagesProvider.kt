package com.example.daniil.finsoftnotifier.providers.mockProviders

import com.example.daniil.finsoftnotifier.daos.PushoverMessageDao
import com.example.daniil.finsoftnotifier.entities.PushoverMessage
import com.example.daniil.finsoftnotifier.entities.PushoverMessageDB
import com.example.daniil.finsoftnotifier.helpers.toUnixTimeStamp
import java.util.*

class MockNotifierMessagesProvider private constructor() : PushoverMessageDao {

    private var mockListOfMessages : MutableList<PushoverMessageDB> = mutableListOf()
    private var tempID = 99L


    override fun getById(id: Long): PushoverMessageDB {
        println("get: $id")
        return mockListOfMessages.filter { pushoverMessageDB -> pushoverMessageDB.id == id }[0]
    }

    override fun insert(pushoverMessage: PushoverMessageDB) : Long {
        tempID++
        println("insert $pushoverMessage")
        return tempID
    }

    override fun update(pushoverMessage: PushoverMessageDB) {
        println("update $pushoverMessage")
    }

    override fun delete(pushoverMessage: PushoverMessageDB) {
        println("delete $pushoverMessage")

    }

    companion object {
        val instance: PushoverMessageDao by lazy {
            MockNotifierMessagesProvider()
        }
    }


    override fun getAll(): List<PushoverMessageDB> {
        mockListOfMessages.clear()
        mockListOfMessages.add(PushoverMessageDB(apiData = PushoverMessage(token = "VK",user = "someUser1",message = "Hello world!",sendingTimeStamp = 1483228800),id = 1L))  // 01/2017
        mockListOfMessages.add(PushoverMessageDB(apiData = PushoverMessage(token = "Instagram",user = "someUser2",message = "Hello world!",sendingTimeStamp = 1488722400),id = 2L) ) // 03/2017
        mockListOfMessages.add(PushoverMessageDB(apiData = PushoverMessage(token = "Twitter",user = "someUser3",message = "Hello world!",sendingTimeStamp = 1497265200),id = 3L))  // 06/2017
        mockListOfMessages.add(PushoverMessageDB(apiData = PushoverMessage(token = "Habr",user = "someUser4",message = "Hello world!",sendingTimeStamp = 1525172400),id = 4L) ) // 05/2018
        mockListOfMessages.add(PushoverMessageDB(apiData = PushoverMessage(token = "Skype",user = "someUser5",message = "Hello world!",sendingTimeStamp = Date().toUnixTimeStamp()),id = 5L) ) // curDay

        mockListOfMessages.add(PushoverMessageDB(apiData = PushoverMessage(token = "Instagram",user = "someUser6",message = "Hello world!",sendingTimeStamp = Date().toUnixTimeStamp()),id = 6L) ) // curDay
        mockListOfMessages.add(PushoverMessageDB(apiData = PushoverMessage(token = "Habr",user = "someUser7",message = "Hello world!",sendingTimeStamp = Date().toUnixTimeStamp()),id = 7L) ) // curDay
        mockListOfMessages.add(PushoverMessageDB(apiData = PushoverMessage(token = "Skype",user = "someUser8",message = "Hello world!",sendingTimeStamp = Date().toUnixTimeStamp()),id = 8L) ) // curDay
        mockListOfMessages.add(PushoverMessageDB(apiData = PushoverMessage(token = "Instagram",user = "someUser9",message = "Hello world!",sendingTimeStamp = Date().toUnixTimeStamp() + 500),id = 9L) ) // curDay
        mockListOfMessages.add(PushoverMessageDB(apiData = PushoverMessage(token = "VK",user = "someUser10",message = "Hello world!",sendingTimeStamp = Date().toUnixTimeStamp() + 5000),id = 10L)) // curDay
        mockListOfMessages.add(PushoverMessageDB(apiData = PushoverMessage(token = "Instagram",user = "someUser11",message = "Hello world!",sendingTimeStamp = Date().toUnixTimeStamp() + 50000),id = 11L) ) // curDay
        return mockListOfMessages
    }

}
