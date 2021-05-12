package com.twx.module_weather.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.twx.module_weather.R
import com.twx.module_weather.databinding.ItemAirContainerBinding
import com.twx.module_weather.domain.AirBean

/**
 * @name AlarmClock
 * @class name：com.example.module_weather.ui.adapter
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2021/2/18 18:24:13
 * @class describe
 */
class AirAdapter:BaseQuickAdapter<AirBean,BaseDataBindingHolder<ItemAirContainerBinding>>(R.layout.item_air_container) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemAirContainerBinding>,
        item: AirBean
    ) {
        holder.dataBinding?.apply {
            tvAirTitle.text=item.title
            tvAirHint.text=item.hint
            tvAirValue.text=item.value
        }
    }
}