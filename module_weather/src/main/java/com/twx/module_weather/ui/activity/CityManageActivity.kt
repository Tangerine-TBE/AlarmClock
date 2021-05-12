package com.twx.module_weather.ui.activity

import android.graphics.Color
import android.text.TextUtils
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.twx.module_weather.databinding.ActivityCityManageBinding
import com.twx.module_base.base.BaseVmViewViewActivity
import com.twx.module_base.util.Constants
import com.twx.module_base.util.MarginStatusBarUtil
import com.twx.module_base.util.RecyclerViewItemDistanceUtil
import com.twx.module_weather.R
import com.twx.module_weather.domain.ValueUserAction
import com.twx.module_weather.domain.WeatherCacheInfo
import com.twx.module_weather.livedata.CityListLiveData
import com.twx.module_weather.livedata.PositionLiveDate
import com.twx.module_weather.ui.adapter.CityListAdapter
import com.twx.module_weather.utils.UserAction
import com.twx.module_weather.utils.formatCity
import com.twx.module_weather.viewmodel.CityManageViewModel
import com.tamsiree.rxkit.view.RxToast
import com.tamsiree.rxkit.view.RxToast.warning
import com.twx.module_base.util.setBarAction
import com.yanzhenjie.recyclerview.*
import com.zaaach.citypicker.CityPicker
import com.zaaach.citypicker.adapter.OnPickListener
import com.zaaach.citypicker.model.City
import com.zaaach.citypicker.model.LocatedCity

class CityManageActivity : BaseVmViewViewActivity<ActivityCityManageBinding, CityManageViewModel>(),
    OnPickListener, SwipeMenuCreator, OnItemMenuClickListener {

    private val mCityListAdapter by lazy {
        CityListAdapter()
    }
    private val mCurrentCity by lazy {
        formatCity(mSPUtil.getString(Constants.LOCATION_CITY, ""))
    }

    private var mSelectCity=""

    override fun getLayoutView(): Int = R.layout.activity_city_manage
    override fun getViewModelClass(): Class<CityManageViewModel> {
        return CityManageViewModel::class.java
    }

    override fun initView() {
        binding.apply {
            //设置顶部距离
            MarginStatusBarUtil.setStatusBar(this@CityManageActivity, cityToolbar, 0)


            //侧滑删除
            cityContainer.setSwipeMenuCreator(this@CityManageActivity)
            cityContainer.setOnItemMenuClickListener(this@CityManageActivity)
            val manager = LinearLayoutManager(this@CityManageActivity)
            RecyclerViewItemDistanceUtil.setDistance(cityContainer, this@CityManageActivity, 3.5f)
            cityContainer.layoutManager = manager
            cityContainer.adapter = mCityListAdapter


          //  CityListLiveData.queryCityNumber()
          //  viewModel.queryCityList()
        }


    }

    private var mCityList:MutableList<WeatherCacheInfo> = ArrayList()
    override fun observerData() {
        viewModel.apply {
          val that=this@CityManageActivity
      /*      cityList.observe(that, {
                mCityList = it
                mCityListAdapter.setData(it)
            })*/

            CityListLiveData.observe(that,{
                mCityList = it
                mCityListAdapter.setData(it)
            })


            cityLocation.observe(that, {
                if (!TextUtils.isEmpty(mSelectCity)) {
                    getWeatherMsg(mSelectCity, it.lng.toString(), it.lat.toString())
                }
            })

        }

    }

    override fun initEvent() {
        binding.apply {
            cityToolbar.setBarAction(this@CityManageActivity) {
                CityPicker.from(this@CityManageActivity)
                    .enableAnimation(false)
                    .setAnimationStyle(0)
                    .setLocatedCity(LocatedCity(mCurrentCity, "", ""))
                    .setHideBar(true)
                    .setOnPickListener(this@CityManageActivity).show()
            }

            mCityListAdapter.setOnItemClickListener(object : CityListAdapter.OnItemClickListener {
                override fun deleteOnClick(weatherCacheInfo: WeatherCacheInfo, position: Int) {
                    if (TextUtils.isEmpty(mCurrentCity)) return
                    if (weatherCacheInfo.city == formatCity(mCurrentCity)) {
                        RxToast.error("当前所在城市不能移除")
                    } else {
                        viewModel.deleteCity(weatherCacheInfo.city)
                    }
                }
                override fun OnItemClick(city: WeatherCacheInfo, position: Int) {

                }

            })

        }
    }

    override fun onPick(position: Int, data: City?) {
        data?.let {
            if (mCityList.size < 10) {
                mSelectCity=it.name
                viewModel.queryCityLocation(mSelectCity)
            } else {
                warning("最多只能添加十个城市")
            }
        }
    }

    override fun onLocate() {

    }

    override fun onCancel() {

    }

    override fun onFail(position: Int, data: City?) {

    }

    override fun onCreateMenu(leftMenu: SwipeMenu?, rightMenu: SwipeMenu?, position: Int) {
        val width = resources.getDimensionPixelSize(R.dimen.dp_70)
        val height = ViewGroup.LayoutParams.MATCH_PARENT
            val deleteItem: SwipeMenuItem =
                SwipeMenuItem(this@CityManageActivity).setBackground(R.drawable.selector_red)
                    .setImage(R.mipmap.ic_action_delete)
                    .setText("删除")
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setBackground(R.drawable.shape_city_delete_bg)
                    .setHeight(height)
            rightMenu!!.addMenuItem(deleteItem) // 添加菜单到右侧。

    }

    override fun onItemClick(menuBridge: SwipeMenuBridge, adapterPosition: Int) {
        menuBridge?.closeMenu()
        val direction = menuBridge.direction // 左侧还是右侧菜单。
        if (direction == SwipeRecyclerView.RIGHT_DIRECTION) {
            mCityListAdapter.deleteCity(adapterPosition)
            PositionLiveDate.setPosition(ValueUserAction(UserAction.DELETE,adapterPosition) )
        }
    }

}