package com.twx.module_weather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alibaba.fastjson.JSON
import com.twx.module_base.base.BaseViewModel
import com.twx.module_base.util.DateUtil
import com.twx.module_base.util.LogUtils
import com.twx.module_base.util.calLastedTime
import com.twx.module_base.util.gsonHelper
import com.twx.module_weather.db.DbHelper
import com.twx.module_weather.domain.*
import com.twx.module_weather.repository.NetRepository
import com.twx.module_weather.utils.Constants
import com.twx.module_weather.utils.RequestState
import com.google.gson.Gson
import com.tamsiree.rxkit.RxTimeTool
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

/**
 * @name AlarmClock
 * @class name：com.example.module_weather.viewmodel
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2021/2/18 10:51:40
 * @class describe
 */
class CurrentCityViewModel:BaseViewModel() {


    val weatherInfo by lazy {
        MutableLiveData<ValueWeatherData>()
    }
    val huangLiInfo by lazy {
        MutableLiveData<HuangLiBean.ResultBean>()
    }



    fun getWeatherMsg(city:String,long: String, lat: String,state:RequestState){
        if (state == RequestState.NORMAL) {
            val cacheTime = sp.getLong(Constants.SP_CACHE_TIME, 0L)
            if (cacheTime != 0L) {
                val calLastedTime = calLastedTime(Date(), Date(cacheTime))
                LogUtils.i("----getWeatherMsg-----------$calLastedTime----------------")
                if (calLastedTime > 5) {
                    doRequest(long, lat, city,state)
                } else {
                    getWeatherMsgCache(city)
                }
            } else {
                doRequest(long, lat, city,state)
            }
        } else {
            doRequest(long, lat, city,state)
        }
    }

    private fun doRequest(long: String, lat: String, city: String,state:RequestState) {
        NetRepository.weatherData(
            long,
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
                weatherInfo.postValue(ValueWeatherData(state,it))
                updateWeatherCache(WeatherCacheInfo(city, long, lat, Gson().toJson(it)))
                sp.putLong(Constants.SP_CACHE_TIME, System.currentTimeMillis())
            }, Consumer {

            })
    }


    fun getWeatherMsgCache(city: String){
        viewModelScope.launch (Dispatchers.IO){
            val msg = DbHelper.findCityWeatherMsg(city)
            if (msg.size==1){
                gsonHelper<ZipWeatherBean>( msg[0].weatherMsg)?.let {
                    weatherInfo.postValue(ValueWeatherData(RequestState.NORMAL,it) )
                }
            }
        }
    }



   private  fun updateWeatherCache(weatherCacheInfo: WeatherCacheInfo){
        viewModelScope.launch(Dispatchers.IO) {
            DbHelper.addCityMsg(weatherCacheInfo)
        }
    }


    fun getHuangLiMsg(){
        val cacheHuangLiStr = sp.getString(Constants.SP_HUANG_LI_DATA)
        val cacheHuangLi = gsonHelper<ValueHuangLiData>(cacheHuangLiStr)
        if (cacheHuangLi != null) {
            if (cacheHuangLi.time == RxTimeTool.getCurTimeString(SimpleDateFormat("yyyy-MM-dd "))) {
                huangLiInfo.value=(cacheHuangLi.huangLiBean)
            } else {
                getHuangLiData()
            }
        } else {
            getHuangLiData()
        }
    }

    private fun getHuangLiData() {
        doRequest({
            val calendar = Calendar.getInstance()
            val year = calendar[Calendar.YEAR]
            val month = calendar[Calendar.MONTH] + 1
            val day = calendar[Calendar.DAY_OF_MONTH]
            val huangLiData = NetRepository.huangLiData(year.toString(),month.toString(),day.toString())
            huangLiData?.result?.let {
                huangLiInfo.postValue(it)
                sp.putString(
                    Constants.SP_HUANG_LI_DATA,
                    Gson().toJson(
                        ValueHuangLiData(
                            RxTimeTool.getCurTimeString(SimpleDateFormat("yyyy-MM-dd ")),
                            it
                        )
                    )
                )
            }
        }, {

        })
    }


}