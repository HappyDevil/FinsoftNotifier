package com.example.daniil.finsoftnotifier.adapters

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.daniil.finsoftnotifier.R
import com.example.daniil.finsoftnotifier.databinding.NotificationItemBinding
import com.example.daniil.finsoftnotifier.helpers.convertDateToString
import com.example.daniil.finsoftnotifier.helpers.unixStampToDate
import com.example.daniil.finsoftnotifier.models.PushoverMessageModel
import com.example.daniil.finsoftnotifier.views.PushoverMessageHolder


class PushoverMessageAdapter : RecyclerView.Adapter<PushoverMessageHolder>() {

    private val items : MutableList<PushoverMessageModel> = mutableListOf()
    private val tempItems : MutableList<PushoverMessageModel> = mutableListOf()

    fun setData(data: List<PushoverMessageModel>) {
        items.clear()
        items.addAll(data)
        filter("")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PushoverMessageHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding : NotificationItemBinding = DataBindingUtil.inflate(inflater, R.layout.notification_item, parent, false)
        return PushoverMessageHolder(binding)
    }

    override fun onBindViewHolder(holder: PushoverMessageHolder, position: Int) {
        holder.bind(tempItems[position])
    }

    override fun getItemCount(): Int {
        return tempItems.size
    }

    fun filter(query: String) {
        tempItems.clear()
        items.forEach {
            val pushoverMessageDB = it.messageModel.get()

            if(pushoverMessageDB!=null){
                val message = pushoverMessageDB.apiData.message
                val token = pushoverMessageDB.apiData.token
                val user = pushoverMessageDB.apiData.user
                val sendingTimeStamp = pushoverMessageDB.apiData.sendingTimeStamp
                val createDate = pushoverMessageDB.createDate

                if(message.contains(query,ignoreCase = true) ||
                        token.contains(query,ignoreCase = true) ||
                        user.contains(query,ignoreCase = true) ||
                        (sendingTimeStamp!=null && convertDateToString(sendingTimeStamp.unixStampToDate()).contains(query,ignoreCase = true)) ||
                        convertDateToString(createDate).contains(query,ignoreCase = true)){
                    tempItems.add(it)
                }
            }
        }
        notifyDataSetChanged()
    }
}