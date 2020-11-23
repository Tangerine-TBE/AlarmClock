package com.example.alarmclock.base

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.lifecycle.LifecycleObserver

/**
 * @author: 铭少
 * @date: 2020/11/21 0021
 * @description：
 */
open class BaseThemeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr),LifecycleObserver {
}