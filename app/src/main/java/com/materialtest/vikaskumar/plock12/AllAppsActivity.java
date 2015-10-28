package com.materialtest.vikaskumar.plock12;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class AllAppsActivity extends ListActivity {

    private PackageManager packageManager = null;
    private List<ApplicationInfo> applist = null;
    private ApplicationAdapter listadaptor = null;
    BroadcastReceiver mReceiver;
    MyService modulater;
    Button button;
    int pidCurrent;
    int pidBack;
    private String backApp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button1);
        modulater = new MyService();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),Plock.class);
                startActivity(intent);
                //pidBack = android.os.Process.getUidForName("com.adobe.reader");
                //pidCurrent = android.os.Process.myPid();
                //android.os.Process.killProcess(pidCurrent);
                //android.os.Process.killProcess(pidBack);




            }
        });
        packageManager = getPackageManager();

        new LoadApplications().execute();






    }


    @Override
    public void onBackPressed() {


       //pidCurrent = android.os.Process.myPid();
       //pidBack = android.os.Process.getUidForName("com.adobe.reader");
        //backApp = modulater.pkgNameBack;
        //PackageManager pm = this.getPackageManager();
        //Intent intent = pm.getLaunchIntentForPackage(backApp);
        //startActivity(intent);
       //Toast.makeText(this, "You pressed button back1 " , Toast.LENGTH_LONG).show();
       // ActivityManager am = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        //am.killBackgroundProcesses("com.adobe.reader");
        //android.os.Process.killProcess(pidBack);
        //android.os.Process.killProcess(pidCurrent);
       // finish();


        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        this.startActivity(intent);
        //finish();
        //super.onBackPressed();

    }


    /*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK){

            pidCurrent = android.os.Process.myPid();
            pidBack = android.os.Process.getUidForName("com.adobe.reader");

            Toast.makeText(this, "You pressed button back2 " , Toast.LENGTH_LONG).show();
            //android.os.Process.killProcess(pidBack);
            //android.os.Process.killProcess(pidCurrent);

        }


        return super.onKeyDown(keyCode, event);
    }
    */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = true;

        return result;
    }



    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        ApplicationInfo app = applist.get(position);
        try {
            Intent intent = packageManager
                    .getLaunchIntentForPackage(app.packageName);

            if (null != intent) {
                startActivity(intent);
            }
        } catch (ActivityNotFoundException e) {
            Toast.makeText(AllAppsActivity.this, e.getMessage(),
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(AllAppsActivity.this, e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private List<ApplicationInfo> checkForLaunchIntent(List<ApplicationInfo> list) {
        ArrayList<ApplicationInfo> applist = new ArrayList<ApplicationInfo>();
        for (ApplicationInfo info : list) {
            try {
                if (null != packageManager.getLaunchIntentForPackage(info.packageName)) {
                    applist.add(info);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return applist;
    }

    private class LoadApplications extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progress = null;

        @Override
        protected Void doInBackground(Void... params) {
            applist = checkForLaunchIntent(packageManager.getInstalledApplications(PackageManager.GET_META_DATA));
            listadaptor = new ApplicationAdapter(AllAppsActivity.this,
                    R.layout.snippet_list_row, applist);

            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(Void result) {
            setListAdapter(listadaptor);
            progress.dismiss();
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(AllAppsActivity.this, null,
                    "Loading application info...");
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
