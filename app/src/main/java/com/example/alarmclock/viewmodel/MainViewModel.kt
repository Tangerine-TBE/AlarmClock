package com.example.alarmclock.viewmodel

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alibaba.fastjson.JSON
import com.example.alarmclock.R
import com.example.alarmclock.bean.ItemBean
import com.example.alarmclock.bean.ValueLocation
import com.example.module_weather.domain.ZipWeatherBean
import com.example.module_weather.repository.NetRepository
import com.example.alarmclock.util.GeneralState
import com.example.module_base.base.BaseViewModel
import com.example.module_base.util.Constants
import com.example.module_base.util.DateUtil
import com.example.module_base.util.GaoDeHelper
import com.example.module_weather.db.DbHelper
import com.example.module_weather.domain.MjLifeBean
import com.example.module_weather.domain.WeatherCacheInfo
import com.example.module_weather.utils.formatCity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.viewmodel
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2021/2/5 10:10:03
 * @class describe
 */
class MainViewModel:BaseViewModel() {
    private val list:MutableList<ItemBean> = ArrayList()
    val weatherMsg by lazy {
        MutableLiveData<MutableList<ItemBean>>()
    }

    val locationMsg by lazy {
        MutableLiveData<ValueLocation>()
    }


    private val mGaoDeHelper by lazy {
        GaoDeHelper.getInstance().apply {
            setListener {
                if (it.longitude != 0.0 || it.latitude != 0.0) {
                    sp.apply {
                        putString(Constants.LOCATION_CITY, it.city)
                        putString(Constants.LOCATION_LONGITUDE, it.longitude.toString())
                        putString(Constants.LOCATION_LATITUDE, it.latitude.toString())
                        putString(Constants.LOCATION, it.address)
                    }
                    locationMsg.value = ValueLocation(GeneralState.SUCCESS, it.city)
                    getWeatherInfo(it.city,it.longitude.toString(), it.latitude.toString())
                } else {
                    locationMsg.value = ValueLocation(GeneralState.ERROR, "")
                }
            }
        }
    }



    /**
     * 获取天气
     * @param log String 经度
     * @param lat String 纬度
     */
    private  fun getWeatherInfo(city:String,log:String,lat:String){
        NetRepository.weatherData(
            log,
            lat
        ) { mjRealWeatherBean, mjAqiBean, mj15DayWeatherBean, mj24WeatherBean, mj5AqiBean, responseBody ->
            val string: String = responseBody.string()
            val jsonObject = JSONObject(string)
            val data = jsonObject.optJSONObject("data")
            val liveIndex = data.getJSONObject("liveIndex")
            val jsonArray = liveIndex.getJSONArray(DateUtil.getDate())
            val lifeBeans = JSON.parseArray(jsonArray.toString(), MjLifeBean::class.java)
            ZipWeatherBean(
                mjRealWeatherBean,
                mjAqiBean,
                mj15DayWeatherBean,
                mj24WeatherBean,
                lifeBeans,
                mj5AqiBean
            )
        }.subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer {
                viewModelScope.launch(Dispatchers.IO){
                    DbHelper.addCityMsg(WeatherCacheInfo(formatCity(city),log,lat,Gson().toJson(it)))
                }

                it?.realWeatherBean?.data?.condition?.let {
                    list.apply {
                        clear()
                        add(ItemBean(icon = R.mipmap.icon_temp, title = it.temp + "°C"))
                        add(ItemBean(icon = R.mipmap.icon_windy, title = it.windDir))
                        add(ItemBean(icon = R.mipmap.icon_weather, title = it.condition))
                        add(ItemBean(icon = R.mipmap.icon_pree, title = it.pressure + "PHA"))
                        //  add(ItemBean(icon = R.mipmap.icon_noise, title = "白噪音"))
                    }
                    weatherMsg.postValue(list)
                    sp.putString(com.example.alarmclock.util.Constants.SP_WEATHER_LIST, Gson().toJson(list))
                }
            }, Consumer {

            })
    }

    /**
     *  缓存的天气情况
     */
    fun getWeatherCache(){
        val weatherData = sp.getString(com.example.alarmclock.util.Constants.SP_WEATHER_LIST)
        if (!TextUtils.isEmpty(weatherData)) {
            val list: MutableList<ItemBean> = Gson().fromJson(weatherData, object : TypeToken<List<ItemBean>>() {}.type)
            list?.let {
                weatherMsg.value=list
            }
        }
    }

    /**
     * 开始定位
     */
    fun startLocation(){
        mGaoDeHelper.startLocation()
    }

}