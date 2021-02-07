package com.example.alarmclock.ui.widget.skin.watch

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.example.alarmclock.R
import com.example.alarmclock.base.BaseThemeView
import com.example.alarmclock.util.Constants
import com.example.module_base.util.SPUtil
import com.tamsiree.rxkit.RxDeviceTool
import kotlinx.coroutines.*
import java.util.*
import kotlin.math.max
import kotlin.math.min

/**
 * @author: 铭少
 * @date: 2020/11/15 0015
 * @description：
 */
class WatchFaceTwoView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseThemeView(context, attrs, defStyleAttr) {
    private val tag=javaClass.simpleName
    private val mSecondPaint= Paint()
    private val mMinutePaint= Paint()
    private val mHourPaint= Paint()
    private val mBigScalePaint= Paint()
    private val mSmallScalePaint= Paint()
    private val mTestPaint= Paint()
    private val mBeginRect= Rect()
    private val mEndRect= Rect()
    private val mTestRect= Rect()

    private var mSecondColor= ContextCompat.getColor(context,R.color.skin_watch_two2)
    private var mMinuteColor=ContextCompat.getColor(context,R.color.skin_watch_two2)
    private var mHourColor=ContextCompat.getColor(context,R.color.skin_watch_two2)
    private var mScaleColor=ContextCompat.getColor(context,R.color.skin_watch_two)
    private var mWatchBackground=R.mipmap.ic_launcher
    private var mBitmap:Bitmap?=null
    private val mCalendar=Calendar.getInstance()
    init {
        initAttrs(context, attrs)

        //秒针
        mSecondPaint.apply {
            color=mSecondColor
            strokeWidth=8f
            style=Paint.Style.STROKE
            isAntiAlias=true
        }
        //分针
        mMinutePaint.apply {
            color=mMinuteColor
            strokeWidth=18f
            style=Paint.Style.STROKE
            isAntiAlias=true
        }
        //时针
        mHourPaint.apply {
            color=mHourColor
            strokeWidth=20f

            style=Paint.Style.STROKE
            isAntiAlias=true
        }
        //刻度
        mBigScalePaint.apply {
            color=mScaleColor
            strokeWidth=5f
            style=Paint.Style.FILL
            isAntiAlias=true
        }
        mSmallScalePaint.apply {
            color=mSecondColor
            strokeWidth=5f
            style=Paint.Style.FILL
            isAntiAlias=true
        }


        mTestPaint.apply {
            color=Color.YELLOW
            strokeWidth=5f
            style=Paint.Style.STROKE
            isAntiAlias=true
        }


    }


    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        context.obtainStyledAttributes(attrs, R.styleable.WatchFaceView).apply {
            mSecondColor = getColor(
                R.styleable.WatchFaceView_secondColor,
                context.resources.getColor(R.color.skin_watch_two2)
            )
            mMinuteColor= getColor(
                R.styleable.WatchFaceView_minuteColor,
                context.resources.getColor(R.color.skin_watch_two2)
            )
            mHourColor=getColor(
                R.styleable.WatchFaceView_hourColor,
                context.resources.getColor(R.color.skin_watch_two2)
            )
            mScaleColor= getColor(
                R.styleable.WatchFaceView_scaleColor,
                context.resources.getColor(R.color.skin_watch_two)
            )
            mWatchBackground=getResourceId(R.styleable.WatchFaceView_faceBackground, -1)
            if (mWatchBackground!=-1) {
                mBitmap = BitmapFactory.decodeResource(resources, mWatchBackground)
            }
            getBoolean(R.styleable.WatchFaceView_scaleShow, true)
            recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val realSize = if (RxDeviceTool.isPortrait(context))
            max(widthSize, heightSize)*2/5
        else
            min(widthSize, heightSize)
      // LogUtils.i("onMeasure1-------$heightSize--------------------------$widthSize---------------${realSize}-------")
        setMeasuredDimension(realSize,realSize)
        initBackground(realSize,realSize)
    }


