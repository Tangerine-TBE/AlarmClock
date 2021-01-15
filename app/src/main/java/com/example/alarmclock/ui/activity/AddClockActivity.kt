package com.example.alarmclock.ui.activity

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.view.Gravity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alarmclock.R
import com.example.alarmclock.bean.ClockBean
import com.example.alarmclock.bean.DiyClockCycleBean
import com.example.alarmclock.bean.ItemBean
import com.example.alarmclock.broadcast.BroadcastChangeReceiver
import com.example.alarmclock.interfaces.DiyTimePopupListener
import com.example.alarmclock.interfaces.ItemCheckedChangeListener
import com.example.alarmclock.model.DataProvider
import com.example.alarmclock.service.TellTimeService
import com.example.alarmclock.ui.adapter.recyclerview.ClockSetAdapter
import com.example.alarmclock.ui.widget.popup.ClockDeletePopup
import com.example.alarmclock.ui.widget.popup.ClockDiyPopup
import com.example.alarmclock.ui.widget.popup.ClockRepeatPopup
import com.example.alarmclock.ui.widget.ClockSelectView
import com.example.alarmclock.util.CheckPermissionUtil
import com.example.alarmclock.util.ClockUtil
import com.example.alarmclock.util.Constants
import com.example.module_base.util.LogUtils
import com.example.module_base.util.MarginStatusBarUtil
import com.example.module_base.widget.MyToolbar
import com.example.td_horoscope.base.MainBaseActivity
import com.google.gson.Gson
import com.tamsiree.rxkit.RxTimeTool
import kotlinx.android.synthetic.main.activity_add_clock.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.EventBus
import org.litepal.LitePal
import java.text.SimpleDateFormat
import java.util.*


class AddClockActivity : MainBaseActivity() {

    override fun getLayoutView(): Int=R.layout.activity_add_clock
    private lateinit var mSetClockAdapter:ClockSetAdapter
    private val mClockSelectView by lazy { ClockSelectView() }
    private val mTimeChangReceiver by lazy { BroadcastChangeReceiver() }
    private val mClockBean by lazy { ClockBean() }
    private val mCalendar by lazy { Calendar.getInstance() }
    private val mClockDeletePopup by lazy {
        ClockDeletePopup(
            this
        )
    }
    private val mClockDiyPopup by lazy {
        ClockDiyPopup(
            this
        )
    }

    private val mRepeatPopupWindow by lazy {
        ClockRepeatPopup(this,"重复",DataProvider.repeatData)
    }

    private val mClosePopupWindow by lazy {
        ClockRepeatPopup(this,"关闭闹钟方式",DataProvider.closeWayData)
    }


    private var mAction=0
    private var mReviseClockBean:ClockBean?=null
    private  var mDiyData:DiyClockCycleBean?=null

