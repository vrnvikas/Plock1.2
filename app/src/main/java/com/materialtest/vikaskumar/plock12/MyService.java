package com.materialtest.vikaskumar.plock12;

import android.app.ActivityManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Vikas Kumar on 27-08-2015.
 */
public class MyService extends Service {


    BroadcastReceiver mReceiver;
    public String pkgNameBack;
    String topPkgName;
    public boolean isLocked = true;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    @Override
    public void onStart(Intent intent, int startId) {

        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        MyThread thread = new MyThread(this);
        thread.start();


        return START_STICKY;
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    class MyThread extends Thread{

        Context context;
        String pkgName;
        MyService service;


        public MyThread(Context context){
            this.context = context;
        }

        @Override
        public void run() {
            service = new MyService();
            while (true) {



                pkgName = service.topPkgName;


                /*
                ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);

                List<ActivityManager.RecentTaskInfo> l = am.getRecentTasks(1, ActivityManager.RECENT_WITH_EXCLUDED);
                Iterator<ActivityManager.RecentTaskInfo> i = l.iterator();

                PackageManager pm = context.getPackageManager();

                while (i.hasNext()) {
                    try {
                        Intent intent = i.next().baseIntent;
                        List<ResolveInfo> list = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                        CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(list.get(0).activityInfo.packageName, PackageManager.GET_META_DATA));
                        pkgName =  list.get(0).activityInfo.packageName;
                        //appName = c.toString();
                        // Toast.makeText(context, "Application name: " + c.toString(), Toast.LENGTH_LONG).show();
                        //Log.d("Activity Name", c.toString());
                        //Log.d("Pakage Nmae", pkgName);

                    } catch (Exception e) {
                       // Toast.makeText(context, "Application name not found: " + e.toString(), Toast.LENGTH_LONG).show();
                    }

                }
                */

                if(pkgName.equals("com.adobe.reader")){
                    if(isLocked = true) {
                        PackageManager pm = context.getPackageManager();
                        Intent intent = pm.getLaunchIntentForPackage("com.materialtest.vikaskumar.plock12");
                        if (intent != null) {
                            startActivity(intent);
                        }
                    }



                }




                try {
                    currentThread().sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }


    }

    public String topActivity(){

        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        PackageManager pm = this.getPackageManager();
        List<ActivityManager.RecentTaskInfo> l = am.getRecentTasks(1, ActivityManager.RECENT_WITH_EXCLUDED);
        Iterator<ActivityManager.RecentTaskInfo> i = l.iterator();

        while (i.hasNext()) {
            try {
                Intent intent = i.next().baseIntent;
                List<ResolveInfo> list = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(list.get(0).activityInfo.packageName, PackageManager.GET_META_DATA));
                topPkgName =  list.get(0).activityInfo.packageName;
                //appName = c.toString();
                // Toast.makeText(context, "Application name: " + c.toString(), Toast.LENGTH_LONG).show();
                //Log.d("Activity Name", c.toString());
                //Log.d("Pakage Nmae", pkgName);

            } catch (Exception e) {
                // Toast.makeText(context, "Application name not found: " + e.toString(), Toast.LENGTH_LONG).show();
            }

        }
        return topPkgName;
    }

}
