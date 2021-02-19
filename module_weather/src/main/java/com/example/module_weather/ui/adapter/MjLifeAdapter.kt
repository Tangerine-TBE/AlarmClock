package com.example.module_weather.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.example.module_weather.R
import com.example.module_weather.databinding.ItemLifeCotainerBinding
import com.example.module_weather.databinding.ItemMj15LayoutBinding
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
class MjLifeAdapter:BaseQuickAdapter<MjDesBean,BaseDataBindingHolder<ItemLifeCotainerBinding>>(R.layout.item_life_cotainer) {

    override fun convert(holder: BaseDataBindingHolder<ItemLifeCotainerBinding>, item: MjDesBean) {
        holder.dataBinding?.apply {
            ivLifeIcon.setImageResource(item.icon)
            tvLifeTitle.text=item.title
            tvLifeValues.text=item.value
        }
    }
}