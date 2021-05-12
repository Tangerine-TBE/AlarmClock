package com.twx.clock.ui.fragment

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.twx.clock.R
import com.twx.clock.databinding.FragmentPermissionsBinding
import com.twx.clock.repository.DataProvider
import com.twx.clock.ui.activity.MainViewActivity
import com.twx.clock.ui.adapter.recyclerview.PermissionAdapter
import com.twx.clock.util.CheckPermissionUtil
import com.twx.module_ad.advertisement.SplashHelper
import com.twx.module_ad.bean.AdBean
import com.twx.module_ad.request.AdPresent
import com.twx.module_ad.request.IAdCallback
import com.twx.module_ad.utils.Contents
import com.twx.module_base.base.BaseViewFragment
import com.twx.module_base.ui.activity.DealViewActivity
import com.twx.module_base.util.PackageUtil
import com.twx.module_base.util.top.toOtherActivity
import com.twx.module_tool.utils.ColorUtil
import com.google.gson.Gson
import com.tamsiree.rxkit.RxNetTool
import com.tamsiree.rxkit.view.RxToast.normal
import kotlinx.android.synthetic.main.fragment_permissions.*

/**
 * @name td_horoscope
 * @class name：com.example.td_horoscope.ui.fragment
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/10 11:48
 * @class describe
 */
class PermissionFragment: BaseViewFragment<FragmentPermissionsBinding>(), IAdCallback {

    private lateinit var mPermissionAdapter: PermissionAdapter
    private val madPresent by lazy {
        AdPresent.getInstance()
    }
    private lateinit var mSplashHelper: SplashHelper

    override fun getChildLayout(): Int =R.layout.fragment_permissions

    override fun initView() {
        mSplashHelper= SplashHelper(activity, permission_container, MainViewActivity::class.java)

        rv_permission.layoutManager=LinearLayoutManager(activity)
        mPermissionAdapter = PermissionAdapter()
        mPermissionAdapter.setList(DataProvider.permissionList)
        rv_permission.adapter=mPermissionAdapter


        permissions_title.text=PackageUtil.difPlatformName(activity, R.string.welcome)
        val str = resources.getString(R.string.user_agreement)
        val stringBuilder = SpannableStringBuilder(str)
        val span1 = TextViewSpan1()
        stringBuilder.setSpan(span1, 10, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val span2 = TextViewSpan2()
        stringBuilder.setSpan(span2, 19, 25, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        user_agreement.text=stringBuilder
        user_agreement.movementMethod=LinkMovementMethod.getInstance()


        madPresent.registerCallback(this)
        if (RxNetTool.isNetworkAvailable(requireContext())) {
            madPresent.getAdMsg()
        }
    }




    override fun initEvent() {
        go_main.setOnClickListener {
            if (mCheck.isChecked) {
               CheckPermissionUtil. checkRuntimePermission(activity, DataProvider.permissions,false,{}){
                    goHome()
                }
            } else {
                normal("请确保您已同意本应用的隐私政策和用户协议")
            }
        }

        bt_try.setOnClickListener {
            goHome()
        }

    }


    private fun goHome() {
        if (RxNetTool.isNetworkAvailable(requireContext())) {
            mSplashHelper.showAd()
        } else {
            toOtherActivity<MainViewActivity>(activity, true) {}
        }
        sp.putBoolean(com.twx.module_base.util.Constants.IS_FIRST, false)
    }


    inner class TextViewSpan1 : ClickableSpan() {
        override fun updateDrawState(ds: TextPaint) {
            ds.color = ColorUtil.THEME_COLOR
        }

        override fun onClick(widget: View) {
            //点击事件
            toOtherActivity<DealViewActivity>(activity,false){
                putExtra(com.twx.module_base.util.Constants.SET_Deal1,2)
            }
        }
    }

     inner  class TextViewSpan2 : ClickableSpan() {
        override fun updateDrawState(ds: TextPaint) {
            ds.color = ColorUtil.THEME_COLOR
        }

        override fun onClick(widget: View) {
            //点击事件
            toOtherActivity<DealViewActivity>(activity,false){
                putExtra(com.twx.module_base.util.Constants.SET_Deal1,3)
            }
        }
    }

    override fun onLoadAdSuccess(adBean: AdBean?) {
        adBean?.let {
            sp.putString(Contents.AD_INFO,Gson().toJson(it.data))
        }

    }

    override fun onLoadError() {
    }


    override fun release() {
        madPresent.unregisterCallback(this)
    }


}