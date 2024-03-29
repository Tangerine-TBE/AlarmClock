package com.twx.clock.ui.activity

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.location.LocationManager
import android.os.BatteryManager
import android.text.TextUtils
import android.view.WindowManager
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.twx.clock.R
import com.twx.clock.bean.ItemBean
import com.twx.clock.bean.SkinType
import com.twx.clock.repository.DataProvider
import com.twx.clock.topfun.setCurrentColor
import com.twx.clock.topfun.setTintImage
import com.twx.clock.topfun.textViewColorTheme
import com.twx.clock.ui.adapter.recyclerview.BottomAdapter
import com.twx.clock.ui.adapter.recyclerview.WeatherAdapter
import com.twx.clock.ui.widget.skin.calendar.ClockView
import com.twx.clock.ui.widget.skin.number.NumberClockView
import com.twx.clock.ui.widget.skin.watch.WatchFaceTwoView
import com.twx.clock.ui.widget.skin.watch.WatchFaceOneView
import com.twx.module_base.util.*
import com.twx.td_horoscope.base.MainBaseViewActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tamsiree.rxkit.RxDeviceTool
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.ui.activity
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/23 18:05
 * @class describe
 */
@Suppress("DEPRECATION")
class LockScreenViewActivity: MainBaseViewActivity() {
    private lateinit var mDataChange: IntentFilter
    private lateinit var mBottomAdapter: BottomAdapter
    private lateinit var mWeatherAdapter: WeatherAdapter
    private lateinit var mClockView: ClockView
    private val mChangeReceiver by lazy {
        DateChangeReceiver()
    }
    private lateinit var mNumberClockView: NumberClockView

    private val mWatchFaceOne by lazy {
        WatchFaceOneView(this)
    }

    private val mWatchFaceTwo by lazy {
        WatchFaceTwoView(this)
    }

    private val mGaoDeHelper by lazy {
        GaoDeHelper.getInstance().apply {
            setListener {
                if (it.longitude != 0.0 || it.latitude != 0.0) {
                    mLocation.text = it.city
                    mSPUtil.putString(Constants.LOCATION_CITY, it.city)
                    mSPUtil.putString(Constants.LOCATION_LONGITUDE, it.longitude.toString())
                    mSPUtil.putString(Constants.LOCATION_LATITUDE, it.latitude.toString())
                    mSPUtil.putString(Constants.LOCATION, it.address)
                    visible(mLocationInclude)
                }
            }
        }
    }

    override fun getLayoutView(): Int = R.layout.activity_main

    override fun setChildTheme() {
        requestedOrientation = if (mSPUtil.getBoolean(com.twx.clock.util.Constants.SET_SHOW_LANDSCAPE)) ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        else ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setLockerWindow()

    }

    /**
     * 显示在锁屏上面
     * @param window
     */
    private fun setLockerWindow() {
        window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or  //这个在锁屏状态下
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                        or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

    }

    override fun initView() {
        mSPUtil.putBoolean(com.twx.module_ad.utils.Contents.NO_BACK, true)
        visible(mSlideContainer)
        //设置当前时间、城市
        setCurrentDate()
        val currentCity = mSPUtil.getString(Constants.LOCATION_CITY)
        if (currentCity != null) {
            mLocation.text = currentCity
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
        mBottomContainer.layoutManager = if (RxDeviceTool.isPortrait(this))
            GridLayoutManager(this,5)
        else
            LinearLayoutManager(this, RecyclerView.VERTICAL,false)
        mBottomAdapter = BottomAdapter()
        mBottomAdapter.setList(DataProvider.bottomData)
        mBottomContainer.adapter = mBottomAdapter

        //天气
        mWeatherAdapter= WeatherAdapter()
        if (RxDeviceTool.isPortrait(this)) {
            mWeatherContainerOne.layoutManager=GridLayoutManager(this,2)
            mWeatherContainerTwo.layoutManager=GridLayoutManager(this,2)

        } else {
            mWeatherContainerOne.layoutManager=LinearLayoutManager(this)
            mWeatherContainerTwo.layoutManager=LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)
        }
        mWeatherContainerOne.adapter=mWeatherAdapter
        mWeatherContainerTwo.adapter=mWeatherAdapter
        val weatherData = mSPUtil.getString(com.twx.clock.util.Constants.SP_WEATHER_LIST)
        if (!TextUtils.isEmpty(weatherData)) {
            val list: List<ItemBean> = Gson().fromJson(weatherData,object : TypeToken<List<ItemBean>>() {}.type)
            list?.let {     mWeatherAdapter.setList(it) }
        }
        //设置顶部距离
        MarginStatusBarUtil.setStatusBar(this, mBatteryView, 0)
        mGaoDeHelper.startLocation()
        setCurrentThemeView()


    }



