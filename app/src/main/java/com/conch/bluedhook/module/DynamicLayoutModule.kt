package com.conch.bluedhook.module

import android.annotation.SuppressLint
import android.content.Context
import android.media.Image
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.conch.bluedhook.common.HookConstant
import com.conch.bluedhook.common.LayoutHelper
import com.conch.bluedhook.common.ReflectionUtils
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers

/**
 * @Description modify layout(u can see [LayoutModule])
 * @Author Benjamin
 * @CreateDate 2018-05-29 14:42
 **/
class DynamicLayoutModule(loader: ClassLoader, mContext: Context) : BaseModule(loader, mContext) {
    fun layout() {
        addMoreCol()
        addSelfDoor()
    }

    private fun addMoreCol() {
        XposedHelpers.findAndHookMethod(HookConstant.mineFragment, loader, "b", List::class.java, object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam?) {
                val classZ = param!!.thisObject.javaClass
                val nField = classZ.getDeclaredField("V")
                nField.isAccessible = true
                val data = (nField.get(param.thisObject) as MutableList<Any>)
                val col = data.find {
                    val id = XposedHelpers.getObjectField(it, "id")
                    if (id != null) {
                        TextUtils.equals("-1", id as String)
                    } else {
                        false
                    }
                }
                if (col == null) {
                    val clazz = XposedHelpers.findClass(HookConstant._more_columns, loader)
                    val c = XposedHelpers.findConstructorBestMatch(clazz, Int::class.java, Int::class.java)
                    val column = c.newInstance(2130838373, 0)
                    XposedHelpers.setObjectField(column, "title", "打开设置界面")
                    XposedHelpers.setBooleanField(column, "isLocal", false)
                    //this is a flag
                    XposedHelpers.setObjectField(column, "id", "-1")
                    data.add(column)
                }
            }
        })
    }

    private fun addSelfDoor() {
        val viewHolder = XposedHelpers.findClass(HookConstant.colViewHolder, loader)
        XposedHelpers.findAndHookMethod(HookConstant.colAdapter, loader, "a",
                viewHolder,
                Int::class.java,
                object : XC_MethodHook() {
                    @SuppressLint("ResourceType")
                    override fun afterHookedMethod(param: MethodHookParam?) {
                        val holder = param!!.args[0]
                        val index = param.args[1] as Int
                        val classZ = param!!.thisObject.javaClass
                        val nField = classZ.getDeclaredField("d")
                        nField.isAccessible = true
                        val data = (nField.get(param.thisObject) as MutableList<*>)
                        val item = data[index]

                        val id = XposedHelpers.getObjectField(item, "id")
                        if (id != null && TextUtils.equals("-1", id as String)) {
                            val o = XposedHelpers.getObjectField(holder, "o") as ImageView
                            //icon_more_setting
                            o.setImageResource(2130838391)
                            val n = XposedHelpers.getObjectField(holder, "n") as View
                            n.setOnClickListener {
                                LayoutHelper.openSelfModule(mContext)
                            }
                        }
                    }
                })
    }
}