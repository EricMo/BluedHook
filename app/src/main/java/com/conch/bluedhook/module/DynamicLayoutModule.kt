package com.conch.bluedhook.module

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import com.conch.bluedhook.common.HookConstant
import com.conch.bluedhook.common.LayoutHelper
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers

/**
 * @Description modify layout(u can see [LayoutModule])
 * @Author Benjamin
 * @CreateDate 2018-05-29 14:42
 **/
class DynamicLayoutModule(loader: ClassLoader, mContext: Context) : BaseModule(loader, mContext) {
    fun layout() {
        addSelfDoor()
    }

    private fun addSelfDoor() {
        XposedHelpers.findAndHookMethod(HookConstant.homePageMore, loader, "c", object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam?) {
                if (XposedHelpers.getAdditionalStaticField(param?.thisObject, "hasAdd") == null) {
                    //ll_help_feedback = 2131756111
                    val feedback = XposedHelpers.getObjectField(param?.thisObject, "x") as View
                    val parent = feedback.parent as LinearLayout
                    val item = LayoutHelper.layoutHomeMoreItem(mContext)
                    item.setOnClickListener {
                        LayoutHelper.openSelfModule(mContext)
                    }
                    parent.addView(item)
                    XposedHelpers.setAdditionalStaticField(param?.thisObject, "hasAdd", "1")
                }
            }
        })
    }
}