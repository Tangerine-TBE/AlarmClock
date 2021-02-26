package com.feisu.noise.ui.fragment

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Color
import android.os.IBinder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.feisu.noise.R
import com.feisu.noise.adapter.TimingAdapter
import com.feisu.noise.audio.IPlayingCallback
import com.feisu.noise.audio.MultiAudioPlayService
import com.feisu.noise.audio.SingleAudioPlayService
import com.feisu.noise.audio.SingleAudioPlayService.Companion.FILE_INFO_KEY
import com.feisu.noise.audio.recommendSource
import com.feisu.noise.bean.MusicFileBean
import com.feisu.noise.utils.checkNetWork
import com.feisu.noise.view.NiceImageView
import com.feisukj.base_library.baseclass.BaseFragment
import com.feisukj.base_library.baseclass.BaseViewHolder
import com.feisukj.base_library.utils.BaseConstant
import com.feisukj.base_library.utils.SPUtil
import kotlinx.android.synthetic.main.fragment_recommend.*
import java.util.*

class RecommendFragment :BaseFragment(),IPlayingCallback{
    private var binder: SingleAudioPlayService.AudioPlayBinder?=null
    private var adapter=NoiseSelectorAdapter()
    private var timer:Timer?= null
    private var connection:ServiceConnection?=null

    override fun getLayoutId()= R.layout.fragment_recommend

    override fun initView() {
        selectorNoiseRecyclerView.layoutManager=LinearLayoutManager(context).apply {
            this.orientation=RecyclerView.HORIZONTAL
        }
        selectorNoiseRecyclerView.adapter=adapter
        timingRecyclerView.layoutManager=LinearLayoutManager(context).apply {
            this.orientation=RecyclerView.HORIZONTAL
        }
        timingRecyclerView.adapter= TimingAdapter().also { timingAdapter ->
            timingAdapter.chooseTimeCallback=object : TimingAdapter.ChooseTimeCallback{
                override fun onChoose(intervalTime: Long) {
                    val date= Date(System.currentTimeMillis()+intervalTime)
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
        play.setOnClickListener {
                it.isSelected=!it.isSelected
                val intent=Intent(context,SingleAudioPlayService::class.java)
                if (it.isSelected){
                    intent.action=SingleAudioPlayService.ACTION_RESUME
                }else{
                    intent.action=SingleAudioPlayService.ACTION_PAUSE
                }
                context?.startService(intent)
        }
        cancelCountdown.setOnClickListener {
            binder?.audioPlayService?.cancelTimingOff()
            timer?.cancel()
            timer=null
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
                if (!isAdded){
                    timer?.cancel()
                    return
                }
                val haveTime=stopTime-System.currentTimeMillis()
                if (haveTime<=0){
                    BaseConstant.mainHandler.post {
                        viewSwitcher?.showNext()
                    }
                    timer?.cancel()
                    return
                }
                val h=haveTime/hours
                val m=haveTime%hours/minutes
                val s=(haveTime%hours%minutes+500)/1000
                BaseConstant.mainHandler.post {
                    countdownTextView?.text = getString(R.string.haveTime,h.toString(),m.toString(),s.toString())
                }
            }
        },0,1000)
        viewSwitcher.showNext()
    }

    private fun bindService(){
        connection=object :ServiceConnection{
            override fun onServiceDisconnected(name: ComponentName?) {
                binder?.removePlayCallback(this@RecommendFragment)
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                if (service is SingleAudioPlayService.AudioPlayBinder) {
                    binder = service
                    binder?.addPlayCallback(this@RecommendFragment)
                    binder?.audioPlayService?.getTimingOffDate()?.also {
                        if (it.after(Date(System.currentTimeMillis()))) {
                            timingOff(it)
                        }
                    }
                    binder?.audioPlayService?.mediaPlayer?.also { mediaPlayerWrapper ->
                        play.isSelected = mediaPlayerWrapper.isPlay() ?: false
                        rootView.setBackgroundResource(mediaPlayerWrapper.getBgV())
                        noiseName.text = mediaPlayerWrapper.getName()
                        Glide.with(this@RecommendFragment).load(mediaPlayerWrapper.getPicUrl()).into(noisePic)
                        noiseDes.text = mediaPlayerWrapper.getDes()
                        recommendSource.indexOf(mediaPlayerWrapper.musicFileBean).also {
                            if (it != -1) {
                                adapter.chooserMusicFileBean = mediaPlayerWrapper.musicFileBean
                                adapter.notifyItemChanged(it)
                            }
                        }
                    }
                    if (!SPUtil.instance.getBoolean(SingleAudioPlayService.IS_PLAY_SING_KEY, false) &&
                        !SPUtil.instance.getBoolean(MultiAudioPlayService.IS_PLAY_MULTI_KEY)
                    ) {
                        val intent = Intent(context, SingleAudioPlayService::class.java)
                        intent.action = SingleAudioPlayService.ACTION_RESUME
                        context?.startService(intent)
                    }
                }
            }

        }
        connection?.also {
            val intent=Intent(context,SingleAudioPlayService::class.java)
            context?.bindService(intent,it, Context.BIND_AUTO_CREATE)
        }
    }

    private fun selectorNoise(noise:MusicFileBean){
            rootView.setBackgroundResource(noise.picVirtualBg)
            Glide.with(this@RecommendFragment).load(noise.picUrl).into(noisePic)
            noiseDes.text=noise.musicDes
            val intent= Intent(context, SingleAudioPlayService::class.java)
            intent.action= SingleAudioPlayService.ACTION_PLAY
            intent.putExtra(FILE_INFO_KEY, noise)
            context?.startService(intent)
    }

    private inner class NoiseSelectorAdapter:RecyclerView.Adapter<BaseViewHolder>(){
        var chooserView:NiceImageView?=null
        var chooserMusicFileBean:MusicFileBean?=null
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
            val itemView=LayoutInflater.from(parent.context).inflate(R.layout.item_noise,parent,false)
            return BaseViewHolder(itemView)
        }

        override fun getItemCount(): Int {
            return recommendSource.size
        }

        override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
            val noise= recommendSource[position]
            val niceImageView=holder.getView<NiceImageView>(R.id.niceImageView)
            holder.loadImage(R.id.niceImageView,noise.picUrl)
            holder.setText(R.id.textView, noise.name ?: "")

            if (chooserMusicFileBean==noise){
                niceImageView.setBorderColor(Color.parseColor("#0CFF8E"))
                chooserView=niceImageView

            }else{
                niceImageView.setBorderColor(Color.TRANSPARENT)
            }
            holder.itemView.setOnClickListener {
                chooserView?.setBorderColor(Color.TRANSPARENT)
                niceImageView.setBorderColor(Color.parseColor("#0CFF8E"))
                chooserView=niceImageView
                chooserMusicFileBean=noise
                selectorNoise(noise)
            }
        }

    }

    override fun changNoise() {
        if (isAdded) {
            updatePlayingInfo()
        }
    }

    override fun onPlay(isPlay: Boolean) {
        if (isAdded) {
            play.isSelected = isPlay
//            updatePlayingInfo()
        }
    }

    private fun updatePlayingInfo(){
        binder?.audioPlayService?.mediaPlayer.let {
            noiseName.text=it?.getName()?:"未知"
            noiseDes.text=it?.getDes()?:"未知"
            play.isSelected = it?.isPlay()?:false
        }
    }

    override fun onDestroy() {
        timer?.cancel()
        timer=null
        super.onDestroy()
        context?.unbindService(connection?:return)
    }
}