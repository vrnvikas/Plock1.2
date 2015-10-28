package com.materialtest.vikaskumar.plock12;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;

public class Plock extends AppCompatActivity {

    EditText editText1;
    EditText editText2,editText3,password;
    String appName;
    String pkgName;
    Button button1,submit;
    int pidCurrent;
    String passwordKey;
    MyService service;
    private PackageManager packageManager;
    boolean serviceRunning = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foreground);
        service = new MyService();
        editText1 = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);
        password = (EditText) findViewById(R.id.password);

        button1 = (Button) findViewById(R.id.button1);
        submit = (Button) findViewById(R.id.submit);


        //if (!(isMyServiceRunning(MyService.class))){
          //  startService(new Intent(this, MyService.class));
        //}


        pidCurrent = android.os.Process.myPid();
        passwordKey = "vikas";
        packageManager = getPackageManager();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),AllAppsActivity.class);
                startActivity(intent);



            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText() != null){
                    if(password.getText().toString().equalsIgnoreCase("vikas")){

                        service.isLocked = false;
                        Intent intent = packageManager
                                .getLaunchIntentForPackage("com.adobe.reader");

                        if (null != intent) {
                            startActivity(intent);
                        }

                    }

                    else{
                        Toast.makeText(v.getContext(),"Try Again",Toast.LENGTH_LONG).show();
                    }
                }

                else{
                    Toast.makeText(v.getContext(),"Enter Password",Toast.LENGTH_LONG).show();
                }
            }
        });



        ActivityManager am = (ActivityManager) this
                .getSystemService(ACTIVITY_SERVICE);

        List<ActivityManager.RecentTaskInfo> l = am.getRecentTasks(1,
                ActivityManager.RECENT_WITH_EXCLUDED);
        Iterator<ActivityManager.RecentTaskInfo> i = l.iterator();

        PackageManager pm = this.getPackageManager();

        while (i.hasNext()) {
            try {
                Intent intent = i.next().baseIntent;
                List<ResolveInfo> list = pm.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);

                CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(
                        list.get(0).activityInfo.packageName,
                        PackageManager.GET_META_DATA));
                pkgName =  list.get(0).activityInfo.packageName;
                    appName = c.toString();
                //Toast.makeText(this, "Application name: " + c.toString(),
                  //      Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                Toast.makeText(this,
                        "Application name not found: " + e.toString(),
                        Toast.LENGTH_LONG).show();
            }
        }

        //int pid = android.os.Process.myPid();
        editText1.setText(appName);
        editText2.setText(pkgName);
        //editText3.setText(pid);


    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(this.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_foreground, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
