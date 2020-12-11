package com.example.alarmclock.util

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation

/**
 * @name AlarmClock
 * @class name：com.example.alarmclock.util
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/12/7 11:44:43
 * @class describe
 */
object AnimationUtil {


    fun setOutTranslationAnimation(view:View){
        view.animation=TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,0f,
            Animation.RELATIVE_TO_SELF,0f, Animation.RELATIVE_TO_SELF,1.0f ).apply {
            duration=500
            fillAfter=true
        }
        view.visibility=View.GONE
    }

    fun setInTranslationAnimation(view:View){
        view.animation=TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,0f,
            Animation.RELATIVE_TO_SELF,1.0f, Animation.RELATIVE_TO_SELF,0.0f ).apply {
            duration=500
            fillAfter=true
        }
        view.visibility=View.VISIBLE
    }


    fun showAlphaAnimation(view:View){
        view.animation = AlphaAnimation(0f, 1f).apply {
            duration=1000
        }
        view.visibility=View.VISIBLE
    }


    var push=true

     fun setParentAnimation(view: View) {
        val animation = TranslateAnimation(
            Animation.RELATIVE_TO_SELF,
            if (push) 0f else -0.1f,
            Animation.RELATIVE_TO_SELF,
            if (push) -0.1f else 0f,
            Animation.RELATIVE_TO_SELF,
           0f,
            Animation.RELATIVE_TO_SELF,
          0f
        ).apply {
            duration = 500
            fillAfter = true
        }
        view.startAnimation(animation)
    }


     fun setChildAnimation(view: View) {
        view.visibility = View.VISIBLE
        val alphaAnimation =
            AlphaAnimation(if (push) 0f else 1f, if (push) 1f else 0f).apply { duration = 500 }
        val translateAnimation = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, if (push) 1f else 0f, Animation.RELATIVE_TO_SELF, if (push) 0f else 1f,
            Animation.RELATIVE_TO_SELF,  0f,Animation.RELATIVE_TO_SELF, 0f
        ).apply {
            duration = 500
            fillAfter = true
        }
        val animationSet = AnimationSet(true).apply {
            addAnimation(alphaAnimation)
            addAnimation(translateAnimation)
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {
                    if (push) { view.visibility=View.GONE
                    }


                }
                override fun onAnimationStart(animation: Animation?) {}
            })
        }
        view.startAnimation(animationSet)
    }



}