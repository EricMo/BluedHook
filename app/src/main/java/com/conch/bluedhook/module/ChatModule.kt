package com.conch.bluedhook.module

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.conch.bluedhook.common.HookConstant
import com.conch.bluedhook.common.ReflectionUtils
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers

/**
 * Created by Benjamin on 2017/11/23.
 */
class ChatModule(loader: ClassLoader, mContext: Context) : BaseModule(loader, mContext) {

    fun hookChat() {
        val chatModel = XposedHelpers.findClass(HookConstant.chatModel, loader)
        XposedHelpers.findAndHookMethod(HookConstant.processName + HookConstant.msgAdapter, loader, "a",
                chatModel,
                Int::class.java, ViewGroup::class.java, object : XC_MethodHook() {
            override fun beforeHookedMethod(param: XC_MethodHook.MethodHookParam?) {
                XposedBridge.log("We are in chat adapter!")
                val classZ = param!!.args[0]
//                ReflectionUtils.getFieldsValue(classZ.javaClass)
            }
        })
    }
}
