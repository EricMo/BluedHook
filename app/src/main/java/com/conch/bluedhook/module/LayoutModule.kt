package com.conch.bluedhook.module

import android.content.res.XModuleResources
import android.widget.LinearLayout
import com.conch.bluedhook.common.HookConstant
import com.conch.bluedhook.common.LayoutHelper
import de.robv.android.xposed.callbacks.XC_InitPackageResources
import de.robv.android.xposed.callbacks.XC_LayoutInflated


/**
 * @Description Layout Hook
 * @Author Benjamin
 * @CreateDate 2018-03-30 16:24
 **/
@Deprecated("In order to be compatible with VirtualXposed, we are no longer to hook Layout ")
class LayoutModule(private val resources: XModuleResources, private val resParam: XC_InitPackageResources.InitPackageResourcesParam) {

//    fun hookLayout() {
//        addSettingDoor()
//    }
//
//    private fun addSettingDoor() {
//        resParam.res.hookLayout(HookConstant.processName, "layout", "fragment_homepage_more", object : XC_LayoutInflated() {
//            override fun handleLayoutInflated(liparam: LayoutInflatedParam?) {
//                val layout = liparam!!.view.findViewById(liparam.res.getIdentifier("ll_help_feedback", "id", HookConstant.processName)).parent as LinearLayout
//                val settingView = LayoutHelper.layoutHomeMoreItem(liparam.res, resources, layout.context)
//                settingView.setOnClickListener {
//                    LayoutHelper.openSelfModule(it.context)
//                }
//                layout.addView(settingView)
//            }
//        })
//    }
}