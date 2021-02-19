package com.example.module_base.util

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.module_base.widget.MyToolbar
import com.google.gson.Gson
import java.util.*

/**
 * @name AlarmClock
 * @class name：com.example.module_base.util
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2021/2/5 11:38:05
 * @class describe
 */


inline fun <reified T> gsonHelper(result: String?): T? =
        try {
            if (result!=null) {
                Gson().fromJson(result, T::class.java)
            } else {
                null
            }
        }catch (e: Exception){
            null
        }


inline fun MyToolbar.setBarAction(activity: FragmentActivity, crossinline block: () -> Unit){
    setOnBackClickListener(object : MyToolbar.OnBackClickListener {
        override fun onBack() {
            activity.finish()
        }

        override fun onRightTo() {
            block()
        }
    })
}

object RecyclerViewItemDistanceUtil {
    fun setDistance(recyclerView: RecyclerView, context: Context?, size: Float) {
        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.bottom = SizeUtils.dip2px1(context, size)
            }
        })
    }
}

//计算相差时间
fun calLastedTime(endDate: Date, nowDate: Date): Long {
    val nd = 1000 * 24 * 60 * 60.toLong()
    val nh = 1000 * 60 * 60.toLong()
    val nm = 1000 * 60.toLong()
    val ns = 1000;
    // 获得两个时间的毫秒时间差异
    val diff = endDate.time - nowDate.time
    // 计算差多少天
    val day = diff / nd
    // 计算差多少小时
    val hour = diff % nd / nh
    // 计算差多少分钟
    val min = diff % nd % nh / nm
    // 计算差多少秒//输出结果
    val sec = diff % nd % nh % nm / ns;
    return min
}