package com.feisu.noise.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feisu.noise.R
import com.feisu.noise.audio.allSource
import com.feisu.noise.bean.MusicFileBean
import com.feisu.noise.adcontrol.isLockForMusic
import com.feisu.noise.adcontrol.isLockMusicSwitch
import com.feisu.noise.ui.fragment.AllFragment.Companion.FREE_MUSIC_COUNT
import com.feisu.noise.view.NiceImageView
import com.feisukj.base_library.baseclass.BaseViewHolder

class AllSelectorAdapter constructor(): RecyclerView.Adapter<BaseViewHolder>(){
    companion object{
        private const val AD_ITEM_TYPE=-1
        private const val ITEM_TYPE=1
    }

    private var adView:View?=null
    constructor(adView:View?):this(){
        this.adView=adView
    }
    private val chooserNoise=ArrayList<Pair<MusicFileBean, NiceImageView>>()
    var selectorNoiseCallback:SelectorNoiseCallback?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if (viewType==ITEM_TYPE) {
            val itemView =
                LayoutInflater.from(parent.context).inflate(R.layout.item_all, parent, false)
            return BaseViewHolder(itemView)
        }else{
            return BaseViewHolder(adView?: View(parent.context))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position>=allSource.size){
            AD_ITEM_TYPE
        }else{
            ITEM_TYPE
        }
    }

    override fun getItemCount(): Int {
        return allSource.size+if (adView==null) 0 else 1
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (position>= allSource.size){
            return
        }
        val noise= allSource[position]
        val isLock= if (position>=FREE_MUSIC_COUNT){
            noise.name.let {
                if(it!=null){
                    isLockMusicSwitch&&isLockForMusic(it)
                }else{
                    false
                }
            }
        }else{
            false
        }
        if (isLock){
            holder.getView<NiceImageView>(R.id.lock).visibility=View.VISIBLE
        }else{
            holder.getView<NiceImageView>(R.id.lock).visibility=View.GONE
        }
        val icon=holder.getView<NiceImageView>(R.id.noiseIcon)
        val noiseName=holder.getView<TextView>(R.id.noiseName)
        icon.setImageResource(noise.picIcon)
        noiseName.text = noise.name?:""
        if (chooserNoise.map { it.first }.contains(noise)){
            icon.setInnerBorderColor(Color.parseColor("#1CEFFF"))
        }else{
            icon.setInnerBorderColor(Color.TRANSPARENT)
        }
        holder.itemView.setOnClickListener {
            if (isLock){
                selectorNoiseCallback?.onSelector(noise,position,isLock,false)
                return@setOnClickListener
            }
            val chooseNoises=chooserNoise.map { it.first }
            if (!chooseNoises.contains(noise)) {
                icon.setInnerBorderColor(Color.parseColor("#1CEFFF"))
                chooserNoise.add(noise to icon)
                if (chooserNoise.size>3){
                    chooserNoise.removeAt(0).let {
                        it.second.setInnerBorderColor(Color.TRANSPARENT)
                    }
                }
                //将选择的噪音回调
                selectorNoiseCallback?.onSelector(noise,position,isLock,true)
            }else{
                icon.setInnerBorderColor(Color.TRANSPARENT)
                chooserNoise.remove(noise to icon)
                //将选择的噪音回调
                selectorNoiseCallback?.onSelector(noise,position,isLock,false)
            }
        }
    }

    fun cleanChooser(){
        while (chooserNoise.isNotEmpty()){
            chooserNoise.removeAt(0).let {
                it.second.setInnerBorderColor(Color.TRANSPARENT)
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val lm=recyclerView.layoutManager
        if (lm is GridLayoutManager){
            lm.spanSizeLookup=object :GridLayoutManager.SpanSizeLookup(){
                override fun getSpanSize(position: Int): Int {
                    return if (getItemViewType(position)== ITEM_TYPE){
                        1
                    }else{
                        lm.spanCount
                    }
                }
            }
        }
    }

    interface SelectorNoiseCallback{
        fun onSelector(musicFileBean: MusicFileBean,position: Int,isLock:Boolean,isSelected:Boolean)
    }

}