package com.feisu.noise.audio

import android.media.MediaPlayer
import com.example.module_base.base.BaseApplication
import com.example.module_base.util.LogUtils
import com.feisu.noise.bean.MusicFileBean
import com.feisu.noise.repository.NoiseRepository
import com.feisu.noise.utils.createFile
import com.feisukj.base_library.utils.BaseConstant
import com.tamsiree.rxkit.RxNetTool
import com.tamsiree.rxkit.view.RxToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

class MediaPlayerWrapper(val musicFileBean: MusicFileBean) : MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {
    enum class AudioFileSource {
        assets, raw
    }

    private val context by lazy { BaseConstant.application }
    private var mediaPlayer: MediaPlayer? = null
    private var currentVolume = 1f
    var mediaPlayerListener: MediaPlayerListener? = null

    fun getName() = musicFileBean.name

    fun getDes() = musicFileBean.musicDes

    fun getBg() = musicFileBean.picBg

    fun getBgV() = musicFileBean.picVirtualBg

    fun getPicUrl() = musicFileBean.picUrl

    fun play() {
        stop()
        val fileSource = musicFileBean.fileSource?.let {
            try {
                AudioFileSource.valueOf(it)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        } ?: return
        when (fileSource) {
            AudioFileSource.assets -> {
                //     val afd = context.assets.openFd(musicFileBean.assetsFileName ?: return)
                //   mediaPlayer?.setDataSource(afd.fileDescriptor,afd.startOffset,afd.length)
                LogUtils.i("------musicFileBean--------${musicFileBean}-----------")

                val createFile = createFile(musicFileBean.assetsFileName.toString())
                if (createFile.exists()) {
                    setPlayer(createFile.absolutePath)
                } else {
                    musicFileBean.allPathString?.let { it ->
                        if (RxNetTool.isNetworkAvailable(BaseApplication.application)) {
                            setPlayer(it)
                            try {
                                musicFileBean.pathString?.let { path ->
                                    GlobalScope.launch(Dispatchers.IO) {
                                        NoiseRepository.getMp3File(path)?.let {
                                            saveFile(it, musicFileBean.assetsFileName)
                                        }
                                    }
                                }
                            } catch (e: Exception) {
                                RxToast.normal("缓存失败！")
                            }
                        } else {
                            RxToast.normal("播放失败，请检查网络是否连接！")
                        }
                    }
                }
            }
            AudioFileSource.raw -> {
                mediaPlayer = MediaPlayer.create(context, musicFileBean.rawResId ?: return)
            }
        }

    }

    private fun setPlayer(path: String) {
        mediaPlayer = MediaPlayer().apply {
            setDataSource(path)
            isLooping = true
            setOnErrorListener(this@MediaPlayerWrapper)
            setOnCompletionListener(this@MediaPlayerWrapper)
            prepareAsync()
            setOnPreparedListener(this@MediaPlayerWrapper)
        }

    }

    fun resume(): Boolean {
        return if (isPlay() == false) {
            mediaPlayer?.start()
            mediaPlayerListener?.onResume(this)
            true
        } else {
            false
        }
    }

    fun pause() {
        if (isPlay() == true) {
            mediaPlayer?.pause()
            mediaPlayerListener?.onPause(this)
        }
    }

    fun stop() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        mediaPlayerListener?.onStop(this)
    }

    fun getVolume(): Float = currentVolume

    fun setVolume(volume: Float) {
        if (volume !in 0f..1f) {
            return
        }
        currentVolume = volume
        mediaPlayer?.setVolume(volume, volume)
    }

    fun isPlay(): Boolean? {
        return mediaPlayer?.isPlaying
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mp?.start()
        mediaPlayerListener?.onResume(this)
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        mp?.reset()
        LogUtils.i("-----MediaPlayerWrapper-------onError-------------------")
        return false
    }

    override fun onCompletion(mp: MediaPlayer?) {

    }


    private fun saveFile(body: ResponseBody, path: String?) {
        body.byteStream()?.apply {
            val externalFilesDir = BaseApplication.application.getExternalFilesDir("music")
            val file = File(externalFilesDir, "$path")
            val fos = FileOutputStream(file)
            try {
                LogUtils.i("-saveFile---${Thread.currentThread().name}----${body.contentLength() / 1024}Kb----------")
                val buf = ByteArray(1024)
                var len: Int
                while (this.read(buf, 0, buf.size).also { len = it } != -1) {
                    fos.write(buf, 0, len)
                }
            } catch (e: Exception) {

            } finally {
                close()
                fos.close()
            }
        }
    }


    interface MediaPlayerListener {
        fun onResume(mediaPlayerWrapper: MediaPlayerWrapper)
        fun onError(mediaPlayerWrapper: MediaPlayerWrapper)
        fun onCompletion(mediaPlayerWrapper: MediaPlayerWrapper)
        fun onPause(mediaPlayerWrapper: MediaPlayerWrapper)
        fun onStop(mediaPlayerWrapper: MediaPlayerWrapper)
    }
}