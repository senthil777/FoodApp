package com.lieferin_global.Receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.util.Log

class MessageReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val bundle = intent.extras
        try {
            if (bundle != null) {
                Log.i("OTP", "--$bundle")
                val pdusObj =
                    bundle["pdus"] as Array<Any>?
                if (pdusObj != null) {
                    for (i in pdusObj.indices) {
                        val currentMessage =
                            SmsMessage.createFromPdu(pdusObj[i] as ByteArray)
                        val phoneNumber =
                            currentMessage.displayOriginatingAddress
                        val message = currentMessage.displayMessageBody
                        Log.i(
                            "OTP",
                            phoneNumber + "" + phoneNumber + "" + message + "--" + message.length
                        )
                        try {
                            if (phoneNumber.contains("SMTCMP")) {
                                //OTPScreen.phoneNo.setText(message.substring(32, 38))
                            }
                        } catch (e: Exception) {
                        }
                    }
                }
            }
        } catch (e: Exception) {
        }
    }
}
