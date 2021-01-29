package com.example.alarmclock.ui.activity

import android.content.*
import android.os.IBinder
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.sdk.android.feedback.impl.FeedbackAPI
import com.example.alarmclock.interfaces.ItemCheckedChangeListener
import com.example.alarmclock.R
import com.example.alarmclock.bean.ItemBean
import com.example.alarmclock.model.DataProvider
import com.example.alarmclock.service.VideoLiveWallpaper
import com.example.alarmclock.topfun.showDialog
import com.example.alarmclock.ui.adapter.recyclerview.SettingAdapter
import com.example.alarmclock.ui.adapter.recyclerview.SettingBottomAdapter
import com.example.alarmclock.util.Constants
import com.example.module_ad.advertisement.AdType
import com.example.module_ad.advertisement.InsertHelper
import com.example.module_ad.service.TimeService
import com.example.module_base.ui.activity.DealActivity
import com.example.module_base.util.LogUtils
import com.example.module_base.util.MarginStatusBarUtil
import com.example.module_base.util.PermissionUtil
import com.example.module_base.util.top.toOtherActivity
import com.example.module_base.widget.MyToolbar
import com.example.module_usercenter.bean.*
import com.example.module_usercenter.present.impl.LoginPresentImpl
import com.example.module_usercenter.present.impl.LogoutPresentImpl
import com.example.module_usercenter.present.impl.ThirdlyLoginPresentImpl
import com.example.module_usercenter.present.impl.WeChatPresentImpl
import com.example.module_usercenter.ui.activity.BuyVipActivity
import com.example.module_usercenter.ui.activity.LoginActivity
import com.example.module_usercenter.utils.Contents
import com.example.module_usercenter.utils.SpUtil
import com.example.module_usercenter.view.ILoginCallback
import com.example.module_usercenter.view.ILogoutCallback
import com.example.module_usercenter.view.IThirdlyLoginCallback
import com.example.module_usercenter.view.IWeChatCallback
import com.example.td_horoscope.base.MainBaseActivity
import com.tamsiree.rxkit.view.RxToast
import com.tamsiree.rxui.view.dialog.RxDialogSureCancel
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.coroutines.launch

class SettingActivity : MainBaseActivity(), ILoginCallback, IWeChatCallback, IThirdlyLoginCallback, ILogoutCallback {

    private lateinit var mOtherSetTopAdapter:SettingBottomAdapter
    private lateinit var mSetAdapter: SettingAdapter
    private val mLogoutPresent by lazy {
        LogoutPresentImpl.getInstance()
    }
    private val mLoginPresent by lazy {
        LoginPresentImpl.getInstance()
    }

    private val mThirdlyLoginPresent by lazy {
        ThirdlyLoginPresentImpl.getInstance()
    }
    private val mWeChatPresent by lazy {
        WeChatPresentImpl.getInstance()
    }
    private val mInsertHelper by lazy {
        InsertHelper(this)
    }

    private val mScreenDialog by lazy {
        RxDialogSureCancel(this).apply {
            setSure("再想想")
            setCancel("去打开")
            setContent("该功能需要开启锁屏显示权限后才能正常使用")
            setCancelable(false)
        }
    }
    private val mRxDialogSureCancel by lazy {
        RxDialogSureCancel(this).apply {
            setContent("您确定要退出登录吗？")
        }
    }
    private val mLogoutDialogSureCancel by lazy {
        RxDialogSureCancel(this).apply {
            setContent("您确定要注销账号吗？")
        }
    }

