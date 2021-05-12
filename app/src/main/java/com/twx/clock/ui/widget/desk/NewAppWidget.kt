package com.twx.clock.ui.widget.desk

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.twx.clock.R
import com.twx.clock.ui.activity.MainViewActivity
import com.tamsiree.rxkit.RxTimeTool
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import java.text.SimpleDateFormat


/**
 * Implementation of App Widget functionality.
 */
class NewAppWidget : AppWidgetProvider() {
    companion object{
        const val ACTION_ONE="action_one"


        private fun getPendingIntent(context: Context, id:Int, actionStr: String): PendingIntent =
            PendingIntent.getBroadcast(context,id,Intent(context, NewAppWidget::class.java).apply {
                action = actionStr
            },PendingIntent.FLAG_UPDATE_CURRENT)


        fun updateWidget(context: Context) {
            val remoteViews = RemoteViews(context.packageName, R.layout.new_app_widget)
            remoteViews.setTextViewText(R.id.mTimeWidget,RxTimeTool.getCurTimeString(SimpleDateFormat("HH:mm")))
            remoteViews.setOnClickPendingIntent(R.id.mTimeWidget,getPendingIntent(context,1,ACTION_ONE))
            val manager = AppWidgetManager.getInstance(context)
           ComponentName(context, NewAppWidget::class.java)?.let {
               manager?.updateAppWidget(it, remoteViews)
           }
        }

    }

    private val job=Job()
    private val scope= CoroutineScope(job)
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
              /*  for (appWidgetId in appWidgetIds) {
                    updateAppWidget(context, appWidgetManager, appWidgetId)
                }*/
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
        updateWidget(context)
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        when(intent.action){
            ACTION_ONE->{
                context.startActivity(Intent(context,MainViewActivity::class.java).apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)})
            }
        }
    }


}




