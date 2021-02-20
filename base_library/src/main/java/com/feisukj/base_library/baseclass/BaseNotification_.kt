//package com.feisukj.base_library.baseclass
//
//import android.app.Notification
//import android.app.NotificationChannel
//import android.app.NotificationManager
//import android.content.Context
//import android.os.Build
//import android.widget.RemoteViews
//import androidx.annotation.RequiresApi
//import com.feisukj.base_library.R
//import com.feisukj.base_library.utils.BaseConstant
//
//class BaseNotification_(val context: Context) {
//    private val notificationManager by lazy { BaseConstant.application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    fun createChannel(){
//        val channel=NotificationChannel("123","123",NotificationManager.IMPORTANCE_DEFAULT)
//        channel.enableVibration(true)
//        channel.vibrationPattern= longArrayOf(100L)
//
//        notificationManager.createNotificationChannel(channel)
//    }
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    fun a(){
//        val build=Notification.Builder(context,"123")
////        build.setCustomContentView(contentView)
//        build.setSmallIcon(R.mipmap.ic_launcher)
//            .setContentText("我是你爸爸")
//            .setContentTitle("提示")
//        notificationManager.notify(0,build.build())
//    }
//}