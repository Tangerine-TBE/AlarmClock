package com.example.module_weather.ui.fragment

import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.module_base.base.BaseVmFragment
import com.example.module_base.util.Constants
import com.example.module_base.util.DateUtil
import com.example.module_base.util.SpeakUtil
import com.example.module_base.util.top.toOtherActivity
import com.example.module_weather.R
import com.example.module_weather.databinding.FragmentCurrentCityBinding
import com.example.module_weather.domain.MjDesBean
import com.example.module_weather.domain.WeatherCacheInfo
import com.example.module_weather.domain.ZipWeatherBean
import com.example.module_weather.livedata.DistanceLiveData
import com.example.module_weather.livedata.DrawableLiveData
import com.example.module_weather.ui.activity.AirActivity
import com.example.module_weather.ui.activity.DayDetailsActivity
import com.example.module_weather.ui.activity.HuangLiActivity
import com.example.module_weather.ui.adapter.Mj15Adapter
import com.example.module_weather.ui.adapter.Mj24Adapter
import com.example.module_weather.ui.adapter.MjDesAdapter
import com.example.module_weather.ui.adapter.MjLifeAdapter
import com.example.module_weather.utils.*
import com.example.module_weather.viewmodel.CurrentCityViewModel
import com.google.gson.Gson
import com.scwang.smart.refresh.header.MaterialHeader
import com.tamsiree.rxkit.RxNetTool
import com.tamsiree.rxkit.view.RxToast
import java.util.*

/**
 * @name AlarmClock
 * @class name：com.example.module_weather.ui.fragment
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2021/2/18 10:50:18
 * @class describe
 */
class CurrentCityFragment : BaseVmFragment<FragmentCurrentCityBinding, CurrentCityViewModel>() {
    private val mj24Adapter by lazy {
        Mj24Adapter()
    }
    private val mjDesAdapter by lazy {
        MjDesAdapter()
    }


    private val mj15Adapter by lazy {
        Mj15Adapter()
    }

    private val mjLifeAdapter by lazy {
        MjLifeAdapter()
    }

    private var currentColor=0
    private var mCurrentCity = ""
    private var mLongitude = ""
    private var mLatitude = ""
    private var mWeatherInfo: ZipWeatherBean? = null
    private var mCurrentWea=""
    private var mCurrentLow=""
    private var mCurrentHigh=""



    override fun getViewModelClass(): Class<CurrentCityViewModel> {
        return CurrentCityViewModel::class.java
    }
    override fun getChildLayout(): Int = R.layout.fragment_current_city

    companion object {
        @JvmStatic
        fun getInstance(weatherInfo: WeatherCacheInfo): CurrentCityFragment {
            val skinTypeFragment = CurrentCityFragment()
            val bundle = Bundle()
            bundle.putString(Constants.LOCATION_CITY, weatherInfo.city)
            bundle.putString(Constants.LOCATION_LONGITUDE, weatherInfo.long)
            bundle.putString(Constants.LOCATION_LATITUDE, weatherInfo.lat)
            skinTypeFragment.arguments = bundle
            return skinTypeFragment
        }
    }



    fun onNestedScrollView() {
        binding?.apply {
            nestedScrollView.scrollTo(0, 0)
            if (SpeakUtil.isSpeaking()) {
                mDrawable.selectDrawable(0)
                mDrawable.stop()
            }
            SpeakUtil.stopSpeak()
        }
    }

    override fun initView() {
        arguments?.apply {
            mCurrentCity = getString(Constants.LOCATION_CITY, "")
            mLongitude = getString(Constants.LOCATION_LONGITUDE, "")
            mLatitude = getString(Constants.LOCATION_LATITUDE, "")
            if (!TextUtils.isEmpty(mCurrentCity) and !TextUtils.isEmpty(mLongitude) and !TextUtils.isEmpty(
                    mLatitude
                )
            ) {
                if (RxNetTool.isNetworkAvailable(requireContext())) {
                    viewModel.getWeatherMsg(
                        formatCity(mCurrentCity),
                        mLongitude,
                        mLatitude,
                        RequestState.NORMAL
                    )
                } else {
                    viewModel.getWeatherMsgCache(formatCity(mCurrentCity))
                }
            }
            viewModel.getHuangLiMsg()
        }

        binding.apply {
            //设置刷新头
            val materialHeader = MaterialHeader(activity)
            smartRefreshLayout.setRefreshHeader(materialHeader)

            rv24Container.layoutManager =
                LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            rv24Container.adapter = mj24Adapter

            rvDes.layoutManager = GridLayoutManager(context, 4)
            rvDes.adapter = mjDesAdapter

            rvLifeDes.layoutManager = GridLayoutManager(context, 4)
            rvLifeDes.adapter = mjLifeAdapter

            rvFifteenContainer.layoutManager =
                LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            rvFifteenContainer.adapter = mj15Adapter

        }
    }

