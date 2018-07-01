package com.conch.bluedhook.module

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import com.conch.bluedhook.common.*
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import java.lang.StringBuilder

/**
 * Created by Benjamin on 2017/11/23.
 * com.blued.android.chat.core.worker.chat.a
 * tv_notify_text
 * tv_safe_notify
 *  private void a(final ChattingModel chattingModel, final int n, final TextView textView)
 */
class MessageModule(loader: ClassLoader, mContext: Context) : BaseModule(loader, mContext) {

    fun hookMessage() {
        hookReceiveRetractMsg()
        convertRetractMsgToNormal()
        convertFlashPic()
        convertNotify()
//        unlockSelf()
        // messageInfo()
    }


    /**
     * receive the retract command，q.h=55,q.d=msgId
     */
    private fun hookReceiveRetractMsg() {
        val q = XposedHelpers.findClass("com.blued.android.chat.core.pack.q", loader)
        XposedHelpers.findAndHookMethod("com.blued.android.chat.core.worker.chat.a", loader, "a", q, object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam?) {
                val q = param!!.args[0]
                XposedBridge.log("j:${XposedHelpers.callMethod(q, "toString")}")
                val msgId = XposedHelpers.getObjectField(q, "d")
                //get msgType
                val type = XposedHelpers.getLongField(q, "h")
                //if msgType is 55.so this is a recall message
                if (type == 55.toLong()) {
                    //Find db,we need do something with database
                    val chatManager = XposedHelpers.findClass(HookConstant.chatManager, loader)
                    val dbOperImpl = XposedHelpers.getStaticObjectField(chatManager, "dbOperImpl")
                    //check the session's msgList
                    var sessionKey = XposedHelpers.callStaticMethod(XposedHelpers.findClass("com.blued.android.chat.data.SessionHeader", loader), "getSessionKey", XposedHelpers.getIntField(q, "b"), XposedHelpers.getLongField(q, "c"))
                    val g = XposedHelpers.getObjectField(param.thisObject, "g") as java.util.Map<String, Any>
                    synchronized(g) {
                        sessionKey = g[sessionKey]
                        if (sessionKey != null) {
                            val msgList = XposedHelpers.getObjectField(sessionKey, "_msgList") as List<*>
                            val msgData = msgList.find {
                                XposedHelpers.getObjectField(it, "msgId") == msgId
                            }
                            if (msgData != null) {
                                markOriginalMsgType(msgData, dbOperImpl)
                            }
                        }
                    }
                    //check the database's msgList
                    val chatModel = XposedHelpers.callMethod(dbOperImpl, "findMsgData",
                            XposedHelpers.getObjectField(q, "b"),
                            XposedHelpers.getObjectField(q, "c"),
                            XposedHelpers.getObjectField(q, "d"),
                            0L
                    )
                    markOriginalMsgType(chatModel, dbOperImpl)
                }
            }
        })
    }

    /**
     * mark original message type to content
     * like:{【message content】+【HookConstant.key】+【original message type】}
     */
    private fun markOriginalMsgType(chatModel: Any?, dbOperImpl: Any): Any? {
        val msgType = XposedHelpers.getShortField(chatModel, "msgType")
        val `object` = XposedHelpers.getObjectField(chatModel, "msgContent")
        val content = if (`object` == null) {
            StringBuilder()
        } else {
            StringBuilder(`object`.toString())
        }
        content.append(HookConstant.key)
        content.append(msgType)
        XposedHelpers.setObjectField(chatModel, "msgContent", content.toString())
        ReflectionUtils.getFieldsValue(chatModel)
        XposedHelpers.callMethod(dbOperImpl, "updateChattingModel", chatModel)
        return chatModel
    }

    /**
     * convert the recall message to normal message
     */
    private fun convertRetractMsgToNormal() {
        XposedHelpers.findAndHookMethod(HookConstant.chatFragment, loader, "onMsgDataChanged", List::class.java, object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam?) {
                val data = param!!.args[0] as List<Any>
                data.forEach {
                    val isSelf = XposedHelpers.callMethod(it, "isFromSelf")
                    if (isSelf.toString() != "true") {
                        val type = XposedHelpers.getShortField(it, "msgType")
                        if (type == 55.toShort()) {
                            val content = XposedHelpers.getObjectField(it, "msgContent")?.toString()
                            if (!TextUtils.isEmpty(content!!)) {
                                if (content.contains(HookConstant.key)) {
                                    val data = content.split(HookConstant.key)
                                    XposedHelpers.setObjectField(it, "msgContent", data[0])
                                    XposedHelpers.setShortField(it, "msgType", data[1].toShort())
                                } else if (content.contains("blued-burn")) {
                                    XposedHelpers.setShortField(it, "msgType", 24)
                                } else if (content.contains("blued-chatfiles") && (content.contains("jpg") || content.contains("png"))) {
                                    XposedHelpers.setShortField(it, "msgType", 2)
                                } else if (content.contains("blued-chatfiles") && (content.contains("mp3"))) {
                                    XposedHelpers.setShortField(it, "msgType", 3)
                                } else {
                                    XposedHelpers.setShortField(it, "msgType", 1)
                                }
                            }
                            XposedHelpers.setAdditionalInstanceField(it, "notify", NotifyConstant.getRECALL_MESSAGE())
                        }
                    }
                }
            }
        })
    }

    /**
     * convert flash  to normal
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
                            XposedHelpers.setAdditionalInstanceField(it, "notify", NotifyConstant.getRECALL_BURNING_PIC())
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
                val notifyType = XposedHelpers.getAdditionalInstanceField(message, "notify")?.toString()?.toInt()
                when (notifyType) {
                    NotifyConstant.getRECALL_MESSAGE() -> {
                        val notify = LayoutHelper.makeNotifyTextView(mContext, ResourcesProvider.extra_recall_hint)
                        root.addView(notify)
                    }
                    NotifyConstant.getRECALL_BURNING_PIC() -> {
                        val notify = LayoutHelper.makeNotifyTextView(mContext, ResourcesProvider.extra_burning_pic_hint)
                        root.addView(notify)
                    }
                }
                param.result = root
            }
        })
    }


    private fun unlockSelf() {
        XposedHelpers.findAndHookMethod(HookConstant.albumManager, loader, "a",
                Boolean::class.java, List::class.java, object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam?) {
                super.afterHookedMethod(param)
                val result = param?.result as List<*>
                result.forEach {
                    ReflectionUtils.getFieldsValue(it)
                }
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
