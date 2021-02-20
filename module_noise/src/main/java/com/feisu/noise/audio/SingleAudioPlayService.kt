package com.feisu.noise.audio

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.widget.RemoteViews
import com.feisu.noise.R
import com.feisu.noise.bean.MusicFileBean
import com.feisukj.base_library.baseclass.BaseNotification
import com.feisukj.base_library.utils.BaseConstant
import com.feisukj.base_library.utils.SPUtil
import java.util.*
import kotlin.collections.ArrayList

class SingleAudioPlayService :Service(),MediaPlayerWrapper.MediaPlayerListener{
    companion object{
        const val ACTION_PLAY="action_play"
        const val ACTION_PAUSE="action_pause"
        const val ACTION_RESUME="action_resume"
        const val ACTION_STOP="action_stop"
        const val ACTION_VOLUME_MUTE="action_volume_mute"//静音

        const val FILE_INFO_KEY="file_info_key"

        const val IS_PLAY_SING_KEY="is_play_key_sing"
    }

    var mediaPlayer:MediaPlayerWrapper?= MediaPlayerWrapper(recommendSource.first())
        private set
    private var binder=AudioPlayBinder()
    private var timer: Timer?=null
    private var time: Date?=null

    override fun onBind(intent: Intent?): IBinder? {
        binder.audioPlayService=this
        return binder
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action){
            ACTION_PLAY->{
                intent.getParcelableExtra<MusicFileBean>(FILE_INFO_KEY)?.let {
                    mediaPlayer?.stop()
                    mediaPlayer=MediaPlayerWrapper(it)
                    mediaPlayer?.mediaPlayerListener=this
                    mediaPlayer?.play()
                    binder.onChange()
                }
            }
            ACTION_PAUSE->{
                mediaPlayer?.pause()
            }
            ACTION_RESUME->{
                mediaPlayer?.let {
                    if (!it.resume()){
                        if (it.mediaPlayerListener==null) {
                            it.mediaPlayerListener = this
                        }
                        it.play()
                    }
                }
            }
            ACTION_STOP->{
                mediaPlayer?.stop()
            }
            ACTION_VOLUME_MUTE->{
                mediaPlayer?.setVolume(0f)
            }
            else->{

            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    class AudioPlayBinder: Binder() {
        var audioPlayService:SingleAudioPlayService?=null

        private val playingCallback=ArrayList<IPlayingCallback>()

        fun addPlayCallback(playCallback:IPlayingCallback){
            playingCallback.add(playCallback)
        }

        fun removePlayCallback(playCallback:IPlayingCallback){
            playingCallback.remove(playCallback)
        }

        fun onPlay(isPlay:Boolean){
            playingCallback.forEach {
                it.onPlay(isPlay)
            }
            sendNotification(isPlay,audioPlayService?.mediaPlayer?.getName()?:"未知",audioPlayService?.mediaPlayer?.getDes()?:"")
            SPUtil.instance.putBoolean(IS_PLAY_SING_KEY,isPlay)
        }

        fun onChange(){
            playingCallback.forEach {
                it.changNoise()
            }
            sendNotification(audioPlayService?.mediaPlayer?.isPlay()?:false,audioPlayService?.mediaPlayer?.getName()?:"未知",audioPlayService?.mediaPlayer?.getDes()?:"")
        }
        private fun sendNotification(isPlay: Boolean,title:String,titleDes:String){
            val notification=object : BaseNotification(BaseConstant.application){
                override fun getRemoteViews(): RemoteViews {
                    val remoteViews=if (isPlay){
                        RemoteViews(context.packageName, R.layout.notification_play_true).also {
                            val intent=Intent(context, SingleAudioPlayService::class.java)
                            intent.action= ACTION_PAUSE
                            it.setOnClickPendingIntent(
                                R.id.resumeIcon,
                                PendingIntent.getService(context,0,intent,0))
                        }
                    }else{
                        RemoteViews(context.packageName, R.layout.notification_play_false).also {
                            val intent=Intent(context, SingleAudioPlayService::class.java)
                            intent.action= ACTION_RESUME
                            it.setOnClickPendingIntent(
                                R.id.resumeIcon,
                                PendingIntent.getService(context,0,intent,0))
                        }
                    }
                    remoteViews.setTextViewText(R.id.title,title)
                    remoteViews.setTextViewText(R.id.titleDes,titleDes)
                    return remoteViews
                }

                override fun getPendingIntent(): PendingIntent {
                    return PendingIntent.getActivity(context,0, Intent(),0)
                }

            }
            notification.sendNotification("播放通知","0")
        }
    }

    fun setTimingOff(time: Date){
        this.time=time
        timer?.cancel()
        timer= Timer()
        timer?.schedule(object :TimerTask(){
            override fun run() {
                mediaPlayer?.also {
                    BaseConstant.mainHandler.post {
                        it.stop()
                    }
                }
            }
        },time)
    }

    fun cancelTimingOff(){
        time=null
        timer?.cancel()
        timer=null
    }

    fun getTimingOffDate()=time

    override fun onResume(mediaPlayerWrapper: MediaPlayerWrapper) {
        binder.onPlay(true)
    }

    override fun onError(mediaPlayerWrapper: MediaPlayerWrapper) {
        binder.onPlay(false)
    }

    override fun onCompletion(mediaPlayerWrapper: MediaPlayerWrapper) {

    }

    override fun onPause(mediaPlayerWrapper: MediaPlayerWrapper) {
        binder.onPlay(false)
    }

    override fun onStop(mediaPlayerWrapper: MediaPlayerWrapper) {
        binder.onPlay(false)
    }

}