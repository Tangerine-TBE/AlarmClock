package com.example.alarmclock.ui.adapter.recyclerview

import android.annotation.SuppressLint
import android.graphics.Color
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.alarmclock.R
import com.example.alarmclock.bean.ItemBean
import com.example.alarmclock.bean.TellTimeBean
import com.example.alarmclock.bean.TimeListBean
import com.example.alarmclock.util.CalendarUtil
import com.example.alarmclock.util.ClockUtil
import com.example.alarmclock.util.Constants
import com.example.module_base.util.LogUtils
import com.example.module_base.util.SPUtil
import com.google.gson.Gson
import com.tamsiree.rxkit.view.RxToast
import kotlinx.android.synthetic.main.item_tell_time_container.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.litepal.LitePal
import java.util.*
import kotlin.collections.ArrayList

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.ui.adapter.recyclerview
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/18 16:39
 * @class describe
 */
class TellTimeAdapter(state: Boolean) : BaseQuickAdapter<TellTimeBean, BaseViewHolder>(R.layout.item_tell_time_container) {
    private var mPosition = -1
    var mSelectList: MutableList<TellTimeBean>? = ArrayList()

    fun selectPosition(position: Int) {
        mPosition = position
        notifyDataSetChanged()
    }

    fun getSelectList(): MutableList<TellTimeBean> {
        return mSelectList!!
    }

    fun setSelectList(list: MutableList<TellTimeBean>) {
        mSelectList = list
        notifyDataSetChanged()
    }


    @SuppressLint("ResourceAsColor")
    override fun convert(holder: BaseViewHolder, item: TellTimeBean) {
        holder.itemView.apply {
            item.let {
                mTimeNumber.text =it.timeHint
                mSelectList?.apply {
                    if (contains(it)) {
                        mTimeNumber.setTextColor(Color.BLACK)
                        mTimeInclude.setBackgroundResource(R.mipmap.icon_tell_select)
                    } else {
                        mTimeNumber.setTextColor(Color.WHITE)
                        mTimeInclude.setBackgroundResource(R.drawable.shape_tell_time_item_normal_bg)
                    }
                    if (holder.adapterPosition == mPosition) {
                        if (contains(it)) {
                            remove(it)
                            mTimeNumber.setTextColor(Color.WHITE)
                            mTimeInclude.setBackgroundResource(R.drawable.shape_tell_time_item_normal_bg)
                            GlobalScope.launch {
                                val isDelete = async {
                                    CalendarUtil.deleteCalendarEvent(context, "现在是${it.time}点整")
                                }
                                if (isDelete.await()==1) {
                                    LitePal.deleteAll(TellTimeBean::class.java, "time=?", "${it.time}")
                                    ClockUtil.stopTellTime(it)
                                }
                            }

                        } else {
                            add(it)
                            GlobalScope.launch {
                                val isSave = async {
                                    it.save()
                                }
                                if (isSave.await()) {
                                    CalendarUtil.addCalendarEvent(
                                        context, "整点报时提醒",
                                        "现在是${it.time}点整"
                                        , it.time, 0
                                    )
                                    ClockUtil.openTellTime(it)
                                }
                            }
                            mTimeNumber.setTextColor(Color.BLACK)
                            mTimeInclude.setBackgroundResource(R.mipmap.icon_tell_select)
                        }
                    }
                }
            }

        }
    }
}