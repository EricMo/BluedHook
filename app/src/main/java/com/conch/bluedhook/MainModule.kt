package com.conch.bluedhook


import android.content.res.XModuleResources
import com.conch.bluedhook.common.HookConstant
import com.conch.bluedhook.common.ResourcesProvider
import com.conch.bluedhook.module.AdsModule
import com.conch.bluedhook.module.ApplicationModule
import com.conch.bluedhook.module.LayoutModule
import com.conch.bluedhook.module.MessageModule
import de.robv.android.xposed.IXposedHookInitPackageResources
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.callbacks.XC_InitPackageResources
import de.robv.android.xposed.callbacks.XC_LoadPackage


/**
 * Created by Benjamin on 2017/7/3.
 */
class MainModule : IXposedHookLoadPackage, IXposedHookZygoteInit, IXposedHookInitPackageResources {

    private var MODULE_PATH: String? = null


    /**
     * first load
     */
    override fun initZygote(startupParam: IXposedHookZygoteInit.StartupParam?) {
        MODULE_PATH = startupParam?.modulePath
    }


    /**
     * hook class
     */
    @Throws(Throwable::class)
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.appInfo == null || lpparam.packageName != HookConstant.processName) {
            return
        }
        ApplicationModule.hookApplicationContext(lpparam, { mContext, mClassLoader ->
            //ad Module
            AdsModule(mClassLoader, mContext).removeAds()
            //Message Module
            MessageModule(mClassLoader, mContext).hookMessage()
        })
    }


    /**
     * hook layout
     */
    override fun handleInitPackageResources(resparam: XC_InitPackageResources.InitPackageResourcesParam?) {
        if (HookConstant.processName != resparam?.packageName) {
            return
        }
        val xModuleRes = XModuleResources.createInstance(MODULE_PATH, resparam.res)
        //load string res
        ResourcesProvider.initProvider(xModuleRes)
        //u can hook layout and use res
        LayoutModule(xModuleRes, resparam).hookLayout()
    }


}