    private fun initBackground(width:Int,height:Int) {
        mBitmap?.let {
             mBeginRect.apply {
                 left = 0
                 top = 0
                 right = mBitmap!!.width
                 bottom = mBitmap!!.height
             }

            mEndRect.apply {
                     left=0
                     top=0
                     right= width
                     bottom=height
                 }

             }

        mTestRect.apply {
            left=0
            top=0
            right= width
            bottom=height
        }
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mCalendar.timeInMillis=System.currentTimeMillis()
        val currentHour = mCalendar.get(Calendar.HOUR)
        val currentMinute = mCalendar.get(Calendar.MINUTE)
        val currentSecond = mCalendar.get(Calendar.SECOND)
        canvas?.apply {
          // drawColor(Color.BLACK)
            //画布半径
            val realRadius=width/2f
            //画刻度
            drawScale(realRadius)
            if (currentSecond == 0) {
                //绘制秒针
                drawSecondPointer(currentSecond, realRadius)
                //绘制分针
                drawMinutePointer(currentMinute, realRadius)
                //绘制时针
                drawHourPointer(currentMinute, currentHour, realRadius)
            } else {
                //绘制时针
                drawHourPointer(currentMinute, currentHour, realRadius)
                //绘制分针
                drawMinutePointer(currentMinute, realRadius)
                //绘制秒针
                drawSecondPointer(currentSecond, realRadius)
            }
            //画圆点
            drawCircle(realRadius,realRadius,20f,mBigScalePaint)

        }
    }


    private fun Canvas.drawSecondPointer(currentSecond: Int, realRadius: Float) {
        if (SPUtil.getInstance().getBoolean(Constants.SET_SHOW_SECOND, true)) {
            save()
            val secondRotate = currentSecond * 6f
            rotate(secondRotate, realRadius, realRadius)
            drawLine(realRadius, realRadius + 40f, realRadius, realRadius - realRadius * 0.7f, mSecondPaint)
            restore()
        }
    }

    private fun Canvas.drawMinutePointer(currentMinute: Int, realRadius: Float) {
        save()
        val minuteRotate = currentMinute * 6f
        rotate(minuteRotate, realRadius, realRadius)
        drawLine(
            realRadius,
            realRadius + 40f,
            realRadius,
            realRadius - realRadius * 0.6f,
            mMinutePaint
        )
        restore()
    }

    private fun Canvas.drawHourPointer(currentMinute: Int, currentHour: Int, realRadius: Float) {
        save()
        val hourOffsetRotate = currentMinute / 2
        val hourRotate = currentHour * 30f + hourOffsetRotate
        rotate(hourRotate, realRadius, realRadius)
        drawLine(
            realRadius,
            realRadius + 40f,
            realRadius,
            realRadius - realRadius * 0.45f,
            mHourPaint
        )
        restore()
    }

    private fun Canvas.drawScale(realRadius: Float) {

        //内圆半径
        val innerCircleRadius = width / 2 * 0.95f
       // drawCircle(width / 2f, width / 2f, outCircleRadius, mScalePaint)
        //  drawCircle(width/2f,width/2f,innerCircleRadius,mScalePaint)
        save()
        for (i in 0 until 24) {
            drawCircle(
                realRadius,
                realRadius - innerCircleRadius,
                7f,
                mSmallScalePaint
            )
            rotate(15f, realRadius, realRadius)
        }
        restore()
        save()
        for (i in 0 until 12) {
            drawCircle(
                realRadius,
                realRadius - innerCircleRadius,
                10f,
                mBigScalePaint
            )
            rotate(30f, realRadius, realRadius)
        }
        restore()

    }

    private var mRunJob: Job?=null
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
     fun onStartRun(){
        mRunJob = CoroutineScope(Dispatchers.Main).launch {
            while (true){
                invalidate()
                delay(1000)
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onStopRun(){
        mRunJob?.cancel()
    }

}