package com.example.daniil.finsoftnotifier.activities

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.daniil.finsoftnotifier.R
import com.example.daniil.finsoftnotifier.databinding.ActivitySendingBinding
import com.example.daniil.finsoftnotifier.helpers.*
import com.example.daniil.finsoftnotifier.models.PushoverMessageModel
import com.example.daniil.finsoftnotifier.presenters.SendingPresenter
import com.example.daniil.finsoftnotifier.views.SendingView
import java.util.*
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import com.example.daniil.finsoftnotifier.constants.*


class SendingActivity : MvpAppCompatActivity(), SendingView {
    @InjectPresenter
    lateinit var sendingPresenter: SendingPresenter

    lateinit var sendingBinding: ActivitySendingBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sendingBinding = DataBindingUtil.setContentView(this, R.layout.activity_sending);
        supportActionBar?.hide()


        sendingBinding.btnSendNotif.setOnClickListener {
            sendingPresenter.sendNotif()
        }

        sendingBinding.qrCodeScan.setOnClickListener {
            ActivityCompat.requestPermissions(this@SendingActivity, arrayOf(Manifest.permission.CAMERA), CAMERA_PIC_REQUEST)
        }


        sendingBinding.sendNowCheck.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) {
                val onDismissListener = DialogInterface.OnDismissListener {
                    sendingBinding.sendNowCheck.isChecked = true
                }
                val curDate = Date()
                val dateListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    val onTimeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                        val calendar = Calendar.getInstance()
                        calendar.set(year, monthOfYear, dayOfMonth, hourOfDay, minute)
                        sendingPresenter.setSendDate(calendar.time)
                        sendingBinding.invalidateAll()
                        sendingBinding.sendInputLayout.visibility = View.VISIBLE
                    }

                    val timePickerDialog = TimePickerDialog(
                            this@SendingActivity, onTimeSetListener, curDate.toHourNumber(), curDate.toMinuteNumber(), true)
                    timePickerDialog.setOnDismissListener(onDismissListener)
                    timePickerDialog.show()
                }
                val datePickerDialog = DatePickerDialog(
                        this@SendingActivity, dateListener, curDate.toYearNumber(), curDate.toMonthNumber() - 1, curDate.toDayNumber())
                datePickerDialog.setOnDismissListener(onDismissListener)
                datePickerDialog.show()

            } else {
                sendingBinding.sendInputLayout.visibility = View.GONE
                sendingPresenter.setSendDate(null)
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data == null) {
            return
        }
        when(requestCode){
            REQUEST_GET_QR_RES -> {
                val message = data.getStringExtra(MESSAGE)
                println(message)
                sendingPresenter.setMessage(message)
                sendingBinding.invalidateAll()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_PIC_REQUEST -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(this@SendingActivity, ScanActivity::class.java)
                startActivityForResult(intent,REQUEST_GET_QR_RES)
            } else {
                val s = "Permission Denied!"
                Toast.makeText(this@SendingActivity, s, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun required(text_field: String) {
        when (text_field) {
            USER_FIELD -> {
                sendingBinding.userID.error = "Required field!"
            }
            TEXT_FIELD -> {
                sendingBinding.textNotif.error = "Required field!"
            }
            APP_FIELD -> {
                sendingBinding.appID.error = "Required field!"
            }

        }
    }

    override fun injectData(pushoverMessageModel: PushoverMessageModel) {
        sendingBinding.pushoverMessageModel = pushoverMessageModel
    }

    override fun sendNotif() {
        sendingBinding.btnSendNotif.text = resources.getText(R.string.waitNotif)
        sendingBinding.btnSendNotif.background = ContextCompat.getDrawable(this, R.color.buttonWaitColor)
        sendingBinding.btnSendNotif.isEnabled = false
    }

    override fun finishSend() {
        sendingBinding.btnSendNotif.text = resources.getText(R.string.sendNotif)
        sendingBinding.btnSendNotif.background = ContextCompat.getDrawable(this, R.color.colorPrimary)
        sendingBinding.btnSendNotif.isEnabled = true

        val intent = Intent(this, StatsActivity::class.java)
        startActivity(intent)
    }
}
