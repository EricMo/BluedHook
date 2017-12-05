package com.conch.bluedhook.common;

import java.lang.reflect.Field;

import de.robv.android.xposed.XposedBridge;

/**
 * Created by Benjamin on 2017/12/5.
 */

public class ReflectionUtils {
    public static void getFieldsValue(Object obj) {
        Field[] fields = obj.getClass().getFields();
        /**
         * 基本类型、包装类型、String类型
         */
        String[] types = {"java.lang.Integer",
                "java.lang.Double",
                "java.lang.Float",
                "java.lang.Long",
                "java.lang.Short",
                "java.lang.Byte",
                "java.lang.Boolean",
                "java.lang.Character",
                "java.lang.String",
                "int", "double", "long", "short", "byte", "boolean", "char", "float"};
        for (Field f : fields) {
            f.setAccessible(true);
            try {
                for (String str : types) {
                    if (f.getType().getName().equals(str)) {
                        XposedBridge.log("字段：" + f.getName() + " 类型为：" + f.getType().getName() + " 值为：" + f.get(obj));
                    }
                }
            } catch (IllegalArgumentException e) {
                XposedBridge.log(e.toString());
            } catch (IllegalAccessException e) {
                XposedBridge.log(e.toString());
            }
        }
    }
}
