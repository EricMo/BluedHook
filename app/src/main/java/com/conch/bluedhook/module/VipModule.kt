package com.conch.bluedhook.module

import android.content.Context
import com.conch.bluedhook.common.HookConstant
import com.conch.bluedhook.common.ReflectionUtils
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import java.lang.StringBuilder

/**
 * @Description vip
 * @Author Benjamin
 * @CreateDate 2018-07-01 10:03
 **/
class VipModule(loader: ClassLoader, mContext: Context) : BaseModule(loader, mContext) {
    fun hookVip() {
        unlockSelfFirst()
        unlockVIP()
    }


    private fun unlockSelfFirst() {
        XposedHelpers.findAndHookMethod(HookConstant.albumManager, loader, "a",
                Boolean::class.java, List::class.java, object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam?) {
                super.afterHookedMethod(param)
                val result = param?.result as List<*>
                result.forEach {
                    if (XposedHelpers.getIntField(it, "album_status") == 0) {
                        XposedHelpers.setIntField(it, "album_status", 1)
                        val picStr = XposedHelpers.getObjectField(it, "album_pic") as String
                        val builder = StringBuilder(picStr)
                        builder.append("!original.png")
                        XposedHelpers.setObjectField(it, "album_pic", builder.toString())
                    }

                }
            }
        })
    }

    //unlock local vip field
    private fun unlockVIP() {
        val targetClass = loader.loadClass(HookConstant.userInfo)
        val resultClass = loader.loadClass(HookConstant.bluedLoginResult)
        val methods = XposedHelpers.findMethodsByExactParameters(targetClass, resultClass)
        methods.forEach {
            XposedHelpers.findAndHookMethod(HookConstant.userInfo, loader, it.name, object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam?) {
                    //login info
                    val loginResult = param!!.result
                    //change to vip
                    XposedHelpers.setIntField(loginResult, "vip_grade", 2)
                }
            })
        }
    }
}