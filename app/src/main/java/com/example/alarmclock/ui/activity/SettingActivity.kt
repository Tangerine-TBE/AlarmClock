package com.example.alarmclock.ui.activity

import android.widget.Switch
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.sdk.android.feedback.impl.FeedbackAPI
import com.example.alarmclock.R
import com.example.alarmclock.model.DataProvider
import com.example.alarmclock.ui.adapter.recyclerview.SettingAdapter
import com.example.alarmclock.ui.adapter.recyclerview.SettingBottomAdapter
import com.example.alarmclock.ui.adapter.recyclerview.SettingTopAdapter
import com.example.alarmclock.util.Constants
import com.example.alarmclock.util.MarginStatusBarUtil
import com.example.module_base.ui.DealActivity
import com.example.module_base.util.LogUtils
import com.example.module_base.util.top.toOtherActivity
import com.example.module_base.widget.MyToolbar
import com.example.td_horoscope.base.MainBaseActivity
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : MainBaseActivity() {

    private lateinit var mSetTopAdapter:SettingTopAdapter
    private lateinit var mOtherSetTopAdapter:SettingBottomAdapter
    private lateinit var mSetAdapter: SettingAdapter

    override fun getLayoutView(): Int=R.layout.activity_setting
    override fun initView() {
        //设置顶部距离
        MarginStatusBarUtil.setStatusBar(this, mSetBar, 1)
        //top设置
      /*  mSetContainer.layoutManager= LinearLayoutManager(this)
        mSetTopAdapter= SettingTopAdapter()
        DataProvider.setData[0].isOpen=mSPUtil.getBoolean(Constants.SET_SHOW_HOUR24)
        DataProvider.setData[1].isOpen=mSPUtil.getBoolean(Constants.SET_SHOW_LANDSCAPE)
        DataProvider.setData[2].isOpen=mSPUtil.getBoolean(Constants.SET_SHOW_SECOND)
        mSetTopAdapter.setList(DataProvider.setData)
        mSetContainer.adapter=mSetTopAdapter
        mSetTopAdapter.addChildClickViewIds(R.id.mSetSwitch)*/


        mSetContainer.layoutManager= LinearLayoutManager(this)
        mSetAdapter=SettingAdapter()
        DataProvider.setData[0].isOpen=mSPUtil.getBoolean(Constants.SET_SHOW_HOUR24)
        DataProvider.setData[1].isOpen=mSPUtil.getBoolean(Constants.SET_SHOW_LANDSCAPE)
        DataProvider.setData[2].isOpen=mSPUtil.getBoolean(Constants.SET_SHOW_SECOND)
        mSetAdapter.setList(DataProvider.setData)
        mSetContainer.adapter=mSetAdapter

        //bottom设置
        mOtherSetContainer.layoutManager=LinearLayoutManager(this)
        mOtherSetTopAdapter= SettingBottomAdapter()
        mOtherSetTopAdapter.setList(DataProvider.setOtherData)
        mOtherSetContainer.adapter=mOtherSetTopAdapter

    }
    override fun initEvent() {
        mSetBar.setOnBackClickListener(object : MyToolbar.OnBackClickListener{
            override fun onBack() {
                finish()
            }
            override fun onRightTo() {

            }
        })
        mSetAdapter.setOnItemCheckedChangeListener(object :SettingAdapter.OnItemCheckedChangeListener{
            override fun onItemChecked(isCheck: Boolean,position:Int) {
                when(position){
                    0-> mSPUtil.putBoolean(Constants.SET_SHOW_HOUR24,isCheck)
                    1-> mSPUtil.putBoolean(Constants.SET_SHOW_LANDSCAPE,isCheck)
                    2-> mSPUtil.putBoolean(Constants.SET_SHOW_SECOND,isCheck)
                }
            }
        })


      /*   mSetTopAdapter.setOnItemChildClickListener { adapter, view, position ->
             when(position){
                 0-> mSPUtil.putBoolean(Constants.SET_SHOW_HOUR24,!DataProvider.setData[position].isOpen)
                 1-> mSPUtil.putBoolean(Constants.SET_SHOW_LANDSCAPE,!DataProvider.setData[position].isOpen)
                 2-> mSPUtil.putBoolean(Constants.SET_SHOW_SECOND,!DataProvider.setData[position].isOpen)
             }
             }*/


        mOtherSetTopAdapter.setOnItemClickListener { adapter, view, position ->
            when(position){
                0-> FeedbackAPI.openFeedbackActivity()
                1->{}
                in 2..3 ->{ toOtherActivity<DealActivity>(this,false){putExtra(com.example.module_base.util.Constants.SET_Deal1,position)} }
            }
        }


        }

}