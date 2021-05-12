package com.twx.clock.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.twx.clock.R
import com.twx.clock.bean.NotificationBean
import com.twx.clock.broadcast.ChimeBroadcastReceiver
import com.twx.clock.ui.activity.MainViewActivity
import com.twx.clock.util.Constants
import com.twx.module_base.base.BaseApplication

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.notification
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/19 9:51
 * @class describe
 */
object NotificationFactory {

    lateinit var mNotificationManager: NotificationManager
    private lateinit var mNotification: Notification
    fun getInstance(): NotificationFactory {
        mNotificationManager = BaseApplication.getContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        return this
    }

    fun createNotificationChannel(id: String, name: String): NotificationFactory {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                    NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH)
            mNotificationManager.createNotificationChannel(notificationChannel)
        }
        return this
    }


    fun foregroundNotification(content: NotificationBean): Notification {
        content?.let {
            val intent = Intent(BaseApplication.getContext(), MainViewActivity::class.java)
            val activityPending = PendingIntent.getActivity(BaseApplication.getContext(), 0, intent, 0)
            mNotification = NotificationCompat.Builder(BaseApplication.getContext(), content.id)
                    .setContentTitle(content.title)
                    .setContentText(content.content)
                    .setSmallIcon(content.logo)
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setContentIntent(activityPending)
                    .setLargeIcon(BitmapFactory.decodeResource(BaseApplication.getContext().resources, content.logo)).build()
        }
        return mNotification
    }


    fun diyNotification(content: NotificationBean): Notification {
        content?.let {
            val apply = Intent(BaseApplication.getContext(), ChimeBroadcastReceiver::class.java).apply {
                action = Constants.ACTION_CUSTOM_VIEW_OPTIONS_CANCEL
            }
            val remoteViews = RemoteViews(BaseApplication.getContext().packageName, R.layout.notification_tell_time_container)
            remoteViews.setTextViewText(R.id.tv_notification_title, it.title)
            remoteViews.setTextViewText(R.id.tv_notification_content, it.content)
            remoteViews.setOnClickPendingIntent(R.id.tv_notification_close, PendingIntent.getBroadcast(BaseApplication.getContext(), 0, apply, PendingIntent.FLAG_UPDATE_CURRENT))
            mNotification = NotificationCompat.Builder(BaseApplication.getContext(), it.id)
                    .setSmallIcon(it.logo)
                    .setOngoing(true)
                    .setCustomContentView(remoteViews)
                    .build()
        }
        return mNotification
    }


    fun normalNotification(content: NotificationBean):Notification{
        content?.let {
            val broadcastPending = Intent(BaseApplication.getContext(), ChimeBroadcastReceiver::class.java).apply {
                action = Constants.ACTION_CUSTOM_VIEW_OPTIONS_CANCEL
            }
            mNotification = NotificationCompat.Builder(BaseApplication.getContext(), content.id)
                .setContentTitle(content.title)
                .setContentText(content.content)
                .setSmallIcon(content.logo)
                .setOngoing(content.diss)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(PendingIntent.getBroadcast(BaseApplication.getContext(), 0, broadcastPending, PendingIntent.FLAG_UPDATE_CURRENT))
                .setLargeIcon(BitmapFactory.decodeResource(BaseApplication.getContext().resources, content.logo)).build()
        }
        return mNotification
    }


}