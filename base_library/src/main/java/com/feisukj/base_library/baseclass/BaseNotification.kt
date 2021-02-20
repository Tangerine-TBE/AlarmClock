package com.feisukj.base_library.baseclass

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.feisukj.base_library.R

abstract class BaseNotification(val context: Context) {
    private val channelID = "default_id"
    private val channelName = channelID + "Default Channel"
    private val notificationManager: NotificationManager by lazy { context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val channel = NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT)
        //是否绕过请勿打扰模式
        channel.canBypassDnd()
        //闪光灯
        channel.enableLights(false)
        //锁屏显示通知
        channel.lockscreenVisibility = Notification.VISIBILITY_SECRET
        //闪关灯的灯光颜色
        channel.lightColor = Color.RED
        //桌面launcher的消息角标
        channel.canShowBadge()
        //是否允许震动
        channel.enableVibration(false)
        channel.vibrationPattern= longArrayOf(0L)
        //获取系统通知响铃声音的配置
        channel.audioAttributes
        //获取通知取到组
        channel.group
        //设置可绕过  请勿打扰模式
        channel.setBypassDnd(true)
        //是否会有灯光
        channel.shouldShowLights()
        notificationManager.createNotificationChannel(channel)
    }

    private fun getNotification(title: String, content: String): NotificationCompat.Builder {
        val builder: NotificationCompat.Builder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = NotificationCompat.Builder(context.applicationContext, channelID)
            builder.priority = NotificationManager.IMPORTANCE_DEFAULT
        } else {
            builder = NotificationCompat.Builder(context.applicationContext)
            builder.priority = Notification.PRIORITY_DEFAULT
        }
//        builder.setVibrate(longArrayOf(0L))
        //标题
        builder.setContentTitle(title)
        //文本内容
        builder.setContentText(content)
        //小图标
        builder.setSmallIcon(R.mipmap.ic_launcher)
        //设置点击信息后自动清除通知
        builder.setAutoCancel(false)

        builder.setContentIntent(getPendingIntent())
        builder.setCustomContentView(getRemoteViews())
        return builder
    }

//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun a(){
//        val build=Notification.Builder(context,channelID)
//        build.setSmallIcon(R.mipmap.ic_launcher)
//            .setContentText("播放通知")
//            .setContentTitle("提示")
//            .setCustomContentView(getRemoteViews())
//        notificationManager.notify(0,build.build())
//    }

    /**
     * 设置通知栏自定义布局
     *
     * @return
     */
    protected abstract fun getRemoteViews(): RemoteViews

    /**
     * 设置点击启动APP PendingIntent
     *
     * @return
     */
    protected abstract fun getPendingIntent(): PendingIntent

    /**
     * 发送通知
     *
     * @param title   标题
     * @param content 内容
     */
    fun sendNotification(title: String, content: String) {
//        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
//            a()
//        }else{
//            val builder = getNotification(title, content)
//            notificationManager.notify(1, builder.build())
//        }

        val builder = getNotification(title, content)
        notificationManager.notify(1, builder.build())
    }

    /**
     * 发送带有进度的通知
     *
     * @param title    标题
     * @param content  内容 可以设置为进度
     * @param progress 进度
     * @param intent   点击启动intent
     */
    fun sendNotificationProgress(title: String, content: String, progress: Int, intent: PendingIntent) {
        val builder = getNotificationProgress(title, content, progress, intent)
        notificationManager.notify(0, builder.build())
    }

    /**
     * 获取带有进度的Notification
     */
    private fun getNotificationProgress(
        title: String, content: String,
        progress: Int, intent: PendingIntent
    ): NotificationCompat.Builder {
        val builder: NotificationCompat.Builder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = NotificationCompat.Builder(context.applicationContext, channelID)
        } else {
            builder = NotificationCompat.Builder(context.applicationContext)
            builder.priority = Notification.PRIORITY_DEFAULT
        }
        //标题
        builder.setContentTitle(title)
        //文本内容
        builder.setContentText(content)
        //小图标
        builder.setSmallIcon(R.mipmap.ic_launcher)
        //设置大图标，未设置时使用小图标代替，拉下通知栏显示的那个图标
        //设置大图片 BitmpFactory.decodeResource(Resource res,int id) 根据给定的资源Id解析成位图
        builder.setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher))
        if (progress in 1..99) {
            //一种是有进度刻度的（false）,一种是循环流动的（true）
            //设置为false，表示刻度，设置为true，表示流动
            builder.setProgress(100, progress, false)
        } else {
            //0,0,false,可以将进度条隐藏
            builder.setProgress(0, 0, false)
            builder.setContentText("下载完成")
        }
        //设置点击信息后自动清除通知
        builder.setAutoCancel(false)
        //通知的时间
        builder.setWhen(System.currentTimeMillis())
        //设置点击信息后的跳转（意图）
        builder.setContentIntent(intent)
        return builder
    }
}