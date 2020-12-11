package com.example.alarmclock.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.withTranslation
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.example.alarmclock.base.BaseThemeView
import com.example.alarmclock.topfun.setCurrentThemeColor
import com.example.module_base.util.LogUtils
import kotlinx.coroutines.*

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.ui.widget
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/12/7 14:19:25
 * @class describe
 */
class SlideView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseThemeView(context, attrs, defStyleAttr) {
    private val mBottomPaint=Paint()
    private val mCoverPaint=Paint()
    private val mArrowPath=Path()
    private var mAnimationJob:Job?=null

    init {
        mBottomPaint.apply {
            color = Color.GRAY
            style=Paint.Style.STROKE
            strokeCap=Paint.Cap.ROUND
            strokeWidth=5f
            isAntiAlias=true
        }

        mCoverPaint.apply {
            color=setCurrentThemeColor(context)
            style=Paint.Style.STROKE
            strokeCap=Paint.Cap.ROUND
            strokeWidth=5f
            isAntiAlias=true
        }


    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(width, height)

    }

    private var mWidth=0f
    private var mHeight=0f
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth=w.toFloat()
        mHeight=h.toFloat()
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawStopArrow(canvas)
        drawRunArrow(canvas)
    }

    private fun drawStopArrow(canvas: Canvas) {
        for ( i in 0..2){
            canvas.withTranslation(mWidth / 2, mHeight / 2+i*20) {
                mArrowPath.moveTo(-mWidth/2.5f,0f)
                mArrowPath.lineTo(0f,-mHeight/6)
                mArrowPath.lineTo(mWidth/2.5f,0f)
                drawPath(mArrowPath,mBottomPaint)
            }
        }
    }


    private fun drawRunArrow(canvas: Canvas){
        mCoverPaint.color= setCurrentThemeColor(context)
        canvas.withTranslation(mWidth / 2, mHeight / 2+position) {
            mArrowPath.moveTo(-mWidth/2.5f,0f)
            mArrowPath.lineTo(0f,-mHeight/6)
            mArrowPath.lineTo(mWidth/2.5f,0f)
            drawPath(mArrowPath,mCoverPaint)
        }
    }

    private var position=40
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun startRun(){
        mAnimationJob=CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                position-=20
                if (position<0) position=40
                invalidate()
                delay(500)
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun stopRun(){
        mAnimationJob?.cancel()
    }
}