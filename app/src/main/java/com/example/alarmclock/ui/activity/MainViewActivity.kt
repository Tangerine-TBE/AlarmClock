package com.example.alarmclock.ui.activity

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.location.LocationManager
import android.view.Gravity
import android.view.KeyEvent
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmclock.R
import com.example.alarmclock.bean.SkinType
import com.example.alarmclock.databinding.ActivityMainBinding
import com.example.alarmclock.repository.DataProvider
import com.example.alarmclock.topfun.orientationAction
import com.example.alarmclock.topfun.setCurrentColor
import com.example.alarmclock.topfun.setTintImage
import com.example.alarmclock.topfun.textViewColorTheme
import com.example.alarmclock.ui.adapter.recyclerview.BottomAdapter
import com.example.alarmclock.ui.adapter.recyclerview.WeatherAdapter
import com.example.alarmclock.ui.widget.BottomSlideView
import com.example.alarmclock.ui.widget.popup.ExitPoPupWindow
import com.example.alarmclock.ui.widget.skin.calendar.ClockView
import com.example.alarmclock.ui.widget.skin.number.NumberClockView
import com.example.alarmclock.ui.widget.skin.watch.WatchFaceTwoView
import com.example.alarmclock.ui.widget.skin.watch.WatchFaceOneView
import com.example.alarmclock.util.AnimationUtil
import com.example.alarmclock.util.CheckPermissionUtil
import com.example.alarmclock.util.GeneralState
import com.example.alarmclock.util.TimerUtil
import com.example.alarmclock.viewmodel.MainViewModel
import com.example.module_ad.utils.BaseBackstage
import com.example.module_base.base.BaseVmViewViewActivity
import com.example.module_base.provider.ModuleProvider
import com.example.module_base.util.*
import com.example.module_base.util.top.toOtherActivity
import com.example.module_weather.ui.activity.WeatherActivity
import com.tamsiree.rxkit.view.RxToast
import kotlinx.android.synthetic.main.activity_main.*

import java.util.*


class MainViewActivity : BaseVmViewViewActivity<ActivityMainBinding, MainViewModel>() {

    private lateinit var mBottomAdapter: BottomAdapter
    private val mWeatherAdapter by lazy { WeatherAdapter() }
    private val mChangeReceiver by lazy { DateChangeReceiver() }
    private val mExitPoPupWindow by lazy { ExitPoPupWindow(this) }
    private val mWatchFaceOne by lazy { WatchFaceOneView(this) }
    private val mWatchFaceTwo by lazy { WatchFaceTwoView(this) }
    private var mCurrentCity=""
    private lateinit var mNumberClockView: NumberClockView
    private lateinit var mClockView: ClockView
    private val mRightCountDownTimer by lazy {
        TimerUtil.startCountDown(5000, 1000) {
            bottomChangeAction()
        }
    }

    private val mBottomCountDownTimer by lazy {
        TimerUtil.startCountDown(10000, 1000) {
            AnimationUtil.setOutTranslationAnimation(mBottomContainer)
            AnimationUtil.showAlphaAnimation(mSlideContainer)
        }
    }

    private val mCheckLocationPermissionTimer by lazy {
        TimerUtil.startCountDown(1000, 1000) {
            orientationAction(this, {
                CheckPermissionUtil.checkRuntimePermission(this, DataProvider.locationPermission, true, { RxToast.warning("没有定位权限，我们将无法为您提供准确的天气信息") }, {})
            }, {})
        }
    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        orientationAction(this, {}, { MyStatusBarUtil.fullScreenWindow(hasFocus, this)})
    }

