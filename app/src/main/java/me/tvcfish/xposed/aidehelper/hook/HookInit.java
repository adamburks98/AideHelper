package me.tvcfish.xposed.aidehelper.hook;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import me.tvcfish.xposed.aidehelper.BuildConfig;
import me.tvcfish.xposed.util.XHelper;
import me.tvcfish.xposed.util.XLog;

public class HookInit implements IXposedHookLoadPackage {

  @Override
  public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
    final String packageName = lpparam.packageName;
    //初始化
    XHelper.init(lpparam,BuildConfig.APPLICATION_ID);

    //判断是否启用模块
    if (packageName.equals(BuildConfig.APPLICATION_ID)) {
      EnabledHookModel.INSTANCE.startHook();
      return;
    }

    if (!packageName.equals("com.aide.ui")) {
      return;
    }

    findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
      @Override
      protected void afterHookedMethod(MethodHookParam param) throws Throwable {
        super.afterHookedMethod(param);
        //解锁高级版
        UnlockPremium.INSTANCE.startHook();
        //禁用自动旋转
        AutoRotate.INSTANCE.startHook();
        //符号栏
        SymbolBar.INSTANCE.startHook();
        //搜索界面
        SearchPage.INSTANCE.startHook();
        //Action
        ActionBarIcon.INSTANCE.startHook();
        //中文补全
        AutoCompletion.INSTANCE.startHook();
        //Gradle补全拓展
        GradleCompletionExpand.INSTANCE.startHook();
        //版本切换
        SwitchVersion.INSTANCE.startHook();
      }
    });
  }
}
