package com.example.alarmclock.ui.activity

import android.R.string
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.location.LocationManager
import android.os.BatteryManager
import android.os.SystemClock
import android.text.TextUtils
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmclock.R
import com.example.alarmclock.bean.ItemBean
import com.example.alarmclock.bean.ZipWeatherBean
import com.example.alarmclock.broadcast.AlarmClockReceiver
import com.example.alarmclock.model.DataProvider
import com.example.alarmclock.present.impl.WeatherPresentImpl
import com.example.alarmclock.service.TellTimeService
import com.example.alarmclock.topfun.setBgResource
import com.example.alarmclock.topfun.setCurrentColor
import com.example.alarmclock.topfun.setThemeTextColor
import com.example.alarmclock.topfun.textViewColorTheme
import com.example.alarmclock.ui.adapter.recyclerview.BottomAdapter
import com.example.alarmclock.ui.adapter.recyclerview.WeatherAdapter
import com.example.alarmclock.ui.weight.NumberClockView
import com.example.alarmclock.ui.weight.WatchFaceOneView
import com.example.alarmclock.ui.weight.WatchFaceTwoView
import com.example.alarmclock.util.ClockUtil
import com.example.alarmclock.util.MarginStatusBarUtil
import com.example.alarmclock.view.IWeatherCallback
import com.example.module_base.util.Constants
import com.example.module_base.util.DateUtil
import com.example.module_base.util.GaoDeHelper
import com.example.module_base.util.LogUtils
import com.example.module_base.util.top.toOtherActivity
import com.example.td_horoscope.base.MainBaseActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tamsiree.rxkit.RxConstTool
import com.tamsiree.rxkit.RxDeviceTool
import com.tamsiree.rxkit.RxTimeTool
import com.tamsiree.rxkit.view.RxToast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.litepal.LitePal
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : MainBaseActivity(), IWeatherCallback {

    private lateinit var mDataChange: IntentFilter
    private lateinit var mBottomAdapter: BottomAdapter
    private lateinit var mWeatherAdapter: WeatherAdapter
    private val mChangeReceiver by lazy {
        DateChangeReceiver()
    }
    private lateinit var mNumberClockView:NumberClockView

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
                    WeatherPresentImpl.getWeatherInfo(it.longitude.toString(),it.latitude.toString())

                }
            }
        }
    }

    override fun getLayoutView(): Int = R.layout.activity_main

    override fun initView() {
        LitePal.getDatabase()
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
        val weatherData = mSPUtil.getString(com.example.alarmclock.util.Constants.SP_WEATHER_LIST)
        if (!TextUtils.isEmpty(weatherData)) {
            val list: List<ItemBean> = Gson().fromJson(weatherData,object : TypeToken<List<ItemBean>>() {}.type)
            list?.let {     mWeatherAdapter.setList(it) }
        }


        //设置顶部距离
        MarginStatusBarUtil.setStatusBar(this, mBatteryView, 0)
        setCurrentThemeView()
    }

    private fun setCurrentThemeView() {
        mClockContainer.removeAllViews()
        mNumberClockContainer.removeAllViews()
       when (mSPUtil.getInt(com.example.alarmclock.util.Constants.CURRENT_THEME)) {
            in 0..1 ->{
                mNumberClockView=NumberClockView(this)
                if (mSPUtil.getBoolean(com.example.alarmclock.util.Constants.SET_SHOW_LANDSCAPE)) mClockContainer.addView(mNumberClockView)
                 else mNumberClockContainer.addView(mNumberClockView)
                lifecycle.addObserver(mNumberClockView)
                visible(mWeatherContainerTwo)
                invisible(mWeatherContainerOne)

            }
            2 -> {
                mClockContainer.addView(mWatchFaceOne)
                lifecycle.addObserver(mWatchFaceOne)
                visible(mWeatherContainerOne)
                invisible(mWeatherContainerTwo)
            }
            3 ->{
                mClockContainer.addView(mWatchFaceTwo)
                lifecycle.addObserver(mWatchFaceTwo)
                visible(mWeatherContainerOne)
                invisible(mWeatherContainerTwo)
            }


        }

        mWeatherAdapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        refreshTheme()

    }

    //刷新主题颜色
    private fun refreshTheme() {
        textViewColorTheme(mBatteryHint,mLocation,mData,mWeekMonth)
        mBottomAdapter.notifyDataSetChanged()
        view_location.setBgResource()
        mBatteryView.setCurrentColor()
        setCurrentThemeView()
        requestedOrientation = if (mSPUtil.getBoolean(com.example.alarmclock.util.Constants.SET_SHOW_LANDSCAPE)) ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
         else ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

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
                  //  LogUtils.i("电池剩余电量为:" + level * 100 / scale)
                    val currentPower = level * 100 / scale
                    mBatteryView.power = currentPower
                    mBatteryHint.text = "$currentPower%"
                    val state = intent.getIntExtra(
                        BatteryManager.EXTRA_STATUS,
                        BatteryManager.BATTERY_STATUS_UNKNOWN
                    )
                  //  LogUtils.i("充电------------------${BatteryManager.BATTERY_STATUS_CHARGING}-----------------$state")
                    when (state) {
                        BatteryManager.BATTERY_STATUS_CHARGING -> {
                     //       LogUtils.i("充电中:$state")
                        }
                        BatteryManager.BATTERY_STATUS_FULL -> {
                     //       LogUtils.i("充电满电:$state")
                        }
                    }
                }
                Intent.ACTION_POWER_CONNECTED -> {
                   // RxToast.normal("正在充电------------------------------")

                }
                Intent.ACTION_POWER_DISCONNECTED -> {
                 //   RxToast.normal("已断开充电-----------------------------")
                }

            }
        }
    }

    override fun initLoadData() {
        WeatherPresentImpl.registerCallback(this)
        mGaoDeHelper.startLocation()

    }

    override fun initEvent() {
        mBottomAdapter.setOnItemClickListener { adapter, view, position ->
            when (position) {
                0 -> toOtherActivity<SettingActivity>(this@MainActivity, false) {}
                1 -> toOtherActivity<SkinActivity>(this@MainActivity, false) {}
                2 -> toOtherActivity<ClockActivity>(this@MainActivity, false) {}
                3 -> toOtherActivity<TellTimeActivity>(this@MainActivity, false) {}
                4 -> toOtherActivity<MoreActivity>(this@MainActivity, false) {}
            }
        }
    }


    //设置当前日期
    private fun setCurrentDate() {
        val formatStr = DateUtil.formatStr(DateUtil.getDate())
        mWeekMonth.text = if (RxDeviceTool.isPortrait(this))
            "${DateUtil.getWeekOfDate2(Date())}  ${DateUtil.getMonthStr(formatStr)}月${DateUtil.getDayStr(formatStr)}"
        else
            "${DateUtil.getMonthStr(formatStr)}月${DateUtil.getDayStr(formatStr)}  ${DateUtil.getWeekOfDate2(Date())}"
        mData.text = DateUtil.getDate()
    }


    //释放资源
    override fun release() {
        unregisterReceiver(mChangeReceiver)
        WeatherPresentImpl.unregisterCallback(this)
    }

    private val mWeatherList:MutableList<ItemBean>?=ArrayList()
    override fun onLoadWeather(data: ZipWeatherBean) {
        mWeatherList?.apply {
            clear()
            val realWeatherBean = data.realWeatherBean.data.condition
            val aqi = data.aqiBean?.data?.aqi?.value ?: "优"
            add(ItemBean(title =realWeatherBean.temp+"°C"))
            add(ItemBean(title =realWeatherBean.windDir))
            add(ItemBean(title =aqi))
            add(ItemBean(title =realWeatherBean.pressure+"PHA"))
        }
        mWeatherAdapter.setList(mWeatherList)
        mSPUtil.putString(com.example.alarmclock.util.Constants.SP_WEATHER_LIST,Gson().toJson(mWeatherList))
    }


    override fun onLoadError(str: String) {

    }


}