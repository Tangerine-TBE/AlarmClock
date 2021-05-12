package com.twx.clock.ui.adapter.recyclerview

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.twx.clock.R
import com.twx.clock.bean.ItemBean
import com.twx.clock.topfun.setThemeTextColor
import com.twx.clock.topfun.setTintImage
import kotlinx.android.synthetic.main.item_bottom_container.view.*

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.ui.adapter.recyclerview
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/16 16:48
 * @class describe
 */
class BottomAdapter:BaseQuickAdapter<ItemBean,BaseViewHolder>(R.layout.item_bottom_container) {
    override fun convert(holder: BaseViewHolder, item: ItemBean) {
        holder.itemView.apply {
            mBottomIcon.setImageResource(item.icon)
            mBottomIcon.setTintImage()
            mBottomTitle.setThemeTextColor()
            mBottomTitle.text=item.title
        }
    }
}