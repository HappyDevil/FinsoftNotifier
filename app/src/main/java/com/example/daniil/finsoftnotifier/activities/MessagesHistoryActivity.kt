package com.example.daniil.finsoftnotifier.activities

import android.databinding.DataBindingUtil
import android.opengl.Visibility
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.daniil.finsoftnotifier.R
import com.example.daniil.finsoftnotifier.databinding.ActivityMessagesHistoryBinding
import com.example.daniil.finsoftnotifier.presenters.MessagesHistoryPresenter
import com.example.daniil.finsoftnotifier.views.MessagesHistoryView
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.daniil.finsoftnotifier.adapters.PushoverMessageAdapter
import com.example.daniil.finsoftnotifier.constants.DATA_TYPE
import com.example.daniil.finsoftnotifier.constants.DEFAULT_MONTH
import com.example.daniil.finsoftnotifier.constants.DEFAULT_YEAR
import com.example.daniil.finsoftnotifier.constants.YEAR_MONTH_DATA
import com.example.daniil.finsoftnotifier.entities.YearMonth
import com.example.daniil.finsoftnotifier.models.PushoverMessageModel


class MessagesHistoryActivity : MvpAppCompatActivity(), MessagesHistoryView {

    @Suppress("unused")
    @InjectPresenter
    lateinit var messagesPresenter: MessagesHistoryPresenter

    private lateinit var mAdapter: PushoverMessageAdapter

    @ProvidePresenter
    fun provideDetailsPresenter(): MessagesHistoryPresenter {
        val dataType = intent?.extras?.getInt(DATA_TYPE) ?: 0
        val yearMonth = intent?.extras?.getParcelable(YEAR_MONTH_DATA) ?: YearMonth(DEFAULT_YEAR, DEFAULT_MONTH)

        return MessagesHistoryPresenter(yearMonth, dataType)
    }

    private lateinit var activityMessagesBinding: ActivityMessagesHistoryBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMessagesBinding = DataBindingUtil.setContentView(this, R.layout.activity_messages_history)

        supportActionBar?.hide()
        mAdapter = PushoverMessageAdapter()

        activityMessagesBinding.recyclerHistory.adapter = mAdapter
        activityMessagesBinding.recyclerHistory.layoutManager = LinearLayoutManager(applicationContext, OrientationHelper.VERTICAL, false)
        activityMessagesBinding.recyclerHistory.setHasFixedSize(true)

        activityMessagesBinding.txtMessagesSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                mAdapter.filter(s.toString())
            }

        })
    }

    override fun injectData(messagesHistoryModel: List<PushoverMessageModel>) {
        if (messagesHistoryModel.isNotEmpty()) {
            activityMessagesBinding.txtNoItems.visibility = View.GONE
            activityMessagesBinding.recyclerHistory.visibility = View.VISIBLE
            mAdapter.setData(messagesHistoryModel)
        }
        else{
            activityMessagesBinding.txtNoItems.visibility = View.VISIBLE
            activityMessagesBinding.recyclerHistory.visibility = View.GONE
        }
    }

}