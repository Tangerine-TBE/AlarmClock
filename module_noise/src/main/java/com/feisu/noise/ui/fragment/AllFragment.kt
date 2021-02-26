package com.feisu.noise.ui.fragment

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import androidx.recyclerview.widget.GridLayoutManager
import com.feisu.noise.R
import com.feisu.noise.adapter.AllSelectorAdapter
import com.feisu.noise.adcontrol.getPageControlData
import com.feisu.noise.adcontrol.getUnlockMusicDate
import com.feisu.noise.adcontrol.putUnlockMusicDate
import com.feisu.noise.audio.IPlayingCallback
import com.feisu.noise.audio.MultiAudioPlayService
import com.feisu.noise.audio.MultiAudioPlayService.Companion.FILE_INFO_KEY
import com.feisu.noise.bean.MusicFileBean
import com.feisu.noise.ui.ControlActivity
import com.feisukj.base_library.baseclass.BaseFragment
import kotlinx.android.synthetic.main.fragment_all.*

class AllFragment :BaseFragment(), IPlayingCallback {
    companion object{
        const val FREE_MUSIC_COUNT=7
    }

    private var adapter:AllSelectorAdapter?=null
    private var binder: MultiAudioPlayService.AudioPlayBinder?=null
    private val currentSelect=ArrayList<MusicFileBean>()

    private var isFirst=true
    private var adView:LinearLayout?=null

    override fun getLayoutId()= R.layout.fragment_all

    override fun initView() {
        context?.let { context ->
            adView= LinearLayout(context)
            adView?.layoutParams=LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
            adView?.orientation=LinearLayout.VERTICAL

            val frameLayout=FrameLayout(context)
            frameLayout.layoutParams=LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT).also {
                it.width=TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,300f,resources.displayMetrics).toInt()
                it.height=ViewGroup.LayoutParams.WRAP_CONTENT
                it.gravity= Gravity.CENTER
            }

            adView?.addView(frameLayout)
        }

        allRecyclerView.layoutManager=GridLayoutManager(context,3)
        adapter=AllSelectorAdapter(adView)
        allRecyclerView.adapter=adapter
        bindService()
    }

    override fun onResume() {
        super.onResume()
        if(isFirst) {
            activity?.apply {
                val adViewF=try {
                    adView?.get(0) as? FrameLayout
                }catch (e:Exception){
                    null
                }?:return
            }
        }
        isFirst=false
    }

    override fun initListener() {
        playing.setOnClickListener {
            it.isSelected=!it.isSelected
            if (it.isSelected){
                play()
            }else{
                pause()
            }
        }
        adapter?.selectorNoiseCallback=object :AllSelectorAdapter.SelectorNoiseCallback{
            override fun onSelector(musicFileBean: MusicFileBean,position:Int,isLock:Boolean, isSelected: Boolean) {
                if (isLock){
                    showAskSeeAdDialog(musicFileBean.name?:"",position)
                    return
                }

                if (isSelected){
                    currentSelect.add(musicFileBean)
                    if (currentSelect.size>3){
                        currentSelect.removeAt(0)
                    }
                    if (currentSelect.size==1){
                        viewSwitcher.showNext()
                    }
                }else{
                    currentSelect.remove(musicFileBean)
                    if (currentSelect.size==0){
                        viewSwitcher.showNext()
                    }
                }
                val stringBuilder= StringBuilder("")
                currentSelect.map { it.name }.forEach {
                    if (it!=null) {
                        stringBuilder.append("$it/")
                    }
                }
                if (stringBuilder.isNotEmpty()){
                    stringBuilder.deleteCharAt(stringBuilder.length-1)
                }
                chooserNoise.text=getString(R.string.selectedNoise,stringBuilder.toString())
            }
        }

        playingOk.setOnClickListener {
            if (currentSelect.isNotEmpty()) {
                    playingNoises(ArrayList(currentSelect))
                    currentSelect.clear()
                    adapter?.cleanChooser()
                    viewSwitcher.showNext()
            }
        }

        control.setOnClickListener {
            startActivity(Intent(context, ControlActivity::class.java))
        }
    }

    private fun bindService(){
        context?.bindService(Intent(context,MultiAudioPlayService::class.java),object :
            ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
                binder?.removePlayCallback(this@AllFragment)
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                if (service is MultiAudioPlayService.AudioPlayBinder&&isAdded){
                    binder=service
                    binder?.addPlayCallback(this@AllFragment)
                    updatePlayingInfo()
                }
            }

        }, Context.BIND_AUTO_CREATE)
    }

    private fun playingNoises(noises: ArrayList<MusicFileBean>){
        val intent= Intent(context, MultiAudioPlayService::class.java)
        intent.action= MultiAudioPlayService.ACTION_PLAY
        intent.putParcelableArrayListExtra(FILE_INFO_KEY, noises)
        context?.startService(intent)
    }

    private fun showAskSeeAdDialog(name:String,position:Int){

    }

    private fun play(){
        val intent= Intent(context, MultiAudioPlayService::class.java)
        intent.action= MultiAudioPlayService.ACTION_RESUME
        context?.startService(intent)
    }

    private fun pause(){
        val intent= Intent(context, MultiAudioPlayService::class.java)
        intent.action= MultiAudioPlayService.ACTION_PAUSE
        context?.startService(intent)
    }

    private fun updatePlayingInfo(){
        if(!isAdded)
            return
        val isPlay=binder?.isPlaying()?:false
        val nameBuilder=StringBuilder()
        binder?.audioPlayService?.mediaPlayers?.forEach {
            nameBuilder.append(it.getName()).append("/")
        }
        playing?.isSelected=isPlay
        if(nameBuilder.toString().isNotEmpty()) {
            nameBuilder.deleteCharAt(nameBuilder.length - 1)
            playNoise?.text = nameBuilder.toString()
        }
    }

    override fun changNoise() {
        if (isAdded) {
            updatePlayingInfo()
        }
    }

    override fun onPlay(isPlay: Boolean) {
        if (isAdded) {
            playing.isSelected = isPlay
//            updatePlayingInfo()
        }
    }
}