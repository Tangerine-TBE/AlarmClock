package com.example.alarmclock.ui.widget.skin

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import androidx.core.graphics.withTranslation
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.example.alarmclock.R
import com.example.alarmclock.base.BaseThemeView
import com.example.alarmclock.util.Constants
import com.example.module_base.util.DateUtil
import com.example.module_base.util.SPUtil
import com.example.module_base.util.SizeUtils
import com.tamsiree.rxkit.RxDeviceTool
import com.tamsiree.rxkit.RxTimeTool
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.*


/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.ui.weight
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/20 13:38
 * @class describe
 */
class WatchFaceOneView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseThemeView(context, attrs, defStyleAttr) {
    private var mWidth = 0f
    private var mHeight = 0f
    private var mRadius = 0f
    private var mAlpha= 0
    private var mCurrentWorkHour=0f
    private var mCurrentWorkMinute=0f
    private var mCurrentSecond = 0
    private var rotatingJob: Job? = null
    private val mScalePaint = Paint()
    private val mScaleBgPaint = Paint()
    private val mCircleBgPaint = Paint()
    private val mCirclePaint = Paint()
    private val mTextPaint = Paint()
    private val mCirclePointPaint = Paint()

    init {

        //刻度
        mScalePaint.apply {
            color = ContextCompat.getColor(context, R.color.skin_watch_one)
            isAntiAlias = true
            strokeCap = Paint.Cap.ROUND
            style = Paint.Style.FILL
            strokeWidth = 10f
        }
        //背景刻度
        mScaleBgPaint.apply {
            color = ContextCompat.getColor(context, R.color.skin_watch_one)
            isAntiAlias = true
            alpha = 80
            strokeCap = Paint.Cap.ROUND
            style = Paint.Style.FILL
            strokeWidth = 10f
        }

        //圈
        mCircleBgPaint.apply {
            color = ContextCompat.getColor(context, R.color.skin_watch_one)
            isAntiAlias = true
            alpha = 80
            style = Paint.Style.STROKE
            strokeWidth = 8f
        }
        //work圈
        mCirclePaint.apply {
            color = ContextCompat.getColor(context, R.color.skin_watch_one)
            isAntiAlias = true
            strokeCap=Paint.Cap.ROUND
            style = Paint.Style.STROKE
            strokeWidth = 8f
        }

        //字
        mTextPaint.apply {
            color = ContextCompat.getColor(context, R.color.skin_watch_one)
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
            textSize = SizeUtils.sp2px(context, 50f).toFloat()
        }

        //圆点
        mCirclePointPaint.apply {
            color = ContextCompat.getColor(context, R.color.white)
            isAntiAlias = true
            style = Paint.Style.FILL
            strokeWidth = 8f
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val realSize = if (RxDeviceTool.isPortrait(context))
            max(widthSize, heightSize)*2/5
        else
            min(widthSize, heightSize)

     //   LogUtils.i("onMeasure1-------$heightSize--------------------------$widthSize---------------${realSize}-------")
        setMeasuredDimension(realSize, realSize)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w.toFloat()
        mHeight = h.toFloat()
        mRadius = mWidth / 2
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
          //  canvas.drawColor(Color.BLUE)
            drawScaleBg(it)
            drawScale(it)
            drawCircle(it)
            drawWorkCircle(it)
            drawCirclePoint(it)
            drawTimeText(it)

        }
    }


    //画背景刻度
    private fun drawScaleBg(canvas: Canvas) {
        canvas.withTranslation(mWidth / 2, mHeight / 2) {
            for (i in 0 until 40) {
                drawLine(mRadius * 0.92f, 0f, mRadius * 0.95f, 0f, mScaleBgPaint)
                rotate(9f, 0f, 0f)
            }
        }

    }

    //画刻度
    private fun drawScale(canvas: Canvas) {
        canvas.withTranslation(mWidth / 2, mHeight / 2) {
            for (i in 0 until mCurrentSecond) {
                drawLine(0f, -mWidth / 2 * 0.92f, 0f, -mWidth / 2 * 0.95f, mScalePaint)
                rotate(9f, 0f, 0f)
            }
        }
    }
    //画圆
    private fun drawCircle(canvas: Canvas) {
        canvas.withTranslation(mWidth / 2, mHeight / 2) {
            // LogUtils.i("--------drawCircle----------${SizeUtils.px2dip(context,mRadius*0.15f)}---->")
            mCircleBgPaint.strokeWidth=8f
            drawCircle(0f, 0f, mRadius * 0.8f, mCircleBgPaint)
            mCircleBgPaint.strokeWidth=10f
            drawCircle(0f, 0f, mRadius * 0.7f, mCircleBgPaint)



        }
    }