    override fun getViewModelClass(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    override fun getLayoutView(): Int = R.layout.activity_main
    override fun initView() {
        //开启后台广告
        mSPUtil.putBoolean(com.example.module_ad.utils.Contents.NO_BACK, false)
        //设置顶部距离
        MarginStatusBarUtil.setStatusBar(this, mBatteryView, 0)
        //设置当前时间、城市
        setCurrentDate()
        val currentCity = mSPUtil.getString(Constants.LOCATION_CITY)
        if (currentCity != null) {
            mLocation.text = currentCity
            visible(mLocationInclude)
        }
        //监听广播
        IntentFilter().apply {
            addAction(Intent.ACTION_DATE_CHANGED)
            addAction(LocationManager.PROVIDERS_CHANGED_ACTION)
            addAction(Intent.ACTION_BATTERY_CHANGED)
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
            registerReceiver(mChangeReceiver, this)
        }
        //底部栏
        orientationAction(this, {
            mBottomContainer.layoutManager = GridLayoutManager(this, 5)
        }, {
            mBottomContainer.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        })
        mBottomAdapter = BottomAdapter()
        mBottomAdapter.setList(DataProvider.bottomData)
        mBottomContainer.adapter = mBottomAdapter

        //天气横竖屏不同设置
        orientationAction(this, {
            mWeatherContainerOne.layoutManager = GridLayoutManager(this, 2)
            mWeatherContainerTwo.layoutManager = GridLayoutManager(this, 2)
        }, {
            mWeatherContainerOne.layoutManager = LinearLayoutManager(this)
            mWeatherContainerTwo.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        })
        mWeatherContainerOne.adapter = mWeatherAdapter
        mWeatherContainerTwo.adapter = mWeatherAdapter
        //获取天气缓存
        viewModel.getWeatherCache()
        //定位
        viewModel.startLocation()

        setCurrentThemeView()
        mCheckLocationPermissionTimer.start()

    }



    override fun observerData() {
        viewModel.apply {
            locationMsg.observe(this@MainViewActivity, {
                when (it.state) {
                    GeneralState.SUCCESS -> {
                        mCurrentCity=it.msg
                        mLocation.text =it.msg
                        visible(mLocationInclude)
                    }
                }
            })

            weatherMsg.observe(this@MainViewActivity, {
                mWeatherAdapter.setList(it)
            })

        }
    }

    //设置主题View
    private fun setCurrentThemeView() {
        mSlideView.setSlideColor()
        lifecycle.addObserver(mSlideView)
        mClockContainer.removeAllViews()
        mNumberClockContainer.removeAllViews()
        val skinTypeStr = SPUtil.getInstance().getString(com.example.alarmclock.util.Constants.CURRENT_THEME)
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



    //数字时钟
    private fun showNumberView() {
        mNumberClockView = NumberClockView(this)
        mNumberClockContainer.addView(mNumberClockView)
        lifecycle.addObserver(mNumberClockView)
        visible(mWeatherContainerTwo)
        invisible(mWeatherContainerOne)
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

    //日历时钟
    private fun showCalendarView(){
        mClockView = ClockView(this)
        mNumberClockContainer.addView(mClockView)
        lifecycle.addObserver(mClockView)
        visible(mWeatherContainerTwo)
        invisible(mWeatherContainerOne)
    }




    override fun onResume() {
        super.onResume()
        refreshTheme()
        orientationAction(this, { mBottomCountDownTimer?.start() }, {})
    }

    override fun onPause() {
        super.onPause()
        orientationAction(this, { mBottomCountDownTimer?.cancel() }, { mRightCountDownTimer?.cancel() })
        mCheckLocationPermissionTimer.cancel()
    }

    override fun onStop() {
        super.onStop()
        BaseBackstage.isExit = false
    }

    //刷新主题颜色
    private fun refreshTheme() {
        requestedOrientation = if (mSPUtil.getBoolean(com.example.alarmclock.util.Constants.SET_SHOW_LANDSCAPE)) ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        else ActivityInfo.SCREEN_ORIENTATION_SENSOR
        textViewColorTheme(mBatteryHint, mLocation, mData, mWeekMonth)
        mBottomAdapter.notifyDataSetChanged()
        mView_location.setTintImage()
        mBatteryView.setCurrentColor()
        setCurrentThemeView()

    }

    override fun initEvent() {
        mBottomAdapter.setOnItemClickListener { adapter, view, position ->
            when (position) {
                0 -> toOtherActivity<SettingViewActivity>(this@MainViewActivity, false) {}
                1 -> toOtherActivity<SkinViewActivity>(this@MainViewActivity, false) {}
                2 -> {
                    CheckPermissionUtil.checkRuntimePermission(this, DataProvider.clockPermission, true, {}) {
                        toOtherActivity<ClockViewActivity>(this@MainViewActivity, false) {}
                    }

                }
                3 -> {
                    CheckPermissionUtil.checkRuntimePermission(this, DataProvider.clockPermission, true, {}) {
                        toOtherActivity<TellTimeViewActivity>(this@MainViewActivity, false) {}
                    }
                }
                4 -> toOtherActivity<MoreViewActivity>(this@MainViewActivity, false) {}
            }
            bottomChangeAction()
        }


        mSlideContainer.setOnDownListener(object : BottomSlideView.OnDownListener {
            override fun onDown() {
                AnimationUtil.setInTranslationAnimation(mBottomContainer)
                mBottomCountDownTimer.start()
                gone(mSlideContainer)
            }
        })

        mHomeContainer.setOnClickListener {
            bottomChangeAction()
        }

        mWeatherAdapter.setOnItemClickListener { adapter, view, position ->
            when (position) {
                in 0..3 -> {
                    toOtherActivity<WeatherActivity>(this){ putExtra(ModuleProvider.CURRENT_CITY_NAME,mCurrentCity)}
                }
                4 -> {
                }
            }
        }

    }


    private fun bottomChangeAction() {
        orientationAction(this, {}, {
            AnimationUtil.setParentAnimation(mHomeContainer)
            AnimationUtil.setChildAnimation(mBottomContainer)
            AnimationUtil.push = !AnimationUtil.push
            if (!AnimationUtil.push)
                mRightCountDownTimer?.start()
            else
                mRightCountDownTimer?.cancel()
        })
    }


    //设置当前日期
    private fun setCurrentDate() {
        val formatStr = DateUtil.formatStr(DateUtil.getDate())
        orientationAction(this, {
            mWeekMonth.text = "${DateUtil.getWeekOfDate2(Date())}  ${DateUtil.getMonthStr(formatStr)}月${DateUtil.getDayStr(formatStr)}"
        }, {
            mWeekMonth.text = "${DateUtil.getMonthStr(formatStr)}月${DateUtil.getDayStr(formatStr)}  ${DateUtil.getWeekOfDate2(Date())}"
        })
        mData.text = DateUtil.getDate()
    }


    //广播监听
    inner class DateChangeReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                Intent.ACTION_DATE_CHANGED -> setCurrentDate()
                LocationManager.PROVIDERS_CHANGED_ACTION -> {
                    val lm = context.getSystemService(Service.LOCATION_SERVICE) as LocationManager
                    if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) viewModel.startLocation()
                }
                Intent.ACTION_BATTERY_CHANGED -> {
                    val level = intent.getIntExtra("level", 0)
                    val scale = intent.getIntExtra("scale", 0)
                    val currentPower = level * 100 / scale
                    mBatteryView.power = currentPower
                    mBatteryHint.text = "$currentPower%"
                }
            }
        }
    }


    //释放资源
    override fun release() {
        unregisterReceiver(mChangeReceiver)
        mBottomCountDownTimer?.cancel()
        mRightCountDownTimer?.cancel()
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) { //如果返回键按下
            //此处写退向后台的处理
            mExitPoPupWindow.show(mScrollContainer, Gravity.BOTTOM)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


}