package com.conch.bluedhook.module

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.res.XModuleResources
import android.widget.LinearLayout
import com.conch.bluedhook.common.HookConstant
import com.conch.bluedhook.common.LayoutHelper
import com.conch.bluedhook.common.SelfHookConstant
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_InitPackageResources
import de.robv.android.xposed.callbacks.XC_LayoutInflated
import kotlin.jvm.internal.Intrinsics


/**
 * @Description Layout Hook
 * @Author Benjamin
 * @CreateDate 2018-03-30 16:24
 **/
class LayoutModule(private val resources: XModuleResources, private val resParam: XC_InitPackageResources.InitPackageResourcesParam) {

    fun hookLayout() {
        addSettingDoor()
    }

    private fun addSettingDoor() {
        resParam.res.hookLayout(HookConstant.processName, "layout", "fragment_homepage_more", object : XC_LayoutInflated() {
            override fun handleLayoutInflated(liparam: LayoutInflatedParam?) {
                val layout = liparam!!.view.findViewById(liparam.res.getIdentifier("ll_help_feedback", "id", HookConstant.processName)).parent as LinearLayout
                val settingView = LayoutHelper.layoutHomeMoreItem(liparam.res, resources, layout.context)
                settingView.setOnClickListener {
                    openSelfModule(it.context)
                }
                layout.addView(settingView)
            }
        })
    }

    /**
     *
     * chat_notice_notify_content_style
     * shape_common_round_graylighter_solid
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