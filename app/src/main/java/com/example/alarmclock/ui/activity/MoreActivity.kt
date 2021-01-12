package com.example.alarmclock.ui.activity

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.alarmclock.R
import com.example.alarmclock.model.DataProvider
import com.example.alarmclock.ui.adapter.recyclerview.ToolAdapter
import com.example.module_ad.advertisement.AdType
import com.example.module_ad.advertisement.FeedHelper
import com.example.module_ad.advertisement.InsertHelper
import com.example.module_ad.service.TimeService
import com.example.module_base.util.MarginStatusBarUtil
import com.example.module_base.util.top.toOtherActivity
import com.example.module_base.widget.MyToolbar
import com.example.module_tool.activity.*
import com.example.module_tool.flashlight_controller.FlashLightManager
import com.example.module_usercenter.ui.activity.BuyVipActivity
import com.example.module_usercenter.utils.Contents
import com.example.module_usercenter.utils.SpUtil
import com.example.td_horoscope.base.MainBaseActivity
import com.permissionx.guolindev.PermissionX
import com.tamsiree.rxkit.view.RxToast
import kotlinx.android.synthetic.main.activity_more.*

class MoreActivity : MainBaseActivity() {
    override fun getLayoutView(): Int = R.layout.activity_more
    private lateinit var mToolAdapter:ToolAdapter
    private lateinit var mToolAdapter2:ToolAdapter
    private val mFeedAd by lazy {
        FeedHelper(this,mMoreAdContainer)
    }
    private val mInsertHelper by lazy {
        InsertHelper(this)
    }

    private val serviceConnection by lazy {
        object: ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
            }
            override fun onServiceConnected(name: ComponentName?, service: IBinder) {
                val myBinder = service as? TimeService.MyBinder
                myBinder?.let { it ->
                    it.getService.showMore.observe(this@MoreActivity, Observer {
                        isShow=it
                    })
                    if (isShow) {
                        mInsertHelper.showAd(AdType.MORE_PAGE)
                    }
                }
            }
        }
    }


    override fun initView() {
        FlashLightManager.getInstance().init(this)
        //设置顶部距离
        MarginStatusBarUtil.setStatusBar(this, mMoreBar, 1)

        MeasuringContainer.layoutManager = GridLayoutManager(this, 2)
        mToolAdapter= ToolAdapter(R.layout.item_tool_container)
        mToolAdapter.setList(DataProvider.toolData.subList(0,4))
        MeasuringContainer.adapter=mToolAdapter

        OtherContainer.layoutManager = GridLayoutManager(this, 3)
        mToolAdapter2 = ToolAdapter(R.layout.item_tool2_container)
        mToolAdapter2.setList(DataProvider.toolData.subList(4,8))
        OtherContainer.adapter=mToolAdapter2

        mFeedAd.showAd(AdType.MORE_PAGE)

        bindService(Intent(this,TimeService::class.java),serviceConnection, Context.BIND_AUTO_CREATE)

    }
    private val permissions = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
    )

    private fun checkRuntimePermission(act:Class<*>) {
        PermissionX.init(this)
                .permissions(*permissions)
                .onExplainRequestReason { scope, deniedList, beforeRequest ->
                    RxToast.normal(this, "该功能必须开启此权限")
                    //   scope.showRequestReasonDialog(deniedList, "即将申请的权限是程序必须依赖的权限", "我已明白");
                }
                .onForwardToSettings { scope, deniedList ->
                    RxToast.normal(this, "您需要去应用程序设置当中手动开启权限")
                    //  scope.showForwardToSettingsDialog(deniedList, "您需要去应用程序设置当中手动开启权限", "我已明白");
                }
                .request { allGranted, grantedList, deniedList ->
                    if (allGranted) {
                        startActivity(Intent(this,act))
                    }
                }
    }


    override fun initEvent() {
        mMoreBar.setOnBackClickListener(object : MyToolbar.OnBackClickListener {
            override fun onBack() {
                finish()
            }
            override fun onRightTo() {
            }
        })

        mToolDistance.setOnClickListener {
            if (SpUtil.isVIP()) {
                checkRuntimePermission(DistanceActivity::class.java)
            } else {
                toOtherActivity<BuyVipActivity>(this, false) {putExtra(Contents.TO_BUY,true)}
            }

        }

        mToolAdapter.setOnItemClickListener { adapter, view, position ->
            when(position){
                0-> toOtherActivity<RulerActivity2>(this,false){}
                1->toOtherActivity<SoundActivity>(this,false){}
                2->checkRuntimePermission(CycleRulerActivity::class.java)
                3->toOtherActivity<HorizontalActivity>(this,false){}
            }
        }
        mToolAdapter2.setOnItemClickListener { adapter, view, position ->
            when(position){
                0->checkRuntimePermission(MirrorActivity::class.java)
                1-> FlashLightManager.getInstance().startFlashLight(!FlashLightManager.getInstance().flashLightState)
                2->toOtherActivity<CompassActivity>(this,false){}
                3->checkRuntimePermission(HandleActivity::class.java)
            }
        }

    }


    override fun onPause() {
        super.onPause()
        mInsertHelper.releaseAd()
    }

    override fun release() {
        super.release()
        mFeedAd.releaseAd()
        mInsertHelper.releaseAd()
        unbindService(serviceConnection)
    }
}
