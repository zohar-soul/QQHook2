package com.dhb.qqhook;


import com.dhb.qqhook.hooks.Qq;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Xposed入口
 * Created by DHB on 2015/12/17.
 */
public class QqMod implements IXposedHookZygoteInit ,IXposedHookLoadPackage {

    private static final String TAG = "QqMod";

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {


    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam param) throws Throwable {
        Qq.handleLoadPackage(param);

        if (param.packageName.equals(QqMod.class.getPackage().getName())){
            XposedHelpers.findAndHookMethod("com.dhb.qqhook.activities.MainActivity",param.classLoader,
                    "isMod", XC_MethodReplacement.returnConstant(true));
        }
    }
}
