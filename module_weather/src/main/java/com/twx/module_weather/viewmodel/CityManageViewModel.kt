package com.twx.module_weather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alibaba.fastjson.JSON
import com.twx.module_base.base.BaseViewModel
import com.twx.module_base.util.DateUtil
import com.twx.module_weather.db.DbHelper
import com.twx.module_weather.domain.*
import com.twx.module_weather.livedata.CityListLiveData
import com.twx.module_weather.livedata.PositionLiveDate
import com.twx.module_weather.repository.NetRepository
import com.twx.module_weather.utils.UserAction
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

/**
 * @name AlarmClock
 * @class name：com.example.module_weather.viewmodel
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2021/2/18 15:17:27
 * @class describe
 */
class CityManageViewModel:BaseViewModel() {

    val cityList by lazy {
        MutableLiveData<MutableList<WeatherCacheInfo>>()
    }

    val cityLocation by lazy {
        MutableLiveData<AddressBean.ResultBean.LocationBean>()
    }

    val weatherInfo by lazy {
        MutableLiveData<ZipWeatherBean>()
    }


    fun getWeatherMsg(city:String,long: String, lat: String){
        NetRepository.weatherData(long, lat) { mjRealWeatherBean, mjAqiBean, mj15DayWeatherBean, mj24WeatherBean, mj5AqiBean, responseBody ->
            val string: String = responseBody.string()
            val jsonObject = JSONObject(string)
            val data = jsonObject.optJSONObject("data")
            val liveIndex = data.getJSONObject("liveIndex")
            val jsonArray = liveIndex.getJSONArray(DateUtil.getDate())
            val lifeBeans = JSON.parseArray(jsonArray.toString(), MjLifeBean::class.java)
            ZipWeatherBean(mjRealWeatherBean, mjAqiBean, mj15DayWeatherBean, mj24WeatherBean, lifeBeans, mj5AqiBean)
        }.subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer {
                weatherInfo.postValue(it)
                updateWeatherCache(WeatherCacheInfo(city,long,lat, Gson().toJson(it)))

            }, Consumer {

            })

    }


    private  fun updateWeatherCache(weatherCacheInfo: WeatherCacheInfo){
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.IO){
                DbHelper.addCityMsg(weatherCacheInfo)
            }
            CityListLiveData.queryCityNumber()

            PositionLiveDate.setPosition(ValueUserAction(UserAction.ADD,0))
            //queryCityList()
        }
    }


    fun deleteCity(city: String?){
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.IO) {
                DbHelper.deleteCityMsg(city)
            }
            CityListLiveData.queryCityNumber()
          //  queryCityList()
        }
    }


    fun queryCityList(){
        viewModelScope.launch(Dispatchers.IO) {
            val list = DbHelper.findCityMsg()
            if (list.isNotEmpty()) {
                cityList.postValue(list)
            }
        }
    }


    fun queryCityLocation(city:String){
        doRequest({
            val addressData = NetRepository.addressData(city)
            addressData?.result?.location?.let {
                cityLocation.postValue(it)
            }
        }, {

        })
    }

}