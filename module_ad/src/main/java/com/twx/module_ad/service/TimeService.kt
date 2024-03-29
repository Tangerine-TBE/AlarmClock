package com.twx.module_ad.service

import android.content.Intent
import android.os.Binder
import android.os.CountDownTimer
import android.os.IBinder
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import com.twx.module_ad.advertisement.AdType
import com.twx.module_ad.utils.Contents

class TimeService :LifecycleService() {
    private var mCountDownTimer:CountDownTimer?=null
    val showSkin=MutableLiveData<Boolean>(true)
    val showSet=MutableLiveData<Boolean>(true)
    val showMore=MutableLiveData<Boolean>(true)


    inner class MyBinder:Binder(){
        val getService=this@TimeService
    }
    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
       return  MyBinder()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let { it ->
            val adType = it.getStringExtra(Contents.AD_TYPE)
            val times = it.getIntExtra(Contents.AD_TIMES,0)
            adType?.let {
                mCountDownTimer = object : CountDownTimer(times * 1000L, 1000) {
                    override fun onFinish() {
                        when(AdType.valueOf(it)){
                            AdType.SKIN_PAGE-> showSkin.value=true
                            AdType.SETTING_PAGE-> showSet.value=true
                            AdType.MORE_PAGE-> showMore.value=true
                        }

                    }
                    override fun onTick(millisUntilFinished: Long) {
                        //LogUtils.i("-*--------------$adType---------------------$millisUntilFinished----------")
                        when(AdType.valueOf(it)){
                            AdType.SKIN_PAGE-> showSkin.value=false
                            AdType.SETTING_PAGE-> showSet.value=false
                            AdType.MORE_PAGE-> showMore.value=false
                        }
                    }
                }.start()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }



    override fun onDestroy() {
        super.onDestroy()
        mCountDownTimer?.cancel()
    }


}