package com.example.module_weather.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.example.module_weather.R
import com.example.module_weather.databinding.ItemMjdesContainerBinding
import com.example.module_weather.domain.MjDesBean

/**
 * @name AlarmClock
 * @class name：com.example.module_weather.ui.adapter
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2021/2/18 18:24:13
 * @class describe
 */
class MjDesAdapter:BaseQuickAdapter<MjDesBean,BaseDataBindingHolder<ItemMjdesContainerBinding>>(R.layout.item_mjdes_container) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemMjdesContainerBinding>,
        item: MjDesBean
    ) {
        holder.dataBinding?.apply {
            tvDesTitle.text=item.title
            ivDesIcon.setImageResource(item.icon)
            tvDesValue.text=item.value
        }
    }
}