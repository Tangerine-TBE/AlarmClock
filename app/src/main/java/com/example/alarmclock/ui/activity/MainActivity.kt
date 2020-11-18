package com.example.alarmclock.ui.activity

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.os.BatteryManager
import android.os.Handler
import android.os.Message
import androidx.recyclerview.widget.GridLayoutManager
import com.example.alarmclock.R
import com.example.alarmclock.bean.ItemBean
import com.example.alarmclock.model.DataProvider
import com.example.alarmclock.service.TellTimeService
import com.example.alarmclock.ui.adapter.recyclerview.BottomAdapter
import com.example.alarmclock.ui.weight.NumberClock
import com.example.alarmclock.util.MarginStatusBarUtil
import com.example.module_base.util.Constants
import com.example.module_base.util.DateUtil
import com.example.module_base.util.GaoDeHelper
import com.example.module_base.util.LogUtils
import com.example.module_base.util.top.toOtherActivity
import com.example.td_horoscope.base.MainBaseActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tamsiree.rxkit.RxTimeTool
import com.tamsiree.rxkit.view.RxToast
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread


class MainActivity : MainBaseActivity() {

    private lateinit var mDataChange: IntentFilter
    private lateinit var mBottomAdapter: BottomAdapter
    private val mChangeReceiver by lazy {
        DateChangeReceiver()
    }


    private val  mNumberClock by lazy {
        NumberClock(this)
    }
    private val mGaoDeHelper by lazy {
        GaoDeHelper.getInstance().apply {
              setListener {
                      if (it.longitude!=0.0|| it.latitude!=0.0 ){
                          mLocation.text=it.city
                          mSPUtil.putString(Constants.LOCATION_CITY,it.city)
                          mSPUtil.putString(Constants.LOCATION_LONGITUDE,it.longitude.toString())
                          mSPUtil.putString(Constants.LOCATION_LATITUDE,it.latitude.toString())
                          mSPUtil.putString(Constants.LOCATION,it.address)
                          visible(mLocationInclude)
                      }
              }
        }
    }




    override fun getLayoutView(): Int =R.layout.activity_main

    override fun initView() {
        startService(Intent(this,TellTimeService::class.java))
        //设置当前时间、城市
        setCurrentDate()
        val currentCity = mSPUtil.getString(Constants.LOCATION_CITY)
        if (currentCity!= null) {
            mLocation.text=currentCity
            visible(mLocationInclude)
        }
        //变化广播
        mDataChange = IntentFilter().apply {
            addAction(Intent.ACTION_DATE_CHANGED)
            addAction(LocationManager.PROVIDERS_CHANGED_ACTION)
            addAction(Intent.ACTION_BATTERY_CHANGED)
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
        }
        registerReceiver(mChangeReceiver, mDataChange)
        //底部栏
        mBottomContainer.layoutManager = GridLayoutManager(this,5)
        mBottomAdapter=BottomAdapter()
        mBottomAdapter.setList(DataProvider.bottomData)
        mBottomContainer.adapter=mBottomAdapter

        //设置顶部距离
        MarginStatusBarUtil.setStatusBar(this, mBatteryView, 0)
        mGaoDeHelper.startLocation()
        mClockContainer.addView(mNumberClock)

    }
    //广播监听
    inner class DateChangeReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                Intent.ACTION_DATE_CHANGED -> setCurrentDate()
                LocationManager.PROVIDERS_CHANGED_ACTION -> {
                    val lm = context.getSystemService(Service.LOCATION_SERVICE) as LocationManager
                    if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) mGaoDeHelper.startLocation()
                }
                Intent.ACTION_BATTERY_CHANGED->{
                    val level = intent.getIntExtra("level", 0)
                    val scale = intent.getIntExtra("scale", 0)
                    LogUtils.i("电池剩余电量为:" + level * 100 / scale )
                    val currentPower = level * 100 / scale
                    mBatteryView.power=currentPower
                    mBatteryHint.text= "$currentPower%"
                    val state = intent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN)
                    LogUtils.i("充电------------------${BatteryManager.BATTERY_STATUS_CHARGING}-----------------$state")
                    when (state) {
                        BatteryManager.BATTERY_STATUS_CHARGING->{
                            LogUtils.i("充电中:$state")
                        }
                        BatteryManager.BATTERY_STATUS_FULL->{
                            LogUtils.i("充电满电:$state")
                        }
                    }
                }
                Intent.ACTION_POWER_CONNECTED->{
                   RxToast.normal("正在充电------------------------------")

                }
                Intent.ACTION_POWER_DISCONNECTED->{
                    RxToast.normal("已断开充电-----------------------------")
                }

            }
        }
    }

    override fun initLoadData() {

        thread(start = true) {
            try {
                while (true) {
                    val message = Message()
                    message.what = 1
                    mHandler.sendMessage(message)
                    Thread.sleep(1000)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    override fun initEvent() {
        mBottomAdapter.setOnItemClickListener { adapter, view, position ->
            when(position){
                0-> toOtherActivity<SettingActivity>(this@MainActivity,false){}
                1-> toOtherActivity<SkinActivity>(this@MainActivity,false){}
                2-> toOtherActivity<ClockActivity>(this@MainActivity,false){}
                3-> toOtherActivity<TellTimeActivity>(this@MainActivity,false){}
                4-> toOtherActivity<MoreActivity>(this@MainActivity,false){}
            }
        }
    }

    private val mHandler =
        object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                when (msg.what) {
                    1 -> {
                        val currentTime = RxTimeTool.milliseconds2String(
                            System.currentTimeMillis(),
                            SimpleDateFormat("HH:mm:ss")
                        )
                        mNumberClock.setTime(currentTime)
                    }
                }
            }
        }


    //设置当前日期
    private fun setCurrentDate() {
        val formatStr = DateUtil.formatStr(DateUtil.getDate())
        mWeekMonth.text =
            "${DateUtil.getWeekOfDate2(Date())}  ${DateUtil.getMonthStr(formatStr)}月${DateUtil.getDayStr(
                formatStr
            )}"
        mData.text = DateUtil.getDate()
    }


    //释放资源
    override fun release() {
        unregisterReceiver(mChangeReceiver)
    }


}