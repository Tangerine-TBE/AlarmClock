package com.example.module_weather.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.module_base.base.BaseVmFragment
import com.example.module_base.util.Constants
import com.example.module_base.util.LogUtils
import com.example.module_weather.R
import com.example.module_weather.databinding.FragmentCurrentCityBinding
import com.example.module_weather.domain.MjDesBean
import com.example.module_weather.domain.WeatherCacheInfo
import com.example.module_weather.ui.adapter.Mj24Adapter
import com.example.module_weather.ui.adapter.MjDesAdapter
import com.example.module_weather.utils.WeatherUtils
import com.example.module_weather.utils.addTemSymbol
import com.example.module_weather.viewmodel.CurrentCityViewModel
import com.scwang.smart.refresh.header.MaterialHeader
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


    private var mCurrentCity = ""
    private var mLongitude = ""
    private var mLatitude = ""
    override fun initView() {
        arguments?.apply {
            mCurrentCity = getString(Constants.LOCATION_CITY, "")
            mLongitude = getString(Constants.LOCATION_LONGITUDE, "")
            mLatitude = getString(Constants.LOCATION_LATITUDE, "")

            if (!TextUtils.isEmpty(mCurrentCity) and !TextUtils.isEmpty(mLongitude) and !TextUtils.isEmpty(
                    mLatitude
                )
            ) {
                viewModel.getWeatherMsg(mCurrentCity, mLongitude, mLatitude)
            }
        }

        binding.apply {
            //设置刷新头
            val materialHeader = MaterialHeader(activity)
            smartRefreshLayout.setRefreshHeader(materialHeader)

            rv24Container.layoutManager=LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            rv24Container.adapter=mj24Adapter


            rvDes.layoutManager=GridLayoutManager(context, 4)
            rvDes.adapter=mjDesAdapter
        }
    }

    private val mDesList: MutableList<MjDesBean> = ArrayList<MjDesBean>()
    override fun observerData() {
        binding.apply {
            viewModel.apply {
                weatherInfo.observe(this@CurrentCityFragment, {
                    LogUtils.i("----weather----------${it.realWeatherBean}-----${it.aqi5Bean}-----")
                    //实时天气
                    it.realWeatherBean?.data?.condition?.apply {
                        tvHomeTeam.text = "${temp}℃"
                        tvHomeWea.text = "$condition"
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
                    it.day15Msg?.data?.forecast?.apply {
                        if (size > 0) {
                            val todayInfo = get(0)
                            val tomorrowInfo = get(1)
                            tvTodayLow.text = addTemSymbol(todayInfo.tempNight)
                            tvTodayHigh.text = addTemSymbol(todayInfo.tempDay)

                            tvTomorrowLow.text = addTemSymbol(tomorrowInfo.tempNight)
                            tvTomorrowHigh.text = addTemSymbol(tomorrowInfo.tempDay)
                        }
                    }

                    //24小时
                    it.day24Msg?.data?.hourly?.apply {
                        if (size > 0) {
                            mj24Adapter.setList(this)
                        }

                    }


                })

            }
        }
    }


    override fun initEvent() {

    }


}