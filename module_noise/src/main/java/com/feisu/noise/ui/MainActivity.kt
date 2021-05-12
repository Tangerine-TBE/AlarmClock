package com.feisu.noise.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.view.Gravity
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.twx.module_base.base.BasePopup
import com.twx.module_base.util.SPUtil
import com.feisu.noise.R
import com.feisu.noise.audio.MultiAudioPlayService
import com.feisu.noise.audio.SingleAudioPlayService
import com.feisu.noise.ui.fragment.AllFragment
import com.feisu.noise.ui.fragment.RecommendFragment
import com.feisu.noise.ui.popup.ExitNoisePopup
import com.feisu.noise.utils.Constants
import com.feisukj.base_library.baseclass.BaseActivity
import com.feisukj.base_library.utils.ActivityLifecycleCallbacksImpl
import kotlinx.android.synthetic.main.activity_main_view.*


class MainActivity:BaseActivity() {
    private val fragments by lazy { listOf<Fragment>(RecommendFragment(),AllFragment()) }

    override fun isActionBar(): Boolean {
        return false
    }

    private val mExitNoisePopup by lazy {
        ExitNoisePopup(this)
    }


    private val singAudioIntent by lazy {
        Intent(this@MainActivity,SingleAudioPlayService::class.java)
    }

    private val audioIntent by lazy {
        Intent(this@MainActivity,MultiAudioPlayService::class.java)
    }

    override fun getLayoutId()= R.layout.activity_main_view

    override fun initView() {
        mViewPage.adapter=object :FragmentPagerAdapter(supportFragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
            override fun getItem(position: Int): Fragment {
                return fragments[position]
            }
            override fun getCount()=fragments.size
        }
        mViewPage.offscreenPageLimit=1
        bindService()
    }

    override fun initListener() {
        mExitNoisePopup.setOnActionClickListener(object : BasePopup.OnActionClickListener {
            override fun sure() {
                stopMusic()
                finish()
            }

            override fun cancel() {
                finish()
            }

        })

        recommended.setOnClickListener {
            if(fragments[mViewPage.currentItem]!is RecommendFragment){
                for (index in fragments.indices){
                    if (fragments[index] is RecommendFragment){
                        mViewPage.setCurrentItem(index,true)
                        break
                    }
                }
            }
        }
        all.setOnClickListener {
            if(fragments[mViewPage.currentItem]!is AllFragment){
                for (index in fragments.indices){
                    if (fragments[index] is AllFragment){
                        mViewPage.setCurrentItem(index,true)
                        break
                    }
                }
            }
        }
        mViewPage.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                if (isFinishing&& ActivityLifecycleCallbacksImpl.isFront){
                    return
                }

                if (fragments[position] is RecommendFragment){
                    //setting.visibility= View.VISIBLE
                    singAudioIntent.action=SingleAudioPlayService.ACTION_RESUME
                    audioIntent.action=MultiAudioPlayService.ACTION_PAUSE
                    isMultiplePlaying=audioPlayService?.mediaPlayers?.any { it.isPlay()==true }?:false
                    startService(audioIntent)
                    if (isSingPlaying) {
                        startService(singAudioIntent)
                    }
                }else if (fragments[position] is AllFragment){
                 //   setting.visibility= View.GONE
                    singAudioIntent.action=SingleAudioPlayService.ACTION_PAUSE
                    audioIntent.action=MultiAudioPlayService.ACTION_RESUME
                    isSingPlaying=singleAudioPlayService?.mediaPlayer?.isPlay()?:false
                    startService(singAudioIntent)
                    if (isMultiplePlaying) {
                        startService(audioIntent)
                    }
                }
            }

        })
    }

    private fun stopMusic() {
        audioIntent.action = MultiAudioPlayService.ACTION_PAUSE
        singAudioIntent.action = SingleAudioPlayService.ACTION_PAUSE
        startService(audioIntent)
        startService(singAudioIntent)
    }

    private var singleAudioPlayService:SingleAudioPlayService?=null
    private var audioPlayService:MultiAudioPlayService?=null
    private var isSingPlaying=false
    private var isMultiplePlaying=false
    private fun bindService(){
        val singAudioIntent=Intent(this@MainActivity,SingleAudioPlayService::class.java)
        val audioIntent=Intent(this@MainActivity,MultiAudioPlayService::class.java)
        val connection=object :ServiceConnection{
            override fun onServiceDisconnected(name: ComponentName?) {

            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                if (service is SingleAudioPlayService.AudioPlayBinder){
                    singleAudioPlayService=service.audioPlayService
                }else if (service is MultiAudioPlayService.AudioPlayBinder){
                    audioPlayService=service.audioPlayService
                }
            }

        }
        bindService(audioIntent,connection,Context.BIND_AUTO_CREATE)
        bindService(singAudioIntent,connection,Context.BIND_AUTO_CREATE)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        isSingPlaying=singleAudioPlayService?.mediaPlayer?.isPlay()?:false
        isMultiplePlaying=audioPlayService?.mediaPlayers?.any { it.isPlay()==true }?:false

        if (keyCode==KeyEvent.KEYCODE_BACK) {
            if (isMultiplePlaying || isSingPlaying) {
                if (SPUtil.getInstance().getBoolean(Constants.SP_CLOSE_POPUP)) {
                    if (SPUtil.getInstance().getBoolean(Constants.SP_CLOSE_NOISE)) {
                        stopMusic()
                        finish()
                    } else {
                        finish()
                    }
                } else {
                    mExitNoisePopup.show(mViewPage, Gravity.CENTER)
                }
                return true
            } else {
                finish()
            }
        }
        return super.onKeyDown(keyCode, event)
    }

}