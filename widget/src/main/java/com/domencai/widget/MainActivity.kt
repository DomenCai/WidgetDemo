package com.domencai.widget

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val seekId = listOf(seek_1, seek_2, seek_3, seek_4, seek_5, seek_6, seek_7)
        getSharedPreferences("user", Context.MODE_PRIVATE).getString("value", "")?.apply {
            split(",").takeIf { it.size == 7 }?.forEachIndexed { index, s ->
                seekId[index].progress = s.toInt()
            }
        }

        btn_apply.setOnClickListener {
            val sb = StringBuilder()
            val remoteView = RemoteViews(packageName, R.layout.new_app_widget)

            seekId.forEachIndexed { index, seekBar ->
                sb.append(seekBar.progress).append(',')
                setupWidget(remoteView, index, seekBar.progress)
            }
            val componentName = ComponentName(applicationContext, NewAppWidget::class.java)
            AppWidgetManager.getInstance(applicationContext).updateAppWidget(componentName, remoteView)

            val value = sb.substring(0, sb.length - 1)
            getSharedPreferences("user", Context.MODE_PRIVATE).apply {
                edit().putString("value", value).apply()
                Log.w("cdm", "MainActivity.onCreate -> $value")
            }
        }
    }
}