package com.feisukj.base_library.baseclass

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class BaseViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {

    fun <T:View> getView(viewId:Int):T{
        return itemView.findViewById(viewId)
    }

    fun setText(viewId: Int,resId: Int){
        getView<TextView>(viewId).setText(resId)
    }

    fun setText(viewId: Int,text: String){
        getView<TextView>(viewId).text=text
    }

    fun setImage(viewId: Int,resId: Int){
        Glide
            .with(itemView.context)
            .load(resId)
            .into(getView(viewId))
    }


    fun loadImage(viewId: Int,url:String?){
        Glide.with(itemView.context)
                .load(url)
                .into(getView(viewId))
    }
}