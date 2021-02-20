package com.feisukj.base_library.baseadapter

import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feisukj.base_library.baseclass.BaseViewHolder

abstract class BaseStickySectionAdapter<H,T>(private var data: List<StickySectionData<H, T>>) :RecyclerView.Adapter<BaseViewHolder>(){

    override fun getItemViewType(position: Int): Int {
        return getItemStatusByPosition(position).viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return if (viewType== ItemStatus.VIEW_TYPE_GROUP_ITEM){
            onCreateHeaderViewHolder(parent)
        }else{
            onCreateSubItemViewHolder(parent)
        }
    }

    abstract fun onCreateHeaderViewHolder(parent: ViewGroup):BaseViewHolder

    abstract fun onCreateSubItemViewHolder(parent: ViewGroup):BaseViewHolder

    override fun getItemCount(): Int {
        var count=0
        data.forEach {
            if (it.isOpen){
                count+= it.items.size
            }
            count++
        }
        return count
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val itemStatus=getItemStatusByPosition(position)
        val treeData=data[itemStatus.groupItemIndex]
        if (holder.itemViewType== ItemStatus.VIEW_TYPE_GROUP_ITEM){
            onBindHeaderViewHolder(holder,treeData.group)
            holder.itemView.setOnClickListener {
                treeData.isOpen=!treeData.isOpen
                notifyItemChanged(position)
                if (treeData.isOpen){
                    notifyItemRangeInserted(position+1,treeData.items.size)
                    notifyItemRangeChanged(position+treeData.items.size+1,itemCount-(position+treeData.items.size+1))
                }else{
                    notifyItemRangeRemoved(position+1,treeData.items.size)
                    notifyItemRangeChanged(position+1,itemCount-(position+1))
                }
            }
        }else{
            onBindSubItemViewHolder(holder,treeData.items[itemStatus.subItemIndex])
        }
    }

    abstract fun onBindHeaderViewHolder(holder: BaseViewHolder,groupItem:H?)

    abstract fun onBindSubItemViewHolder(holder: BaseViewHolder,subItem:T)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val lm=recyclerView.layoutManager
        if (lm is GridLayoutManager){
            lm.spanSizeLookup=object :GridLayoutManager.SpanSizeLookup(){
                override fun getSpanSize(position: Int): Int {
                    val itemStatus=getItemStatusByPosition(position)
                    return if (itemStatus.viewType== ItemStatus.VIEW_TYPE_GROUP_ITEM){
                        getGroupItemSpanSize(lm.spanCount)
                    }else{
                        getSubItemSpanSize(lm.spanCount)
                    }
                }

            }
        }
    }

    protected open fun getSubItemSpanSize(spanCont:Int):Int{
        return 1
    }

    protected open fun getGroupItemSpanSize(spanCont: Int):Int{
        return spanCont
    }

    private fun getItemStatusByPosition(position: Int): ItemStatus {
        var count=0
        val itemStatus= ItemStatus()
        for (index in data.indices){
            val treeData=data[index]
            count++
            if (position==(count-1)){
                itemStatus.viewType= ItemStatus.VIEW_TYPE_GROUP_ITEM
                itemStatus.groupItemIndex=index
                break
            }
            if (treeData.isOpen) {
                count += treeData.items.size
            }
            if (count>position){
                val start=count- treeData.items.size
                val subItemIndex=position-start
                itemStatus.viewType= ItemStatus.VIEW_TYPE_SUB_ITEM
                itemStatus.groupItemIndex=index
                itemStatus.subItemIndex=subItemIndex
                break
            }
        }
        return itemStatus
    }

    private class ItemStatus{
        companion object{
            const val VIEW_TYPE_GROUP_ITEM=1
            const val VIEW_TYPE_SUB_ITEM=2
        }
        var viewType= VIEW_TYPE_SUB_ITEM
        var groupItemIndex=0
        var subItemIndex=-1
    }
}