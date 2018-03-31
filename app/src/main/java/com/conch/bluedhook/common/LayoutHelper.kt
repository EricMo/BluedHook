package com.conch.bluedhook.common

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.XModuleResources
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.conch.bluedhook.R
import org.jetbrains.annotations.NotNull


/**
 * @Description use to create view
 * @Author Benjamin
 * @CreateDate 2018-03-30 16:51
 **/
object LayoutHelper {

    @SuppressLint("ResourceType")
    fun layoutHomeMoreItem(res: XModuleResources, context: Context): View {
        val root = LinearLayout(context)
        root.layoutParams = ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 49)
        root.setPadding(15, 0, 15, 0)
        root.setBackgroundResource(2130838845)

        val icon = ImageView(context)
        val iconParams = LinearLayout.LayoutParams(20, 20)
        iconParams.rightMargin = 15
        icon.layoutParams = iconParams
        icon.setImageResource(res.getIdentifier("extra_icon_open_setting", "drawable", SelfHookConstant.processName))
        root.addView(icon)

        val title = TextView(context)
        val titleParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT)
        titleParams.weight = 1.0f
        title.textSize = 14.0f
        title.setTextColor(res.getColor(R.color.textColor))
        title.text = res.getString(R.string.openSetting)
        root.addView(title, titleParams)

        val iconRight = ImageView(context)
        iconRight.layoutParams = LinearLayout.LayoutParams(7, 11)
        iconRight.scaleType = ImageView.ScaleType.FIT_XY
        iconRight.setImageResource(res.getIdentifier("extra_icon_common_right_arrow_my", "drawable", SelfHookConstant.processName))
        root.addView(iconRight)
        return root
    }

    @SuppressLint("ResourceType")
    @NotNull
    fun makeNotifyTextView(context: Context, s: String): TextView {
        val textView = TextView(context)
        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(60, 40, 60, 0)
        layoutParams.gravity = 1
        textView.tag = 1
        textView.textSize = 12.0f
        textView.setBackgroundResource(2130838894)
        textView.text = s
        textView.setTextColor(ContextCompat.getColor(context, 2131558736))
        textView.gravity = 1
        textView.setPadding(20, 5, 20, 5)
        textView.layoutParams = layoutParams
        return textView
    }
}