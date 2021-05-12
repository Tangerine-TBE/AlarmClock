package com.twx.module_weather.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.twx.module_base.util.DateUtil
import com.twx.module_weather.R
import com.twx.module_weather.databinding.ItemFiveAirContainerBinding
import com.twx.module_weather.domain.AirBean
import com.twx.module_weather.utils.WeatherUtils

/**
 * @name AlarmClock
 * @class name：com.example.module_weather.ui.adapter
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2021/2/18 18:24:13
 * @class describe
 */
class FiveAirAdapter :
    BaseQuickAdapter<AirBean, BaseDataBindingHolder<ItemFiveAirContainerBinding>>(R.layout.item_five_air_container) {
    private val values = intArrayOf(0, 50, 100, 150, 200, 300)
    override fun convert(
        holder: BaseDataBindingHolder<ItemFiveAirContainerBinding>,
        item: AirBean
    ) {
        holder.dataBinding?.apply {
            val position = holder.adapterPosition
            val date: String = item.title
            when (position) {
                0 -> {
                    tvItemDate.text = "今天"
                }
                1 -> {
                    tvItemDate.text = "明天"
                }
                else -> {
                    val week: String = DateUtil.getWeek(date)
                    tvItemDate.text = week
                }
            }
            tvDateNumber.text = DateUtil.StrToData2(date)

            val value: String = item.value
            val chn = Integer.valueOf(value)
            tvAirLever.text = value
            tvAirLeverTitle.text = WeatherUtils.aqiType(chn)
            btAirLeverBg.background = WeatherUtils.aqiTypeBg(values, chn)

        }


    }
}