package com.feisu.noise.audio

import android.media.MediaPlayer
import com.feisu.noise.bean.MusicFileBean
import com.feisukj.base_library.utils.BaseConstant
import java.lang.Exception

class MediaPlayerWrapper(val musicFileBean:MusicFileBean):MediaPlayer.OnPreparedListener,MediaPlayer.OnErrorListener,MediaPlayer.OnCompletionListener {
    enum class AudioFileSource{
        assets,raw
    }
    private val context by lazy { BaseConstant.application }
    private var mediaPlayer:MediaPlayer?=null
    private var currentVolume=1f
    var mediaPlayerListener:MediaPlayerListener?=null

    fun getName()=musicFileBean.name

    fun getDes()=musicFileBean.musicDes

    fun getBg()=musicFileBean.picBg

    fun getBgV()=musicFileBean.picVirtualBg

    fun play(){
        stop()
        val fileSource=musicFileBean.fileSource?.let {
            try {
                AudioFileSource.valueOf(it)
            }catch (e:Exception){
                e.printStackTrace()
                null
            }
        }?:return
        when(fileSource){
            AudioFileSource.assets -> {
                mediaPlayer= MediaPlayer()
                val afd=context.assets.openFd(musicFileBean.assetsFileName?:return)
                mediaPlayer?.setDataSource(afd.fileDescriptor,afd.startOffset,afd.length)
            }
            AudioFileSource.raw -> {
                mediaPlayer= MediaPlayer.create(context,musicFileBean.rawResId?:return)
            }
        }
        mediaPlayer?.isLooping=true
        mediaPlayer?.setOnErrorListener(this)
        mediaPlayer?.setOnCompletionListener(this)
        mediaPlayer?.prepareAsync()
        mediaPlayer?.setOnPreparedListener(this)
    }

    fun resume():Boolean{
        return if (isPlay()==false){
            mediaPlayer?.start()
            mediaPlayerListener?.onResume(this)
            true
        }else{
            false
        }
    }

    fun pause(){
        if (isPlay()==true){
            mediaPlayer?.pause()
            mediaPlayerListener?.onPause(this)
        }
    }

    fun stop(){
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer=null
        mediaPlayerListener?.onStop(this)
    }

    fun getVolume():Float=currentVolume

    fun setVolume(volume:Float){
        if (volume !in 0f..1f){
            return
        }
        currentVolume=volume
        mediaPlayer?.setVolume(volume,volume)
    }

    fun isPlay():Boolean?{
        return mediaPlayer?.isPlaying
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mp?.start()
        mediaPlayerListener?.onResume(this)
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        mp?.reset()
        return false
    }

    override fun onCompletion(mp: MediaPlayer?) {

    }

    interface MediaPlayerListener{
        fun onResume(mediaPlayerWrapper: MediaPlayerWrapper)
        fun onError(mediaPlayerWrapper: MediaPlayerWrapper)
        fun onCompletion(mediaPlayerWrapper: MediaPlayerWrapper)
        fun onPause(mediaPlayerWrapper: MediaPlayerWrapper)
        fun onStop(mediaPlayerWrapper: MediaPlayerWrapper)
    }
}