package com.example.daniil.finsoftnotifier.models

import android.databinding.ObservableField
import com.example.daniil.finsoftnotifier.entities.PushoverMessageDB

class PushoverMessageModel {
    val messageModel : ObservableField<PushoverMessageDB> = ObservableField()

    constructor()

    constructor(messageModel: PushoverMessageDB){
        this.messageModel.set(messageModel)
    }
}