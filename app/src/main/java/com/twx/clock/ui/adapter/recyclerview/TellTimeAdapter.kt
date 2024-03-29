package com.twx.clock.ui.adapter.recyclerview

import android.graphics.Color
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.twx.clock.R
import com.twx.clock.bean.TellTimeBean
import com.twx.module_base.util.LogUtils
import com.twx.module_base.util.SpeakUtil
import kotlinx.android.synthetic.main.item_tell_time_container.view.*
import kotlinx.coroutines.*
import org.litepal.LitePal
import kotlin.collections.ArrayList

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.ui.adapter.recyclerview
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/18 16:39
 * @class describe
 */
class TellTimeAdapter() : BaseQuickAdapter<TellTimeBean, BaseViewHolder>(R.layout.item_tell_time_container) {
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

    }


    override fun convert(holder: BaseViewHolder, item: TellTimeBean) {
        holder.setIsRecyclable(false)
        holder.itemView.apply {
            item.let {
                mTimeNumber.text = it.timeHint
                mSelectList?.apply {
                    if (contains(it)) {
                        mTimeNumber.setTextColor(Color.BLACK)
                        mTimeInclude.setBackgroundResource(R.mipmap.icon_tell_select)
                    } else {
                        mTimeNumber.setTextColor(Color.WHITE)
                        mTimeInclude.setBackgroundResource(R.drawable.shape_tell_time_item_normal_bg)
                    }
                    if (holder.adapterPosition == mPosition) {
                        GlobalScope.launch(Dispatchers.Main) {
                            if (contains(it)) {
                                val deleteAll = withContext(Dispatchers.IO) {
                                    LitePal.deleteAll(
                                        TellTimeBean::class.java,
                                        "time=?",
                                        "${it.time}"
                                    )
                                }
                                LogUtils.i("-aaavvdeleteAll----------${deleteAll}------${it.time}-----")
                                remove(it)
                                SpeakUtil.stopSpeak()
                                mTimeNumber.setTextColor(Color.WHITE)
                                mTimeInclude.setBackgroundResource(R.drawable.shape_tell_time_item_normal_bg)
                            } else {
                                withContext(Dispatchers.IO) {
                                    if (it.isSaved) {
                                        it.delete()
                                    } else {
                                      it.save()
                                    }
                                }
                                add(it)
                                mTimeNumber.setTextColor(Color.BLACK)
                                mTimeInclude.setBackgroundResource(R.mipmap.icon_tell_select)
                                SpeakUtil.speakText(it.timeText)
                            }
                        }
                    }
                }

            }
        }
    }
}