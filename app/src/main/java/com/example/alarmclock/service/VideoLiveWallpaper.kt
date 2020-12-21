package com.example.alarmclock.service

import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import androidx.core.graphics.drawable.toBitmap
import com.example.alarmclock.R
import com.example.alarmclock.ui.activity.MainActivity
import com.example.module_base.ui.activity.DealActivity
import com.example.module_base.util.LogUtils
import com.example.module_tool.base.BaseConstant
import com.tamsiree.rxkit.RxDeviceTool

import java.io.File

class VideoLiveWallpaper: WallpaperService() {
    companion object{
        fun getVideoWallpaperPreview(context: Context): Intent {
            val intent = Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER)
            intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                    ComponentName(context, VideoLiveWallpaper::class.java))
            return intent
        }
        fun saveWallpaper(context: Context):Boolean{
            val drawable= try {
                LogUtils.i("保存壁纸")
                WallpaperManager.getInstance(context).drawable
            }catch (e:Exception){
                LogUtils.i("保存壁纸")
                null
            } ?: return false

            val w= RxDeviceTool.getScreenWidths(context)
            val h=RxDeviceTool.getScreenWidths(context)
            val bitmap=drawable.toBitmap(w,h)
            try {
                val file=File(context.filesDir,"wallpaper.png")
                bitmap.compress(Bitmap.CompressFormat.PNG,100,file.outputStream())
//                bitmap.recycle()
                LogUtils.i("壁纸写入磁盘","保存壁纸")
            }catch (e:Exception){
                LogUtils.i("写入磁盘失败","保存壁纸")
                return false
            }
            LogUtils.i("保存壁纸成功","保存壁纸")
            return true
        }

        fun getWallpaper(context: Context):Bitmap?{
            val file=File(context.filesDir,"wallpaper.png")
            return try {
                BitmapFactory.decodeFile(file.absolutePath)
            }catch (e:Exception){
                null
            }
        }

        var isSetWallpaper=false
            private set(value) {
                if (!field && value){
                  //  MobclickAgent.onEvent(BaseConstant.application,BaseConstant.OutAd.s800002_setwallpaper)
                }
                field = value
            }
    }

    override fun onCreateEngine(): Engine {
        return VideoEngine()
    }

    inner class VideoEngine : Engine() {
        override fun onCreate(surfaceHolder: SurfaceHolder) {
            super.onCreate(surfaceHolder)
        }

        override fun onDestroy() {
            super.onDestroy()
        }

        override fun onSurfaceCreated(holder: SurfaceHolder) {
            super.onSurfaceCreated(holder)
            if (isPreview){
                val canvas=holder.lockCanvas()
                getWallpaper(this@VideoLiveWallpaper)?.apply {
                    Bitmap.createScaledBitmap(
                        this,
                        resources.displayMetrics.widthPixels,
                        resources.displayMetrics.heightPixels,
                        true
                    ).let {
                        canvas.drawBitmap(it,0f,0f, null)
                    }
                }
                holder.unlockCanvasAndPost(canvas)
            /*    val bitmap=BitmapFactory.Options().let {
                    it.inJustDecodeBounds=true
                    BitmapFactory.decodeResource(resources, R.mipmap.qdy_bg,it)
                    val bitmapWidth=it.outWidth
                    val sample=bitmapWidth/resources.displayMetrics.widthPixels
                    it.inJustDecodeBounds=false
                    it.inSampleSize=sample
                    BitmapFactory.decodeResource(resources,R.mipmap.qdy_bg,it)
                }
                Bitmap.createScaledBitmap(
                    bitmap,
                    resources.displayMetrics.widthPixels,
                    resources.displayMetrics.heightPixels,
                    true
                ).let {
                    canvas.drawBitmap(it,0f,0f, null)
                }*/

            }else{
                isSetWallpaper =true
                try {
                    val canvas=holder.lockCanvas()
                 getWallpaper(this@VideoLiveWallpaper)?.apply {
                    Bitmap.createScaledBitmap(
                          this,
                          resources.displayMetrics.widthPixels,
                        resources.displayMetrics.heightPixels,
                          true
                      ).let {
                          canvas.drawBitmap(it,0f,0f, null)
                      }
                    }
                    holder.unlockCanvasAndPost(canvas)
                }catch (e:Exception){
                    e.printStackTrace()
                }
                if (!BaseConstant.isForeground){
                    val intent=Intent(BaseConstant.application,MainActivity::class.java)
                    intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
            }
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder) {
            super.onSurfaceDestroyed(holder)
        }

        override fun onVisibilityChanged(visible: Boolean) {

        }
    }
}