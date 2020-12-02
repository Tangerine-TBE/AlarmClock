package com.example.alarmclock.ui.adapter.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmclock.interfaces.ItemCheckedChangeListener
import com.example.alarmclock.R
import com.example.alarmclock.bean.ItemBean
import com.example.alarmclock.model.DataProvider
import kotlinx.android.synthetic.main.item_set_container.view.*
import java.util.ArrayList

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.ui.adapter.recyclerview
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/23 14:02
 * @class describe
 */
class SettingAdapter : RecyclerView.Adapter<SettingAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_set_container, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int = DataProvider.setData.size

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.setData(mList!![position], position)
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setData(itemBean: ItemBean, position: Int) {
            itemView.apply {
                mSetTitle.text = itemBean.title
                mSetSwitch.isChecked = itemBean.isOpen
                mSetSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                    mItemCheckedChangeListener?.onItemChecked(itemBean,isChecked,position)
                }
            }
        }
    }


    private val mList:MutableList<ItemBean>?=ArrayList()
    fun setList(data: ArrayList<ItemBean>) {
        mList?.apply {
           clear()
            addAll(data)
            notifyDataSetChanged()
        }
    }

    private var mItemCheckedChangeListener: ItemCheckedChangeListener<ItemBean>?=null
    fun setOnItemCheckedChangeListener(listenerI:ItemCheckedChangeListener<ItemBean>){
        mItemCheckedChangeListener=listenerI
    }

}