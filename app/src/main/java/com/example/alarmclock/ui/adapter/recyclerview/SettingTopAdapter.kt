package com.example.alarmclock.ui.adapter.recyclerview

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.alarmclock.R
import com.example.alarmclock.bean.ItemBean
import kotlinx.android.synthetic.main.item_set_container.view.*

/**
 * @author: 铭少
 * @date: 2020/11/21 0021
 * @description：
 */
class SettingTopAdapter():BaseQuickAdapter<ItemBean,BaseViewHolder>(R.layout.item_set_container){
    override fun convert(holder: BaseViewHolder, item: ItemBean) {
        holder.itemView.apply {
            item.let {
                mSetTitle.text=it.title
                mSetSwitch.isChecked = item.isOpen
            }
        }

    }
}