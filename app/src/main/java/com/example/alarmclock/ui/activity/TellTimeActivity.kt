package com.example.alarmclock.ui.activity

import android.text.TextUtils
import androidx.recyclerview.widget.GridLayoutManager
import com.example.alarmclock.R
import com.example.alarmclock.bean.ItemBean
import com.example.alarmclock.bean.TellTimeBean
import com.example.alarmclock.bean.TimeListBean
import com.example.alarmclock.present.impl.TellTimePresentImpl
import com.example.alarmclock.ui.adapter.recyclerview.TellTimeAdapter
import com.example.alarmclock.util.Constants
import com.example.alarmclock.util.MarginStatusBarUtil
import com.example.alarmclock.view.ITellTimeCallback
import com.example.module_base.util.LogUtils
import com.example.module_base.widget.MyToolbar
import com.example.td_horoscope.base.MainBaseActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_tell_time.*
import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.litepal.LitePal

class TellTimeActivity : MainBaseActivity(), ITellTimeCallback {
    private lateinit var mTellTimeAdapter: TellTimeAdapter
    private lateinit var mTellTimeAdapter2: TellTimeAdapter
    private val mAmTimeData: MutableList<TellTimeBean>? = ArrayList()
    private val mPmTimeData: MutableList<TellTimeBean>? = ArrayList()

    override fun getLayoutView(): Int = R.layout.activity_tell_time
    override fun initView() {
        //设置顶部距离
        MarginStatusBarUtil.setStatusBar(this, mTimeBar, 1)
        for (i in 1..12) {
                mAmTimeData?.add(TellTimeBean(time = i, timeHint = i.toString(),type = 0))
        }
        for (i in 13..24) {
            if (i == 24) {
                mPmTimeData?.add(TellTimeBean(time = 0, timeHint = i.toString(), type = 1))
            } else {
                mPmTimeData?.add(TellTimeBean(time = i, timeHint = i.toString(), type = 1))
            }
        }

        mAmTimeData?.let {
            mMorning.layoutManager = GridLayoutManager(this, 6)
            mTellTimeAdapter = TellTimeAdapter(true)
            mTellTimeAdapter.setList(it)
            mMorning.adapter = mTellTimeAdapter
        }

        mPmTimeData?.let {
            mAfternoon.layoutManager = GridLayoutManager(this, 6)
            mTellTimeAdapter2 = TellTimeAdapter(false)
            mTellTimeAdapter2.setList(it)
            mAfternoon.adapter = mTellTimeAdapter2
        }

       setTimeItemList()
        if (!mSPUtil.getBoolean(Constants.DISMISS_DIALOG)) mRemindDialog.show()
    }


    private fun setTimeItemList() {
       GlobalScope.launch(Dispatchers.Main){
           val pmList = withContext(Dispatchers.IO) {
               async {
                   LitePal.where("type=?", "1").find(TellTimeBean::class.java)
               }

           }
           mTellTimeAdapter2.setSelectList( pmList.await())


            val amList = withContext(Dispatchers.IO) {
                async {
                    LitePal.where("type=?", "0").find(TellTimeBean::class.java)
                }
            }
            mTellTimeAdapter.setSelectList(amList.await())


        }
    }

    override fun initPresent() {
        TellTimePresentImpl.registerCallback(this)
    }

    override fun initEvent() {
        mTimeBar.setOnBackClickListener(object : MyToolbar.OnBackClickListener {
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

        }

    }



    override fun release() {
        super.release()
       TellTimePresentImpl.unregisterCallback(this)
    }

    override fun onLoadTimeList(data: String) {
        setTimeItemList()
    }

}