    override fun initView() {
        //设置顶部距离
        MarginStatusBarUtil.setStatusBar(this, mAddClock, 0)
        mSetClockContainer.layoutManager=LinearLayoutManager(this)
        mSetClockAdapter= ClockSetAdapter()
        mSetClockContainer.adapter = mSetClockAdapter
        val intentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_TIME_TICK)
        }
        registerReceiver(mTimeChangReceiver, intentFilter)


        mAction=intent.getIntExtra(Constants.CLOCK_ACTION,0)
        when(mAction){
            //添加闹钟
            0->{
                mSetClockAdapter.setList(DataProvider.setClockData)
                showPickerView(this, Calendar.getInstance())
                showOnTimeHint(Date(),0)
            }
            //修改闹钟
            1->{
                visible(mDeleteClock)
                mReviseClockBean = intent.getSerializableExtra(Constants.CLOCK_INFO) as ClockBean
                mReviseClockBean?.let { it ->
                    mCalendar[Calendar.HOUR_OF_DAY]=it.clockTimeHour
                    mCalendar[Calendar.MINUTE]=it.clockTimeMin
                    showPickerView(this, mCalendar)

                    if (it.setClockCycle == 3) {
                        mDiyData = Gson().fromJson(it.setDiyClockCycle, DiyClockCycleBean::class.java)
                        mDiyData?.let { it ->
                            val stringBuffer = StringBuffer()
                            it.list.forEach {
                                stringBuffer.append("  ${it.title}")
                            }
                            DataProvider.setClockData[0].hint = "周${stringBuffer}"

                            var day = ClockUtil.getBetweenDay(it, mCalendar)
                            showOnTimeHint(mCalendar.time,day)
                        }
                    }
                    else {
                        showOnTimeHint(mCalendar.time,0)
                        DataProvider.setClockData[0].hint=DataProvider.repeatData[it.setClockCycle].title
                        DataProvider.setClockData[1].hint=DataProvider.closeWayData[it.closeClockWay].title

                    }
                    LogUtils.i("---222----getCurrentTimeHint----${RxTimeTool.date2String(mCalendar.time)}----------")


                    DataProvider.setClockData[2].isOpen=it.setVibration
                    DataProvider.setClockData[3].isOpen=it.setDeleteClock
                    mSetClockAdapter.setList(DataProvider.setClockData)

                    mClockBean.closeClockWay=it.closeClockWay
                    mClockBean.setClockCycle=it.setClockCycle
                    mClockBean.setVibration=it.setVibration
                    mClockBean.setDeleteClock=it.setDeleteClock
                    mClockBean.clockTimestamp=it.clockTimestamp
                    mClockBean.setDiyClockCycle=it.setDiyClockCycle

                }
            }

        }
        mClockBean.clockTimeHour=mCalendar[Calendar.HOUR_OF_DAY]
        mClockBean.clockTimeMin=mCalendar[Calendar.MINUTE]


    }

    private var mComeTime=Date()
    private var mPickHour="0"
    private var mPickMin="0"
    //显示时间滚轮
    private fun showPickerView(context: Context,selectTime:Calendar) {
        mClockSelectView.showTimePicker(context, selectTime, mTimeSelect) {it->
            mComeTime=it
            var hour = RxTimeTool.date2String(it, SimpleDateFormat("HH"))
            var min = RxTimeTool.date2String(it, SimpleDateFormat("mm"))
            if (hour.startsWith("0")) {
                hour= hour.substring(1)
            }
            if (min.startsWith("0")) {
                min = min.substring(1)
            }
            mPickHour=hour
            mPickMin=min
            mClockBean.clockTimeHour=hour.toInt()
            mClockBean.clockTimeMin=min.toInt()
            mClockBean.clockTimestamp=it.time

            if (mClockBean.setClockCycle == 3) {
                showDiyTime()

            } else {
                showOnTimeHint(it,0)
            }

        }
    }

    private fun showDiyTime() {
        mDiyData?.let {
            mCalendar[Calendar.HOUR_OF_DAY] = mPickHour.toInt()
            mCalendar[Calendar.MINUTE] = mPickMin.toInt()
            var day = ClockUtil.getBetweenDay(it, mCalendar)
            showOnTimeHint(mComeTime, day)
        }
    }




    override fun initEvent() {
        //广播监听
        mTimeChangReceiver.setListener(object :BroadcastChangeReceiver.OnReceiverListener{
            override fun onBroadcastReceiverAction(intent: Intent) {
                when (intent.action) {
                    Intent.ACTION_TIME_TICK -> {
                        when(mAction){
                            0-> showOnTimeHint(mComeTime,0)
                            1->mReviseClockBean?.apply {
                                if (setClockCycle == 3) {
                                    mDiyData?.let { it ->
                                        var day = ClockUtil.getBetweenDay(it, mCalendar)
                                        showOnTimeHint(Date(clockTimestamp), day)
                                    }
                                } else {
                                    showOnTimeHint(Date(clockTimestamp),0)
                                }

                            }
                        }
                    }
                }
            }

        })

        //保存闹钟、返回
        mAddClock.setOnBackClickListener(object :MyToolbar.OnBackClickListener{
            override fun onBack() {
                finish()
            }
            override fun onRightTo() {
                mClockBean.clockOpen=true
                GlobalScope.launch (Dispatchers.Main){
               withContext(Dispatchers.IO) {
                   val clockList= if (mClockBean.setClockCycle == 3) {
                       LitePal.where(
                               Constants.CONDITION_TWO,
                               "${mClockBean.setClockCycle}",
                               "${mClockBean.clockTimeHour}",
                               "${mClockBean.clockTimeMin}",
                               "${mClockBean.setDiyClockCycle}"
                       ).find(ClockBean::class.java)
                   } else {
                       LitePal.where(
                               Constants.CONDITION,
                               "${mClockBean.setClockCycle}",
                               "${mClockBean.clockTimeHour}",
                               "${mClockBean.clockTimeMin}"
                       ).find(ClockBean::class.java)
                   }

                   if (clockList.isEmpty()) mClockBean.save()
                   else {
                       val clockState = ClockUtil.setClockState(mClockBean, true)
                       LitePal.updateAll(ClockBean::class.java, clockState, Constants.CONDITION
                               , "${mClockBean.setClockCycle}", "${mClockBean.clockTimeHour}", "${mClockBean.clockTimeMin}")
                   }
                  }
                    //添加日历提醒
                    ClockUtil.addClockCalendarEvent(mClockBean)

                    TellTimeService.startTellTimeService(this@AddClockActivity){putExtra(Constants.TELL_TIME_SERVICE,2)}
                    setResult(1)
                    finish()
                }
            }
        })

        //设置响铃方式
        mSetClockAdapter.setOnItemCheckedChangeListener(object : ItemCheckedChangeListener<ItemBean> {
            override fun onItemChecked(itemBean: ItemBean,isCheck: Boolean, position: Int) {
                    when(position){
                        1->mClockBean.setVibration=isCheck
                        2->mClockBean.setDeleteClock=isCheck
                    }
            }

            override fun onItemClick(itemBean:ItemBean,position: Int) {
                when(position){
                    0-> mRepeatPopupWindow.show(mSetClockContainer,Gravity.CENTER)
                    1-> mClosePopupWindow.show(mSetClockContainer,Gravity.CENTER)
                }

            }
        })

        //选择循环状态
        mRepeatPopupWindow.mRepeatCountAdapter.setOnItemClickListener { adapter, view, position ->
            mRepeatPopupWindow.dismiss()
            when(position){
                in 0..2-> DataProvider.setClockData[0].hint=DataProvider.repeatData[position].title
                3->{
                    mClockDiyPopup.show(view,Gravity.BOTTOM)
                }
            }
            mClockBean.setClockCycle=position
            mSetClockAdapter.setList(DataProvider.setClockData)
        }

        //关闭闹钟方式
        mClosePopupWindow.mRepeatCountAdapter.setOnItemClickListener { adapter, view, position ->
            mClosePopupWindow.dismiss()
            DataProvider.setClockData[1].hint=DataProvider.closeWayData[position].title
            mClockBean.closeClockWay = position
            mSetClockAdapter.setList(DataProvider.setClockData)

        }



        //选择自定义时间
        mClockDiyPopup.mDiyClockTimeAdapter.setOnItemClickListener { adapter, view, position ->
            mClockDiyPopup.mDiyClockTimeAdapter.setPosition(position)
        }

        // TODO: 2020/12/1  
        //自定义时间选择监听
        mClockDiyPopup.setOnClickListener(object :DiyTimePopupListener{
            override fun cancel() {
                mClockDiyPopup.dismiss()
                mRepeatPopupWindow.mInValueAnimator?.start()
                mRepeatPopupWindow.showAtLocation(mSetClockContainer,Gravity.CENTER,0,0)
            }

            override fun sure() {
                mClockDiyPopup.dismiss()
                mClockDiyPopup.mDiyClockTimeAdapter.getSelectList()?.let { it ->
                    it.sortBy { it.icon }
                    val stringBuffer = StringBuffer()
                    when(it.size){
                       in 1..6->{
                           it.forEach {
                               stringBuffer.append("  ${it.title}")
                           }
                           DataProvider.setClockData[0].hint = "周${stringBuffer}"
                           mClockBean.setClockCycle = 3
                           mDiyData= DiyClockCycleBean(it)
                           showDiyTime()
                           mClockBean.setDiyClockCycle=Gson().toJson(mDiyData)
                       }
                        7->{
                            DataProvider.setClockData[0].hint=DataProvider.repeatData[2].title
                            mClockBean.setClockCycle=2
                        }
                        else->{
                            DataProvider.setClockData[0].hint=DataProvider.repeatData[0].title
                            mClockBean.setClockCycle=0
                        }
                    }
                    mSetClockAdapter.setList(DataProvider.setClockData)
                }
            }
        })

        //删除闹钟
        mDeleteClock.setOnClickListener { it ->
            if (!isFinishing) {
                mClockDeletePopup.show(it, Gravity.BOTTOM)
                mClockDeletePopup.mTextView.setOnClickListener {
                    GlobalScope.launch (Dispatchers.Main){
                        val deleteCount = withContext(Dispatchers.IO) {
                            mReviseClockBean?.let {
                                ClockUtil.stopAlarmClock(it)
                                //删除日历提醒
                                ClockUtil.deleteCalendarEvent(it)
                                if (it.setClockCycle == 3) {
                                    LitePal.deleteAll(ClockBean::class.java, Constants.CONDITION_TWO
                                        , "${it.setClockCycle}", "${it.clockTimeHour}", "${it.clockTimeMin}","${mClockBean.setDiyClockCycle}")
                                } else {
                                    LitePal.deleteAll(ClockBean::class.java, Constants.CONDITION
                                        , "${it.setClockCycle}", "${it.clockTimeHour}", "${it.clockTimeMin}")
                                }

                            }
                        }
                        if (deleteCount == 1) {
                            mClockDeletePopup.dismiss()
                            finish()
                        }
                    }

                }
            }
        }

    }

    //提示响铃时间
    private fun showOnTimeHint(it: Date,day:Int) {
        mOnTimeHint.text=if (day == 0)
            ClockUtil.getCurrentTimeHint(it)
        else
            "${day}天${ClockUtil.getCurrentTimeHint(it)}"
    }

    //释放资源
    override fun release() {
        unregisterReceiver(mTimeChangReceiver)
        DataProvider.setClockData[0].hint=DataProvider.repeatData[0].title
        DataProvider.setClockData[2].isOpen=true
        DataProvider.setClockData[3].isOpen=false

    }

}

