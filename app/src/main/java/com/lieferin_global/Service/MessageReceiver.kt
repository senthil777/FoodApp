package com.lieferin_global.Service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage

class MessageReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val bundle = intent.extras
        try {
            if (bundle != null) {
                val pdusObj =
                    bundle["pdus"] as Array<Any>?
                if (pdusObj != null) {
                    for (i in pdusObj.indices) {
                        val currentMessage =
                            SmsMessage.createFromPdu(pdusObj[i] as ByteArray)
                        val phoneNumber =
                            currentMessage.displayOriginatingAddress
                        val message = currentMessage.displayMessageBody
                        try {
                            /*if (phoneNumber.contains("SMTCMP")) {
                                OtpVerification.txt_pin_entry!!.setText(message.substring(37, 43))

                                OtpVerificationActivity.txt_pin_entry!!.setText(message.substring(37, 43))
                            }*/
                        } catch (e: Exception) {
                        }
                    }
                }
            }
        } catch (e: Exception) {
        }
    }
}