package com.feisukj.base_library.baseclass

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.feisukj.base_library.R
import com.feisukj.base_library.widget.LoadingDialog
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.base_activity.*

abstract class BaseActivity :AppCompatActivity(){
    protected lateinit var immersionBar: ImmersionBar
    protected var loadingDialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isActionBar()) {
            setContentView(R.layout.base_activity)
            contentView.also {
                it.removeAllViews()
                LayoutInflater.from(this).inflate(getLayoutId(),it,true)
            }

            getStatusBarColor().also {
                baseBar?.setBackgroundColor(ContextCompat.getColor(this,it))
                initImmersionBar(it)
            }
        }else{
            setContentView(getLayoutId())
            initImmersionBar(R.color.transparent)
        }

        leftIcon?.setOnClickListener { finish() }
        initView()
        initListener()
    }

    protected open fun getStatusBarColor():Int{
        return R.color.them_base
    }

    abstract fun getLayoutId():Int

    abstract fun initView()

    abstract fun initListener()

    protected open fun isActionBar():Boolean{
        return true
    }

    protected fun setLeftIcon(resId:Int=R.drawable.ic_arrow_back_white_24dp,unit:()->Unit){
        leftIcon?.setImageResource(resId)
        leftIcon?.setOnClickListener {
            unit.invoke()
        }
    }

    protected fun getLeftIcon():ImageView?{
        return leftIcon
    }

    protected fun setContentText(resId:Int){
        contentText?.setText(resId)
    }

    protected fun setContentText(text:String){
        contentText?.text=(text)
    }

    protected fun setRightIcon(resId:Int=R.drawable.ic_settings_black_24dp,unit:()->Unit){
        rightIcon?.setImageResource(resId)
        rightIcon?.setOnClickListener {
            unit.invoke()
        }
    }

    protected fun getRightIcon():ImageView?{
        return rightIcon
    }

    /**
     * 沉浸栏颜色
     */
    private fun initImmersionBar(color: Int) {
        immersionBar = ImmersionBar.with(this)
            .statusBarDarkFont(isStatusBarDarkFont())
        if (color != 0) {
            immersionBar.statusBarColor(color)
        }
        immersionBar.init()
    }

    protected open fun isStatusBarDarkFont():Boolean{
        return false
    }

    protected fun showLoadingDialog():LoadingDialog?{
        loadingDialog = LoadingDialog(this)
        if (!this.isFinishing) {
            loadingDialog?.show()
        }
        return loadingDialog
    }

    private fun dismissLoadingDialog(){
        if (loadingDialog?.isShowing==true)
            loadingDialog?.dismiss()
        loadingDialog=null
    }

    protected fun showAlertDialog(message:Int,cancelable:Boolean=true,
                                  negative:Int=R.string.no,negativeExe:()->Unit,
                                  positive:Int=R.string.yes,positiveExe:()->Unit){
        AlertDialog.Builder(this)
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
                                  negative:Int=R.string.no,negativeExe:()->Unit,
                                  positive:Int=R.string.yes,positiveExe:()->Unit){
        AlertDialog.Builder(this)
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