package com.example.alarmclock.ui.adapter.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmclock.interfaces.ItemCheckedChangeListener
import com.example.alarmclock.R
import com.example.alarmclock.bean.ItemBean
import kotlinx.android.synthetic.main.item_clock_set_container.view.*
import java.util.ArrayList

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.ui.adapter.recyclerview
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/24 11:46
 * @class describe
 */
class ClockSetAdapter: RecyclerView.Adapter<ClockSetAdapter.MyHolder>() {

   inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       fun setItemData(itemBean: ItemBean, position: Int) {
           itemView.apply {
               mSetClockTitle.text=itemBean.title
               if (position in 0..1) {
                   mSetClockSwitch.visibility = View.GONE
                   mRepeatClock.visibility = View.VISIBLE
                   mRepeatClock.text = itemBean.hint

                   itemView.setOnClickListener {
                       mItemCheckedChangeListener?.onItemClick(itemBean,position)
                   }

               } else {
                   mSetClockSwitch.isChecked=itemBean.isOpen
               }

               mSetClockSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                   mItemCheckedChangeListener?.onItemChecked(itemBean,isChecked,position)
               }



           }

       }

   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClockSetAdapter.MyHolder {
           return MyHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_clock_set_container,parent,false))
    }

    override fun getItemCount(): Int {
        return mList!!.size
    }


    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.setItemData(mList!![position],position)
    }

    private val mList:MutableList<ItemBean>?= ArrayList()
    fun setList(data: ArrayList<ItemBean>) {
        mList?.apply {
            clear()
            addAll(data)
            notifyDataSetChanged()
        }
    }

    private var mItemCheckedChangeListener:ItemCheckedChangeListener<ItemBean>?=null
    fun setOnItemCheckedChangeListener(listenerI: ItemCheckedChangeListener<ItemBean>){
        mItemCheckedChangeListener=listenerI
    }


}