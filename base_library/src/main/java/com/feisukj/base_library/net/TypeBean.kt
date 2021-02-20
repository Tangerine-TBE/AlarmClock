package com.feisukj.base_library.net


/**
 * Created by Gpp on 2018/1/23.
 */

class TypeBean {
    var insert_screen: StatusBean? = null
    var banner_screen: StatusBean? = null
    var spread_screen: StatusBean? = null
    var native_screen: StatusBean?=null
        get() {
            return if (field==null&&native_advertising!=null){
                native_advertising
            }else{
                field
            }
        }
    var native_advertising:StatusBean?=null
    var incentive_video:StatusBean?=null
}
