package com.example.module_calendar.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.module_base.base.BaseViewModel
import com.example.module_base.util.LogUtils
import com.example.module_weather.domain.HuangLiBean
import com.example.module_weather.domain.ValueHuangLiData
import com.example.module_weather.repository.NetRepository
import com.example.module_weather.utils.Constants
import com.google.gson.Gson
import com.tamsiree.rxkit.RxTimeTool
import java.text.SimpleDateFormat

/**
 * @name AlarmClock
 * @class name：com.example.module_calendar.viewmodel
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2021/2/20 17:29:30
 * @class describe
 */
class CalendarViewModel:BaseViewModel() {

    val huangLiInfo by lazy {
        MutableLiveData<HuangLiBean.ResultBean>()
    }

    fun getHuangLiMsg(year:String,month:String,day:String){
        doRequest({
            val huangLiData = NetRepository.huangLiData(year,month,day)

            huangLiData?.result?.let {
             //   LogUtils.i("-------getHuangLiMsg------------${huangLiData.result}--")
                huangLiInfo.postValue(it)
            }
        },{

        })
    }

}