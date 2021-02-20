package com.feisukj.base_library.baseclass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.feisukj.base_library.R
import com.feisukj.base_library.widget.LoadingDialog
import kotlinx.android.synthetic.main.base_fragment.*

abstract class BaseFragment :Fragment(){
    protected var loadingDialog: LoadingDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (isActionBar()){
            val view=inflater.inflate(R.layout.base_fragment,container,false)
            view.findViewById<FrameLayout>(R.id.frameLayout).also {
                inflater.inflate(getLayoutId(),it,true)
            }
            view
        }else{
            inflater.inflate(getLayoutId(),container,false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
    }

    protected fun showLoadingDialog():LoadingDialog?{
        if (!isAdded){
            return null
        }
        loadingDialog = LoadingDialog(context!!)
        loadingDialog?.show()
        return loadingDialog
    }

    private fun dismissLoadingDialog(){
        if (!isAdded){
            return
        }
        if (loadingDialog?.isShowing==true)
            loadingDialog?.dismiss()
        loadingDialog=null
    }

    protected open fun isActionBar():Boolean{
        return false
    }

    abstract fun getLayoutId():Int

    abstract fun initView()

    abstract fun initListener()

    protected fun setLeftIcon(resId:Int=R.drawable.ic_arrow_back_white_24dp,unit:()->Unit){
        leftIcon_fragment?.setImageResource(resId)
        leftIcon_fragment?.setOnClickListener {
            unit.invoke()
        }
    }

    protected fun getLeftIcon():ImageView?{
        return leftIcon_fragment
    }

    protected fun setContentText(resId:Int){
        contentText_fragment?.setText(resId)
    }

    protected fun setContentText(text:String){
        contentText_fragment?.text=(text)
    }

    protected fun setRightIcon(resId:Int=R.drawable.ic_settings_black_24dp,unit:()->Unit){
        rightIcon_fragment?.setImageResource(resId)
        rightIcon_fragment?.setOnClickListener {
            unit.invoke()
        }
    }

    protected fun getRightIcon():ImageView?{
        return rightIcon_fragment
    }

    protected fun getActionBar():View?{
        return actionBar
    }

    protected fun showAlertDialog(message:Int,cancelable:Boolean=true,
                                  negative:Int= R.string.no, negativeExe:()->Unit,
                                  positive:Int= R.string.yes, positiveExe:()->Unit){
        val c=context?:return
        AlertDialog.Builder(c)
            .setCancelable(cancelable)
            .setMessage(message)
            .setNegativeButton(negative){ _, _ ->
                negativeExe.invoke()
            }
            .setPositiveButton(positive){ _, _ ->
                positiveExe.invoke()
            }
            .show()
    }
    protected fun showAlertDialog(message:String,cancelable:Boolean=true,
                                  negative:Int= R.string.no, negativeExe:()->Unit,
                                  positive:Int= R.string.yes, positiveExe:()->Unit){
        val c=context?:return
        AlertDialog.Builder(c)
            .setCancelable(cancelable)
            .setMessage(message)
            .setNegativeButton(negative){ _, _ ->
                negativeExe.invoke()
            }
            .setPositiveButton(positive){ _, _ ->
                positiveExe.invoke()
            }
            .show()
    }
}