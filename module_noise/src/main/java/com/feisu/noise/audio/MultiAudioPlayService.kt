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

class MultiAudioPlayService :Service(),MediaPlayerWrapper.MediaPlayerListener{
    companion object{
        const val ACTION_PLAY="action_play"
        const val ACTION_PAUSE="action_pause"
        const val ACTION_RESUME="action_resume"
        const val ACTION_STOP="action_stop"
        const val ACTION_VOLUME_MUTE="action_volume_mute"//静音

        const val FILE_INFO_KEY="file_info_key"

        const val IS_PLAY_MULTI_KEY="is_play_multi_key"
    }

    var mediaPlayers:List<MediaPlayerWrapper>?= listOf(MediaPlayerWrapper(allSource[0]),
        MediaPlayerWrapper(allSource[1])
    )
        private set
    private var binder=AudioPlayBinder()
    private var timer: Timer?=null
    private var time:Date?=null

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
                val noises=intent.getParcelableArrayListExtra<MusicFileBean>(FILE_INFO_KEY)?.map {
                    if (it!=null) {
                        MediaPlayerWrapper(it)
                    }else{
                        null
                    }
                }?.filterNotNull()
                if (noises!=null){
                    mediaPlayers?.forEach { it.stop() }
                    mediaPlayers=noises
                    binder.onChange()
                }
                mediaPlayers?.forEach {
                    it.mediaPlayerListener=this
                    it.play()
                }
            }
            ACTION_PAUSE->{
                mediaPlayers?.forEach {
                    it.pause()
                }
            }
            ACTION_RESUME->{
                mediaPlayers?.forEach {
                    if (!it.resume()){
                        if (it.mediaPlayerListener==null) {
                            it.mediaPlayerListener = this
                        }
                        it.play()
                    }
                }
            }
            ACTION_STOP->{
                mediaPlayers?.forEach {
                    it.stop()
                }
            }
            ACTION_VOLUME_MUTE->{
                mediaPlayers?.forEach {
                    it.setVolume(0f)
                }
            }
            else->{

            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    class AudioPlayBinder: Binder() {
        var audioPlayService:MultiAudioPlayService?=null

        private val playingCallback=ArrayList<IPlayingCallback>()

        fun addPlayCallback(playCallback:IPlayingCallback){
            playingCallback.add(playCallback)
        }

        fun removePlayCallback(playCallback:IPlayingCallback){
            playingCallback.remove(playCallback)
        }

        fun isPlaying():Boolean{
            return audioPlayService?.mediaPlayers?.any { it.isPlay()==true }?:false
        }

        fun onPlay(isPlay:Boolean){
            playingCallback.forEach {
                it.onPlay(isPlay)
            }
            sendNotification(isPlay)
            SPUtil.instance.putBoolean(IS_PLAY_MULTI_KEY,isPlay)
        }

        fun onChange(){
            playingCallback.forEach {
                it.changNoise()
            }
            sendNotification(isPlaying())
        }

        private fun sendNotification(isPlay: Boolean){
            val nameBuilder=StringBuilder()
            audioPlayService?.mediaPlayers?.forEach {
                nameBuilder.append(it.getName()).append("/")
            }
            if(nameBuilder.toString().isNotEmpty()) {
                nameBuilder.deleteCharAt(nameBuilder.length - 1)
            }
            val title=BaseConstant.application.getString(R.string.zixuan)
            val titleDes=nameBuilder.toString()
            val notification=object : BaseNotification(BaseConstant.application){
                override fun getRemoteViews(): RemoteViews {
                    val remoteViews=if (isPlay){
                        RemoteViews(context.packageName, R.layout.notification_play_true).also {
                            val intent=Intent(context, MultiAudioPlayService::class.java)
                            intent.action= ACTION_PAUSE
                            it.setOnClickPendingIntent(
                                R.id.resumeIcon,
                                PendingIntent.getService(context,0,intent,0))
                        }
                    }else{
                        RemoteViews(context.packageName, R.layout.notification_play_false).also {
                            val intent=Intent(context, MultiAudioPlayService::class.java)
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
                BaseConstant.mainHandler.post {
                    mediaPlayers?.forEach {
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
        if (mediaPlayers?.any { it.isPlay()==true }!=true) {
            binder.onPlay(false)
        }
    }

    override fun onCompletion(mediaPlayerWrapper: MediaPlayerWrapper) {

    }

    override fun onPause(mediaPlayerWrapper: MediaPlayerWrapper) {
        if (mediaPlayers?.any { it.isPlay()==true }!=true) {
            binder.onPlay(false)
        }
    }

    override fun onStop(mediaPlayerWrapper: MediaPlayerWrapper) {
        if (mediaPlayers?.any { it.isPlay()==true }!=true) {
            binder.onPlay(false)
        }
    }

}