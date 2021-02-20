package com.feisu.noise.adcontrol

import com.feisukj.base_library.net.GsonUtils
import com.feisukj.base_library.net.TypeBean
import com.feisukj.base_library.utils.SPUtil
import java.lang.Exception

fun getPageControlData(page:String): TypeBean?{
    val typeBeanString= SPUtil.instance.getString(page)
    return try {
        GsonUtils.parseObject(typeBeanString,TypeBean::class.java)?:null
    }catch (e: Exception){
        null
    }
}