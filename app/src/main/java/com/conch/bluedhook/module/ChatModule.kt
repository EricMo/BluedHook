package com.conch.bluedhook.module

import android.content.Context
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
        // place your hooks here, it should work with lpparam.classLoader
        //获取聊天模型
        val chatModel = XposedHelpers.findClass(HookConstant.chatModel, loader)
        //开始黑入方法
        XposedHelpers.findAndHookMethod(HookConstant.processName + HookConstant.msgAdapter, loader, "a",
                chatModel,
                Int::class.java, ViewGroup::class.java, object : XC_MethodHook() {
            override fun beforeHookedMethod(param: XC_MethodHook.MethodHookParam?) {
                ReflectionUtils.getFieldsValue(param!!.args[0])
            }
        })
    }
}
