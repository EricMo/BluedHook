package com.conch.bluedhook.common

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import kotlin.jvm.internal.Intrinsics


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
    fun layoutHomeMoreItem(context: Context): View {
        //create a item
        //selector_textview_bgcolor
        val root = LinearLayout(context)
        root.gravity = Gravity.CENTER_VERTICAL
        root.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Density.dip2px(context, 49.0f))
        root.setPadding(Density.dip2px(context, 15.0f), 0, Density.dip2px(context, 15.0f), 0)
        root.setBackgroundResource(2130838933)

        //left icon
        //icon_more_setting
        val icon = ImageView(context)
        val iconParams = LinearLayout.LayoutParams(Density.dip2px(context, 20.0f), Density.dip2px(context, 20.0f))
        iconParams.rightMargin = Density.dip2px(context, 15.0f)
        icon.layoutParams = iconParams
        icon.setImageResource(2130838314)
        root.addView(icon)
        //center title
        val title = TextView(context)
        val titleParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT)
        titleParams.weight = 1.0f
        title.textSize = 14.0f
        title.setTextColor(Color.parseColor("#3D4244"))
        title.text = ResourcesProvider.openSetting
        root.addView(title, titleParams)

        //right arrow
        //icon_right_arrow_my
        val iconRight = ImageView(context)
        iconRight.scaleType = ImageView.ScaleType.FIT_XY
        iconRight.setImageResource(2131362317)
        root.addView(iconRight, LinearLayout.LayoutParams(Density.dip2px(context, 7.0f), Density.dip2px(context, 11.0f)))
        return root
    }

    /**
     * @Description:
     * @Author Benjamin
     * @Date 2018年05月29日 16:27:47
     * chat_notice_notify_content_style
     * sara_e
     * shape_common_round_graylighter_solid
     * @return
     */
    @SuppressLint("ResourceType")
    fun makeNotifyTextView(context: Context, s: String): TextView {
        val textView = TextView(context)
        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(60, 40, 60, 0)
        layoutParams.gravity = 1
        textView.tag = 1
        textView.textSize = 12.0f
        textView.setBackgroundResource(2130838996)
        textView.text = s
        textView.setTextColor(ContextCompat.getColor(context, 2131493190))
        textView.gravity = 1
        textView.setPadding(20, 5, 20, 5)
        textView.layoutParams = layoutParams
        return textView
    }


    /**
     *
     * @Author Benjamin
     * @Date 2018年03月30日 17:38:19
     * @param context
     * @return
     */
    fun openSelfModule(context: Context) {
        Intrinsics.checkParameterIsNotNull(context, "mContext")
        val intent = Intent("android.intent.action.MAIN")
        intent.addCategory("android.intent.category.LAUNCHER")
        intent.action = "android.intent.action.VIEW"
        intent.component = ComponentName(SelfHookConstant.processName, SelfHookConstant.mainActivity)
        context.startActivity(intent)
    }
}