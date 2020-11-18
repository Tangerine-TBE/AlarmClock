package com.example.module_base.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.module_base.R
import com.example.module_base.util.SPUtil
import com.example.module_base.widget.LoadingDialog
import com.tamsiree.rxkit.RxTimeTool
import java.text.SimpleDateFormat

/**
 * @name td_horoscope
 * @class name：com.example.module_base.base
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/10/27 19:34
 * @class describe
 */
open abstract class BaseFragment : Fragment() {
    protected var mCurTimeString: String? = null
    protected lateinit var mSPUtil: SPUtil
    protected lateinit var mActivity:FragmentActivity
    protected lateinit var mLoadingDialog //正在加载
    : LoadingDialog
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return getRootView(inflater, container)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragment = view.findViewById<ViewGroup>(R.id.fragment_base)
        setUpView(fragment)
        mActivity=activity!!
        mLoadingDialog = LoadingDialog(mActivity)
        mLoadingDialog.setCancelable(true)
        mCurTimeString = RxTimeTool.getCurTimeString(SimpleDateFormat("yyyy-MM-dd"))
        mSPUtil= SPUtil.getInstance();
        initView()
        initPresent()
        initLoadData()
        initEvent()
    }


    private var successView: View? = null
    private var errorView: View? = null

    enum class PageState {
        NONE, LOADING, ERROR, EMPTY, SUCCESS
    }


     fun setUpView(mFragment: ViewGroup?) {

        successView=setSuccessView(mFragment)
        mFragment?.addView(successView)

        errorView=setErrorView(mFragment)
        mFragment?.addView(errorView)
         errorView?.setOnClickListener {
             onReload()
         }

        switchUIByState(PageState.NONE)
    }

    //重新加载
    open fun onReload() {

    }

    //根据状态显示UI
    fun switchUIByState(state: PageState) {
        successView?.visibility = if (state == PageState.SUCCESS) View.VISIBLE else View.GONE
        errorView?.visibility = if (state == PageState.ERROR) View.VISIBLE else View.GONE
        if (state == PageState.NONE) {
            successView?.visibility = View.GONE
            errorView?.visibility = View.GONE
        }
    }



    private fun setErrorView(mFragment: ViewGroup?): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_error,mFragment,false)
    }


    private fun setSuccessView(mFragment: ViewGroup?): View? {
        return  LayoutInflater.from(context).inflate(getLayoutView(),mFragment,false)
    }




    open  fun getRootView(inflater: LayoutInflater, container: ViewGroup?):View{
       return inflater.inflate(R.layout.fragment_base, container, false)
    }









    open fun initView() {

    }
    open fun initEvent() {

    }

    open fun initLoadData() {

    }

    open fun initPresent() {

    }

    abstract fun getLayoutView(): Int

    fun showLoading() {
        if (!mLoadingDialog.isShowing) {
            mLoadingDialog.show()
        }
    }

    fun dismissLoading() {
        mLoadingDialog.dismiss()
    }

    open fun visible(vararg views: View) {
        for (view in views) {
            view.visibility = View.VISIBLE
        }
    }
    open fun invisible(vararg views: View) {
        for (view in views) {
            view.visibility = View.INVISIBLE
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        dismissLoading()
        release()

    }

    open fun release() {

    }


}