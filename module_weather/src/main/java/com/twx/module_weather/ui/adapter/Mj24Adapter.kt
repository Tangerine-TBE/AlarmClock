package com.twx.module_weather.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.twx.module_weather.R
import com.twx.module_weather.databinding.ItemMj24ContainerBinding
import com.twx.module_weather.domain.Mj24WeatherBean.DataBean.HourlyBean
import com.twx.module_weather.utils.Constants
import com.twx.module_weather.utils.WeatherUtils
import com.twx.module_weather.utils.addTemSymbol

/**
 * @author wujinming QQ:1245074510
 * @name WeatherOne
 * @class name：com.example.tianqi.ui.adapter
 * @class describe
 * @time 2020/9/8 17:56
 * @class describe
 */
class Mj24Adapter : BaseQuickAdapter<HourlyBean, BaseDataBindingHolder<ItemMj24ContainerBinding>>(R.layout.item_mj24_container) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemMj24ContainerBinding>,
        item: HourlyBean
    ) {
        holder.dataBinding?.apply {
            tvItem24Team.text= addTemSymbol(item.temp)
            tvItem24Date.text = WeatherUtils.formatTime(item.hour)
            val speed: Double = item.windSpeed.toDouble()
            tvItem24Speed.text = WeatherUtils.winType(speed, true)
            ivItem24Direction.text = WeatherUtils.formatWindyDir(item.windDir)
            val hour: Int = Integer.valueOf(item.hour)
            if (hour in 6..18) {
                ivItem24Icon.setImageResource(WeatherUtils.selectDayIcon(item.iconDay)[Constants.MJ_ICON]!!)
            } else {
                ivItem24Icon.setImageResource(WeatherUtils.selectNightIcon(item.iconNight)[Constants.MJ_ICON]!!)
            }
        }
    }


}