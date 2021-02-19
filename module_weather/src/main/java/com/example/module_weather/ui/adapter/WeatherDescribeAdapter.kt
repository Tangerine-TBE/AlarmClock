package com.example.module_weather.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.example.module_weather.R
import com.example.module_weather.databinding.ItemDescribeBinding
import com.example.module_weather.domain.DescribeBean
import com.example.module_weather.utils.ColorUtil
import com.example.module_weather.utils.WeatherUtils

/**
 * @name AlarmClock
 * @class name：com.example.module_weather.ui.adapter
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2021/2/19 13:53:17
 * @class describe
 */
class WeatherDescribeAdapter:
    BaseQuickAdapter<DescribeBean.Des, BaseDataBindingHolder<ItemDescribeBinding>>(R.layout.item_describe) {
    override fun convert(holder: BaseDataBindingHolder<ItemDescribeBinding>, item: DescribeBean.Des) {
        holder.dataBinding?.apply {
            if (holder.adapterPosition == 2) {
                for (i in WeatherUtils.ALARM_LEVEL.indices) {
                    if (item.values.equals(WeatherUtils.ALARM_LEVEL[i])) {
                            values.setTextColor(ColorUtil.AQI_COLOR[i])
                    }
                }
            }
            title.text = item.title
            values.text = item.values
        }

    }

}