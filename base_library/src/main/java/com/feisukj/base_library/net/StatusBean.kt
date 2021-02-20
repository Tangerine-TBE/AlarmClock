package com.feisukj.base_library.net

import com.feisukj.base_library.BuildConfig


/**
 * Created by Gpp on 2018/1/23.
 */

class StatusBean {
    var status: Boolean = false//是否开启
    get() {
        if (BuildConfig.DEBUG){
            return field
        }else{
            return field
        }
    }
    var ad_origin: String = ""//广告源
    var times: Long = 0
    var ad_percent: String = ""//广告比例
    var change_times: Long = 0//切换时间
}
