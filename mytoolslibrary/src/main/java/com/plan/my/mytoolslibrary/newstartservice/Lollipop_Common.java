package com.plan.my.mytoolslibrary.newstartservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.List;

/**
 * 5.0之后startService的新特性
 *
 */
public class Lollipop_Common {

    /**
     * 解决
     * 有些时候我们使用Service的时需要采用隐私启动的方式，但是Android 5.0一出来后，
     * 其中有个特性就是Service Intent  must be explitict，也就是说从Lollipop开始，
     * service服务必须采用显示方式启动。
     *
     * （官方推荐用法）
     * Intent mIntent = new Intent();
     * mIntent.setAction("XXX.XXX.XXX");//你定义的service的action
     * mIntent.setPackage(getPackageName());//这里你需要设置你应用的包名
     * context.startService(mIntent);
     *
     *
     * （将隐式启动转换为显示启动：--参考地址：http://stackoverflow.com/a/26318757/1446466）
     * Intent mIntent = new Intent();
     * mIntent.setAction("XXX.XXX.XXX");
     * Intent eintent = new Intent(getExplicitIntent(mContext,mIntent));
     * context.startService(eintent);
     *
     *
     *          (bindService)
     *   Intent intent = new Intent(PLAYER_SERVICE_ACTION);
     *   Intent eintent = new Intent(Lollipop_Common.getExplicitIntent(this.mContext,intent));(需要加上的方法)
     *   mContext.bindService(eintent, mConn, Context.BIND_AUTO_CREATE);
     * @param context
     * @param implicitIntent
     * @return
     */
    public static Intent getExplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);
        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }
        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);
        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);
        // Set the component to be explicit
        explicitIntent.setComponent(component);
        return explicitIntent;
    }


}
