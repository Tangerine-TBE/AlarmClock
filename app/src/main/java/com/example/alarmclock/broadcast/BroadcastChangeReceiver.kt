package com.example.alarmclock.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BroadcastChangeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        mOnReceiverListener?.onBroadcastReceiverAction(intent)
    }

    interface OnReceiverListener{
        fun onBroadcastReceiverAction(intent:Intent)
    }
    private var mOnReceiverListener:OnReceiverListener?=null
    fun setListener(listener:OnReceiverListener){
        mOnReceiverListener=listener
    }




}