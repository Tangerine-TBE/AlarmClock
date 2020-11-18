package com.example.alarmclock.ui.activity

import android.text.TextUtils
import androidx.recyclerview.widget.GridLayoutManager
import com.example.alarmclock.R
import com.example.alarmclock.bean.ItemBean
import com.example.alarmclock.bean.TimeListBean
import com.example.alarmclock.present.TellTimePresentImpl
import com.example.alarmclock.ui.adapter.recyclerview.TellTimeAdapter
import com.example.alarmclock.util.Constants
import com.example.alarmclock.util.MarginStatusBarUtil
import com.example.alarmclock.view.ITellTimeCallback
import com.example.module_base.util.LogUtils
import com.example.module_base.widget.MyToolbar
import com.example.td_horoscope.base.MainBaseActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_tell_time.*

class TellTimeActivity : MainBaseActivity(), ITellTimeCallback {
    private lateinit var mTellTimeAdapter:TellTimeAdapter
    private lateinit var mTellTimeAdapter2:TellTimeAdapter
    private val mSelectTimeList:MutableList<ItemBean>?=ArrayList()
    private val mTimeData:MutableList<ItemBean>?=ArrayList()
    override fun getLayoutView(): Int=R.layout.activity_tell_time
    override fun initView() {
        //设置顶部距离
        MarginStatusBarUtil.setStatusBar(this, mTimeBar, 1)
        for (i in 1..24) {
            mTimeData?.add(ItemBean(title = i.toString()))
        }
        mMorning.layoutManager=GridLayoutManager(this,6)
        mTellTimeAdapter=TellTimeAdapter(true)
        mTellTimeAdapter.setList(mTimeData?.subList(0,12))
        mMorning.adapter=mTellTimeAdapter

        mAfternoon.layoutManager=GridLayoutManager(this,6)
        mTellTimeAdapter2=TellTimeAdapter(false)
        mTellTimeAdapter2.setList(mTimeData?.subList(12,24))
        mAfternoon.adapter=mTellTimeAdapter2

        val listData = mSPUtil.getString(Constants.TELL_TIME_LIST)
        LogUtils.i("------------JSON1------------${listData}-")
        if (!TextUtils.isEmpty(listData)) {
            val timeListData = Gson().fromJson(listData, TimeListBean::class.java)
            val topList = timeListData.topList
            val bottomList = timeListData.bottomList
            if (topList.size!=0) {
                mTellTimeAdapter.setSelectList(topList)
            }
            if (bottomList.size!=0) {
                mTellTimeAdapter2.setSelectList(bottomList)
            }
        }

    }

    override fun initPresent() {
        TellTimePresentImpl.registerCallback(this)
    }
    override fun initEvent() {
        mTimeBar.setOnBackClickListener(object :MyToolbar.OnBackClickListener{
            override fun onBack() {
                finish()
            }
            override fun onRightTo() {

            }
        })

        mTellTimeAdapter.setOnItemClickListener { adapter, view, position ->
            mTellTimeAdapter.selectPosition(position)

        }

        mTellTimeAdapter2.setOnItemClickListener { adapter, view, position ->
            mTellTimeAdapter2.selectPosition(position)
        }

        mTellTimeIcon.setOnClickListener {
            mSelectTimeList?.apply {
                clear()
                val selectList = mTellTimeAdapter.getSelectList()
                val selectList2 = mTellTimeAdapter2.getSelectList()
                if (selectList.size!=0||selectList2.size!=0){
                    addAll(selectList)
                    addAll(selectList2)
                    TellTimePresentImpl.getTellTimeLists(mSelectTimeList)
                    mSPUtil.putString(Constants.TELL_TIME_LIST,Gson().toJson(TimeListBean(selectList,selectList2)))
            }
            }
        }

    }

    override fun onLoadTimeList(data: MutableList<ItemBean>) {

    }

    override fun release() {
        TellTimePresentImpl.unregisterCallback(this)

    }

}