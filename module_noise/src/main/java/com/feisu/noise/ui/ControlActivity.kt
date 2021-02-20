package com.feisu.noise.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.view.View
import android.widget.SeekBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.feisu.noise.R
import com.feisu.noise.adapter.TimingAdapter
import com.feisu.noise.audio.MultiAudioPlayService
import com.feisu.noise.audio.MediaPlayerWrapper
import com.feisukj.base_library.baseclass.BaseActivity
import com.feisukj.base_library.utils.toast
import kotlinx.android.synthetic.main.activity_control.*
import java.util.*

class ControlActivity :BaseActivity(){
    private var binder: MultiAudioPlayService.AudioPlayBinder?=null
    private var mediaPlayers:List<MediaPlayerWrapper>?=null
    private var timer:Timer?= null

    override fun getLayoutId()= R.layout.activity_control

    override fun isActionBar(): Boolean {
        return false
    }

    override fun initView() {
        timingRecyclerView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        timingRecyclerView.adapter= TimingAdapter().also { timingAdapter ->
            timingAdapter.chooseTimeCallback=object : TimingAdapter.ChooseTimeCallback{
                override fun onChoose(intervalTime: Long) {
                    val date=Date(System.currentTimeMillis()+intervalTime)
                    binder?.audioPlayService?.also {
                        it.setTimingOff(date)
                        timingOff(date)
                    }
                }
            }
        }
        bindService()
    }

    override fun initListener() {
        back.setOnClickListener {
            finish()
        }
        //音量
        volume.setOnClickListener {
            it.isSelected=!it.isSelected
            volumeProgress.visibility=if(volume.isSelected) View.VISIBLE else View.GONE
        }
        seekBar1.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mediaPlayers?.let {
                    if (it.isNotEmpty()){
                        it[0].setVolume(progress.toFloat()/100f)
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
        seekBar2.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mediaPlayers?.let {
                    if (it.size>=2){
                        it[1].setVolume(progress.toFloat()/100f)
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
        seekBar3.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mediaPlayers?.let {
                    if (it.size>=3){
                        it[2].setVolume(progress.toFloat()/100f)
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        //静音
        mute.setOnClickListener { view ->
            if (mediaPlayers==null){
                return@setOnClickListener
            }
            view.isSelected=!view.isSelected
            if (view.isSelected){
                mediaPlayers?.forEach {
                    it.setVolume(0f)
                }
            }else{
                mediaPlayers?.forEach {
                    it.setVolume(1f)
                    it.resume()
                }
            }
        }

        //定时
        timingView.visibility=if(timing.isSelected) View.VISIBLE else View.GONE
        timing.setOnClickListener {
            if(binder==null){
                toast()
                return@setOnClickListener
            }
            it.isSelected=!it.isSelected
            timingView.visibility=if(timing.isSelected) View.VISIBLE else View.GONE
        }
        cancelCountdown.setOnClickListener {
            binder?.audioPlayService?.cancelTimingOff()
            timer?.cancel()
            timer=null
            timing.isSelected=false
            viewSwitcher.showNext()
        }
    }

    private fun timingOff(date:Date){
        val stopTime=date.time
        val seconds=1000L
        val minutes=seconds*60
        val hours=minutes*60L
        timer?.cancel()
        timer= Timer()
        timer?.schedule(object :TimerTask(){
            override fun run() {
                val haveTime=stopTime-System.currentTimeMillis()
                if (haveTime<=0){
                    runOnUiThread {
                        viewSwitcher.showNext()
                    }
                    timer?.cancel()
                    return
                }
                val h=haveTime/hours
                val m=haveTime%hours/minutes
                val s=(haveTime%hours%minutes+500)/1000
                runOnUiThread {
                    countdownTextView.text = getString(R.string.haveTime,h.toString(),m.toString(),s.toString())
                }
            }
        },0,1000)
        viewSwitcher.showNext()
    }

    private fun bindService(){
        bindService(Intent(this,MultiAudioPlayService::class.java),object :
            ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
                mediaPlayers=null
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                if (service is MultiAudioPlayService.AudioPlayBinder){
                    binder=service
                    binder?.audioPlayService?.getTimingOffDate()?.also {
                        if (it.after(Date(System.currentTimeMillis()))){
                            timingOff(it)
                        }
                    }
                    mediaPlayers=binder?.audioPlayService?.mediaPlayers?.also { list ->
                        mute.isSelected = list.all { it.getVolume()==0f }
                        list.forEachIndexed { index, mediaPlayerWrapper ->
                            when(index){
                                0->{
                                    volume1.visibility=View.VISIBLE
                                    seekBar1.progress=(mediaPlayerWrapper.getVolume()*100).toInt()
                                    noiseName1.text=mediaPlayerWrapper.getName()
                                }
                                1->{
                                    volume2.visibility=View.VISIBLE
                                    seekBar2.progress=(mediaPlayerWrapper.getVolume()*100).toInt()
                                    noiseName2.text=mediaPlayerWrapper.getName()
                                }
                                2->{
                                    volume3.visibility=View.VISIBLE
                                    seekBar3.progress=(mediaPlayerWrapper.getVolume()*100).toInt()
                                    noiseName3.text=mediaPlayerWrapper.getName()
                                }
                            }
                        }
                    }?:return
                }
            }

        }, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        timer?.cancel()
        timer=null
        super.onDestroy()
    }
}