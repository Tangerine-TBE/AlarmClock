package com.example.alarmclock.ui.adapter.recyclerview

import android.graphics.Color
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.alarmclock.R
import com.example.alarmclock.bean.ItemBean
import kotlinx.android.synthetic.main.item_diy_time_container.view.*

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.ui.adapter.recyclerview
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/30 17:41
 * @class describe
 */
class DiyClockTimeAdapter:BaseQuickAdapter<ItemBean, BaseViewHolder>(R.layout.item_diy_time_container) {

    private var mPosition=-1
    private var mSelectList:MutableList<ItemBean>?=ArrayList()

    fun setPosition(position:Int){
        mPosition=position
        notifyDataSetChanged()
    }

    fun getSelectList()=mSelectList

    fun setSelectList(list:MutableList<ItemBean>){
        mSelectList=list
        notifyDataSetChanged()
    }



    override fun convert(holder: BaseViewHolder, item: ItemBean) {
        holder.itemView.apply {
            item.let {
            mSelectList?.apply {
                mDiyTime.setTextColor(if (contains(it)) Color.WHITE else Color.GRAY)
                mDiyTime.setBackgroundResource(if (contains(it))R.drawable.shape_diy_select_time else R.drawable.shape_diy_normal_time)
                if (mPosition==holder.adapterPosition) {
                    if (contains(it)) {
                        remove(it)
                        mDiyTime.setTextColor(Color.GRAY)
                        mDiyTime.setBackgroundResource(R.drawable.shape_diy_normal_time)
                    } else {
                        add(it)
                        mDiyTime.setTextColor(Color.WHITE)
                        mDiyTime.setBackgroundResource(R.drawable.shape_diy_select_time)
                    }
                }
            }
                mDiyTime.text = it.title
            }

        }
    }
}