package com.example.module_weather.ui.activity

import androidx.recyclerview.widget.GridLayoutManager
import com.example.module_base.base.BaseVmViewViewActivity
import com.example.module_base.util.MarginStatusBarUtil
import com.example.module_base.util.setBarAction
import com.example.module_weather.R
import com.example.module_weather.databinding.ActivityAirBinding
import com.example.module_weather.domain.AirBean
import com.example.module_weather.ui.adapter.AirAdapter
import com.example.module_weather.ui.adapter.FiveAirAdapter
import com.example.module_weather.utils.Constants
import com.example.module_weather.utils.WeatherUtils
import com.example.module_weather.utils.selectIcon
import com.example.module_weather.viewmodel.AirViewModel
import kotlinx.android.synthetic.main.activity_air.*
import java.util.*

class AirActivity : BaseVmViewViewActivity<ActivityAirBinding, AirViewModel>() {

    private val mAirAdapter by lazy {
        AirAdapter()
    }

    private val mFiAirAdapter by lazy {
        FiveAirAdapter()
    }


    override fun getLayoutView(): Int=R.layout.activity_air
    override fun getViewModelClass(): Class<AirViewModel> { return AirViewModel::class.java }
    override fun initView() {
        binding.apply {
            //设置顶部距离
            MarginStatusBarUtil.setStatusBar(this@AirActivity, airToolbar, 0)
            airContainer.layoutManager = GridLayoutManager(this@AirActivity, 3)
            airContainer.adapter = mAirAdapter

            airFiveContainer.layoutManager = GridLayoutManager(this@AirActivity, 5)
            airFiveContainer.adapter = mFiAirAdapter

            viewModel.getAqiInfo(intent.getStringExtra(Constants.WEATHER_INFO))
        }
    }

    override fun observerData() {
        viewModel.weatherMsg.observe(this, {


            it.aqiBean?.data?.aqi?.apply {
                //空气质量数据展示
                val list: MutableList<AirBean> = ArrayList<AirBean>()
                list.add(AirBean("细颗粒物", "PM2.5", pm25))
                list.add(AirBean("粗颗粒物", "PM10", pm10))
                list.add(AirBean("二氧化硫", "SO2", so2))
                list.add(AirBean("二氧化氮", "NO2", no2))
                list.add(AirBean("一氧化碳", "CO", co))
                list.add(AirBean("臭氧", "O3", o3))
                mAirAdapter.setList(list)
            }

            it.day15Msg?.data?.forecast?.let { msg15 ->
                if (msg15.size > 0) {
                    it.aqi5Bean?.data?.aqiForecast?.apply {
                        if (size > 0) {
                            val list: MutableList<AirBean> = ArrayList<AirBean>()
                            for (i in 0 until msg15.subList(1, 6).size) {
                                list.add(
                                    AirBean(
                                        msg15[i].predictDate,
                                        if (selectIcon()) msg15[i].tempDay else msg15[i].tempNight,
                                        get(
                                            i
                                        ).value.toString()
                                    )
                                )
                            }
                            mFiAirAdapter.setList(list)


                            binding.apply {
                                val aqiValue: Int = get(0).value
                                val currentAqi = aqiValue.toString()
                                tvAqiValues.text=currentAqi
                                tvCurrentAqi.text= "当前空气质量指数:$currentAqi"
                                val aqiBg: Int = WeatherUtils.aqiBg(aqiValue)
                                aqiCircle.setInnerRingColor(aqiBg)
                                aqiCircle.setMsgContext("当前空气质量:${ WeatherUtils.aqiType(aqiValue)}")
                            }
                        }

                    }
                }
            }


        })
    }

    override fun initEvent() {
        binding.apply {
            airToolbar.setBarAction(this@AirActivity){}
        }
    }

}