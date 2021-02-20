package com.feisu.noise.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.feisu.noise.R


class RoundRectImageView @JvmOverloads constructor(context: Context,attributeSet: AttributeSet?=null,defStyleAttr:Int=0): AppCompatImageView(context,attributeSet,defStyleAttr) {
    private var corner:Int=0
    private var srcBitmap:Bitmap?=null
    init {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.RoundRectImageView);
        corner =  typedArray.getDimension(R.styleable.RoundRectImageView_corner, 0f).toInt()
        val srcResource: Int = attributeSet?.getAttributeResourceValue(
            "http://schemas.android.com/apk/res/android", "src", 0
        )?:0
        if (srcResource != 0) srcBitmap = BitmapFactory.decodeResource(
            resources,
            srcResource
        )
        typedArray.recycle();
    }


    /**
     * drawable转bitmap
     *
     * @param drawable
     * @return
     */
    private fun drawableToBitmap(drawable: Drawable?): Bitmap? {
        if (drawable == null) {
            return if (srcBitmap != null) {
                srcBitmap
            } else {
                null
            }
        } else if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }
}