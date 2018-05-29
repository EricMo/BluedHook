package com.conch.bluedhook.module

import android.content.Context
import android.content.ContextWrapper
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage


/**
 * Created by Benjamin on 2017/11/23.
 */
object ApplicationModule {
    fun hookApplicationContext(lpparam: XC_LoadPackage.LoadPackageParam, body: (Context, ClassLoader) -> Unit) {
        XposedHelpers.findAndHookMethod(ContextWrapper::class.java, "attachBaseContext", Context::class.java, object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam?) {
                body.invoke((param!!.args[0] as Context), lpparam.classLoader)
            }
        })
    }
}

