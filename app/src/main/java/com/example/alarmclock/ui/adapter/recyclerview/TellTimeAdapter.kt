package com.example.alarmclock.ui.adapter.recyclerview

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.alarmclock.R
import com.example.alarmclock.bean.ItemBean
import com.tamsiree.rxkit.view.RxToast
import kotlinx.android.synthetic.main.item_tell_time_container.view.*

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.ui.adapter.recyclerview
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/18 16:39
 * @class describe
 */
class TellTimeAdapter(state:Boolean):BaseQuickAdapter<ItemBean,BaseViewHolder>(R.layout.item_tell_time_container) {
    private var mPosition = -1
    private var mSelectList: MutableList<ItemBean>? = ArrayList()

    fun selectPosition(position:Int){
        mPosition = position
        notifyDataSetChanged()
    }

    fun getSelectList():MutableList<ItemBean>{
        return mSelectList!!
    }

    fun setSelectList(list:MutableList<ItemBean>){
        mSelectList=list
    }



    override fun convert(holder: BaseViewHolder, item: ItemBean) {
        holder.itemView.apply {
            item.let {
                mTimeNumber.text=it.title
                mSelectList?.apply {
                    if (contains(it)) mTimeInclude.setBackgroundResource(R.drawable.shape_tell_time_item_select_bg) else mTimeInclude.setBackgroundResource(R.drawable.shape_tell_time_item_normal_bg)
                if (holder.adapterPosition == mPosition) {
                    if (contains(it)) {
                        remove(it)
                        mTimeInclude.setBackgroundResource(R.drawable.shape_tell_time_item_normal_bg)
                    } else {
                            add(it)
                            mTimeInclude.setBackgroundResource(R.drawable.shape_tell_time_item_select_bg)
                        }
                    }
                }
                }

            }
        }
}