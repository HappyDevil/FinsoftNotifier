package com.example.daniil.finsoftnotifier.activities

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.daniil.finsoftnotifier.R
import com.example.daniil.finsoftnotifier.constants.*
import com.example.daniil.finsoftnotifier.databinding.ActivityShowMessageBinding
import com.example.daniil.finsoftnotifier.models.PushoverMessageModel
import com.example.daniil.finsoftnotifier.presenters.ShowMessagePresenter
import com.example.daniil.finsoftnotifier.views.ShowMessageView


class ShowMessageActivity : MvpAppCompatActivity(), ShowMessageView {
    @InjectPresenter
    lateinit var showMessagePresenter: ShowMessagePresenter

    lateinit var sendingBinding: ActivityShowMessageBinding


    @ProvidePresenter
    fun provideDetailsPresenter(): ShowMessagePresenter {
        val idMessage = intent?.extras?.getLong(ID_MESSAGE)
        return ShowMessagePresenter(idMessage)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sendingBinding = DataBindingUtil.setContentView(this, R.layout.activity_show_message)
        supportActionBar?.hide()

    }

    override fun injectData(pushoverMessageModel: PushoverMessageModel) {
        sendingBinding.pushoverMessageModel = pushoverMessageModel
    }

}
