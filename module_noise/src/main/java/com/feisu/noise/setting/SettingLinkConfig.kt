package com.feisu.noise.setting

import android.content.Context
import android.content.Intent
import com.feisukj.base_library.utils.BaseConstant
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram
import com.tencent.mm.opensdk.openapi.WXAPIFactory

class SettingLinkConfig {

    enum class LinkType{
        miniprogram,BMW,APK,unkown
    }

    var data:List<SettingItem>?=null

    class SettingItem{
        var status=false
        var type="miniprogram"
        var userName="gh_d1978695dca9"
        var appId="wx143822f474eb9e71"
        var url=""
        var desc=""
        var name=""
        var imageUrl=""

        fun getLinkType(): LinkType {
            return when(type){
                "miniprogram"->{
                    LinkType.miniprogram
                }
                "BMW"->{
                    LinkType.BMW
                }
                ""->{
                    LinkType.APK
                }
                else->{
                    LinkType.unkown
                }
            }
        }

    }
}