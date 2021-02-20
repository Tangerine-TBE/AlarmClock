package com.feisukj.base_library.utils

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import java.lang.reflect.Type
import java.util.*

/**
 * jianbao
 */
class GsonHelper private constructor(){
    companion object{
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { GsonHelper() }
    }

    private val gson by lazy { Gson() }

    /**
     * 解析jsonObject
     * @param jsonString
     * @param classes
     * @param <T>
     * @return
    </T> */
    fun <T> parseObject(jsonString: String, classes: Class<T>): T? {
        return try {
            gson.fromJson(jsonString, classes)
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 解析jsonArray
     * @param jsonString
     * @param classes
     * @param <T>
     * @return
    </T> */
    fun <T> parseArray(jsonString: String, classes: Class<Array<T>>): List<T>? {
        return try {
            listOf(*gson.fromJson(jsonString, classes))
        }catch (e:JsonSyntaxException){
            e.printStackTrace()
            null
        }
    }

    fun <T> parseArray(jsonString: String, type: Type): T? {
        return try {
            gson.fromJson(jsonString, type)
        }catch (e:JsonSyntaxException){
            e.printStackTrace()
            null
        }
    }
//    fun test(){
//        getList<List<Int>>("",object :TypeToken<List<Int>>(){}.type)
//    }

    /**
     * 把实体转换成json数据
     * @param o
     * @return
     */
    fun parseToJsonString(o: Any): String {
        return gson.toJson(o)
    }

    /**
     * 转成list
     * 解决泛型问题
     * @param json
     * @param cls
     * @param <T>
     * @return
    </T> */
    fun <T> jsonToList(json: String, cls: Class<T>): List<T> {
        val list = ArrayList<T>()
        val array: JsonArray
        try {
            array = JsonParser().parse(json).asJsonArray
        } catch (e: Exception) {
            e.printStackTrace()
            return list
        }
        for (elem in array) {
            list.add(gson.fromJson(elem, cls))
        }
        return list
    }

    /**
     * 转成json
     *
     * @param `any`
     * @return
     */
    fun GsonString(any: Any): String? {
        return gson.toJson(any)
    }
}