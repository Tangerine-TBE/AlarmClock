package com.example.alarmclock.ui.activity

import android.Manifest
import android.content.Intent
import androidx.recyclerview.widget.GridLayoutManager
import com.example.alarmclock.R
import com.example.alarmclock.model.DataProvider
import com.example.alarmclock.ui.adapter.recyclerview.ToolAdapter
import com.example.alarmclock.util.MarginStatusBarUtil
import com.example.module_base.base.BaseActivity
import com.example.module_base.util.top.toOtherActivity
import com.example.module_base.widget.MyToolbar
import com.example.module_tool.activity.*
import com.example.module_tool.flashlight_controller.FlashLightManager
import com.example.td_horoscope.base.MainBaseActivity
import com.permissionx.guolindev.PermissionX
import com.tamsiree.rxkit.view.RxToast
import kotlinx.android.synthetic.main.activity_more.*

class MoreActivity : MainBaseActivity() {
    override fun getLayoutView(): Int = R.layout.activity_more
    private lateinit var mToolAdapter:ToolAdapter
    private lateinit var mToolAdapter2:ToolAdapter

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
            checkRuntimePermission(DistanceActivity::class.java)
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
}
