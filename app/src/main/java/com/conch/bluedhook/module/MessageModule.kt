package com.conch.bluedhook.module

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import com.conch.bluedhook.common.HookConstant
import com.conch.bluedhook.common.MessageHelper
import com.conch.bluedhook.common.ReflectionUtils
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers

/**
 * Created by Benjamin on 2017/11/23.
 * com.blued.android.chat.core.worker.chat.a
 * tv_notify_text
 * tv_safe_notify
 */
class MessageModule(loader: ClassLoader, mContext: Context) : BaseModule(loader, mContext) {

    fun hookMessage() {
        messageInfo()
        filterChattingMsgContent()
        convertNotify()
    }

    /**
     * convert flash pic to normal pic
     */
    private fun filterChattingMsgContent() {
        XposedHelpers.findAndHookMethod("com.soft.blued.ui.msg.MsgChattingFragment", loader, "onMsgDataChanged", List::class.java, object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam?) {
                val data = param!!.args[0] as List<Any>
                data.forEach {
                    val isSelf = XposedHelpers.callMethod(it, "isFromSelf")
                    if (isSelf.toString() != "true") {
                        val type = ReflectionUtils.getFieldValue(it, "msgType")
                        if (type.toString() == "24") {
                            val instance = XposedHelpers.callStaticMethod(XposedHelpers.findClass(HookConstant.processName + HookConstant.chatHelpler, loader), "a")
                            val flashPath = XposedHelpers.callMethod(instance, "a", it).toString()
                            XposedHelpers.setShortField(it, "msgType", 2)
                            XposedHelpers.setObjectField(it, "msgContent", flashPath)
//                            XposedHelpers.setAdditionalInstanceField(it, "notify", "This picture is converted from a \"Snaps\".")
                            XposedHelpers.setAdditionalInstanceField(it, "notify", "该图片由\"闪照\"解析展示,且行且珍惜")
                        }
                    }
                }
            }
        })
    }

    private fun convertNotify() {
        XposedHelpers.findAndHookMethod(HookConstant.processName + HookConstant.msgAdapter, loader, "a", Int::class.java, View::class.java, ViewGroup::class.java, object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam?) {
                val root = param!!.result as ViewGroup
                if (root.findViewWithTag(0x0000001) != null) {
                    root.removeView(root.findViewWithTag(0x0000001))
                }
                val data = XposedHelpers.getObjectField(param.thisObject, "a") as List<Any>
                val message = data[param.args[0] as Int]
                val notifyContent = XposedHelpers.getAdditionalInstanceField(message, "notify").toString()
                if (!TextUtils.isEmpty(notifyContent)) {
                    val notify = MessageHelper.makeNotifyTextView(mContext, notifyContent)
                    root.addView(notify)
                }
                param.result = root
            }
        })
    }

    private fun messageInfo() {
        val chatModel = XposedHelpers.findClass(HookConstant.chatModel, loader)
        XposedHelpers.findAndHookMethod(HookConstant.processName + HookConstant.msgAdapter, loader, "a",
                chatModel,
                Int::class.java, ViewGroup::class.java, object : XC_MethodHook() {
            override fun beforeHookedMethod(param: XC_MethodHook.MethodHookParam?) {
                XposedBridge.log("========This is a item start============")
                ReflectionUtils.getFieldsValue(param!!.args[0])
                XposedBridge.log("========This is a item end============")
            }
        })
    }
}
