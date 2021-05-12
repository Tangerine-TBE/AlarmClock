package com.twx.clock.ui.adapter.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.twx.clock.repository.DataProvider
import com.twx.clock.ui.fragment.SkinTypeFragment

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.ui.adapter.viewpager
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2021/2/4 19:37:11
 * @class describe
 */
class SkinViewPagerAdapter(fm:FragmentManager):FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int= DataProvider.skinTypeList.size

    override fun getItem(position: Int): Fragment =SkinTypeFragment.getInstance(position)

    override fun getPageTitle(position: Int): CharSequence? = DataProvider.skinTypeList[position].title


}