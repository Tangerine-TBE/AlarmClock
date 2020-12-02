package com.example.alarmclock.ui.activity

import android.content.Intent
import android.content.IntentFilter
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alarmclock.R
import com.example.alarmclock.bean.ClockBean
import com.example.alarmclock.broadcast.AlarmClockReceiver
import com.example.alarmclock.broadcast.BroadcastChangeReceiver
import com.example.alarmclock.interfaces.ItemCheckedChangeListener
import com.example.alarmclock.interfaces.OnClockTimeOutListener
import com.example.alarmclock.service.TellTimeService
import com.example.alarmclock.ui.adapter.recyclerview.ClockListAdapter
import com.example.alarmclock.util.ClockUtil
import com.example.alarmclock.util.Constants
import com.example.alarmclock.util.MarginStatusBarUtil
import com.example.module_base.base.BaseApplication
import com.example.module_base.util.LogUtils
import com.example.module_base.util.top.toOtherActivity
import com.example.module_base.widget.MyToolbar
import com.example.td_horoscope.base.MainBaseActivity
import com.tamsiree.rxui.view.dialog.RxDialogSureCancel
import kotlinx.android.synthetic.main.activity_clock.*
import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.litepal.LitePal

class ClockActivity : MainBaseActivity() {
    override fun getLayoutView(): Int=R.layout.activity_clock
    private val mTimeChangeReceiver by lazy {
        BroadcastChangeReceiver()
    }

    private val mDeleteClock by lazy {
        RxDialogSureCancel(this).apply {
            setContent("您确定要删除全部闹钟吗")
        }
    }

    private val mClockAdapter by lazy {
        ClockListAdapter()
    }

    override fun initView() {
        //设置顶部距离
        MarginStatusBarUtil.setStatusBar(this, mClockBar, 0)

        mClockContainer.layoutManager=LinearLayoutManager(this)
        mClockContainer.adapter = mClockAdapter

    }

    override fun initPresent() {
        val timeFilter = IntentFilter().apply {
            addAction(Intent.ACTION_TIME_TICK)
        }
        registerReceiver(mTimeChangeReceiver,timeFilter)
        EventBus.getDefault().register(this)
    }

    override fun onResume() {
        super.onResume()
        getClockList()
    }

    private fun getClockList() {
        GlobalScope.launch(Dispatchers.Main) {
            val allClock = withContext(Dispatchers.IO) {
                LitePal.findAll(ClockBean::class.java)
            }
            allClock.sortBy { it.clockTimestamp }
            mClockAdapter.setData(allClock)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoadTimeOutList(clockList: MutableList<ClockBean>){
        clockList.sortBy { it.clockTimestamp }
        mClockAdapter?.setData(clockList)
    }


    override fun initEvent() {
        //toolbar按键监听
        mClockBar.setOnBackClickListener(object : MyToolbar.OnBackClickListener{
            override fun onBack() {
                finish()
            }
            override fun onRightTo() {
                mDeleteClock.show()
            }
        })

        //删除全部闹钟
        mDeleteClock.setSureListener(View.OnClickListener {
            GlobalScope.launch (Dispatchers.Main){
                val queryOpenClick = ClockUtil.queryOpenClick()
                queryOpenClick?.let { it ->
                    it.forEach {
                       ClockUtil.stopAlarmClock(it)
                    }
                }
               val list= withContext(Dispatchers.IO){
                    LitePal.deleteAll(ClockBean::class.java)
                    LitePal.findAll(ClockBean::class.java)
                }
                mClockAdapter.setData(list)
                mDeleteClock.dismiss()
            }

        })

        mDeleteClock.setCancelListener(View.OnClickListener {
            mDeleteClock.dismiss()
        })


        //添加闹钟
        mSetClock.setOnClickListener {
            toOtherActivity<AddClockActivity>(this,false){
                putExtra(Constants.CLOCK_ACTION,0)
            }
        }

        //闹钟列表点击及开关
        mClockAdapter.setOnItemCheckedChangeListener(object :ItemCheckedChangeListener<ClockBean>{
            override fun onItemChecked(itemBean: ClockBean, isCheck: Boolean, position: Int) {
                val clockState = ClockUtil.setClockState(itemBean, isCheck)
                GlobalScope.launch (Dispatchers.Main){
                         val async = async {
                             withContext(Dispatchers.IO){
                                 if (itemBean.setClockCycle == 3) {
                                     LitePal.updateAll(
                                             ClockBean::class.java,
                                             clockState,
                                             Constants.CONDITION_TWO,
                                             "${itemBean.setClockCycle}",
                                             "${itemBean.clockTimeHour}",
                                             "${itemBean.clockTimeMin}",
                                             "${itemBean.setDiyClockCycle}"
                                     )
                                 } else {
                                     LitePal.updateAll(
                                             ClockBean::class.java,
                                             clockState,
                                             Constants.CONDITION,
                                             "${itemBean.setClockCycle}",
                                             "${itemBean.clockTimeHour}",
                                             "${itemBean.clockTimeMin}"
                                     )
                                 }
                             }
                         }
                    val list =
                        withContext(Dispatchers.IO) {
                                async.await()
                                LitePal.findAll(ClockBean::class.java)
                        }
                    list.sortBy { it.clockTimestamp }
                    mClockAdapter.setData(list)
                }

                BaseApplication.getMainHandler().postDelayed(Runnable {
                    TellTimeService.startTellTimeService(this@ClockActivity)
                },1000)

            }

            override fun onItemClick(itemBean: ClockBean, position: Int) {
                toOtherActivity<AddClockActivity>(this@ClockActivity,false){
                    putExtra(Constants.CLOCK_ACTION,1)
                    putExtra(Constants.CLOCK_INFO,itemBean)
                }
            }
        })


        // 广播监听
        mTimeChangeReceiver.setListener(object :BroadcastChangeReceiver.OnReceiverListener{
            override fun onBroadcastReceiverAction(intent: Intent) {
                when(intent.action){
                    Intent.ACTION_TIME_TICK->{
                        mClockAdapter.notifyDataSetChanged()
                        LogUtils.i("我是时间变化----------------------onBroadcastReceiverListener")
                    }
                }
            }
        })

    }


    override fun release() {
        mDeleteClock.dismiss()
        unregisterReceiver(mTimeChangeReceiver)
        EventBus.getDefault().unregister(this)
    }
}