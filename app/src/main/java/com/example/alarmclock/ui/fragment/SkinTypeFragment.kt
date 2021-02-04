package com.example.alarmclock.ui.fragment

import android.os.Bundle
import com.example.alarmclock.R
import com.example.module_base.base.BaseFragment
import com.example.module_base.util.LogUtils
import kotlinx.android.synthetic.main.fragment_skin.*

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.ui.fragment
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2021/2/4 19:42:18
 * @class describe
 */
class SkinTypeFragment: BaseFragment() {


    companion object{
        @JvmStatic
        fun getInstance(type:Int):SkinTypeFragment{
            val skinTypeFragment = SkinTypeFragment()
            val bundle = Bundle()
            bundle.putInt("q",type)
            skinTypeFragment.arguments=bundle
            return skinTypeFragment
        }

    }
    override fun getLayoutView(): Int=
            R.layout.fragment_skin

    override fun initView() {
        switchUIByState(PageState.SUCCESS)
        val int1 = arguments?.getInt("q", 0)
        LogUtils.i("-----initView----------$arguments---------$int1-------")
   //     texdsgds.text="$int1"+"ssssssssssssssssss"

    }


}