package com.twx.clock.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.twx.clock.notification.NotificationFactory
import com.twx.clock.service.MusicService
import com.twx.clock.util.Constants
import com.twx.module_base.util.LogUtils

class ChimeBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when(intent.action ){
            Constants.ACTION_CUSTOM_VIEW_OPTIONS_CANCEL->{
                LogUtils.i("通知的广播----------ChimeBroadcastReceiver------->")

                NotificationFactory.getInstance().mNotificationManager.cancel(Constants.SERVICE_ID_TELL_TIME)
                NotificationFactory.getInstance().mNotificationManager.cancel(Constants.SERVICE_ID_CLOCK)
                context.stopService(Intent(context,MusicService::class.java))
            }
        }
    }
}