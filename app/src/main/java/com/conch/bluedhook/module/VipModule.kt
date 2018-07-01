package com.conch.bluedhook.module

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.conch.bluedhook.common.HookConstant
import com.conch.bluedhook.common.ResourcesProvider
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers

/**
 * @Description vip
 * @Author Benjamin
 * @CreateDate 2018-07-01 10:03
 **/
class VipModule(loader: ClassLoader, mContext: Context) : BaseModule(loader, mContext) {
    fun hookVip() {
        secretlyMsg()
        totalVisitor()
    }


    /**
     * @Description: secretly look
     * onItemLongClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
     * @return
     */
    private fun secretlyMsg() {
        XposedHelpers.findAndHookMethod(HookConstant.msgFragment, loader, "onItemLongClick", AdapterView::class.java,
                View::class.java, Int::class.java, Long::class.java, object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam?) {
                //find userinfo class
                val userInfo = XposedHelpers.findClassIfExists(HookConstant.userInfo, loader)
                //create userinfo
                XposedHelpers.callStaticMethod(userInfo, "m")
                //change vip_grade to 2
                XposedHelpers.findAndHookMethod(userInfo, "p", object : XC_MethodHook() {
                    override fun afterHookedMethod(param: MethodHookParam?) {
                        //login info
                        val loginResult = param!!.result
                        //change to vip
                        XposedHelpers.setIntField(loginResult, "vip_grade", 2)
                        // is toast
                        if (XposedHelpers.getAdditionalStaticField(loginResult, "toast") != 1) {
                            //Notify user
                            Toast.makeText(mContext, ResourcesProvider.extra_secretly_hint, Toast.LENGTH_SHORT).show()
                        }
                        //mark toast
                        XposedHelpers.setAdditionalStaticField(loginResult, "toast", 1)
                    }
                })

            }
        })
    }


    /**
     * @Description: totalVisitor
     * onItemLongClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
     * @return
     */
    private fun totalVisitor() {
        XposedHelpers.findAndHookConstructor(HookConstant.visitorFragment, loader, object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam?) {
                //find userinfo class
                val userInfo = XposedHelpers.findClassIfExists(HookConstant.userInfo, loader)
                //create userinfo
                XposedHelpers.callStaticMethod(userInfo, "m")
                //change vip_grade to 2
                XposedHelpers.findAndHookMethod(userInfo, "p", object : XC_MethodHook() {
                    override fun afterHookedMethod(param: MethodHookParam?) {
                        //login info
                        val loginResult = param!!.result
                        //change to vip
                        XposedHelpers.setIntField(loginResult, "vip_grade", 2)
                    }
                })
            }
        })
    }
}