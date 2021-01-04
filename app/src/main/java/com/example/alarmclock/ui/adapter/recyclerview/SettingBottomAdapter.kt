package com.example.alarmclock.ui.adapter.recyclerview

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.alarmclock.R
import com.example.alarmclock.bean.ItemBean
import kotlinx.android.synthetic.main.item_set_bottom_container.view.*
import kotlinx.android.synthetic.main.item_set_container.view.*
import kotlinx.android.synthetic.main.item_set_container.view.mSetTitle
import kotlinx.android.synthetic.main.pickerview_custom_time.view.*

/**
 * @author: 铭少
 * @date: 2020/11/21 0021
 * @description：
 */
class SettingBottomAdapter():BaseQuickAdapter<ItemBean,BaseViewHolder>(R.layout.item_set_bottom_container){
    override fun convert(holder: BaseViewHolder, item: ItemBean) {
        holder.itemView.apply {
            item.let {
                if (holder.adapterPosition==6){
                    mSetHint.text=it.hint
                    mSetHint.visibility=View.VISIBLE
                }
                if (holder.adapterPosition==4){
                    mDes.text=it.hint
                    mDes.visibility=View.VISIBLE
                }


                mSetTitle.text=it.title
            }
        }

    }
}