    override fun initEvent() {
        mScrollContainer.setOnSlideListener {
            finish()
        }
    }

    private fun setCurrentThemeView() {
        mSlideView.setSlideColor()
        lifecycle.addObserver(mSlideView)
        mClockContainer.removeAllViews()
        mNumberClockContainer.removeAllViews()
        val skinTypeStr = SPUtil.getInstance().getString(com.twx.clock.util.Constants.CURRENT_THEME)
        val skinTypeMsg = gsonHelper<SkinType>(skinTypeStr)
        if (skinTypeMsg != null) {
            skinTypeMsg.skin?.let {
                when (it.type) {
                    0 -> {
                        showNumberView()
                    }
                    1 -> {
                        showWatchView(skinTypeMsg)
                    }
                    2 -> {
                        showCalendarView()
                    }
                }
            }

        } else {
            showNumberView()
        }
        mWeatherAdapter.notifyDataSetChanged()
    }

    //表盘时钟
    private fun showWatchView(skinTypeMsg: SkinType) {
        val baseThemeView = when (skinTypeMsg.selectPosition) {
            0 -> mWatchFaceOne
            1 -> mWatchFaceTwo
            else -> mWatchFaceOne
        }
        lifecycle.addObserver(baseThemeView)
        mClockContainer.addView(baseThemeView)
        visible(mWeatherContainerOne)
        invisible(mWeatherContainerTwo)
    }

    //数字时钟
    private fun showNumberView() {
        mNumberClockView = NumberClockView(this)
        mNumberClockContainer.addView(mNumberClockView)
        lifecycle.addObserver(mNumberClockView)
        visible(mWeatherContainerTwo)
        invisible(mWeatherContainerOne)
    }

    //日历时钟
    private fun showCalendarView(){
        mClockView = ClockView(this)
        mNumberClockContainer.addView(mClockView)
        lifecycle.addObserver(mClockView)
        visible(mWeatherContainerTwo)
        invisible(mWeatherContainerOne)
    }



    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        MyStatusBarUtil.fullScreenWindow(hasFocus,this)
        if (RxDeviceTool.isPortrait(this)) {
            val layoutParams = mSlideContainer.layoutParams as RelativeLayout.LayoutParams
            layoutParams.bottomMargin=SizeUtils.dip2px(this,50f)
            mSlideContainer.layoutParams=layoutParams

        }
    }


    override fun onResume() {
        super.onResume()
        refreshTheme()
    }

    //刷新主题颜色
    private fun refreshTheme() {
        textViewColorTheme(mBatteryHint,mLocation,mData,mWeekMonth)
        mBottomAdapter.notifyDataSetChanged()
        mView_location.setTintImage()
        mBatteryView.setCurrentColor()
        setCurrentThemeView()
        invisible(mBottomContainer)
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
                Intent.ACTION_BATTERY_CHANGED -> {
                    val level = intent.getIntExtra("level", 0)
                    val scale = intent.getIntExtra("scale", 0)
                    LogUtils.i("电池剩余电量为:" + level * 100 / scale)
                    val currentPower = level * 100 / scale
                    mBatteryView.power = currentPower
                    mBatteryHint.text = "$currentPower%"
                    val state = intent.getIntExtra(
                        BatteryManager.EXTRA_STATUS,
                        BatteryManager.BATTERY_STATUS_UNKNOWN
                    )
                   // LogUtils.i("充电------------------${BatteryManager.BATTERY_STATUS_CHARGING}-----------------$state")
                    when (state) {
                        BatteryManager.BATTERY_STATUS_CHARGING -> {
                          //  LogUtils.i("充电中:$state")
                        }
                        BatteryManager.BATTERY_STATUS_FULL -> {
                         //   LogUtils.i("充电满电:$state")
                        }
                    }
                }
                Intent.ACTION_POWER_CONNECTED -> {
                    //RxToast.normal("正在充电------------------------------")

                }
                Intent.ACTION_POWER_DISCONNECTED -> {
                 //   RxToast.normal("已断开充电-----------------------------")
                }

            }
        }
    }


    //设置当前日期
    private fun setCurrentDate() {
        val formatStr = DateUtil.formatStr(DateUtil.getDate())
        mWeekMonth.text = if (RxDeviceTool.isPortrait(this))
            "${DateUtil.getWeekOfDate2(Date())}  ${DateUtil.getMonthStr(formatStr)}月${DateUtil.getDayStr(formatStr)}"
        else
            "${DateUtil.getMonthStr(formatStr)}月${DateUtil.getDayStr(formatStr)}  ${DateUtil.getWeekOfDate2(
                Date()
            )}"
        mData.text = DateUtil.getDate()
    }




    //释放资源
    override fun release() {
        unregisterReceiver(mChangeReceiver)
        mSPUtil.putBoolean(com.twx.module_ad.utils.Contents.NO_BACK, false)
    }

}