    //画走动的圈
    private fun drawWorkCircle(canvas:Canvas){
        canvas.withTranslation(mWidth / 2, mHeight / 2) {
            val outRectF = RectF(-mRadius * 0.8f, -mRadius * 0.8f, mRadius * 0.8f, mRadius * 0.8f)
            val innerRectF = RectF(-mRadius * 0.7f, -mRadius * 0.7f, mRadius * 0.7f, mRadius * 0.7f)
           // drawRect(rectF,mCirclePaint)
            mCirclePaint.strokeWidth=10f
            drawArc(innerRectF,-90f,mCurrentWorkHour,false,mCirclePaint)
            mCirclePaint.strokeWidth=8f
            drawArc(outRectF,-90f,mCurrentWorkMinute,false,mCirclePaint)
        }

    }
    //画圆点
   private fun drawCirclePoint(canvas:Canvas){
        canvas.withTranslation(mWidth / 2, mHeight / 2){
             val x =  mRadius * 0.8 * cos((mCurrentWorkMinute-90) * PI/180 )
            val y =  mRadius * 0.8 * sin((mCurrentWorkMinute-90) * PI /180)

            val x1 =  mRadius * 0.7 * cos((mCurrentWorkHour-90) * PI/180 )
            val y1 =  mRadius * 0.7 * sin((mCurrentWorkHour-90) * PI /180)
            mCirclePointPaint.alpha = mAlpha
            drawCircle(x.toFloat(),y.toFloat(),14f,mCirclePointPaint)
            drawCircle(x1.toFloat(),y1.toFloat(),16f,mCirclePointPaint)

            mCirclePointPaint.alpha=255
            drawCircle(x.toFloat(),y.toFloat(),8f,mCirclePointPaint)
            drawCircle(x1.toFloat(),y1.toFloat(),10f,mCirclePointPaint)

        }
    }

    //画字
    private fun drawTimeText(canvas: Canvas) {
        canvas.withTranslation(mWidth / 2, mHeight / 2) {
            //时间
            mTextPaint.textSize = SizeUtils.sp2px(context, 50f).toFloat()
            val fontMetrics: Paint.FontMetrics = mTextPaint.fontMetrics
            val baselineY = (fontMetrics.bottom - fontMetrics.top) / 2
            drawText(getCurrentTime(), 0f, baselineY / 2, mTextPaint)
            //时间区间
            mTextPaint.textSize = SizeUtils.sp2px(context, 18f).toFloat()
            val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            drawText(if (hour > 12) "PM" else "AM", 0f, -mRadius / 3.5f, mTextPaint)
            //星期
            mTextPaint.textSize = SizeUtils.sp2px(context, 14f).toFloat()
            drawText(getCurrentWeek(), 0f, +mRadius / 2.5f, mTextPaint)

        }

    }

    //获取当前时间
    private fun getCurrentTime(): String {
        val currentTime = RxTimeTool.milliseconds2String(System.currentTimeMillis(),
                SimpleDateFormat(if (SPUtil.getInstance().
                        getBoolean(Constants.SET_SHOW_HOUR24, true))"HH:mm:ss" else "hh:mm:ss"))
        return "${currentTime.substring(0..1)}:${currentTime.substring(3..4)}"
    }

    //获取当前星期
    private fun getCurrentWeek() = DateUtil.getWeekOfDate2(Date())


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun startRotate() {
        rotatingJob = CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                val hour = Calendar.getInstance().get(Calendar.HOUR)
                val min = Calendar.getInstance().get(Calendar.MINUTE)
                var second = Calendar.getInstance().get(Calendar.SECOND)
                mAlpha= if (second<=30 ) 5*second else 150-(second*5-150)
                mCurrentWorkHour=hour*30+min/2f
                mCurrentWorkMinute = if (second == 0) min * 6f else min * 6f+0.1f*second
            //    LogUtils.i("--------mAlpha-------------$mAlpha")
                mCurrentSecond = when (second) {
                    58 -> 39
                    59 -> 40
                    0 -> 1
                    1 -> 1
                    2 -> 2
                    4 -> 3
                    else -> floor(second / 1.5).toInt()
                }
                invalidate()
                delay(1000)

            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onStopRotate() {
        rotatingJob?.cancel()
    }


}