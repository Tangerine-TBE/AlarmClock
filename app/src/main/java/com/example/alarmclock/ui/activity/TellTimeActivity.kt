package com.example.alarmclock.ui.activity

import androidx.recyclerview.widget.GridLayoutManager
import com.example.alarmclock.R
import com.example.alarmclock.bean.TellTimeBean
import com.example.alarmclock.service.TellTimeService
import com.example.alarmclock.ui.adapter.recyclerview.TellTimeAdapter
import com.example.alarmclock.util.Constants
import com.example.alarmclock.util.MarginStatusBarUtil
import com.example.module_base.util.LogUtils
import com.example.module_base.widget.MyToolbar
import com.example.td_horoscope.base.MainBaseActivity
import kotlinx.android.synthetic.main.activity_tell_time.*
import kotlinx.coroutines.*
import org.litepal.LitePal

class TellTimeActivity : MainBaseActivity() {
    private lateinit var mTellTimeAdapter: TellTimeAdapter
    private lateinit var mTellTimeAdapter2: TellTimeAdapter
    private val mAmTimeData: MutableList<TellTimeBean>? = ArrayList()
    private val mPmTimeData: MutableList<TellTimeBean>? = ArrayList()
    private var  isOpen = false
    private var i=1


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

        if (!mSPUtil.getBoolean(Constants.DISMISS_DIALOG)) mRemindDialog.show()

        isOpen=mSPUtil.getBoolean(Constants.TELL_TIME_IS_OPEN)
        if (isOpen)  setTimeItemList()
        setTellTimeState()
    }

    private fun setTellTimeState() {
        mTellTimeIcon.setImageResource(if (isOpen) R.mipmap.icon_tell_time_open else R.mipmap.icon_tell_time_close)
        mTellTimeTitle.text = if (isOpen) "点击关闭整点报时" else "点击开启整点报时"
    }


    private fun setTimeItemList() {
        mJobScope.launch(Dispatchers.Main){
          val pmList= async (Dispatchers.IO) {
                   LitePal.where("type=?", "1").find(TellTimeBean::class.java)
               }

          val amList= async(Dispatchers.IO) {
                    LitePal.where("type=?", "0").find(TellTimeBean::class.java)
                }

            mTellTimeAdapter.setSelectList(amList.await())
           mTellTimeAdapter.notifyDataSetChanged()
            mTellTimeAdapter2.setSelectList(pmList.await())
            mTellTimeAdapter2.notifyDataSetChanged()

        }
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
            if (isOpen) {

                mTellTimeAdapter.selectPosition(position) }
        }

        mTellTimeAdapter2.setOnItemClickListener { adapter, view, position ->
            if (isOpen) {

                mTellTimeAdapter2.selectPosition(position) }
        }

        mTellTimeIcon.setOnClickListener {
            i++
            isOpen=!isOpen
            setTellTimeState()
            if (isOpen) {
                setTimeItemList()

            } else {
                mTellTimeAdapter.apply {
                    selectPosition(-1)
                    mSelectList?.clear()
                    mTellTimeAdapter.notifyDataSetChanged()
                }
                mTellTimeAdapter2.apply {
                    selectPosition(-1)
                    mSelectList?.clear()
                    mTellTimeAdapter2.notifyDataSetChanged()
                }
            }
            mSPUtil.putBoolean(Constants.TELL_TIME_IS_OPEN,isOpen)

            mJobScope.launch {
                delay(i*3000L)
                TellTimeService.startTellTimeService(this@TellTimeActivity){ putExtra(Constants.TELL_TIME_SERVICE,1)}
            }

        }

    }


    override fun release() {
        super.release()
        TellTimeService.startTellTimeService(this){ putExtra(Constants.TELL_TIME_SERVICE,1)}
        mJobScope.cancel()
    }


}