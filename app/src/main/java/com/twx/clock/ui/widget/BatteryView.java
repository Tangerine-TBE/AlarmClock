package com.twx.clock.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.twx.clock.R;

/**
 * @author wujinming QQ:1245074510
 * @name AlarmClock
 * @class name：com.example.alarmclock.ui.weight
 * @class describe
 * @time 2020/11/18 15:03
 * @class describe
 */
public class BatteryView extends View {
    private int mPower = 100;
    private int orientation;
    private int width;
    private int height;
    private int mColor;
    private Paint paint;
    private Paint paint1;
    private RectF r1;
    private RectF r2;
    private RectF r3;
    private RectF rect;
    private RectF rect2;
    private RectF headRect;

    public BatteryView(Context context) {
        super(context);
    }

    public BatteryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Battery);
        mColor = typedArray.getColor(R.styleable.Battery_batteryColor, 0xFFFFFFFF);
        orientation = typedArray.getInt(R.styleable.Battery_batteryOrientation, 0);
        mPower = typedArray.getInt(R.styleable.Battery_batteryPower, 100);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        /**
         * recycle() :官方的解释是：回收TypedArray，以便后面重用。在调用这个函数后，你就不能再使用这个TypedArray。
         * 在TypedArray后调用recycle主要是为了缓存。当recycle被调用后，这就说明这个对象从现在可以被重用了。
         *TypedArray 内部持有部分数组，它们缓存在Resources类中的静态字段中，这样就不用每次使用前都需要分配内存。
         */
        typedArray.recycle();
        paint = new Paint();
        paint1 = new Paint();
        r1 = new RectF();
        r2 = new RectF();
        r3 = new RectF();
        rect = new RectF();
        rect2 = new RectF();
        headRect = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //对View上的內容进行测量后得到的View內容占据的宽度
        width = getMeasuredWidth();
        //对View上的內容进行测量后得到的View內容占据的高度
        height = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //判断电池方向    horizontal: 0   vertical: 1
        if (orientation == 0) {
            drawHorizontalBattery(canvas);
        } else {
            drawVerticalBattery(canvas);
        }
    }

    /**
     * 绘制水平电池
     *
     * @param canvas
     */


    private void drawHorizontalBattery(Canvas canvas) {
        paint.setColor(mColor);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        float strokeWidth = width / 20f;
        float strokeWidth_2 = strokeWidth / 2;
        paint.setStrokeWidth(2);

        r1.set(strokeWidth_2, strokeWidth_2, width - strokeWidth - strokeWidth_2, height - strokeWidth_2);
        //设置外边框颜色为黑色
        paint.setColor(mColor);
        canvas.drawRoundRect(r1,3,3, paint);
        paint.setStrokeWidth(0);
        paint.setStyle(Paint.Style.FILL);
        //画电池内矩形电量
        float offset = (width - strokeWidth * 2) * mPower / 100.f;

        r2.set(strokeWidth, strokeWidth, offset, height - strokeWidth);
        //根据电池电量决定电池内矩形电量颜色
 /*       if (mPower < 30) {
            paint.setColor(Color.RED);
        }
        if (mPower >= 30 && mPower < 50) {
            paint.setColor(Color.BLUE);

        }
        if (mPower >= 50) {
            paint.setColor(Color.GREEN);
        }*/
        paint.setColor(mColor);
        canvas.drawRoundRect(r2, 3,3,paint);
        //画电池头
       // LogUtils.i("---------drawRoundRect-----------"+strokeWidth+"---------"+width);

        r3.set(width - strokeWidth, height * 0.3f, width, height * 0.7f);
        //设置电池头颜色为黑色
        paint.setColor(mColor);
        canvas.drawArc(r3,-90,180,true, paint);
    }

    /**
     * 绘制垂直电池
     *
     * @param canvas
     */
    private void drawVerticalBattery(Canvas canvas) {

        paint1.setColor(mColor);
        paint1.setStyle(Paint.Style.STROKE);
        float strokeWidth = height / 20.0f;
        float strokeWidth2 = strokeWidth / 2;
        paint1.setStrokeWidth(strokeWidth);
        int headHeight = (int) (strokeWidth + 0.5f);

        rect.set(strokeWidth2, headHeight + strokeWidth2, width - strokeWidth2, height - strokeWidth2);
        canvas.drawRect(rect, paint1);
        paint1.setStrokeWidth(0);
        float topOffset = (height - headHeight - strokeWidth) * (100 - mPower) / 100.0f;

        rect2.set(strokeWidth, headHeight + strokeWidth + topOffset, width - strokeWidth, height - strokeWidth);
        paint1.setStyle(Paint.Style.FILL);
        canvas.drawRect(rect2, paint1);
        headRect.set(width / 4.0f, 0, width * 0.75f, headHeight);
        canvas.drawRect(headRect, paint1);
    }

    /**
     * 设置电池电量
     *
     * @param power
     */
    public void setPower(int power) {
        this.mPower = power;
        if (mPower < 0) {
            mPower = 100;
        }
        invalidate();//刷新VIEW
    }

    /**
     * 设置电池颜色
     *
     * @param color
     */
    public void setColor(int color) {
        this.mColor = color;
        invalidate();
    }

    /**
     * 获取电池电量
     *
     * @return
     */
    public int getPower() {
        return mPower;
    }
}
