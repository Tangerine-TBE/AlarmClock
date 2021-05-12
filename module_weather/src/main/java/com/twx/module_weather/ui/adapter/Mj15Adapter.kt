package com.twx.module_weather.ui.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.twx.module_base.util.DateUtil
import com.twx.module_weather.R
import com.twx.module_weather.databinding.ItemMj15LayoutBinding
import com.twx.module_weather.domain.Mj15DayWeatherBean
import com.twx.module_weather.utils.Constants
import com.twx.module_weather.utils.WeatherUtils
import com.twx.module_weather.utils.selectIcon

/**
 * @name AlarmClock
 * @class name：com.example.module_weather.ui.adapter
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2021/2/19 9:42:14
 * @class describe
 */
class Mj15Adapter:BaseQuickAdapter<Mj15DayWeatherBean.DataBean.ForecastBean, BaseDataBindingHolder<ItemMj15LayoutBinding>>(R.layout.item_mj15_layout) {
    override fun convert(holder: BaseDataBindingHolder<ItemMj15LayoutBinding>, forecastBean: Mj15DayWeatherBean.DataBean.ForecastBean) {
        holder.dataBinding?.apply {
            val position=holder.adapterPosition
            windDes.visibility = if (position == 0) View.VISIBLE else View.INVISIBLE
            windDes.text = "风力/风向"

            val date: String = forecastBean.predictDate
            when (position) {
                0 -> {
                    week.text = "今天"
                }
                1 -> {
                    week.text = "明天"
                }
                else -> {
                    val weekMsg: String = DateUtil.getWeek(date)
                    week.text = weekMsg
                }
            }

            fiveTemMax.text = WeatherUtils.addTemSymbol2(forecastBean.tempDay).toString() + "/" + WeatherUtils.addTemSymbol2(forecastBean.tempNight)
            day.text = DateUtil.StrToData(date)


            windLevel.text=if (selectIcon()) forecastBean.windLevelDay.toString() + "级" else forecastBean.windLevelNight.toString() + "级"
            windOrientation.text=if (selectIcon()) forecastBean.windDirDay else forecastBean.windDirNight
            fiveWeaIcon.setImageResource(if (selectIcon()) WeatherUtils.selectDayIcon(forecastBean.conditionIdDay)[Constants.MJ_ICON]!!
            else WeatherUtils.selectDayIcon(forecastBean.conditionIdNight)[Constants.MJ_ICON]!!)


        }
    }
}