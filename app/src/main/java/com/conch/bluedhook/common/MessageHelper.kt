package com.conch.bluedhook.common

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView

/**
 * Created by Benjamin on 2017/12/29.
 */
object MessageHelper {
    @SuppressLint("ResourceType")
    fun makeNotifyTextView(mContext: Context, content: String): TextView {
        val notify = TextView(mContext)
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.setMargins(60, 40, 60, 0)
        params.gravity = Gravity.CENTER_HORIZONTAL
        notify.tag = 0x0000001
        notify.textSize = 12f
        notify.setBackgroundResource(2130838772)
        notify.text = content
        notify.setTextColor(Color.parseColor("#ffadafb0"))
        notify.gravity = Gravity.CENTER_HORIZONTAL
        notify.setPadding(20, 5, 20, 5)
        notify.layoutParams = params
        return notify
    }
}