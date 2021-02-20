package com.feisukj.base_library.utils

import android.app.Activity
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.feisukj.base_library.R
import java.util.*

fun Activity.getLocale(): Locale {
    return if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.N){
        resources.configuration.locales[0]
    }else{
        resources.configuration.locale
    }
}

fun Activity.toast(message:Int=R.string.error){
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}

fun Activity.toast(message:String){
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(message:Int=R.string.error){
    Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(message:String){
    Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
}

fun Any.eLog(message: String,tag:String="Default eLog"){
    Log.e(this.javaClass.simpleName, "$tag:$message")
}

fun Any.iLog(message: String,tag:String="Default iLog"){
    Log.i(this.javaClass.simpleName, "$tag:$message")
}