package com.example.module_weather.ui.activity

import android.view.View
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.module_base.base.BaseVmViewViewActivity
import com.example.module_base.provider.ModuleProvider
import com.example.module_base.util.MarginStatusBarUtil
import com.example.module_base.util.SizeUtils
import com.example.module_base.util.top.toOtherActivity
import com.example.module_weather.R
import com.example.module_weather.databinding.ActivityWeatherBinding
import com.example.module_weather.domain.WeatherCacheInfo
import com.example.module_weather.livedata.CityListLiveData
import com.example.module_weather.ui.adapter.SelectCityAdapter
import com.example.module_weather.viewmodel.WeatherViewModel


@Route(path = ModuleProvider.ROUTE_WEATHER_ACTIVITY)
class WeatherActivity : BaseVmViewViewActivity<ActivityWeatherBinding, WeatherViewModel>(){

    private val mPagerAdapter by lazy {
        SelectCityAdapter(supportFragmentManager)
    }

    override fun getLayoutView(): Int=R.layout.activity_weather
    override fun getViewModelClass(): Class<WeatherViewModel> {
        return WeatherViewModel::class.java
    }

    override fun initView() {
        binding.apply {
            //设置顶部距离
            MarginStatusBarUtil.setStatusBar(this@WeatherActivity, rlHomeTopToolbar, 0)
            vpCityContainer.adapter= mPagerAdapter
            vpCityContainer.offscreenPageLimit=2
        }
        //  viewModel.queryCityNumber()
        CityListLiveData.queryCityNumber()

    }


    private var mListCity:MutableList<WeatherCacheInfo> = ArrayList()

    override fun observerData() {
        viewModel.apply {
         /*   cityList.observe(this@WeatherActivity, {
                mListCity=it
                mPagerAdapter.setData(it)
                showIndicator(it.size)
            })*/

            CityListLiveData.observe(this@WeatherActivity,{
                mListCity=it
                mPagerAdapter.setData(it)
                showIndicator(it.size)
            })

        }

    }


    override fun initEvent() {
        binding.apply {
            vpCityContainer.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {

                }

                override fun onPageSelected(position: Int) {
                    updateIndicator(position)
                       if (mListCity.size>position){
                           tvHomeCity.text=mListCity[position].city
                    }
                }

                override fun onPageScrollStateChanged(state: Int) {

                }


            })

            ivHomeSet.setOnClickListener {
                toOtherActivity<CityManageActivity>(this@WeatherActivity){}
            }

        }




    }

    //更新指示器
    private fun updateIndicator(index: Int) {

        for (i in 0 until binding.indicator.childCount) {
            val childAt: View = binding.indicator.getChildAt(i)
            if (index == i) {
                childAt.setBackgroundResource(R.drawable.loop_action_point_select)
            } else {
                childAt.setBackgroundResource(R.drawable.loop_action_point_nomal)
            }
        }
    }

    //显示指示器
    private fun showIndicator(number:Int) {
        binding.indicator.removeAllViews()
        val size: Int = SizeUtils.dip2px1(this, 5f)
        for (i in 0 until number) {
            val view = View(this)
            val params = LinearLayout.LayoutParams(size, size)
            params.leftMargin = SizeUtils.dip2px1(this, 3f)
            params.rightMargin = SizeUtils.dip2px1(this, 3f)
            view.layoutParams = params
            if (i == 0) {
                view.setBackgroundResource(R.drawable.loop_action_point_select)
            } else {
                view.setBackgroundResource(R.drawable.loop_action_point_nomal)
            }
            binding.indicator.addView(view)
        }
    }

}