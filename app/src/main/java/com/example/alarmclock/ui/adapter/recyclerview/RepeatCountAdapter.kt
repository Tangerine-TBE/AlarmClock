package com.example.alarmclock.ui.adapter.recyclerview

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.alarmclock.R
import com.example.alarmclock.bean.ItemBean
import kotlinx.android.synthetic.main.item_repeat_container.view.*

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.ui.adapter.recyclerview
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/24 18:53
 * @class describe
 */
class RepeatCountAdapter:BaseQuickAdapter<ItemBean,BaseViewHolder>(R.layout.item_repeat_container) {
    override fun convert(holder: BaseViewHolder, item: ItemBean) {
        holder.itemView.apply {
            mRepeatTitle.text=item.title
        }
    }
}