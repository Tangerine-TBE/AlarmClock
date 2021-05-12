package com.twx.module_weather.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.twx.module_base.util.gsonHelper
import com.twx.module_weather.R
import com.twx.module_weather.domain.WeatherCacheInfo
import com.twx.module_weather.domain.ZipWeatherBean
import com.twx.module_weather.utils.WeatherUtils
import kotlinx.android.synthetic.main.item_city_container.view.*
import java.util.*

/**
 * @author: Administrator
 * @date: 2020/7/5 0005
 */
class CityListAdapter : RecyclerView.Adapter<CityListAdapter.MyHolder>() {
    private val mLocationBeans: MutableList<WeatherCacheInfo> = ArrayList()
    private var mOnItemClickListener: OnItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_city_container, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.setItemData(mLocationBeans[position], position)
    }

    override fun getItemCount(): Int {
        return mLocationBeans.size
    }

    fun setData(cityList: List<WeatherCacheInfo>) {
        mLocationBeans.clear()
        mLocationBeans.addAll(cityList)
        notifyDataSetChanged()
    }

    fun deleteCity(adapterPosition: Int) {
            mOnItemClickListener?.deleteOnClick(mLocationBeans[adapterPosition], adapterPosition)

    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        mOnItemClickListener = listener
    }

    interface OnItemClickListener {
        fun deleteOnClick(city: WeatherCacheInfo, position: Int)
        fun OnItemClick(city: WeatherCacheInfo, position: Int)
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setItemData(info: WeatherCacheInfo, position: Int) {
            itemView.apply {
                city_name.text=info.city
                gsonHelper<ZipWeatherBean>(info.weatherMsg)?.let { it ->
                    it.day15Msg?.data?.forecast?.get(0)?.let {
                       city_tem.text="${it.tempNight}°/${it.tempDay}°"
                   }

                    it.day24Msg?.data?.hourly?.get(0)?.let {
                        city_wea.text=it.condition+"    "+ WeatherUtils.formatWindyDir(it.windDir)+"/"+WeatherUtils.winType(it.windSpeed.toDouble(), true)
                        tv_item_currentTeam.text="${it.temp}°"
                    }

                }
            }
            itemView.setOnClickListener {
                mOnItemClickListener?.OnItemClick(info, position)
            }
        }

    }
}