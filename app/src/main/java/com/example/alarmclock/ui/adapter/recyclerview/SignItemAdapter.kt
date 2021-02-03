package com.example.alarmclock.ui.adapter.recyclerview

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.alarmclock.R
import com.example.alarmclock.bean.StopWatchTimeBean
import com.example.module_base.util.LogUtils
import kotlinx.android.synthetic.main.item_sign_container.view.*

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.bean
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2021/2/3 9:42:19
 * @class describe
 */
class SignItemAdapter:BaseQuickAdapter<StopWatchTimeBean,BaseViewHolder>(R.layout.item_sign_container) {
    override fun convert(holder: BaseViewHolder, item: StopWatchTimeBean) {
        holder.itemView.apply {
            itemCount.text ="${item.id}"

            itemTime.text=hintTimeText(item)
            if (item.id == 1) {
                itemGap.text="+${hintTimeText(item)}"

            } else {
                itemGap.text="+${hintTimeText(item.date!!)}"
            }

        }
    }


    private fun hintTimeText(item: StopWatchTimeBean):String{
        val min = String.format("%02d", item.min)
        val sec = String.format("%02d", item.second)
        val mil = String.format("%02d", item.mil)
        return "$min:$sec.$mil"
    }



}