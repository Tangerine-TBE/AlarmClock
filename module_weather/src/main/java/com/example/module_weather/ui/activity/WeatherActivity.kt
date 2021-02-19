package com.example.module_weather.ui.activity

import android.graphics.drawable.Drawable
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.module_base.base.BaseVmViewViewActivity
import com.example.module_base.provider.ModuleProvider
import com.example.module_base.util.LogUtils
import com.example.module_base.util.MarginStatusBarUtil
import com.example.module_base.util.MyStatusBarUtil
import com.example.module_base.util.SizeUtils
import com.example.module_base.util.top.toOtherActivity
import com.example.module_weather.R
import com.example.module_weather.databinding.ActivityWeatherBinding
import com.example.module_weather.domain.ValueUserAction
import com.example.module_weather.domain.WeatherCacheInfo
import com.example.module_weather.livedata.CityListLiveData
import com.example.module_weather.livedata.DistanceLiveData
import com.example.module_weather.livedata.DrawableLiveData
import com.example.module_weather.livedata.PositionLiveDate
import com.example.module_weather.ui.adapter.SelectCityAdapter
import com.example.module_weather.ui.fragment.CurrentCityFragment
import com.example.module_weather.utils.UserAction
import com.example.module_weather.viewmodel.CurrentCityViewModel


@Route(path = ModuleProvider.ROUTE_WEATHER_ACTIVITY)
class WeatherActivity : BaseVmViewViewActivity<ActivityWeatherBinding, CurrentCityViewModel>() {

    private val mPagerAdapter by lazy {
        SelectCityAdapter(supportFragmentManager)
    }

    override fun getLayoutView(): Int = R.layout.activity_weather
    override fun getViewModelClass(): Class<CurrentCityViewModel> {
        return CurrentCityViewModel::class.java
    }

    private var mStatusBarHeight = 0
    override fun initView() {
        binding.apply {
            //设置顶部距离
            MarginStatusBarUtil.setStatusBar(this@WeatherActivity, rlHomeTopToolbar, 0)
            vpCityContainer.adapter = mPagerAdapter
            vpCityContainer.offscreenPageLimit = 10
        }
        //  viewModel.queryCityNumber()
        CityListLiveData.queryCityNumber()
        mStatusBarHeight = MyStatusBarUtil.getStatusBarHeight(this)
    }


    private var mListCity: MutableList<WeatherCacheInfo> = ArrayList()
    private var mDrawable: Drawable? = null
    override fun observerData() {
        binding.apply {
            viewModel.apply {

                CityListLiveData.observe(this@WeatherActivity, { data ->
                    mListCity = data
                    mPagerAdapter.setData(data)
                    showIndicator(data.size)
                })

                PositionLiveDate.observe(this@WeatherActivity, {
                    var position = when (it.action) {
                        UserAction.NONE -> { 0 }
                        UserAction.ADD -> { mListCity.size - 1 }
                        UserAction.DELETE -> {
                            when{
                                it.position > mPosition-> mPosition
                                it.position < mPosition->  mPosition - 1
                                (it.position == mPosition) and (it.position != mListCity.size)-> mPosition
                                it.position == mListCity.size + 1->  mListCity.size - 1
                                it.position == mPosition && it.position == mListCity.size->    mListCity.size - 1
                                else->0
                            }
                            }
                    }
                    if (mListCity.size>position){
                        tvHomeCity.text = mListCity[position].city
                        updateIndicator(position)
                        vpCityContainer.setCurrentItem(position,true)
                    }
                })

                DrawableLiveData.observe(this@WeatherActivity, {
                    mDrawable = it
                })


                DistanceLiveData.observe(this@WeatherActivity, {
                    val dis: Int = topInclude.height + mStatusBarHeight
                    if (it >= dis) {
                        if (mDrawable == null) {
                            topInclude.background = resources.getDrawable(
                                R.color.color_rv_text,
                                null
                            )
                        } else {
                            topInclude.background = mDrawable
                        }
                    } else if (it < dis) {
                        topInclude.background = resources.getDrawable(
                            R.color.transparent,
                            null
                        )
                        setShowAnimation()
                    }
                })

            }
        }
    }


    private var mPosition = 0
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
                    mPosition = position

                    if (mListCity.size > position) {
                        tvHomeCity.text = mListCity[position].city
                        updateIndicator(position)
                    }

                    topInclude.background = resources.getDrawable(R.color.transparent, null)
                    if (mPagerAdapter.instantFragment is CurrentCityFragment) {
                        (mPagerAdapter.instantFragment as CurrentCityFragment).onNestedScrollView()
                    }

                }

                override fun onPageScrollStateChanged(state: Int) {

                }


            })

            ivHomeSet.setOnClickListener {
                toOtherActivity<CityManageActivity>(this@WeatherActivity) {}
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
    private fun showIndicator(number: Int) {
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

    private var mShowAnimation: AlphaAnimation? = null
    private fun setShowAnimation() {
        if (mShowAnimation == null) {
            mShowAnimation = AlphaAnimation(0.0f, 1.0f)
        }
        mShowAnimation?.duration = 1000
        mShowAnimation?.fillAfter = true
        binding.topInclude.startAnimation(mShowAnimation)
    }

    override fun release() {
        PositionLiveDate.setPosition(ValueUserAction(UserAction.NONE, 0))
    }

}