package com.aware.plugin.dummyaware;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.aware.Aware;
import com.aware.Aware_Preferences;
import com.aware.utils.Aware_Plugin;
import com.aware.plugin.dummyaware.Provider.*;

public class Plugin extends Aware_Plugin {

    public static final String ACTION_AWARE_PLUGIN_DUMMYAWARE = "ACTION_AWARE_PLUGIN_DUMMYAWARE";

    public static final String EXTRA_DATA = "data";

    //context
    //private static ContextProducer sContext;

    public Thread dummy_thread = new Thread() {
        public void run() {
            while (Aware.getSetting(getApplicationContext(), Settings.STATUS_PLUGIN_DUMMYAWARE).equals("true")) {
                //check if data is there
                Cursor BC = getApplicationContext().getContentResolver().query(Applications_Crashes.CONTENT_URI, null, null, null, Applications_Crashes.TIMESTAMP + " DESC LIMIT 1");
                if (BC != null && BC.moveToFirst()) {
                    //data is there

                    Log.d("DUMMAYAWARE","31");

                }
                if(BC == null)
                {
                    //data is not there, insert
                    createApplicationsCrashes(1462685057894L, "a2c07971-db0b-41b7-8f09-c8b26a942358",
                            "com.klakegg", "Klakegg", 19L, "database error", "database written by Klakegg", 0, 0);
                    //close
                    break;
                }
                if (BC != null && !BC.isClosed()) {
                    BC.close();
                    //close
                    break;
                }

                try {
                    Thread.sleep(6000);
                    //detect once every 6 secs
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };


                @Override
    public void onCreate() {
        super.onCreate();

        TAG = "AWARE::"+getResources().getString(R.string.app_name);

        //Activate programmatically any sensors/plugins you need here
        //e.g., Aware.setSetting(this, Aware_Preferences.STATUS_ACCELEROMETER,true);
        //NOTE: if using plugin with dashboard, you can specify the sensors you'll use there.

        //Any active plugin/sensor shares its overall context using broadcasts
        /*
        sContext = new ContextProducer() {
            @Override
            public void onContext() {
                //Broadcast your context here
                ContentValues data = new ContentValues();
                data.put(Dummy_AWARE_Data_Applications_Crashes.TIMESTAMP, System.currentTimeMillis());
                data.put(Dummy_AWARE_Data_Applications_Crashes.DEVICE_ID, Aware.getSetting(getApplicationContext(), Aware_Preferences.DEVICE_ID));
                //send to AWARE
                Intent context_unlock = new Intent();
                context_unlock.setAction(ACTION_AWARE_PLUGIN_DUMMYAWARE);
                context_unlock.putExtra(EXTRA_DATA,data);
                sendBroadcast(context_unlock);
                getContentResolver().insert(Dummy_AWARE_Data_Applications_Crashes.CONTENT_URI, data);
            }
        };
        CONTEXT_PRODUCER = sContext;
*/
        //Add permissions you need (Support for Android M) e.g.,
        //REQUIRED_PERMISSIONS.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        //To sync data to the server, you'll need to set this variables from your ContentProvider


        DATABASE_TABLES = Provider.DATABASE_TABLES;
        TABLES_FIELDS = Provider.TABLES_FIELDS;

        CONTEXT_URIS = new Uri[]{ Accelerometer_Sensor.CONTENT_URI , Accelerometer_Data.CONTENT_URI,
                Applications_Foreground.CONTENT_URI, Applications_History.CONTENT_URI, Applications_Notifications.CONTENT_URI, Applications_Crashes.CONTENT_URI,
                Aware_Device.CONTENT_URI, Aware_Settings.CONTENT_URI, Aware_Plugins.CONTENT_URI, Barometer_Sensor.CONTENT_URI, Barometer_Data.CONTENT_URI,
                Battery_Data.CONTENT_URI, Battery_Discharges.CONTENT_URI, Battery_Charges.CONTENT_URI
        };

        //need to put all the providers into one provider
        //Aware.startPlugin(this, "com.aware.plugin.dummyaware");

        Log.d("DUMMAYAWARE","106");



        if (Aware.getSetting(this, "study_id").length() == 0) {
            Aware.joinStudy(this, "https://api.awareframework.com/index.php/webservice/index/719/qXL9fBibsyJl");
        }

        dummy_thread.start();

    }


    public void createApplicationsCrashes(long timestamp, String device_id, String package_name, 
                                    String application_name, long application_version, 
                                    String error_short, String error_long,
                                    int error_condition, int is_system_app) {
        ContentValues data = new ContentValues();
        data.put(Applications_Crashes.TIMESTAMP, timestamp);
        data.put(Applications_Crashes.DEVICE_ID, device_id);
        data.put(Applications_Crashes.PACKAGE_NAME, package_name);
        data.put(Applications_Crashes.APPLICATION_NAME, application_name);
        data.put(Applications_Crashes.APPLICATION_VERSION, application_version);
        data.put(Applications_Crashes.ERROR_SHORT, error_short);
        data.put(Applications_Crashes.ERROR_LONG, error_long);
        data.put(Applications_Crashes.ERROR_CONDITION, error_condition);
        data.put(Applications_Crashes.IS_SYSTEM_APP, is_system_app);
        Intent applications_crashes = new Intent();
        applications_crashes.setAction(ACTION_AWARE_PLUGIN_DUMMYAWARE);
        applications_crashes.putExtra(EXTRA_DATA,data);
        sendBroadcast(applications_crashes);
        getContentResolver().insert(Applications_Crashes.CONTENT_URI, data);}
    



    //This function gets called every 5 minutes by AWARE to make sure this plugin is still running.
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //Deactivate any sensors/plugins you activated here

        //Stop plugin
        Aware.stopPlugin(this, "com.aware.plugin.dummyaware");
    }



}
