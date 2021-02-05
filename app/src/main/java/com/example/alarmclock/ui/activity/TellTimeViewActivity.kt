package com.example.alarmclock.ui.activity

import androidx.recyclerview.widget.GridLayoutManager
import com.example.alarmclock.R
import com.example.alarmclock.bean.TellTimeBean
import com.example.alarmclock.repository.DataProvider
import com.example.alarmclock.service.TellTimeService
import com.example.alarmclock.ui.adapter.recyclerview.TellTimeAdapter
import com.example.alarmclock.util.Constants
import com.example.alarmclock.util.DialogUtil
import com.example.module_base.util.LogUtils
import com.example.module_base.util.MarginStatusBarUtil
import com.example.module_base.widget.MyToolbar
import com.example.td_horoscope.base.MainBaseViewActivity
import kotlinx.android.synthetic.main.activity_tell_time.*
import kotlinx.coroutines.*
import org.litepal.LitePal

class TellTimeViewActivity : MainBaseViewActivity(){
    private lateinit var mTellTimeAdapter: TellTimeAdapter
    private lateinit var mTellTimeAdapter2: TellTimeAdapter
    private val mRemindDialog by lazy { DialogUtil.createRemindDialog(this) }
    private var  isOpen = true
    private var i=1

    override fun getLayoutView(): Int = R.layout.activity_tell_time
    override fun initView() {
        //设置顶部距离
        MarginStatusBarUtil.setStatusBar(this, mTimeBar, 1)


            mMorning.layoutManager = GridLayoutManager(this, 6)
            mTellTimeAdapter = TellTimeAdapter()
            mTellTimeAdapter.setList(DataProvider.amTimeData)
            mMorning.adapter = mTellTimeAdapter

            mAfternoon.layoutManager = GridLayoutManager(this, 6)
            mTellTimeAdapter2 = TellTimeAdapter()
            mTellTimeAdapter2.setList(DataProvider.pmTimeData)
            mAfternoon.adapter = mTellTimeAdapter2

        if (!mSPUtil.getBoolean(Constants.DISMISS_DIALOG)) {
            if (!isFinishing) {
                mRemindDialog.show()
            }
        }

        isOpen=mSPUtil.getBoolean(Constants.TELL_TIME_IS_OPEN,true)
        if (isOpen) {setTimeItemList() }
        setTellTimeState()
    }

    private fun setTellTimeState() {
        mTellTimeIcon.setImageResource(if (isOpen)
            R.mipmap.icon_tell_time_open
        else
            R.mipmap.icon_tell_time_close)
        mTellTimeTitle.text = if (isOpen) "点击关闭整点报时" else "点击开启整点报时"
    }


    private fun setTimeItemList() {
        mJobScope.launch(Dispatchers.Main){
          val pmList= async (Dispatchers.IO) {
                   LitePal.where("type=?", "1").find(TellTimeBean::class.java)
               }

            LogUtils.i("----List--------${pmList.await().size}---------")
          val amList= async(Dispatchers.IO) {
                    LitePal.where("type=?", "0").find(TellTimeBean::class.java)
                }
            LogUtils.i("-----List-------${amList.await().size}---------")
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
                TellTimeService.startTellTimeService(this@TellTimeViewActivity){ putExtra(Constants.TELL_TIME_SERVICE,1)}
            }

        }

    }


    override fun release() {
        if (mRemindDialog.isShowing) {
            mRemindDialog.dismiss()
        }
        TellTimeService.startTellTimeService(this){ putExtra(Constants.TELL_TIME_SERVICE,1)}
        mJobScope.cancel()
    }


}