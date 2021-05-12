package com.twx.clock.ui.fragment

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.twx.clock.R
import com.twx.clock.bean.ItemBean
import com.twx.clock.bean.SkinType
import com.twx.clock.databinding.FragmentSkinBinding
import com.twx.clock.repository.DataProvider
import com.twx.clock.ui.adapter.recyclerview.SkinAdapter
import com.twx.clock.util.Constants
import com.twx.module_base.base.BaseViewFragment
import com.twx.module_base.util.top.toOtherActivity
import com.twx.module_usercenter.ui.activity.BuyVipViewActivity
import com.twx.module_usercenter.utils.Contents
import com.twx.module_usercenter.utils.SpUtil
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_skin.*


/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.ui.fragment
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2021/2/4 19:42:18
 * @class describe
 */
class SkinTypeFragment:BaseViewFragment<FragmentSkinBinding>() {
    companion object{
        @JvmStatic
        fun getInstance(type:Int):SkinTypeFragment{
            val skinTypeFragment = SkinTypeFragment()
            val bundle = Bundle()
            bundle.putInt(Constants.SKIN_TYPE,type)
            skinTypeFragment.arguments=bundle
            return skinTypeFragment
        }
    }

    private var list:MutableList<ItemBean> = ArrayList()
    private lateinit var mSkinAdapter: SkinAdapter
    private var type=0
    override fun getChildLayout():Int = R.layout.fragment_skin
    override fun initView() {

        mSkinContainer.layoutManager = GridLayoutManager(activity, 2)
        mSkinAdapter= SkinAdapter()
        mSkinContainer.adapter=mSkinAdapter
        type= arguments?.getInt(Constants.SKIN_TYPE, 0)?:0
        list = when (type) {
            0 -> {
                mSkinAdapter.setList(DataProvider.skinNumber)
                DataProvider.skinNumber
            }
            1 -> {
                mSkinAdapter.setList(DataProvider.skinWatch)
                DataProvider.skinWatch
            }
            2 -> {
                mSkinAdapter.setList(DataProvider.skinCalendar)
                DataProvider.skinCalendar
            }
            else -> DataProvider.skinNumber
        }

    }



    override fun initEvent() {
        mSkinAdapter.setOnItemClickListener { adapter, view, position ->
           if (list.size >0){
               val skinType = list[position]
               if (skinType.isOpen) {
                   if (SpUtil.isVIP()) {
                       changeSkin(skinType,position)
                   } else {
                       toOtherActivity<BuyVipViewActivity>(activity, false) {putExtra(Contents.TO_BUY,true)}
                   }
               } else {
                   changeSkin(skinType,position)
               }
           }
        }
    }


    private fun changeSkin(skinType:ItemBean,position: Int) {
        mSkinAdapter.setCurrentPosition(position)
        sp.putString(Constants.CURRENT_THEME, Gson().toJson(SkinType(skinType,position)))
        activity?.finish()
    }
}