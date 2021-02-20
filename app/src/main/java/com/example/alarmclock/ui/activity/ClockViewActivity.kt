package com.example.alarmclock.ui.activity

import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmclock.R
import com.example.alarmclock.bean.ClockBean
import com.example.alarmclock.broadcast.BroadcastChangeReceiver
import com.example.alarmclock.interfaces.ItemCheckedChangeListener
import com.example.alarmclock.service.TellTimeService
import com.example.alarmclock.topfun.showDialog
import com.example.alarmclock.ui.adapter.recyclerview.ClockListAdapter
import com.example.alarmclock.util.*
import com.example.module_base.util.LogUtils
import com.example.module_base.util.MarginStatusBarUtil
import com.example.module_base.util.SizeUtils
import com.example.module_base.util.top.toOtherActivityForResult
import com.example.module_base.widget.MyToolbar
import com.example.td_horoscope.base.MainBaseViewActivity
import com.tamsiree.rxui.view.dialog.RxDialogSureCancel
import com.yanzhenjie.recyclerview.*
import kotlinx.android.synthetic.main.activity_clock.*
import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.litepal.LitePal

class ClockViewActivity : MainBaseViewActivity(), SwipeMenuCreator, OnItemMenuClickListener {
    private var mClockList: MutableList<ClockBean>? = ArrayList()
    override fun getLayoutView(): Int = R.layout.activity_clock
    private val mRemindDialog by lazy { DialogUtil.createRemindDialog(this) }
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
        mClockContainer.setSwipeMenuCreator(this)
        mClockContainer.setOnItemMenuClickListener(this)
        mClockContainer.layoutManager = LinearLayoutManager(this)
        mClockContainer.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.bottom = SizeUtils.dip2px(this@ClockViewActivity, 7.5f)
            }
        })
        mClockContainer.adapter = mClockAdapter

    }

    override fun initPresent() {
        val timeFilter = IntentFilter().apply {
            addAction(Intent.ACTION_TIME_TICK)
        }
        registerReceiver(mTimeChangeReceiver, timeFilter)
        EventBus.getDefault().register(this)
    }

    override fun onResume() {
        super.onResume()
        getClockList()
    }


    //获取数据库闹钟列表
    private fun getClockList() {
        GlobalScope.launch(Dispatchers.Main) {
            mClockList = withContext(Dispatchers.IO) {
                LitePal.findAll(ClockBean::class.java)
            }
            sortByClockList()
        }
    }

    private fun sortByClockList() {
        mClockList?.let { it ->
            it.sortBy { it.clockTimestamp }
            mClockAdapter.setData(it)
        }
    }

    //响铃后更新数据库刷新列表
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoadTimeOutList(clockList: MutableList<ClockBean>) {
        mClockList = clockList
        sortByClockList()
    }


    override fun initEvent() {
        //toolbar按键监听
        mClockBar.setOnBackClickListener(object : MyToolbar.OnBackClickListener {
            override fun onBack() {
                finish()
            }

            override fun onRightTo() {
                    mDeleteClock.showDialog(this@ClockViewActivity)
            }
        })

        //删除全部闹钟
        mDeleteClock.setSureListener(View.OnClickListener {
            mJobScope.launch(Dispatchers.Main) {
                val queryOpenClick = ClockUtil.queryOpenClick()
                queryOpenClick?.let { it ->
                    it.forEach {
                        ClockUtil.stopAlarmClock(it)
                    }
                }


                val list = withContext(Dispatchers.IO) {
                    LitePal.deleteAll(ClockBean::class.java)
                    LitePal.findAll(ClockBean::class.java)
                }
                mClockAdapter.setData(list)
                mDeleteClock.dismiss()

                //删除日历提醒
                CalendarUtil.deleteAllCalendarEvent(this@ClockViewActivity,"闹钟提醒")

            }
        })

        mDeleteClock.setCancelListener(View.OnClickListener {
            mDeleteClock.dismiss()
        })


        //添加闹钟
        mSetClock.setOnClickListener {
            toOtherActivityForResult<AddClockViewActivity>(this, 1) {
                putExtra(Constants.CLOCK_ACTION, 0)
            }

        }

        //闹钟列表点击及开关闹钟
        mClockAdapter.setOnItemCheckedChangeListener(object : ItemCheckedChangeListener<ClockBean> {
            override fun onItemChecked(itemBean: ClockBean, isCheck: Boolean, position: Int) {
                val clockState = ClockUtil.setClockState(itemBean, isCheck)
                mJobScope.launch(Dispatchers.Main) {
                    val async = async {
                        withContext(Dispatchers.IO) {
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
                            if (CheckPermissionUtil.lacksPermissions()){
                                //添加日历提醒
                                if (isCheck) {
                                    ClockUtil.addClockCalendarEvent(itemBean)
                                } else {
                                    ClockUtil.deleteCalendarEvent(itemBean)
                                }
                            }
                        }
                    }
                    mClockList = withContext(Dispatchers.IO) {
                        async.await()
                        LitePal.findAll(ClockBean::class.java)
                    }
                    sortByClockList()
                    TellTimeService.startTellTimeService(this@ClockViewActivity){  putExtra(Constants.TELL_TIME_SERVICE,2) }
                }

            }

            override fun onItemClick(itemBean: ClockBean, position: Int) {
                toOtherActivityForResult<AddClockViewActivity>(this@ClockViewActivity, 1) {
                    putExtra(Constants.CLOCK_ACTION, 1)
                    putExtra(Constants.CLOCK_INFO, itemBean)
                }
            }
        })




        // 广播监听
        mTimeChangeReceiver.setListener(object : BroadcastChangeReceiver.OnReceiverListener {
            override fun onBroadcastReceiverAction(intent: Intent) {
                when (intent.action) {
                    Intent.ACTION_TIME_TICK -> {
                        mClockAdapter.notifyDataSetChanged()
                        LogUtils.i("我是时间变化----------------------onBroadcastReceiverListener")
                    }
                }
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode==1) if (!mSPUtil.getBoolean(Constants.DISMISS_DIALOG))
                mRemindDialog.showDialog(this)
    }


    override fun release() {
            mDeleteClock.dismiss()
            mRemindDialog.dismiss()

        mJob?.cancel()
        unregisterReceiver(mTimeChangeReceiver)
        EventBus.getDefault().unregister(this)
    }

    override fun onCreateMenu(leftMenu: SwipeMenu?, rightMenu: SwipeMenu?, position: Int) {
        val width = resources.getDimensionPixelSize(R.dimen.dp_70)
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        val deleteItem: SwipeMenuItem =
            SwipeMenuItem(this).setBackground(R.drawable.selector_red)
                .setImage(R.mipmap.ic_action_delete)
                .setText("删除")
                .setTextColor(Color.WHITE)
                .setWidth(width)
                .setBackground(R.drawable.shape_document_delete_bg)
                .setHeight(height)
        rightMenu!!.addMenuItem(deleteItem) // 添加菜单到右侧。
    }

    override fun onItemClick(menuBridge: SwipeMenuBridge, adapterPosition: Int) {
        val direction = menuBridge.direction // 左侧还是右侧菜单。
        if (direction == SwipeRecyclerView.RIGHT_DIRECTION) {
            mJobScope.launch(Dispatchers.Main) {
                mClockList?.let {
                        val deleteCount = withContext(Dispatchers.IO) {
                            val clockBean = it[adapterPosition]
                            clockBean?.let {
                                ClockUtil.stopAlarmClock(it)
                                //删除日历提醒
                                if (CheckPermissionUtil.lacksPermissions()) ClockUtil.deleteCalendarEvent(
                                    it
                                )
                                if (it.setClockCycle == 3) {
                                    LitePal.deleteAll(
                                        ClockBean::class.java,
                                        Constants.CONDITION_TWO
                                        ,
                                        "${it.setClockCycle}",
                                        "${it.clockTimeHour}",
                                        "${it.clockTimeMin}",
                                        "${it.setDiyClockCycle}"
                                    )
                                } else {
                                    LitePal.deleteAll(
                                        ClockBean::class.java,
                                        Constants.CONDITION
                                        ,
                                        "${it.setClockCycle}",
                                        "${it.clockTimeHour}",
                                        "${it.clockTimeMin}"
                                    )
                                }
                            }

                        }

                    mClockList = withContext(Dispatchers.IO) {
                      if (deleteCount == 1) {
                          LitePal.findAll(ClockBean::class.java)
                      } else {
                          mClockList
                      }
                    }
                  sortByClockList()
                }
            }
        }

    }


}