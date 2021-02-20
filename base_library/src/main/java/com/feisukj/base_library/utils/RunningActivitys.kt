package com.feisukj.base_library.utils

import android.app.Activity
import java.lang.ref.WeakReference

object RunningActivitys {
    private val activitys by lazy { HashSet<Activity>() }
    private var currentActivity: WeakReference<Activity>?=null

    fun getActivitys():List<Activity>{
        return ArrayList(activitys)
    }

    fun addActivity(activity: Activity){
        activitys.add(activity)
    }

    fun removeActivity(activity: Activity){
        activitys.remove(activity)
    }

    fun getCurrentActivity():Activity?{
        return currentActivity?.get()
    }

    fun setCurrentActivity(activity: Activity){
        currentActivity= WeakReference(activity)
    }
}