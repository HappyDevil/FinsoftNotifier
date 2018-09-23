package com.example.daniil.finsoftnotifier.activities

import android.content.Intent
import android.databinding.DataBindingUtil
import android.opengl.Visibility
import android.os.Bundle
import android.view.MotionEvent
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.daniil.finsoftnotifier.R
import com.example.daniil.finsoftnotifier.databinding.ActivityStatsBinding
import com.example.daniil.finsoftnotifier.models.StatsModel
import com.example.daniil.finsoftnotifier.presenters.StatsPresenter
import com.example.daniil.finsoftnotifier.views.StatsView
import kotlin.math.abs
import android.support.v4.app.NotificationCompat.getExtras
import android.view.View


class StatsActivity : MvpAppCompatActivity(), StatsView {
    override fun startPosition() {
        statBinding.prevImage.visibility = View.INVISIBLE
        statBinding.nextImage.visibility = View.VISIBLE
    }

    override fun endPosition() {
        statBinding.nextImage.visibility = View.INVISIBLE
        statBinding.prevImage.visibility = View.VISIBLE
    }

    override fun simplePosition() {
        statBinding.nextImage.visibility = View.VISIBLE
        statBinding.prevImage.visibility = View.VISIBLE
    }

    override fun oneElementPosition() {
        statBinding.nextImage.visibility = View.INVISIBLE
        statBinding.prevImage.visibility = View.INVISIBLE
    }


    @InjectPresenter
    lateinit var statsPresenter: StatsPresenter
    private lateinit var statBinding: ActivityStatsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        statBinding = DataBindingUtil.setContentView(this, R.layout.activity_stats)
        supportActionBar?.hide()


        statBinding.fab.setOnClickListener{
            val intent = Intent(this, SendingActivity::class.java)
            startActivity(intent)
        }

        statBinding.nextImage.setOnClickListener {
            statsPresenter.nextYearMonth()
        }

        statBinding.prevImage.setOnClickListener {
            statsPresenter.prevYearMonth()
        }


        statBinding.btnScheduleMessages.setOnClickListener {
            val intent = Intent(this, MessagesHistoryActivity::class.java)
            statsPresenter.fullyScheduledIntent(intent)
            startActivity(intent)
        }

        statBinding.btnSendMessages.setOnClickListener {
            val intent = Intent(this, MessagesHistoryActivity::class.java)
            statsPresenter.fullySendIntent(intent)
            startActivity(intent)
        }
    }

    private var x1 : Float = 0F
    private var y1 : Float = 0F
    private var x2 : Float = 0F
    private var y2 : Float = 0F


    override fun onTouchEvent(event: MotionEvent): Boolean {

        val action: Int = event.actionMasked

        return when (action) {
            MotionEvent.ACTION_DOWN -> {
                x1 = event.x
                y1 = event.y
                true
            }
            MotionEvent.ACTION_MOVE -> {
                true
            }
            MotionEvent.ACTION_UP -> {
                x2 = event.x
                y2 = event.y

                val deltaY = abs(y2 - y1)
                val deltaX = abs(x2 - x1)
                val isXswap = deltaY < deltaX
                if (x1 < x2 && isXswap) { //left_to_right
                    statsPresenter.prevYearMonth()

                }
                if (x1 > x2 && isXswap) {
                    statsPresenter.nextYearMonth()
                }
                true
            }
            MotionEvent.ACTION_CANCEL -> {
                true
            }
            MotionEvent.ACTION_OUTSIDE -> {
                true
            }
            else -> super.onTouchEvent(event)
        }
    }

    override fun injectData(statsModel: StatsModel){
        statBinding.statsModel = statsModel
    }

}