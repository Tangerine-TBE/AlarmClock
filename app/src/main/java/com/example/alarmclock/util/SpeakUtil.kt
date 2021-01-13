package com.example.alarmclock.util

import android.content.Context
import com.baidu.tts.client.SpeechError
import com.baidu.tts.client.SpeechSynthesizer
import com.baidu.tts.client.SpeechSynthesizerListener
import com.baidu.tts.client.TtsMode
import com.example.module_base.util.LogUtils

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.util
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2021/1/13 19:10:37
 * @class describe
 */
class SpeakUtil(private val context: Context) {
    private val mSpeak by lazy {
        SpeechSynthesizer.getInstance()
    }

    private var isSpeak=false
    init {
        mSpeak.apply {
            setContext(context)
            setAppId("23537278")
            setApiKey("dSijt9iG7C4P64T4d2jUL0QQ","4NX8b00KenZUNvFGYCfw6ujohLMjGRQM")
            initTts(TtsMode.ONLINE)
            setParam(SpeechSynthesizer.PARAM_SPEAKER, "0")
            setSpeechSynthesizerListener(object : SpeechSynthesizerListener{
                override fun onSynthesizeStart(p0: String?) {
                    println("----SpeakUtil---onSynthesizeStart---------------")
                }

                override fun onSpeechFinish(p0: String?) {
                    println("--SpeakUtil-----onSpeechFinish---------------")
                }

                override fun onSpeechProgressChanged(p0: String?, p1: Int) {
                    isSpeak=true
                    println("---SpeakUtil----onSpeechProgressChanged---------------")
                }

                override fun onSynthesizeFinish(p0: String?) {
                    isSpeak=false
                    println("---SpeakUtil----onSynthesizeFinish---------------")
                }

                override fun onSpeechStart(p0: String?) {
                    println("---SpeakUtil----onSpeechStart---------------")
                }

                override fun onSynthesizeDataArrived(p0: String?, p1: ByteArray?, p2: Int, p3: Int) {
                    println("----SpeakUtil---onSynthesizeDataArrived---------------")

                }

                override fun onError(p0: String?, p1: SpeechError?) {
                    isSpeak=false
                    println("---SpeakUtil----onError---------------")
                }

            })
        }

    }

    fun isSpeaking()=isSpeak

    fun getSpeaker(): SpeechSynthesizer =mSpeak

    fun speakText(str:String){
        if (isSpeak) { mSpeak?.stop() }
        mSpeak?.speak(str)
    }

   fun  stopSpeak(){
       if (isSpeak) {
           mSpeak?.stop()
       }

    }


    fun releaseSrc(){
        mSpeak?.release()
    }



}