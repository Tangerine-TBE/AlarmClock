package com.twx.clock.ui.adapter.recyclerview

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.twx.clock.R
import com.twx.clock.bean.IconTitleBean
import kotlinx.android.synthetic.main.item_permission_container.view.*

/**
 * @name WeatherOne
 * @class name：com.example.tianqi.ui.adapter
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/10/14 9:39
 * @class describe
 */
class PermissionAdapter: BaseQuickAdapter<IconTitleBean, BaseViewHolder>(R.layout.item_permission_container) {
    override fun convert(helper: BaseViewHolder, item: IconTitleBean) {
        helper.itemView.apply {
            item.let {
                mDeviceIcon.setImageResource(it.icon)
                mHint.text=it.hint
                mDes.text=it.title
            }
        }

    }


}