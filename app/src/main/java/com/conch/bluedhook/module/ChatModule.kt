package com.conch.bluedhook.module

import android.content.Context
import android.view.ViewGroup
import com.conch.bluedhook.common.HookConstant
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
                Int::class.javaPrimitiveType, ViewGroup::class.java, object : XC_MethodHook() {
            @Throws(Throwable::class)
            override fun beforeHookedMethod(param: XC_MethodHook.MethodHookParam?) {
                XposedBridge.log("I'm in method,beforeHookedMethod:")
                val aClass = param!!.args[0].javaClass
                val newInstance = aClass.newInstance()
                val fs = aClass.declaredFields
                for (filed in fs) {
                    filed.isAccessible = true
                    val filedName = filed.name
                    val filedValue = XposedHelpers.getObjectField(newInstance, filedName)
                    XposedBridge.log("反射出：$filedName--->$filedValue")
                }
            }

            @Throws(Throwable::class)
            override fun afterHookedMethod(param: XC_MethodHook.MethodHookParam?) {
                XposedBridge.log("I'm in method,afterHookedMethod")
                val aClass = param!!.args[0].javaClass
                val newInstance = aClass.newInstance()
                val fs = aClass.declaredFields
                for (filed in fs) {
                    filed.isAccessible = true
                    val filedName = filed.name
                    val filedValue = XposedHelpers.getObjectField(newInstance, filedName)
                    XposedBridge.log("反射出：$filedName--->$filedValue")
                }
            }
        })
    }
}
