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
 *  private void a(final ChattingModel chattingModel, final int n, final TextView textView)
 */
class MessageModule(loader: ClassLoader, mContext: Context) : BaseModule(loader, mContext) {

    fun hookMessage() {
        convertRecallMessage()
        convertFlashPic()
        convertNotify()
        //reverseMessage()
        messageInfo()
    }


    private fun convertRecallMessage() {
        XposedHelpers.findAndHookMethod(HookConstant.chatFragment, loader, "onMsgDataChanged", List::class.java, object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam?) {
                val data = param!!.args[0] as List<Any>
                data.forEach {
                    val isSelf = XposedHelpers.callMethod(it, "isFromSelf")
                    if (isSelf.toString() != "true") {
                        val type = XposedHelpers.getShortField(it, "msgType")
                        if (type == 55.toShort()) {
                            val content = XposedHelpers.getObjectField(it, "msgContent")?.toString()
                            if (TextUtils.isEmpty(content!!)) {
                                if (content.contains("blued-burn")) {
                                    XposedHelpers.setShortField(it, "msgType", 24)
                                } else if (content.contains("blued-chatfiles") && (content.contains("jpg") || content.contains("png"))) {
                                    XposedHelpers.setShortField(it, "msgType", 2)
                                } else if (content.contains("blued-chatfiles") && (content.contains("mp3"))) {
                                    XposedHelpers.setShortField(it, "msgType", 3)
                                } else {
                                    XposedHelpers.setShortField(it, "msgType", 1)
                                }
                            }
                            XposedHelpers.setAdditionalInstanceField(it, "notify", "He tried to recall this message.")
                        }

                        //                        else if (type == 55.toShort()) {
//                            val msgContent = XposedHelpers.getObjectField(it, "msgContent")?.toString()
//                            XposedBridge.log("show message====msgContent = $msgContent")
//                            var data: List<String>
//                            if (!TextUtils.isEmpty(msgContent) && msgContent!!.contains(HookConstant.key)) {
//                                data = msgContent.split(HookConstant.key)
//                                XposedHelpers.setShortField(it, "msgType", data[1].toShort())
//                                XposedHelpers.setObjectField(it, "msgContent", data[0])
//                                if (TextUtils.isEmpty(data[0])) {
//                                    XposedHelpers.setAdditionalInstanceField(it, "notify", "He has recalled the message before you got it")
//                                } else {
//                                    XposedHelpers.setAdditionalInstanceField(it, "notify", "He tried to recall this message")
//                                }
//
//                            }
//
//                        }
                    }
                }
            }
        })
    }

    /**
     * mark this message last type
     */
    private fun reverseMessage() {
        XposedHelpers.findAndHookConstructor(HookConstant.chatModel, loader, XposedHelpers.findClassIfExists(HookConstant.chatModel, loader), object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam?) {
                val message = param!!.args[0]
                val type = XposedHelpers.getIntField(message, "msgType")
                var content = XposedHelpers.getObjectField(message, "msgContent")?.toString()
                if (content != null && TextUtils.isEmpty(content)) {
                    content = HookConstant.key
                } else {
                    content += HookConstant.key
                }
                content += type
                XposedHelpers.setObjectField(message, "msgContent", content)
                XposedBridge.log("copy message====msgContent = ${XposedHelpers.getObjectField(message, "msgContent")}")
            }
        })
    }


    /**
     * convert flash pic to normal pic
     */
    private fun convertFlashPic() {
        XposedHelpers.findAndHookMethod(HookConstant.chatFragment, loader, "onMsgDataChanged", List::class.java, object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam?) {
                val data = param!!.args[0] as List<Any>
                data.forEach {
                    val isSelf = XposedHelpers.callMethod(it, "isFromSelf")
                    if (isSelf.toString() != "true") {
                        val type = XposedHelpers.getShortField(it, "msgType")
                        if (type == 24.toShort()) {
                            val instance = XposedHelpers.callStaticMethod(XposedHelpers.findClass(HookConstant.chatHelpler, loader), "a")
                            val flashPath = XposedHelpers.callMethod(instance, "a", it).toString()
                            XposedHelpers.setShortField(it, "msgType", 2)
                            XposedHelpers.setObjectField(it, "msgContent", flashPath)
                            XposedHelpers.setAdditionalInstanceField(it, "notify", "This picture is converted from a \"Snaps\".")
                        }
                    }
                }
            }
        })
    }

    /**
     * create some customer's notify
     */
    private fun convertNotify() {
        XposedHelpers.findAndHookMethod(HookConstant.msgAdapter, loader, "a", Int::class.java, View::class.java, ViewGroup::class.java, object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam?) {
                val root = param!!.result as ViewGroup
                if (root.findViewWithTag(0x0000001) != null) {
                    root.removeView(root.findViewWithTag(0x0000001))
                }
                val data = XposedHelpers.getObjectField(param.thisObject, "a") as List<Any>
                val index = param.args[0] as Int
                val message = data[index]
                val notifyContent = XposedHelpers.getAdditionalInstanceField(message, "notify")?.toString()
                if (!notifyContent.isNullOrEmpty()) {
                    val notify = MessageHelper.makeNotifyTextView(mContext, notifyContent!!)
                    root.addView(notify)
                }
                param.result = root
            }
        })
    }

    private fun messageInfo() {
        val chatModel = XposedHelpers.findClass(HookConstant.chatModel, loader)
        XposedHelpers.findAndHookMethod(HookConstant.msgAdapter, loader, "a",
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
