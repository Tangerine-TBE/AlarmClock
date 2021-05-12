package com.twx.clock.ui.widget
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.widget.NestedScrollView

/**
 * @name TounchApplication
 * @class name：com.example.tounchapplication
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/11/25 10:41
 * @class describe
 */
class ScreenViewLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : NestedScrollView(context, attrs, defStyleAttr) {
    private var oldY=0f
    private var oldX=0f
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action){
            MotionEvent.ACTION_DOWN->{
                oldY=event.y
                oldX=event.x

            }
            MotionEvent.ACTION_UP->{
            }
            MotionEvent.ACTION_MOVE->{
                mOnDistanceChangeListener?.overDistance(oldX-event.x,oldY - event.y)
            }
        }
        return super.onTouchEvent(event)
    }

    private var mOnDistanceChangeListener:OnDistanceChangeListener?=null
    fun setOnDistanceChangeListener(listener:OnDistanceChangeListener){
        mOnDistanceChangeListener=listener
    }

    interface OnDistanceChangeListener{
        fun overDistance(slideX:Float,slideY: Float)
    }

}