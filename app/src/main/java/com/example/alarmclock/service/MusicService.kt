package com.example.alarmclock.service

import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import androidx.lifecycle.LifecycleService
import java.io.IOException


/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.service
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/19 15:13
 * @class describe
 */
class MusicService:LifecycleService(){

    private lateinit var mMediaPlayer:MediaPlayer

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startAlarm()
        return super.onStartCommand(intent, flags, startId)
    }
    /**
     * 开启手机系统自带铃声
     */
    private fun startAlarm(){
        mMediaPlayer = MediaPlayer.create(this, getSystemDefultRingtoneUri())
        mMediaPlayer.isLooping = true
        try {
            mMediaPlayer.prepare()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        mMediaPlayer.start()
    }

    private fun stopAlarm() {
        mMediaPlayer.stop()
    }

    /**
     * 获取系统自带铃声的uri
     * @return RingtoneManager.getActualDefaultRingtoneUri(this,
     * RingtoneManager.TYPE_RINGTONE)
     */
    private fun getSystemDefultRingtoneUri(): Uri? {
        return RingtoneManager.getActualDefaultRingtoneUri(
            this,
            RingtoneManager.TYPE_RINGTONE
        )
    }


    override fun onDestroy() {
        super.onDestroy()
        stopAlarm()
    }
}