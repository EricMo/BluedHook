package com.conch.bluedhook.module

import android.app.Application
import android.content.Context
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

/**
 * Created by Benjamin on 2017/11/23.
 */
object ApplicationModule {
    fun hookApplicationContext(lpparam: XC_LoadPackage.LoadPackageParam, body: (Context, ClassLoader) -> Unit) {
        XposedHelpers.findAndHookMethod(Application::class.java, "attach", Context::class.java, object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam?) {
                    body.invoke(param!!.thisObject as Context, lpparam.classLoader)
            }
        })
    }
}