    private val mDesList: MutableList<MjDesBean> = ArrayList<MjDesBean>()
    private val mLifeList: MutableList<MjDesBean> = ArrayList()
    private fun getYiJiData(list: List<String>): StringBuffer {
        val stringBuffer = StringBuffer()
        var realList: List<String> = if (list.size >= 9) {
            list.subList(2, 9)
        } else {
            list.subList(2, list.size)
        }
        for (s in realList) {
            stringBuffer.append("$s  ")
        }
        return stringBuffer
    }


    override fun observerData() {
        binding.apply {
            viewModel.apply {
                huangLiInfo.observe(this@CurrentCityFragment, {
                    tvHuangliDate.text = DateUtil.getDate2()
                    tvHuangliWeek.text = "星期" + it.week

                    val stringBuffer: StringBuffer = getYiJiData(it.yi)
                    tvYi.text = "$stringBuffer...查看更多"

                    val stringBuffer1: StringBuffer = getYiJiData(it.ji)
                    tvJi.text = "$stringBuffer1...查看更多"
                })


                weatherInfo.observe(this@CurrentCityFragment, { it ->
                    if (it.state==RequestState.REFRESH) {
                        smartRefreshLayout.finishRefresh()
                        RxToast.success("刷新成功！")
                    }
                    mWeatherInfo = it.msg
                    //实时天气
                    it.msg.realWeatherBean?.data?.condition?.apply {
                        tvHomeTeam.text = "${temp}℃"

                        //添加适配器数据
                        mDesList.clear()
                        mDesList.add(
                            MjDesBean(
                                windDir,
                                R.mipmap.home_icon_feng,
                                WeatherUtils.winType(windSpeed.toDouble(), true)
                            )
                        )
                        mDesList.add(
                            MjDesBean(
                                "湿度",
                                R.mipmap.home_icon_wendu,
                                WeatherUtils.formatHumidity(humidity)
                            )
                        )
                        mDesList.add(
                            MjDesBean(
                                "气压",
                                R.mipmap.home_icon_qiya,
                                pressure.toString() + "hPa"
                            )
                        )
                        mDesList.add(
                            MjDesBean(
                                "舒适度",
                                R.mipmap.home_icon_shushi,
                                realFeel
                            )
                        )
                        mjDesAdapter.setList(mDesList)
                    }

                    //15天
                    it.msg.day15Msg?.data?.forecast?.apply {
                        if (size > 0) {
                            val todayInfo = get(1)
                            mCurrentLow = addTemSymbol(todayInfo.tempNight)
                            mCurrentHigh = addTemSymbol(todayInfo.tempDay)
                            tvTodayLow.text = mCurrentLow
                            tvTodayHigh.text = mCurrentHigh

                            val tomorrowInfo = get(2)
                            tvTomorrowLow.text = addTemSymbol(tomorrowInfo.tempNight)
                            tvTomorrowHigh.text = addTemSymbol(tomorrowInfo.tempDay)

                            mj15Adapter.setList(subList(1, size))

                        }
                    }

                    //24小时
                    it.msg.day24Msg?.data?.hourly?.apply {
                        val hourlyBean = get(0)
                        mCurrentWea = "${hourlyBean.condition}"
                        tvHomeWea.text = mCurrentWea
                        currentColor =
                            if (hourlyBean.hour.toInt() in 6..18) WeatherUtils.selectDayIcon(
                                hourlyBean.iconDay
                            )[com.example.module_weather.utils.Constants.MJ_COLOR]!! else WeatherUtils.selectNightIcon(
                                hourlyBean.iconNight
                            )[com.example.module_weather.utils.Constants.MJ_COLOR]!!

                        val hour = Integer.valueOf(hourlyBean.hour)
                        if (hour in 6..18) {
                            ivTopBg.setImageResource(WeatherUtils.selectDayIcon(hourlyBean.iconDay)[com.example.module_weather.utils.Constants.MJ_BG]!!)
                            ivIconWeather.setImageResource(WeatherUtils.selectDayIcon(hourlyBean.iconDay)[com.example.module_weather.utils.Constants.MJ_LAGER_ICON]!!)
                        } else {
                            ivTopBg.setImageResource(WeatherUtils.selectNightIcon(hourlyBean.iconNight)[com.example.module_weather.utils.Constants.MJ_BG]!!)
                            ivIconWeather.setImageResource(WeatherUtils.selectNightIcon(hourlyBean.iconNight)[com.example.module_weather.utils.Constants.MJ_LAGER_ICON]!!)
                        }
                        if (size > 0) {
                            mj24Adapter.setList(this)
                        }


                    }

                    //生活指数
                    it.msg.lifeBean?.apply {
                        if (size > 0) {
                            mLifeList.clear()
                            forEach {
                                when {
                                    it.name.equals("紫外线指数") -> {
                                        tvHomeZwx.text = it.status
                                        mLifeList.add(
                                            MjDesBean(
                                                "紫外线",
                                                R.mipmap.home_icon_zwx,
                                                it.status
                                            )
                                        )
                                    }
                                    it.name.equals("穿衣指数") -> {
                                        mLifeList.add(
                                            MjDesBean(
                                                "舒适度",
                                                R.mipmap.home_icon_ssd,
                                                it.status
                                            )
                                        )
                                    }
                                    it.name.equals("洗车指数") -> {
                                        mLifeList.add(
                                            MjDesBean(
                                                "洗车",
                                                R.mipmap.home_icon_xc,
                                                it.status
                                            )
                                        )
                                    }
                                    it.name.equals("感冒指数") -> {
                                        mLifeList.add(
                                            MjDesBean(
                                                "感冒",
                                                R.mipmap.home_icon_gm,
                                                it.status
                                            )
                                        )
                                    }
                                }
                            }
                            mjLifeAdapter.setList(mLifeList)
                        }
                    }
                    //空气质量
                    it.msg.aqi5Bean?.data?.aqiForecast?.let {
                        if (it.size > 0) {
                            tvHomeAqi.text = WeatherUtils.aqiType(it[0].value)
                        }
                    }
                })

            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun initEvent() {
        binding.apply {
            tvCheckWea.setOnClickListener {
                toOtherActivity<AirActivity>(activity) {
                    mWeatherInfo?.let {
                        putExtra(
                            com.example.module_weather.utils.Constants.WEATHER_INFO,
                            Gson().toJson(it)
                        )
                    }

                }
            }
            tvHomeDetails.setOnClickListener {
                toOtherActivity<DayDetailsActivity>(activity) {
                    mWeatherInfo?.let {
                        putExtra(
                            com.example.module_weather.utils.Constants.WEATHER_INFO,
                            Gson().toJson(it)
                        )
                    }

                }
            }

            rlHuangliInclude.setOnClickListener {
                toOtherActivity<HuangLiActivity>(activity) {}
            }


            nestedScrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                DrawableLiveData.setTopDrawable(setBarColor())
                DistanceLiveData.setTopType(scrollY)
            }

            ivHomeRepo.setOnClickListener {
                val report = "${mCurrentCity},今天天气%s,气温%s到%s"
                SpeakUtil.speakText(String.format(report, mCurrentWea, mCurrentLow, mCurrentHigh))
                SpeakUtil.setOnSpeechListener(object : SpeakUtil.OnSpeechListener {
                    override fun onStart() {
                        mDrawable.start()
                    }

                    override fun onStop() {
                        mDrawable.selectDrawable(0)
                        mDrawable.stop()
                    }
                })
            }

            smartRefreshLayout.setOnRefreshListener {
                if (RxNetTool.isNetworkAvailable(requireContext())) {
                    if (!TextUtils.isEmpty(mCurrentCity) and !TextUtils.isEmpty(mLongitude) and !TextUtils.isEmpty(
                            mLatitude
                        )
                    ) {
                        viewModel.getWeatherMsg(
                            mCurrentCity,
                            mLongitude,
                            mLatitude,
                            RequestState.REFRESH
                        )
                        viewModel.getHuangLiMsg()
                    }
                } else {
                    smartRefreshLayout.finishRefresh()
                    RxToast.warning("网络连接不可用！")
                }
            }
        }
    }

    private val mDrawable by  lazy {
        binding.ivHomeRepo.drawable as AnimationDrawable
    }

    private fun setBarColor(): Drawable {
            return when (currentColor) {
                ColorUtil.CEHENGSE -> {
                    resources.getDrawable(R.color.bg_a, null)
                }
                ColorUtil.SHENLAN -> {
                    resources.getDrawable(R.color.bg_b, null)
                }
                ColorUtil.LAN -> {
                    resources.getDrawable(R.color.bg_c, null)
                }
                ColorUtil.HUILAN -> {
                    resources.getDrawable(R.color.bg_d, null)
                }
                ColorUtil.MAI -> {
                    resources.getDrawable(R.color.bg_e, null)
                }
                else -> {
                    resources.getDrawable(R.color.bg_a, null)
                }
            }
        }


}