    private val serviceConnection by lazy {
        object: ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
            }
            override fun onServiceConnected(name: ComponentName?, service: IBinder) {
                val myBinder = service as? TimeService.MyBinder
                myBinder?.let { it ->
                    it.getService.showSet.observe(this@SettingActivity, Observer {
                       isShow=it
                    })
                    if (isShow) {
                        mInsertHelper.showAd(AdType.SETTING_PAGE)
                    }
                }
            }
        }
    }


    override fun getLayoutView(): Int=R.layout.activity_setting
    override fun initView() {
        //设置顶部距离
        MarginStatusBarUtil.setStatusBar(this, mSetBar, 1)
        //top设置
        mSetContainer.layoutManager= LinearLayoutManager(this)
        mSetAdapter=SettingAdapter()
        DataProvider.setData[0].isOpen=mSPUtil.getBoolean(Constants.SET_SHOW_HOUR24,true)
        DataProvider.setData[1].isOpen=mSPUtil.getBoolean(Constants.SET_SHOW_LANDSCAPE)
        DataProvider.setData[2].isOpen=mSPUtil.getBoolean(Constants.SET_SHOW_SECOND,true)
        DataProvider.setData[3].isOpen=mSPUtil.getBoolean(Constants.SET_SHOW_TIME)

        mSetAdapter.setList(DataProvider.setData)
        mSetContainer.adapter=mSetAdapter

        //bottom设置
        mOtherSetContainer.layoutManager=LinearLayoutManager(this)
        mOtherSetTopAdapter= SettingBottomAdapter()
        mOtherSetTopAdapter.setList(DataProvider.setOtherData)
        mOtherSetContainer.adapter=mOtherSetTopAdapter
        mJobScope.launch {
            VideoLiveWallpaper.saveWallpaper(this@SettingActivity)
            LogUtils.i("--VideoLiveWallpaper----------${Thread.currentThread().name}--------------")
        }

        bindService(Intent(this,TimeService::class.java),serviceConnection,Context.BIND_AUTO_CREATE)

    }

    private var mIsLogin=false
    override fun onResume() {
        super.onResume()
        refreshLoginState()
    }


    override fun onPause() {
        super.onPause()
        mInsertHelper.releaseAd()
    }

    private fun refreshLoginState() {
        mIsLogin = mSPUtil.getBoolean(Contents.USER_IS_LOGIN, false)
        var userId = mSPUtil.getString(Contents.USER_ID, "");
        var userVip = mSPUtil.getInt(Contents.USER_VIP_LEVEL, 0);
        var userVipTime = mSPUtil.getString(Contents.USER_VIP_TIME, "");
        if (mIsLogin) {
            mUserId.text = userId
            mVipTime.text = if (userVip == 0) "您还不是VIP" else "VIP等级:$userVip  有效期:$userVipTime"
            mUserIcon.setImageResource(R.mipmap.icon_login)
        } else {
            logoutState()
        }
    }

    private fun logoutState() {
        mUserId?.text="立即登录"
        mVipTime?.text="登录数据不丢失，享受更多功能"
        mUserIcon?.setImageResource(R.mipmap.icon_no_login)
    }

    private fun loginState(loginBean: LoginBean?) {
        loginBean?.data?.let {
            mUserId?.text=it.id.toString()
            mVipTime?.text=  if (it.vip==0) "您还不是VIP" else "VIP等级:"+it.vip+"  有效期:"+it.vipexpiretime
            mUserIcon?.setImageResource(R.mipmap.icon_login)
        }
    }


    override fun initPresent() {
        mLoginPresent.registerCallback(this)
        mWeChatPresent.registerCallback(this)
        mThirdlyLoginPresent.registerCallback(this)
        mLogoutPresent.registerCallback(this)
    }


    override fun initEvent() {
        //登录
        mLoginInclude.setOnClickListener {
            if (!mSPUtil.getBoolean(Contents.USER_IS_LOGIN, false)) {
                toOtherActivity<LoginActivity>(this, false) {}
                mSPUtil.putBoolean(Contents.BUY_PAGER, false)
            } else {
                    mRxDialogSureCancel.showDialog(this)
            }
        }

        mRxDialogSureCancel.setSureListener(View.OnClickListener {
            SpUtil.deleteUserInfo()
            refreshLoginState()
            mRxDialogSureCancel.dismiss()
        })

        mRxDialogSureCancel.setCancelListener(View.OnClickListener {
            mRxDialogSureCancel.dismiss()
        })



        //购买vip
        mVipLogo.setOnClickListener {
            toOtherActivity<BuyVipActivity>(this,false){putExtra(Contents.TO_BUY,false)}
        }


        mSetBar.setOnBackClickListener(object : MyToolbar.OnBackClickListener{
            override fun onBack() {
                finish()
            }
            override fun onRightTo() {

            }
        })
        mSetAdapter.setOnItemCheckedChangeListener(object : ItemCheckedChangeListener<ItemBean> {
            override fun onItemChecked(itemBean: ItemBean,isCheck: Boolean,position:Int) {
                when(position){
                    0-> mSPUtil.putBoolean(Constants.SET_SHOW_HOUR24,isCheck)
                    1-> mSPUtil.putBoolean(Constants.SET_SHOW_LANDSCAPE,isCheck)
                    2-> mSPUtil.putBoolean(Constants.SET_SHOW_SECOND,isCheck)
                    3-> {
                        if (isCheck) {
                            if (!mSPUtil.getBoolean(Constants.TOAST_SCREEN_TIME )) {
                                    mScreenDialog.showDialog(this@SettingActivity)
                            }
                        }
                        mSPUtil.putBoolean(Constants.SET_SHOW_TIME,isCheck)
                    }
                }
            }

            override fun  onItemClick(itemBean: ItemBean, position: Int) {
            }
        })

        mScreenDialog.setSureListener(View.OnClickListener {
            mScreenDialog.dismiss()
            DataProvider.setData[3].isOpen=false
            mSetAdapter.setList(DataProvider.setData)
            mSPUtil.putBoolean(Constants.SET_SHOW_TIME,false)

        })
        mScreenDialog.setCancelListener(View.OnClickListener {
            mScreenDialog.dismiss()
            PermissionUtil.gotoPermission(this)
            mSPUtil.putBoolean(Constants.TOAST_SCREEN_TIME,true)

        })

        mOtherSetTopAdapter.setOnItemClickListener { adapter, view, position ->
            when (position) {
                0 -> FeedbackAPI.openFeedbackActivity()
                1 -> PermissionUtil.gotoPermission(this)
                in 2..3 -> {
                    toOtherActivity<DealActivity>(
                        this,
                        false
                    ) { putExtra(com.example.module_base.util.Constants.SET_Deal1, position) }
                }

                4->{
                    val clipboardManager =
                        getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val data = ClipData.newPlainText("contact", "2681706890@qq.com")
                    clipboardManager.setPrimaryClip(data)
                    RxToast.normal("邮箱复制成功")

                }
                5 -> {
                    if (!mSPUtil.getBoolean(Contents.USER_IS_LOGIN, false)) {
                        RxToast.warning("您还没有登录")
                    } else {
                            mLogoutDialogSureCancel.showDialog(this)
                    }
                }
                6 -> {
                    try {
                        VideoLiveWallpaper.getVideoWallpaperPreview(this@SettingActivity)?.let {
                            startActivity(it)
                        }
                    }catch (e:Exception){
                        RxToast.warning("该手机不支持此功能")
                    }
                }

            }
        }

        mLogoutDialogSureCancel.setSureListener(View.OnClickListener {
            mLogoutPresent.toLogout(mSPUtil.getString(Contents.USER_ID))
        })
        mLogoutDialogSureCancel.setCancelListener(View.OnClickListener {
            mLogoutDialogSureCancel.dismiss()
        })


        }

    override fun release() {
        super.release()

        mScreenDialog.dismiss()
        mRxDialogSureCancel.dismiss()
        mLogoutDialogSureCancel.dismiss()
        mInsertHelper.releaseAd()
        mLoginPresent.unregisterCallback(this)
        mWeChatPresent.unregisterCallback(this)
        mThirdlyLoginPresent.unregisterCallback(this)
        mLogoutPresent.unregisterCallback(this)
        unbindService(serviceConnection)
    }

    override fun onLoginSuccess(loginBean: LoginBean?) {
        loginState(loginBean)
    }

    override fun onWxLoginError() {

    }

    override fun onThirdlyRegisterError() {

    }

    override fun onLoading() {

    }

    override fun onLogoutLoading() {
        mLogoutDialogSureCancel.dismiss()
        showLoading()
    }

    override fun onLogoutSuccess(registerBean: RegisterBean?) {
        dismissLoading()
        if (registerBean?.ret == 200) {
            SpUtil.deleteUserInfo()
            RxToast.success("注销成功！")
            logoutState()
        } else {
            RxToast.error("注销失败！")
        }
    }

    override fun onLogoutError() {
        dismissLoading()
        RxToast.error("注销失败！")
    }

    override fun onThirdlyLoginSuccess(loginBean: LoginBean?) {
        loginState(loginBean)
    }

    override fun onThirdlyLoginError() {

    }

    override fun onWxLoginSuccess(loginBean: LoginBean?) {
        loginState(loginBean)
    }

    override fun onWxRegisterSuccess(thirdlyRegisterBean: ThirdlyRegisterBean?) {

    }

    override fun onWxAccreditSuccess(weiXinBean: WeiXinBean?) {

    }

    override fun onCheckWxError() {
    }

    override fun onNumberSuccess(oauthBean: OauthBean?) {

    }

    override fun onError() {

    }

    override fun onCheckThirdlyRegisterSuccess(bean: RegisterBean?) {

    }

    override fun onCheckError() {

    }

    override fun onThirdlyRegisterSuccess(bean: ThirdlyRegisterBean?) {

    }

    override fun onWxAccreditError() {

    }

    override fun onCheckWxRegisterSuccess(bean: RegisterBean?) {

    }

    override fun onWxRegisterError() {

    }

    override fun onLoginError() {

    }

}