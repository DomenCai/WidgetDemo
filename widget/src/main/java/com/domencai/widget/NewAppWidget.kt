package com.domencai.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.RemoteViews

/**
 * Implementation of App Widget functionality.
 */
class NewAppWidget : AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
    }

    override fun onDisabled(context: Context) {
    }
}

val VALUE_IDS = arrayOf(R.id.value_1, R.id.value_2, R.id.value_3, R.id.value_4, R.id.value_5, R.id.value_6, R.id.value_7)
val COL_IDS = arrayOf(R.id.column_1, R.id.column_2, R.id.column_3, R.id.column_4, R.id.column_5, R.id.column_6, R.id.column_7)

internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
    context.getSharedPreferences("user", Context.MODE_PRIVATE).getString("value", "")?.apply {
        val views = RemoteViews(context.packageName, R.layout.new_app_widget)
        split(",").takeIf { it.size == 7 }?.forEachIndexed { index, s ->
            setupWidget(views, index, s.toInt())
        }
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}

fun setupWidget(views: RemoteViews, index: Int, value: Int) {
    val size = (value * 0.16).toInt()
    views.setViewPadding(COL_IDS[index], 0, size, 0, 0)
    if (value > 0) {
        views.setViewVisibility(VALUE_IDS[index], View.VISIBLE)
        views.setTextViewText(VALUE_IDS[index], value.toString())
    } else {
        views.setViewVisibility(VALUE_IDS[index], View.GONE)
    }
}