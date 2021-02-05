package com.example.alarmclock.ui.adapter.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmclock.R
import com.example.alarmclock.bean.ClockBean
import com.example.alarmclock.bean.DiyClockCycleBean
import com.example.alarmclock.interfaces.ItemCheckedChangeListener
import com.example.alarmclock.repository.DataProvider
import com.example.alarmclock.util.ClockUtil
import com.example.module_base.util.LogUtils
import com.google.gson.Gson
import com.tamsiree.rxkit.RxTimeTool
import kotlinx.android.synthetic.main.item_clock_list_container.view.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.ui.adapter.recyclerview
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/25 19:51
 * @class describe
 */
class ClockListAdapter:RecyclerView.Adapter<ClockListAdapter.MyHolder>() {
    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setItemData(clockBean: ClockBean, position: Int) {
            itemView.apply {
                var clockTimeHour = clockBean.clockTimeHour.toString()
                var clockTimeMin = clockBean.clockTimeMin.toString()
                val realHour= if (clockTimeHour.length != 2) "0$clockTimeHour" else clockTimeHour
                val realMin= if (clockTimeMin.length!=2) "0$clockTimeMin" else clockTimeMin
                mClockTitle.text="$realHour:$realMin"

                val clockOpen = clockBean.clockOpen
                mClockSwitch.isChecked=clockOpen
                mClockTitle.setTextColor(ContextCompat.getColor(context, if (clockOpen) R.color.white else R.color.close_clock))
                mClockCycle.setTextColor(ContextCompat.getColor(context, if (clockOpen) R.color.open_clock else R.color.close_clock))

                val calender = Calendar.getInstance()
                calender[Calendar.HOUR_OF_DAY] = clockBean.clockTimeHour
                calender[Calendar.MINUTE] = clockBean.clockTimeMin
                calender[Calendar.SECOND] = 0


                val cycle = when (clockBean.setClockCycle) {
                    0 -> DataProvider.repeatData[0].title
                    1 -> DataProvider.repeatData[1].title
                    2 -> DataProvider.repeatData[2].title
                    3 -> {
                        val diyData = Gson().fromJson(clockBean.setDiyClockCycle, DiyClockCycleBean::class.java)
                        val stringBuffer = StringBuffer()
                        diyData?.let { it ->
                            it.list.forEach {
                                stringBuffer.append("  ${it.title}")
                            }
                        }
                        "周${stringBuffer}"
                    }
                    else-> DataProvider.repeatData[0].title
                }

                val countdownTime = if (clockBean.setClockCycle == 3) {
                    val diyClockCycle = clockBean.setDiyClockCycle
                    Gson().fromJson(diyClockCycle, DiyClockCycleBean::class.java)?.let { it ->
                        var day = ClockUtil.getBetweenDay(it, calender)
                        if (day == 0)
                            ClockUtil.getCurrentTimeHint(calender.time)
                        else
                            "${day}天${ClockUtil.getCurrentTimeHint(calender.time)}"
                    }


                } else {
                    ClockUtil.getCurrentTimeHint(calender.time)
                }

                LogUtils.i("-----111--getCurrentTimeHint----${RxTimeTool.date2String(calender.time)}----------")

                mClockCycle.text= if (clockOpen) "$cycle    $countdownTime" else "$cycle"

                mClockSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                    mItemCheckedChangeListener?.onItemChecked(clockBean,isChecked,position)
                }

                setOnClickListener {
                    mItemCheckedChangeListener?.onItemClick(clockBean,position)
                }

            }

        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder = MyHolder(LayoutInflater.from(parent.context)
        .inflate(R.layout.item_clock_list_container,parent,false))

    private var mClockList: MutableList<ClockBean>? = ArrayList()

    override fun getItemCount(): Int=mClockList?.size?:0


    fun setData( data:MutableList<ClockBean>){
        mClockList?.let {
            it.clear()
            it.addAll(data)
            notifyDataSetChanged()
        }

    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.setIsRecyclable(false)
        holder.setItemData(mClockList!![position],position)

    }

    private var mItemCheckedChangeListener: ItemCheckedChangeListener<ClockBean>?=null
    fun setOnItemCheckedChangeListener(listenerI: ItemCheckedChangeListener<ClockBean>){
        mItemCheckedChangeListener=listenerI
    }

}