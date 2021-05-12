package com.twx.module_weather.ui.activity


import androidx.recyclerview.widget.GridLayoutManager
import com.twx.module_base.base.BaseVmViewViewActivity
import com.twx.module_base.util.MarginStatusBarUtil
import com.twx.module_base.util.setBarAction
import com.twx.module_weather.R
import com.twx.module_weather.databinding.ActivityDetailsBinding
import com.twx.module_weather.domain.DescribeBean
import com.twx.module_weather.ui.adapter.WeatherDescribeAdapter
import com.twx.module_weather.utils.Constants
import com.twx.module_weather.utils.WeatherUtils
import com.twx.module_weather.utils.addTemSymbol
import com.twx.module_weather.viewmodel.AirViewModel
import java.util.*

class DayDetailsActivity : BaseVmViewViewActivity<ActivityDetailsBinding, AirViewModel>() {

    private val mWeatherDescribeAdapter by lazy {
        WeatherDescribeAdapter()
    }

    override fun getViewModelClass(): Class<AirViewModel> {
        return AirViewModel::class.java
    }
    override fun getLayoutView(): Int =R.layout.activity_details
    override fun initView() {
        binding.apply {
            //设置顶部距离
            MarginStatusBarUtil.setStatusBar(this@DayDetailsActivity, detailsBar, 0)



            rvDetContainer.layoutManager=GridLayoutManager(this@DayDetailsActivity, 3)
            rvDetContainer.adapter=mWeatherDescribeAdapter
        }





        viewModel.getAqiInfo(intent.getStringExtra(Constants.WEATHER_INFO))

    }


    override fun observerData() {
        binding.apply {
            viewModel.weatherMsg.observe(this@DayDetailsActivity, { msg ->
                msg.realWeatherBean?.data?.condition?.let { it ->
                    tvDetTitle.text = "${it.condition}"
                    tvSunUp.text = "日出：" + it.sunRise.substring(10, it.sunRise.length).substring(
                        0,
                        6
                    )
                    tvSunDown.text = ("日落：" + it.sunSet.substring(10, it.sunRise.length).substring(
                        0,
                        6
                    ))

                    //空气质量
                    msg.aqi5Bean?.data?.aqiForecast?.let { aqi ->
                        if (aqi.size > 0) {
                            msg.lifeBean?.apply {
                                var zwx = ""
                                if (size > 0) {
                                    forEach {
                                        if (it.name == "紫外线指数") {
                                            zwx = it.status
                                        }
                                    }
                                    val desList: MutableList<DescribeBean.Des> =
                                        ArrayList<DescribeBean.Des>()
                                    desList.add(DescribeBean.Des("紫外线", zwx))
                                    desList.add(
                                        DescribeBean.Des(
                                            "体感温度",
                                            WeatherUtils.addTemSymbol2(it.realFeel)
                                        )
                                    )
                                    desList.add(
                                        DescribeBean.Des(
                                            "空气质量",
                                            WeatherUtils.aqiType(aqi[0].value)
                                        )
                                    )
                                    desList.add(
                                        DescribeBean.Des(
                                            it.windDir,
                                            it.windLevel.toString() + "级"
                                        )
                                    )
                                    desList.add(
                                        DescribeBean.Des(
                                            "能见度",
                                            it.uvi.toString() + "公里"
                                        )
                                    )
                                    desList.add(
                                        DescribeBean.Des(
                                            "气压",
                                            it.pressure.toString() + "hPa"
                                        )
                                    )

                                    mWeatherDescribeAdapter.setList(desList)
                                }
                            }
                        }
                    }


                }

                msg.day24Msg?.data?.hourly?.apply {
                    if (size > 0) {
                        val hourlyBean = get(0)
                        val hour = hourlyBean.hour.toInt()
                        ivDetailsBg.setImageResource(if (hour in 6..18) WeatherUtils.selectDayIcon(hourlyBean.iconDay)[Constants.MJ_BG]!! else
                            WeatherUtils.selectNightIcon(hourlyBean.iconNight)[Constants.MJ_BG]!!
                            )
                        ivDetWea.setImageResource(
                            if (hour in 6..18) WeatherUtils.selectDayIcon(hourlyBean.iconDay)[Constants.MJ_LAGER_ICON]!! else
                                WeatherUtils.selectNightIcon(hourlyBean.iconNight)[Constants.MJ_LAGER_ICON]!!
                                )
                        ivDetails.setBackgroundColor(    if (hour in 6..18) WeatherUtils.selectDayIcon(hourlyBean.iconDay)[Constants.MJ_COLOR]!! else
                            WeatherUtils.selectNightIcon(hourlyBean.iconNight)[Constants.MJ_COLOR]!!
                            )
                    }
                }

                msg.day15Msg?.data?.forecast?.apply {
                    if (size > 0) {
                        val todayInfo = get(1)
                        tvDetTeam.text = "${addTemSymbol(todayInfo.tempNight)}/${
                            addTemSymbol(
                                todayInfo.tempDay
                            )
                        }"
                    }
                }
            })
        }

    }




    override fun initEvent() {
        binding.detailsBar.setBarAction(this){}
    }
}