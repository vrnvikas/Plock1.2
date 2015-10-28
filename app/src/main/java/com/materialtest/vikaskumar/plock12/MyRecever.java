package com.materialtest.vikaskumar.plock12;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Vikas Kumar on 27-08-2015.
 */
public class MyRecever extends BroadcastReceiver {

    String[] list = {"com.adobe.reader","com.materialtest.vikaskumar.databaseapp","cn.xender"};

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("com.adobe.reader")){
            Intent intent1 = new Intent(context,LockScreen.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
        }
    }
}
