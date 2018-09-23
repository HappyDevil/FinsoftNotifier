package com.example.daniil.finsoftnotifier.views

import android.content.Intent
import android.support.v7.widget.RecyclerView
import com.example.daniil.finsoftnotifier.activities.MessagesHistoryActivity
import com.example.daniil.finsoftnotifier.activities.ShowMessageActivity
import com.example.daniil.finsoftnotifier.constants.ID_MESSAGE
import com.example.daniil.finsoftnotifier.databinding.NotificationItemBinding
import com.example.daniil.finsoftnotifier.models.PushoverMessageModel


class PushoverMessageHolder(private var binding: NotificationItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(pushoverMessageModel: PushoverMessageModel) {
        binding.pushoverMessageModel = pushoverMessageModel
        binding.executePendingBindings()

        binding.root.setOnClickListener {
            val context = binding.root.context
            val intent = Intent(context, ShowMessageActivity::class.java)
            intent.putExtra(ID_MESSAGE,pushoverMessageModel.messageModel.get()?.id)
            context.startActivity(intent)
        }

    }

}