package com.feisu.noise.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.feisu.noise.R
import com.feisukj.base_library.baseclass.BaseViewHolder

class TimingAdapter: RecyclerView.Adapter<BaseViewHolder>(){
    private val timerNum= listOf(5,10,20,30,40,50,60,70,80,90,100,110,120)
    var chooseTimeCallback: ChooseTimeCallback?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val itemView=
            LayoutInflater.from(parent.context).inflate(R.layout.item_timing,parent,false)
        return BaseViewHolder(itemView)
    }

    override fun getItemCount()=timerNum.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val minutes=timerNum[position]
        holder.setText(R.id.timeNum,minutes.toString())
        holder.itemView.setOnClickListener {
            chooseTimeCallback?.onChoose(minutes*60*1000L)
        }
    }

    interface ChooseTimeCallback{
        fun onChoose(intervalTime:Long)
    }
}