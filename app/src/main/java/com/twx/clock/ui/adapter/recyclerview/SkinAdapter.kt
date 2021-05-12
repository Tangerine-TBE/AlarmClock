package com.twx.clock.ui.adapter.recyclerview

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.twx.clock.R
import com.twx.clock.bean.ItemBean
import kotlinx.android.synthetic.main.item_view_container.view.*

/**
 * @author: 铭少
 * @date: 2020/11/21 0021
 * @description：
 */
class SkinAdapter:BaseQuickAdapter<ItemBean,BaseViewHolder>(R.layout.item_view_container) {

    private var mPosition= -1

    fun setCurrentPosition(position:Int){
        mPosition=position
        notifyDataSetChanged()
    }

    override fun convert(holder: BaseViewHolder, item: ItemBean) {
        holder.setIsRecyclable(false)
        holder.itemView.apply {
            item.let {
                mVipPoint.visibility=  if (it.isOpen)  View.VISIBLE else View.GONE
                mSkinView.setImageResource(it.icon)
                if (mPosition == holder.adapterPosition) {
                  mSkinViewContainer.setBackgroundResource(R.drawable.shape_skin_select_bg)
                }
                 else {
                    mSkinViewContainer.setBackgroundResource(R.drawable.shape_skin_normal_bg)
                }

            }

        }
    }
}