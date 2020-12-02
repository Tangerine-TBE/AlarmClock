package com.example.alarmclock.service

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.lifecycle.LifecycleService
import com.example.alarmclock.util.Constants
import kotlin.random.Random


/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.service
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/19 15:13
 * @class describe
 */
class MusicService : LifecycleService() {
    private lateinit var mMediaPlayer: MediaPlayer
    private lateinit var mVibrator: Vibrator

    override fun onCreate() {
        super.onCreate()
        mMediaPlayer = MediaPlayer.create(this, getSystemDefaultRingtoneUri())
        mMediaPlayer.isLooping = true
        mVibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let { it ->
            //震动
            startVibration(it)
            //播放音乐
            startAlarm()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startVibration(it: Intent) {
        val isVibration = it.getBooleanExtra(Constants.CLOCK_VIBRATION, false)
        if (isVibration) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //数组的a[0]表示静止的时间，a[1]代表的是震动的时间，然后数组的a[2]表示静止的时间，a[3]代表的是震动的时间……依次类推下去
                val intArrayOf = intArrayOf(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255), Random.nextInt(255)
                        , Random.nextInt(255), Random.nextInt(255))
                mVibrator.vibrate(VibrationEffect.createWaveform(longArrayOf(800, 1000, 800, 1000, 800, 1000), intArrayOf, 0))
            } else {
                mVibrator.vibrate(longArrayOf(800, 1000, 800, 1000, 800, 1000), 0)
            }
        }
    }

    /**
     * 开启手机系统自带铃声
     */
    private fun startAlarm() {
        try {
            mMediaPlayer.prepare()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (!mMediaPlayer.isPlaying) mMediaPlayer.start()
    }

    private fun stopAlarm() {
        if (mMediaPlayer.isPlaying) mMediaPlayer.stop()
    }
    private fun stopVibration() {
        mVibrator.cancel()
    }
    /**
     * 获取系统自带铃声的uri
     * @return RingtoneManager.getActualDefaultRingtoneUri(this,
     * RingtoneManager.TYPE_RINGTONE)
     */
    private fun getSystemDefaultRingtoneUri(): Uri? {
        return RingtoneManager.getActualDefaultRingtoneUri(
                this,
                RingtoneManager.TYPE_RINGTONE
        )
    }


    override fun onDestroy() {
        super.onDestroy()
        stopAlarm()
        stopVibration()
    }


}