package com.twx.clock.ui.activity

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
import com.twx.clock.R
import com.twx.clock.bean.SkinType
import com.twx.clock.databinding.ActivityMainBinding
import com.twx.clock.repository.DataProvider
import com.twx.clock.topfun.orientationAction
import com.twx.clock.topfun.setCurrentColor
import com.twx.clock.topfun.setTintImage
import com.twx.clock.topfun.textViewColorTheme
import com.twx.clock.ui.adapter.recyclerview.BottomAdapter
import com.twx.clock.ui.adapter.recyclerview.WeatherAdapter
import com.twx.clock.ui.widget.BottomSlideView
import com.twx.clock.ui.widget.popup.ExitPoPupWindow
import com.twx.clock.ui.widget.skin.calendar.ClockView
import com.twx.clock.ui.widget.skin.number.NumberClockView
import com.twx.clock.ui.widget.skin.watch.WatchFaceTwoView
import com.twx.clock.ui.widget.skin.watch.WatchFaceOneView
import com.twx.clock.util.AnimationUtil
import com.twx.clock.util.CheckPermissionUtil
import com.twx.clock.util.GeneralState
import com.twx.clock.util.TimerUtil
import com.twx.clock.viewmodel.MainViewModel
import com.twx.module_ad.utils.BaseBackstage
import com.twx.module_base.base.BaseVmViewViewActivity
import com.twx.module_base.provider.ModuleProvider
import com.twx.module_base.util.*
import com.twx.module_base.util.top.toOtherActivity
import com.twx.module_calendar.ui.activity.CalendarActivity
import com.twx.module_weather.ui.activity.WeatherActivity
import com.feisu.noise.ui.MainActivity
import com.tamsiree.rxkit.view.RxToast


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
            AnimationUtil.setOutTranslationAnimation(binding.mBottomContainer)
            AnimationUtil.showAlphaAnimation(binding.mSlideContainer)
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
        binding.apply {
           val that=this@MainViewActivity
            //开启后台广告
            mSPUtil.putBoolean(com.twx.module_ad.utils.Contents.NO_BACK, false)
            //设置顶部距离
            MarginStatusBarUtil.setStatusBar(that, mBatteryView, 0)
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
            orientationAction(that, {
                mBottomContainer.layoutManager = GridLayoutManager(that, 5)
            }, {
                mBottomContainer.layoutManager = LinearLayoutManager(that, RecyclerView.VERTICAL, false)
            })
            mBottomAdapter = BottomAdapter()
            mBottomAdapter.setList(DataProvider.bottomData)
            mBottomContainer.adapter = mBottomAdapter

            //天气横竖屏不同设置
            orientationAction(that, {
                mWeatherContainerOne.layoutManager = GridLayoutManager(that, 2)
                mWeatherContainerTwo.layoutManager = GridLayoutManager(that, 2)
            }, {
                mWeatherContainerOne.layoutManager = LinearLayoutManager(that)
                mWeatherContainerTwo.layoutManager = LinearLayoutManager(that, RecyclerView.HORIZONTAL, false)
            })
            mWeatherContainerOne.adapter = mWeatherAdapter
            mWeatherContainerTwo.adapter = mWeatherAdapter
        }

        //获取天气缓存
        viewModel.getWeatherCache()
        //定位
        viewModel.startLocation()

        setCurrentThemeView()
        mCheckLocationPermissionTimer.start()



    }



    override fun observerData() {
        binding.apply {
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

    }

    //设置主题View
    private fun setCurrentThemeView() {
        binding.apply {
            mSlideView.setSlideColor()
            lifecycle.addObserver(mSlideView)
            mClockContainer.removeAllViews()
            mNumberClockContainer.removeAllViews()
        }
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



    //数字时钟
    private fun showNumberView() {
        binding.apply {
            mNumberClockView = NumberClockView(this@MainViewActivity)
            mNumberClockContainer.addView(mNumberClockView)
            lifecycle.addObserver(mNumberClockView)
            visible(mWeatherContainerTwo)
            invisible(mWeatherContainerOne)
        }

    }
    //表盘时钟
    private fun showWatchView(skinTypeMsg: SkinType) {
        binding.apply {
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

    }

    //日历时钟
    private fun showCalendarView(){
        binding.apply {
            mClockView = ClockView(this@MainViewActivity)
            mNumberClockContainer.addView(mClockView)
            lifecycle.addObserver(mClockView)
            visible(mWeatherContainerTwo)
            invisible(mWeatherContainerOne)
        }
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
        binding.apply {
            requestedOrientation = if (mSPUtil.getBoolean(com.twx.clock.util.Constants.SET_SHOW_LANDSCAPE)) ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            else ActivityInfo.SCREEN_ORIENTATION_SENSOR
            textViewColorTheme(mBatteryHint, mLocation, mData, mWeekMonth)
            mBottomAdapter.notifyDataSetChanged()
            mViewLocation.setTintImage()
            mBatteryView.setCurrentColor()
            setCurrentThemeView()
        }


    }

    override fun initEvent() {
        binding.apply {
            val that=this@MainViewActivity

            mData.setOnClickListener {
                toOtherActivity<CalendarActivity>(that){}
            }

            mWeekMonth.setOnClickListener {
                toOtherActivity<CalendarActivity>(that){}
            }

            mBottomAdapter.setOnItemClickListener { adapter, view, position ->
                when (position) {
                    0 -> toOtherActivity<SettingViewActivity>(that, false) {}
                    1 -> toOtherActivity<SkinViewActivity>(that, false) {}
                    2 -> {
                        CheckPermissionUtil.checkRuntimePermission(that, DataProvider.clockPermission, true, {}) {
                            toOtherActivity<ClockViewActivity>(that, false) {}
                        }

                    }
                    3 -> {
                        CheckPermissionUtil.checkRuntimePermission(that, DataProvider.clockPermission, true, {}) {
                            toOtherActivity<TellTimeViewActivity>(that, false) {}
                        }
                    }
                    4 -> toOtherActivity<MoreViewActivity>(that, false) {}
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
                        toOtherActivity<WeatherActivity>(that){ putExtra(ModuleProvider.CURRENT_CITY_NAME,mCurrentCity)}
                    }
                    4 -> {
                        toOtherActivity<MainActivity>(that){ }
                    }
                }
            }
        }


    }


    private fun bottomChangeAction() {
        binding.apply {
            orientationAction(this@MainViewActivity, {}, {
                AnimationUtil.setParentAnimation(mHomeContainer)
                AnimationUtil.setChildAnimation(mBottomContainer)
                AnimationUtil.push = !AnimationUtil.push
                if (!AnimationUtil.push)
                    mRightCountDownTimer?.start()
                else
                    mRightCountDownTimer?.cancel()
            })
        }

    }


    //设置当前日期
    private fun setCurrentDate() {
        binding.apply {
            val formatStr = DateUtil.formatStr(DateUtil.getDate())
            orientationAction(this@MainViewActivity, {
                mWeekMonth.text = "${DateUtil.getWeekOfDate2(Date())}  ${DateUtil.getMonthStr(formatStr)}月${DateUtil.getDayStr(formatStr)}"
            }, {
                mWeekMonth.text = "${DateUtil.getMonthStr(formatStr)}月${DateUtil.getDayStr(formatStr)}  ${DateUtil.getWeekOfDate2(Date())}"
            })
            mData.text = DateUtil.getDate()
        }

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
                    binding.mBatteryView.power = currentPower
                    binding.mBatteryHint.text = "$currentPower%"
                }
            }
        }
    }


    //释放资源
    override fun release() {
        mExitPoPupWindow.dismiss()
        unregisterReceiver(mChangeReceiver)
        mBottomCountDownTimer?.cancel()
        mRightCountDownTimer?.cancel()
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) { //如果返回键按下
            //此处写退向后台的处理
            mExitPoPupWindow.show(binding.mScrollContainer, Gravity.BOTTOM)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


}