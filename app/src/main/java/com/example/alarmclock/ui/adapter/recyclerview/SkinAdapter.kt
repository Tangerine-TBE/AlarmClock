package com.example.alarmclock.ui.adapter.recyclerview

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.alarmclock.R
import com.example.alarmclock.bean.ItemBean
import com.example.alarmclock.util.Constants
import com.example.module_base.util.LogUtils
import com.example.module_base.util.SPUtil
import kotlinx.android.synthetic.main.item_view_container.view.*

/**
 * @author: 铭少
 * @date: 2020/11/21 0021
 * @description：
 */
class SkinAdapter:BaseQuickAdapter<ItemBean,BaseViewHolder>(R.layout.item_view_container) {

    private var mPosition= SPUtil.getInstance().getInt(Constants.CURRENT_THEME);

    fun setCurrentPosition(position:Int){
        mPosition=position
        notifyDataSetChanged()
    }

    override fun convert(holder: BaseViewHolder, item: ItemBean) {
        holder.setIsRecyclable(false)
        holder.itemView.apply {
            item.let {
                mSkinView.setImageResource(it.icon)
                if (mPosition == holder.adapterPosition)
                    mSkinViewContainer.setBackgroundResource(R.drawable.shape_skin_select_bg)
                 else
                    mSkinViewContainer.setBackgroundResource(R.color.transparent)
            }

        }
    }
}