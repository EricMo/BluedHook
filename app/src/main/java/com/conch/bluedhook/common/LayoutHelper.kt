package com.conch.bluedhook.common

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.content.res.XModuleResources
import android.content.res.XResources
import android.support.v4.content.ContextCompat
import android.view.Gravity
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

    /**
     * @param xRes this can get app's resources
     * @param res this can get module's resources
     * @param context
     */
    @SuppressLint("ResourceType")
    fun layoutHomeMoreItem(xRes: XResources, res: XModuleResources, context: Context): View {
        //create a item
        val root = LinearLayout(context)
        root.gravity = Gravity.CENTER_VERTICAL
        root.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Density.dip2px(context, 49.0f))
        root.setPadding(Density.dip2px(context, 15.0f), 0, Density.dip2px(context, 15.0f), 0)
        root.setBackgroundResource(xRes.getIdentifier("selector_textview_bgcolor", "drawable", HookConstant.processName))

        //left icon
        val icon = ImageView(context)
        val iconParams = LinearLayout.LayoutParams(Density.dip2px(context, 20.0f), Density.dip2px(context, 20.0f))
        iconParams.rightMargin = Density.dip2px(context, 15.0f)
        icon.layoutParams = iconParams
        icon.setImageDrawable(res.getDrawable(R.drawable.extra_icon_open_setting))
        root.addView(icon)
        //center title
        val title = TextView(context)
        val titleParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT)
        titleParams.weight = 1.0f
        title.textSize = 14.0f
        title.setTextColor(res.getColor(R.color.textColor))
        title.text = res.getString(R.string.openSetting)
        root.addView(title, titleParams)

        //right arrow
        val iconRight = ImageView(context)
        iconRight.scaleType = ImageView.ScaleType.FIT_XY
        iconRight.setImageDrawable(res.getDrawable(R.drawable.extra_icon_common_right_arrow_my))
        root.addView(iconRight, LinearLayout.LayoutParams(Density.dip2px(context, 7.0f), Density.dip2px(context, 11.0f)))
        return root
    }

    @SuppressLint("ResourceType")
    fun makeNotifyTextView(context: Context, s: String): TextView {
        val textView = TextView(context)
        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(60, 40, 60, 0)
        layoutParams.gravity = 1
        textView.tag = 1
        textView.textSize = 12.0f
        textView.setBackgroundResource(2130838865)
        textView.text = s
        textView.setTextColor(ContextCompat.getColor(context, 2131624264))
        textView.gravity = 1
        textView.setPadding(20, 5, 20, 5)
        textView.layoutParams = layoutParams
        return textView
    }
}