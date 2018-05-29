package com.conch.bluedhook


import com.conch.bluedhook.common.HookConstant
import com.conch.bluedhook.module.AdsModule
import com.conch.bluedhook.module.ApplicationModule
import com.conch.bluedhook.module.DynamicLayoutModule
import com.conch.bluedhook.module.MessageModule
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.callbacks.XC_LoadPackage


/**
 * Created by Benjamin on 2017/7/3.
 */
class MainModule : IXposedHookLoadPackage {


    //    private var MODULE_PATH: String? = null
    //
    //
    //    /**
    //     * first load
    //     */
    //    override fun initZygote(startupParam: IXposedHookZygoteInit.StartupParam?) {
    //        MODULE_PATH = startupParam?.modulePath
    //    }

    /**
     * hook class
     */
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.appInfo == null || lpparam.packageName != HookConstant.processName) {
            return
        }
        ApplicationModule.hookApplicationContext(lpparam, { mContext, mClassLoader ->
            //ad Module
            AdsModule(mClassLoader, mContext).removeAds()
            //Message Module
            MessageModule(mClassLoader, mContext).hookMessage()
            //layout
            DynamicLayoutModule(mClassLoader, mContext).layout()
        })
    }


    //    /**
    //     * hook layout
    //     */
    //    override fun handleInitPackageResources(resparam: XC_InitPackageResources.InitPackageResourcesParam?) {
    //        //if use VirtualXposed we can't hook PackageResources
    //        if (System.getProperty("vxp") != null) {
    //            return
    //        }
    //        if (HookConstant.processName != resparam?.packageName) {
    //            return
    //        }
    //        val xModuleRes = XModuleResources.createInstance(MODULE_PATH, resparam.res)
    //        //load string res
    //        ResourcesProvider.initProvider(xModuleRes)
    //        //u can hook layout and use res
    //        LayoutModule(xModuleRes, resparam).hookLayout()
    //    }


}
