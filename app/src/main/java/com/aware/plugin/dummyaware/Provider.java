package com.aware.plugin.dummyaware;

/**
 * Created by Comet on 27/05/16.
 */


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.Environment;
import android.provider.BaseColumns;
import android.util.Log;

import com.aware.Accelerometer;
import com.aware.Aware;
import com.aware.BuildConfig;
import com.aware.utils.DatabaseHelper;

import java.io.File;
import java.util.HashMap;

public class Provider extends ContentProvider {
    public static final int DATABASE_VERSION = 1;
    /**
     * Provider authority: com.aware.plugin.dummyaware.provider.dummyaware
     */
    public static String AUTHORITY = "com.aware.plugin.dummyaware.provider.dummyaware";
    //store activity data

    // ContentProvider query paths
    //accelerometer, app
    //
    private static final int ACCEL_DEV = 1;
    private static final int ACCEL_DEV_ID = 2;
    private static final int ACCEL_DATA = 3;
    private static final int ACCEL_DATA_ID = 4;
    private static final int FOREGROUND = 5;
    private static final int FOREGROUND_ID = 6;
    private static final int APPLICATIONS = 7;
    private static final int APPLICATIONS_ID = 8;
    private static final int NOTIFICATIONS = 9;
    private static final int NOTIFICATIONS_ID = 10;
    private static final int ERROR = 11;
    private static final int ERROR_ID = 12;
    private static final int DEVICE_INFO = 13;
    private static final int DEVICE_INFO_ID = 14;
    private static final int SETTING = 15;
    private static final int SETTING_ID = 16;
    private static final int PLUGIN = 17;
    private static final int PLUGIN_ID = 18;
    private static final int SENSOR_DEV = 19;
    private static final int SENSOR_DEV_ID = 20;
    private static final int SENSOR_DATA = 21;
    private static final int SENSOR_DATA_ID = 22;
    private static final int BATTERY = 23;
    private static final int BATTERY_ID = 24;
    private static final int BATTERY_DISCHARGE = 25;
    private static final int BATTERY_DISCHARGE_ID = 26;
    private static final int BATTERY_CHARGE = 27;
    private static final int BATTERY_CHARGE_ID = 28;

    public static final String DATABASE_NAME = "dummyaware.db";


    public static final class Accelerometer_Sensor implements BaseColumns {
        private Accelerometer_Sensor() {}

        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/sensor_accelerometer");
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.aware.plugin.dummyaware.accelerometer.sensor";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.aware.plugin.dummyaware.accelerometer.sensor";

        public static final String _ID = "_id";
        public static final String TIMESTAMP = "timestamp";
        public static final String DEVICE_ID = "device_id";
        public static final String MAXIMUM_RANGE = "double_sensor_maximum_range";
        public static final String MINIMUM_DELAY = "double_sensor_minimum_delay";
        public static final String NAME = "sensor_name";
        public static final String POWER_MA = "double_sensor_power_ma";
        public static final String RESOLUTION = "double_sensor_resolution";
        public static final String TYPE = "sensor_type";
        public static final String VENDOR = "sensor_vendor";
        public static final String VERSION = "sensor_version";
    }

    public static final class Accelerometer_Data implements BaseColumns {
        private Accelerometer_Data() {
        };

        public static final Uri CONTENT_URI = Uri.parse("content://"
                + AUTHORITY + "/accelerometer");
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.aware.plugin.dummyaware.accelerometer.data";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.aware.plugin.dummyaware.accelerometer.data";

        public static final String _ID = "_id";
        public static final String TIMESTAMP = "timestamp";
        public static final String DEVICE_ID = "device_id";
        public static final String VALUES_0 = "double_values_0";
        public static final String VALUES_1 = "double_values_1";
        public static final String VALUES_2 = "double_values_2";
        public static final String ACCURACY = "accuracy";
        public static final String LABEL = "label";
    }

    public static final class Applications_Foreground implements BaseColumns {
        private Applications_Foreground() {
        };

        public static final Uri CONTENT_URI = Uri.parse("content://"
                + AUTHORITY + "/applications_foreground");
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.aware.plugin.dummyaware.applications.foreground";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.aware.plugin.dummyaware.applications.foreground";

        public static final String _ID = "_id";
        public static final String TIMESTAMP = "timestamp";
        public static final String DEVICE_ID = "device_id";
        public static final String PACKAGE_NAME = "package_name";
        public static final String APPLICATION_NAME = "application_name";
        public static final String IS_SYSTEM_APP = "is_system_app";
    }

    public static final class Applications_History implements BaseColumns {
        private Applications_History() {
        };

        public static final Uri CONTENT_URI = Uri.parse("content://"
                + AUTHORITY + "/applications_history");
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.aware.plugin.dummyaware.applications.history";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.aware.plugin.dummyaware.applications.history";

        public static final String _ID = "_id";
        public static final String TIMESTAMP = "timestamp";
        public static final String DEVICE_ID = "device_id";
        public static final String PACKAGE_NAME = "package_name";
        public static final String APPLICATION_NAME = "application_name";
        public static final String PROCESS_IMPORTANCE = "process_importance";
        public static final String PROCESS_ID = "process_id";
        public static final String END_TIMESTAMP = "double_end_timestamp";
        public static final String IS_SYSTEM_APP = "is_system_app";
    }

    public static final class Applications_Notifications implements BaseColumns {
        private Applications_Notifications() {
        };

        public static final Uri CONTENT_URI = Uri.parse("content://"
                + AUTHORITY
                + "/applications_notifications");
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.aware.plugin.dummyaware.applications.notifications";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.aware.plugin.dummyaware.applications.notifications";

        public static final String _ID = "_id";
        public static final String TIMESTAMP = "timestamp";
        public static final String DEVICE_ID = "device_id";
        public static final String PACKAGE_NAME = "package_name";
        public static final String APPLICATION_NAME = "application_name";
        public static final String TEXT = "text";
        public static final String SOUND = "sound";
        public static final String VIBRATE = "vibrate";
        public static final String DEFAULTS = "defaults";
        public static final String FLAGS = "flags";
    }

    public static final class Applications_Crashes implements BaseColumns {
        private Applications_Crashes() {
        };

        public static final Uri CONTENT_URI = Uri.parse("content://"
                + AUTHORITY + "/applications_crashes");
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.aware.plugin.dummyaware.applications.crashes";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.aware.plugin.dummyaware.applications.crashes";

        public static final String _ID = "_id";
        public static final String TIMESTAMP = "timestamp";
        public static final String DEVICE_ID = "device_id";
        public static final String PACKAGE_NAME = "package_name";
        public static final String APPLICATION_NAME = "application_name";
        public static final String APPLICATION_VERSION = "application_version";
        public static final String ERROR_SHORT = "error_short";
        public static final String ERROR_LONG = "error_long";
        public static final String ERROR_CONDITION = "error_condition";
        public static final String IS_SYSTEM_APP = "is_system_app";
    }

    public static final class Aware_Device implements BaseColumns {
        private Aware_Device() {
        };

        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/aware_device");
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.aware.plugin.dummyaware.device";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.aware.plugin.dummyaware.device";

        public static final String _ID = "_id";
        public static final String TIMESTAMP = "timestamp";
        public static final String DEVICE_ID = "device_id";
        public static final String BOARD = "board";
        public static final String BRAND = "brand";
        public static final String DEVICE = "device";
        public static final String BUILD_ID = "build_id";
        public static final String HARDWARE = "hardware";
        public static final String MANUFACTURER = "manufacturer";
        public static final String MODEL = "model";
        public static final String PRODUCT = "product";
        public static final String SERIAL = "serial";
        public static final String RELEASE = "release";
        public static final String RELEASE_TYPE = "release_type";
        public static final String SDK = "sdk";
        public static final String LABEL = "label";
    }

    public static final class Aware_Settings implements BaseColumns {
        private Aware_Settings() {
        };

        public static final Uri CONTENT_URI = Uri.parse("content://"
                + AUTHORITY + "/aware_settings");
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.aware.plugin.dummyaware.settings";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.aware.plugin.dummyaware.settings";

        public static final String SETTING_ID = "_id";
        public static final String SETTING_KEY = "key";
        public static final String SETTING_VALUE = "value";
        public static final String SETTING_PACKAGE_NAME = "package_name";
    }

    public static final class Aware_Plugins implements BaseColumns {
        private Aware_Plugins() {};

        public static final Uri CONTENT_URI = Uri.parse("content://"
                + AUTHORITY + "/aware_plugins");
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.aware.plugin.dummyaware.plugins";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.aware.plugin.dummyaware.plugins";

        public static final String PLUGIN_ID = "_id";
        public static final String PLUGIN_PACKAGE_NAME = "package_name";
        public static final String PLUGIN_NAME = "plugin_name";
        public static final String PLUGIN_VERSION = "plugin_version";
        public static final String PLUGIN_STATUS = "plugin_status";
        public static final String PLUGIN_AUTHOR = "plugin_author";
        public static final String PLUGIN_ICON = "plugin_icon";
        public static final String PLUGIN_DESCRIPTION = "plugin_description";
    }

    public static final class Barometer_Sensor implements BaseColumns {
        private Barometer_Sensor() {
        };

        public static final Uri CONTENT_URI = Uri.parse("content://"
                + AUTHORITY + "/sensor_barometer");
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.aware.plugin.dummyaware.barometer.sensor";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.aware.plugin.dummyaware.barometer.sensor";

        public static final String _ID = "_id";
        public static final String TIMESTAMP = "timestamp";
        public static final String DEVICE_ID = "device_id";
        public static final String MAXIMUM_RANGE = "double_sensor_maximum_range";
        public static final String MINIMUM_DELAY = "double_sensor_minimum_delay";
        public static final String NAME = "sensor_name";
        public static final String POWER_MA = "double_sensor_power_ma";
        public static final String RESOLUTION = "double_sensor_resolution";
        public static final String TYPE = "sensor_type";
        public static final String VENDOR = "sensor_vendor";
        public static final String VERSION = "sensor_version";
    }

    public static final class Barometer_Data implements BaseColumns {
        private Barometer_Data() {
        };

        public static final Uri CONTENT_URI = Uri.parse("content://"
                + AUTHORITY + "/barometer");
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.aware.plugin.dummyaware.barometer.data";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.aware.plugin.dummyaware.barometer.data";

        public static final String _ID = "_id";
        public static final String TIMESTAMP = "timestamp";
        public static final String DEVICE_ID = "device_id";
        public static final String AMBIENT_PRESSURE = "double_values_0";
        public static final String ACCURACY = "accuracy";
        public static final String LABEL = "label";
    }

    public static final class Battery_Data implements BaseColumns {
        private Battery_Data() {
        };

        public static final Uri CONTENT_URI = Uri.parse("content://"
                + AUTHORITY + "/battery");
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.aware.plugin.dummyaware.battery";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.aware.plugin.dummyaware.battery";

        public static final String _ID = "_id";
        public static final String TIMESTAMP = "timestamp";
        public static final String DEVICE_ID = "device_id";
        public static final String STATUS = "battery_status";
        public static final String LEVEL = "battery_level";
        public static final String SCALE = "battery_scale";
        public static final String VOLTAGE = "battery_voltage";
        public static final String TEMPERATURE = "battery_temperature";
        public static final String PLUG_ADAPTOR = "battery_adaptor";
        public static final String HEALTH = "battery_health";
        public static final String TECHNOLOGY = "battery_technology";
    }

    public static final class Battery_Discharges implements BaseColumns {
        private Battery_Discharges() {
        };

        public static final Uri CONTENT_URI = Uri.parse("content://"
                + AUTHORITY + "/battery_discharges");
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.aware.plugin.dummyaware.battery.discharges";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.aware.plugin.dummyaware.battery.discharges";

        public static final String _ID = "_id";
        public static final String TIMESTAMP = "timestamp";
        public static final String DEVICE_ID = "device_id";
        public static final String BATTERY_START = "battery_start";
        public static final String BATTERY_END = "battery_end";
        public static final String END_TIMESTAMP = "double_end_timestamp";
    }

    public static final class Battery_Charges implements BaseColumns {
        private Battery_Charges() {
        };

        public static final Uri CONTENT_URI = Uri.parse("content://"
                + AUTHORITY + "/battery_charges");
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.aware.plugin.dummyaware.battery.charges";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.aware.plugin.dummyaware.battery.charges";

        public static final String _ID = "_id";
        public static final String TIMESTAMP = "timestamp";
        public static final String DEVICE_ID = "device_id";
        public static final String BATTERY_START = "battery_start";
        public static final String BATTERY_END = "battery_end";
        public static final String END_TIMESTAMP = "double_end_timestamp";
    }



    //tables, edit
    public static final String[] DATABASE_TABLES = { "sensor_accelerometer", "accelerometer",
            "applications_foreground", "applications_history", "applications_notifications", "applications_crashes",
            "aware_device", "aware_settings", "aware_plugins",
            "sensor_barometer", "barometer",
            "battery", "battery_discharges", "battery_charges"

    };


    public static final String[] TABLES_FIELDS = {
            // accelerometer device information
            Accelerometer_Sensor._ID + " integer primary key autoincrement,"
                    + Accelerometer_Sensor.TIMESTAMP + " real default 0,"
                    + Accelerometer_Sensor.DEVICE_ID + " text default '',"
                    + Accelerometer_Sensor.MAXIMUM_RANGE + " real default 0,"
                    + Accelerometer_Sensor.MINIMUM_DELAY + " real default 0,"
                    + Accelerometer_Sensor.NAME + " text default '',"
                    + Accelerometer_Sensor.POWER_MA + " real default 0,"
                    + Accelerometer_Sensor.RESOLUTION + " real default 0,"
                    + Accelerometer_Sensor.TYPE + " text default '',"
                    + Accelerometer_Sensor.VENDOR + " text default '',"
                    + Accelerometer_Sensor.VERSION + " text default '',"
                    + "UNIQUE (" + Accelerometer_Sensor.TIMESTAMP + ","
                    + Accelerometer_Sensor.DEVICE_ID + ")",

            // accelerometer data
            Accelerometer_Data._ID + " integer primary key autoincrement,"
                    + Accelerometer_Data.TIMESTAMP + " real default 0,"
                    + Accelerometer_Data.DEVICE_ID + " text default '',"
                    + Accelerometer_Data.VALUES_0 + " real default 0,"
                    + Accelerometer_Data.VALUES_1 + " real default 0,"
                    + Accelerometer_Data.VALUES_2 + " real default 0,"
                    + Accelerometer_Data.ACCURACY + " integer default 0,"
                    + Accelerometer_Data.LABEL + " text default '',"
                    + "UNIQUE (" + Accelerometer_Data.TIMESTAMP + ","
                    + Accelerometer_Data.DEVICE_ID + ")",

            Applications_Foreground._ID + " integer primary key autoincrement,"
                    + Applications_Foreground.TIMESTAMP + " real default 0,"
                    + Applications_Foreground.DEVICE_ID + " text default '',"
                    + Applications_Foreground.PACKAGE_NAME
                    + " text default '',"
                    + Applications_Foreground.APPLICATION_NAME
                    + " text default '',"
                    + Applications_Foreground.IS_SYSTEM_APP
                    + " integer default 0," + "UNIQUE ("
                    + Applications_Foreground.TIMESTAMP + ","
                    + Applications_Foreground.DEVICE_ID + ")",

            // Applications
            Applications_History._ID + " integer primary key autoincrement,"
                    + Applications_History.TIMESTAMP + " real default 0,"
                    + Applications_History.DEVICE_ID + " text default '',"
                    + Applications_History.PACKAGE_NAME + " text default '',"
                    + Applications_History.APPLICATION_NAME
                    + " text default '',"
                    + Applications_History.PROCESS_IMPORTANCE
                    + " integer default 0," + Applications_History.PROCESS_ID
                    + " integer default 0,"
                    + Applications_History.END_TIMESTAMP + " real default 1,"
                    + Applications_History.IS_SYSTEM_APP
                    + " integer default 0," + "UNIQUE ("
                    + Applications_History.TIMESTAMP + ","
                    + Applications_History.DEVICE_ID + ")",

            // Notifications
            Applications_Notifications._ID
                    + " integer primary key autoincrement,"
                    + Applications_Notifications.TIMESTAMP + " real default 0,"
                    + Applications_Notifications.DEVICE_ID
                    + " text default '',"
                    + Applications_Notifications.PACKAGE_NAME
                    + " text default '',"
                    + Applications_Notifications.APPLICATION_NAME
                    + " text default ''," + Applications_Notifications.TEXT
                    + " text default ''," + Applications_Notifications.SOUND
                    + " text default ''," + Applications_Notifications.VIBRATE
                    + " text default ''," + Applications_Notifications.DEFAULTS
                    + " integer default -1," + Applications_Notifications.FLAGS
                    + " integer default -1," + "UNIQUE ("
                    + Applications_Notifications.TIMESTAMP + ","
                    + Applications_Notifications.DEVICE_ID + ")",

            // Crashes
            Applications_Crashes._ID + " integer primary key autoincrement,"
                    + Applications_Crashes.TIMESTAMP + " real default 0,"
                    + Applications_Crashes.DEVICE_ID + " text default '',"
                    + Applications_Crashes.PACKAGE_NAME + " text default '',"
                    + Applications_Crashes.APPLICATION_NAME
                    + " text default '',"
                    + Applications_Crashes.APPLICATION_VERSION
                    + " real default 0," + Applications_Crashes.ERROR_SHORT
                    + " text default ''," + Applications_Crashes.ERROR_LONG
                    + " text default '',"
                    + Applications_Crashes.ERROR_CONDITION
                    + " integer default 0,"
                    + Applications_Crashes.IS_SYSTEM_APP
                    + " integer default 0," + "UNIQUE("
                    + Applications_Crashes.TIMESTAMP + ","
                    + Applications_Crashes.DEVICE_ID + ")",

            // Device information
            Aware_Device._ID + " integer primary key autoincrement,"
                    + Aware_Device.TIMESTAMP + " real default 0,"
                    + Aware_Device.DEVICE_ID + " text default '',"
                    + Aware_Device.BOARD + " text default '',"
                    + Aware_Device.BRAND + " text default '',"
                    + Aware_Device.DEVICE + " text default '',"
                    + Aware_Device.BUILD_ID + " text default '',"
                    + Aware_Device.HARDWARE + " text default '',"
                    + Aware_Device.MANUFACTURER + " text default '',"
                    + Aware_Device.MODEL + " text default '',"
                    + Aware_Device.PRODUCT + " text default '',"
                    + Aware_Device.SERIAL + " text default '',"
                    + Aware_Device.RELEASE + " text default '',"
                    + Aware_Device.RELEASE_TYPE + " text default '',"
                    + Aware_Device.SDK + " integer default 0,"
                    + Aware_Device.LABEL + " text default '',"
                    + "UNIQUE (" + Aware_Device.TIMESTAMP + "," + Aware_Device.DEVICE_ID + ")",

            // Settings
            Aware_Settings.SETTING_ID + " integer primary key autoincrement,"
                    + Aware_Settings.SETTING_KEY + " text default '',"
                    + Aware_Settings.SETTING_VALUE + " text default '',"
                    + Aware_Settings.SETTING_PACKAGE_NAME + " text default ''",

            // Plugins
            Aware_Plugins.PLUGIN_ID + " integer primary key autoincrement,"
                    + Aware_Plugins.PLUGIN_PACKAGE_NAME + " text default '',"
                    + Aware_Plugins.PLUGIN_NAME + " text default '',"
                    + Aware_Plugins.PLUGIN_VERSION + " integer default 0,"
                    + Aware_Plugins.PLUGIN_STATUS + " integer default 0,"
                    + Aware_Plugins.PLUGIN_AUTHOR + " text default '',"
                    + Aware_Plugins.PLUGIN_ICON + " blob default null,"
                    + Aware_Plugins.PLUGIN_DESCRIPTION + " text default ''",

            Barometer_Sensor._ID + " integer primary key autoincrement,"
                    + Barometer_Sensor.TIMESTAMP + " real default 0,"
                    + Barometer_Sensor.DEVICE_ID + " text default '',"
                    + Barometer_Sensor.MAXIMUM_RANGE + " real default 0,"
                    + Barometer_Sensor.MINIMUM_DELAY + " real default 0,"
                    + Barometer_Sensor.NAME + " text default '',"
                    + Barometer_Sensor.POWER_MA + " real default 0,"
                    + Barometer_Sensor.RESOLUTION + " real default 0,"
                    + Barometer_Sensor.TYPE + " text default '',"
                    + Barometer_Sensor.VENDOR + " text default '',"
                    + Barometer_Sensor.VERSION + " text default '',"
                    + "UNIQUE(" + Barometer_Sensor.TIMESTAMP + ","
                    + Barometer_Sensor.DEVICE_ID + ")",
            // sensor data
            Barometer_Data._ID + " integer primary key autoincrement,"
                    + Barometer_Data.TIMESTAMP + " real default 0,"
                    + Barometer_Data.DEVICE_ID + " text default '',"
                    + Barometer_Data.AMBIENT_PRESSURE + " real default 0,"
                    + Barometer_Data.ACCURACY + " integer default 0,"
                    + Barometer_Data.LABEL + " text default ''," + "UNIQUE("
                    + Barometer_Data.TIMESTAMP + "," + Barometer_Data.DEVICE_ID
                    + ")",

            Battery_Data._ID + " integer primary key autoincrement,"
                    + Battery_Data.TIMESTAMP + " real default 0,"
                    + Battery_Data.DEVICE_ID + " text default '',"
                    + Battery_Data.STATUS + " integer default 0,"
                    + Battery_Data.LEVEL + " integer default 0,"
                    + Battery_Data.SCALE + " integer default 0,"
                    + Battery_Data.VOLTAGE + " integer default 0,"
                    + Battery_Data.TEMPERATURE + " integer default 0,"
                    + Battery_Data.PLUG_ADAPTOR + " integer default 0,"
                    + Battery_Data.HEALTH + " integer default 0,"
                    + Battery_Data.TECHNOLOGY + " text default '',"
                    + "UNIQUE (" + Battery_Data.TIMESTAMP + ","
                    + Battery_Data.DEVICE_ID + ")",
            // battery discharges
            Battery_Discharges._ID + " integer primary key autoincrement,"
                    + Battery_Discharges.TIMESTAMP + " real default 0,"
                    + Battery_Discharges.DEVICE_ID + " text default '',"
                    + Battery_Discharges.BATTERY_START + " integer default 0,"
                    + Battery_Discharges.BATTERY_END + " integer default 0,"
                    + Battery_Discharges.END_TIMESTAMP + " real default 0,"
                    + "UNIQUE (" + Battery_Discharges.TIMESTAMP + ","
                    + Battery_Discharges.DEVICE_ID + ")",
            // battery charges
            Battery_Charges._ID + " integer primary key autoincrement,"
                    + Battery_Charges.TIMESTAMP + " real default 0,"
                    + Battery_Charges.DEVICE_ID + " text default '',"
                    + Battery_Charges.BATTERY_START + " integer default 0,"
                    + Battery_Charges.BATTERY_END + " integer default 0,"
                    + Battery_Charges.END_TIMESTAMP + " real default 0,"
                    + "UNIQUE (" + Battery_Charges.TIMESTAMP + ","
                    + Battery_Charges.DEVICE_ID + ")"
    };

    private static UriMatcher sUriMatcher = null;
    private static HashMap<String, String> accelDeviceMap = null;
    private static HashMap<String, String> accelDataMap = null;
    private static HashMap<String, String> foregroundMap = null;
    private static HashMap<String, String> applicationsMap = null;
    private static HashMap<String, String> notificationMap = null;
    private static HashMap<String, String> crashesMap = null;
    private static HashMap<String, String> deviceMap = null;
    private static HashMap<String, String> settingsMap = null;
    private static HashMap<String, String> pluginsMap = null;
    private static HashMap<String, String> sensorMap = null;
    private static HashMap<String, String> sensorDataMap = null;
    private static HashMap<String, String> batteryProjectionMap = null;
    private static HashMap<String, String> batteryDischargesMap = null;
    private static HashMap<String, String> batteryChargesMap = null;
    private static DatabaseHelper databaseHelper = null;
    private static SQLiteDatabase database = null;

    private boolean initializeDB() {
        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper( getContext(), DATABASE_NAME, null, DATABASE_VERSION, DATABASE_TABLES, TABLES_FIELDS );
        }
        if( database == null || ! database.isOpen() ) {
            database = databaseHelper.getWritableDatabase();
        }
        return( database != null );
    }

    /**
     * Recreates the ContentProvider
     */
    public static void resetDB( Context c ) {
        Log.d("AWARE", "Resetting " + DATABASE_NAME + "...");
        File db = new File(DATABASE_NAME);
        if( db.delete() ) {
            databaseHelper = new DatabaseHelper( c, DATABASE_NAME, null, DATABASE_VERSION, DATABASE_TABLES, TABLES_FIELDS);
            database = databaseHelper.getWritableDatabase();
        }
    }

    /**
     * Delete entry from the database
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        if( ! initializeDB() ) {
            Log.w(AUTHORITY,"Database unavailable...");
            return 0;
        }

        int count = 0;
        switch (sUriMatcher.match(uri)) {
            case ACCEL_DEV:
                database.beginTransaction();
                count = database.delete(DATABASE_TABLES[0], selection,
                        selectionArgs);
                database.setTransactionSuccessful();
                database.endTransaction();
                break;
            case ACCEL_DATA:
                database.beginTransaction();
                count = database.delete(DATABASE_TABLES[1], selection,
                        selectionArgs);
                database.setTransactionSuccessful();
                database.endTransaction();
                break;
            case FOREGROUND:
                database.beginTransaction();
                count = database.delete(DATABASE_TABLES[2], selection,
                        selectionArgs);
                database.setTransactionSuccessful();
                database.endTransaction();
                break;
            case APPLICATIONS:
                database.beginTransaction();
                count = database.delete(DATABASE_TABLES[3], selection,
                        selectionArgs);
                database.setTransactionSuccessful();
                database.endTransaction();
                break;
            case NOTIFICATIONS:
                database.beginTransaction();
                count = database.delete(DATABASE_TABLES[4], selection,
                        selectionArgs);
                database.setTransactionSuccessful();
                database.endTransaction();
                break;
            case ERROR:
                database.beginTransaction();
                count = database.delete(DATABASE_TABLES[5], selection,
                        selectionArgs);
                database.setTransactionSuccessful();
                database.endTransaction();
                break;
            case DEVICE_INFO:
                database.beginTransaction();
                count = database.delete(DATABASE_TABLES[6], selection,
                        selectionArgs);
                database.setTransactionSuccessful();
                database.endTransaction();
                break;
            case SETTING:
                database.beginTransaction();
                count = database.delete(DATABASE_TABLES[7], selection,
                        selectionArgs);
                database.setTransactionSuccessful();
                database.endTransaction();
                break;
            case PLUGIN:
                database.beginTransaction();
                count = database.delete(DATABASE_TABLES[8], selection,
                        selectionArgs);
                database.setTransactionSuccessful();
                database.endTransaction();
                break;
            case SENSOR_DEV:
                database.beginTransaction();
                count = database.delete(DATABASE_TABLES[9], selection,
                        selectionArgs);
                database.setTransactionSuccessful();
                database.endTransaction();
                break;
            case SENSOR_DATA:
                database.beginTransaction();
                count = database.delete(DATABASE_TABLES[10], selection,
                        selectionArgs);
                database.setTransactionSuccessful();
                database.endTransaction();
                break;
            case BATTERY:
                database.beginTransaction();
                count = database.delete(DATABASE_TABLES[11], selection,
                        selectionArgs);
                database.setTransactionSuccessful();
                database.endTransaction();
                break;
            case BATTERY_DISCHARGE:
                database.beginTransaction();
                count = database.delete(DATABASE_TABLES[12], selection,
                        selectionArgs);
                database.setTransactionSuccessful();
                database.endTransaction();
                break;
            case BATTERY_CHARGE:
                database.beginTransaction();
                count = database.delete(DATABASE_TABLES[13], selection,
                        selectionArgs);
                database.setTransactionSuccessful();
                database.endTransaction();
                break;


            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case ACCEL_DEV:
                return Accelerometer_Sensor.CONTENT_TYPE;
            case ACCEL_DEV_ID:
                return Accelerometer_Sensor.CONTENT_ITEM_TYPE;
            case ACCEL_DATA:
                return Accelerometer_Data.CONTENT_TYPE;
            case ACCEL_DATA_ID:
                return Accelerometer_Data.CONTENT_ITEM_TYPE;
            case FOREGROUND:
                return Applications_Foreground.CONTENT_TYPE;
            case FOREGROUND_ID:
                return Applications_Foreground.CONTENT_ITEM_TYPE;
            case APPLICATIONS:
                return Applications_History.CONTENT_TYPE;
            case APPLICATIONS_ID:
                return Applications_History.CONTENT_ITEM_TYPE;
            case NOTIFICATIONS:
                return Applications_Notifications.CONTENT_TYPE;
            case NOTIFICATIONS_ID:
                return Applications_Notifications.CONTENT_ITEM_TYPE;
            case ERROR:
                return Applications_Crashes.CONTENT_TYPE;
            case ERROR_ID:
                return Applications_Crashes.CONTENT_ITEM_TYPE;
            case DEVICE_INFO:
                return Aware_Device.CONTENT_TYPE;
            case DEVICE_INFO_ID:
                return Aware_Device.CONTENT_ITEM_TYPE;
            case SETTING:
                return Aware_Settings.CONTENT_TYPE;
            case SETTING_ID:
                return Aware_Settings.CONTENT_ITEM_TYPE;
            case PLUGIN:
                return Aware_Plugins.CONTENT_TYPE;
            case PLUGIN_ID:
                return Aware_Plugins.CONTENT_ITEM_TYPE;
            case SENSOR_DEV:
                return Barometer_Sensor.CONTENT_TYPE;
            case SENSOR_DEV_ID:
                return Barometer_Sensor.CONTENT_ITEM_TYPE;
            case SENSOR_DATA:
                return Barometer_Data.CONTENT_TYPE;
            case SENSOR_DATA_ID:
                return Barometer_Data.CONTENT_ITEM_TYPE;
            case BATTERY:
                return Battery_Data.CONTENT_TYPE;
            case BATTERY_ID:
                return Battery_Data.CONTENT_ITEM_TYPE;
            case BATTERY_DISCHARGE:
                return Battery_Discharges.CONTENT_TYPE;
            case BATTERY_DISCHARGE_ID:
                return Battery_Discharges.CONTENT_ITEM_TYPE;
            case BATTERY_CHARGE:
                return Battery_Charges.CONTENT_TYPE;
            case BATTERY_CHARGE_ID:
                return Battery_Charges.CONTENT_ITEM_TYPE;


            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    /**
     * Insert entry to the database
     */
    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {

        if( ! initializeDB() ) {
            Log.w(AUTHORITY,"Database unavailable...");
            return null;
        }

        ContentValues values = (initialValues != null) ? new ContentValues(
                initialValues) : new ContentValues();

        switch (sUriMatcher.match(uri)) {
            case ACCEL_DEV:
                long accel_id = database.insertWithOnConflict(DATABASE_TABLES[0],
                        Accelerometer_Sensor.DEVICE_ID, values, SQLiteDatabase.CONFLICT_IGNORE);
                if (accel_id > 0) {
                    Uri accelUri = ContentUris.withAppendedId(
                            Accelerometer_Sensor.CONTENT_URI, accel_id);
                    getContext().getContentResolver().notifyChange(accelUri, null);
                    return accelUri;
                }
                throw new SQLException("Failed to insert row into " + uri);
            case ACCEL_DATA:
                long accelData_id = database.insertWithOnConflict(DATABASE_TABLES[1],
                        Accelerometer_Data.DEVICE_ID, values, SQLiteDatabase.CONFLICT_IGNORE);
                if (accelData_id > 0) {
                    Uri accelDataUri = ContentUris.withAppendedId(
                            Accelerometer_Data.CONTENT_URI, accelData_id);
                    getContext().getContentResolver().notifyChange(accelDataUri,
                            null);
                    return accelDataUri;
                }
                throw new SQLException("Failed to insert row into " + uri);
            case FOREGROUND:
                database.beginTransaction();
                long foreground_id = database.insertWithOnConflict(DATABASE_TABLES[2],
                        Applications_Foreground.APPLICATION_NAME, values, SQLiteDatabase.CONFLICT_IGNORE);
                database.setTransactionSuccessful();
                database.endTransaction();
                if (foreground_id > 0) {
                    Uri foregroundUri = ContentUris.withAppendedId(
                            Applications_Foreground.CONTENT_URI, foreground_id);
                    getContext().getContentResolver().notifyChange(foregroundUri,
                            null);
                    return foregroundUri;
                }
                throw new SQLException("Failed to insert row into " + uri);
            case APPLICATIONS:
                database.beginTransaction();
                long applications_id = database.insertWithOnConflict(DATABASE_TABLES[3],
                        Applications_History.PACKAGE_NAME, values, SQLiteDatabase.CONFLICT_IGNORE);
                database.setTransactionSuccessful();
                database.endTransaction();
                if (applications_id > 0) {
                    Uri applicationsUri = ContentUris.withAppendedId(
                            Applications_History.CONTENT_URI, applications_id);
                    getContext().getContentResolver().notifyChange(applicationsUri,
                            null);
                    return applicationsUri;
                }
                throw new SQLException("Failed to insert row into " + uri);
            case NOTIFICATIONS:
                database.beginTransaction();
                long notifications_id = database.insertWithOnConflict(DATABASE_TABLES[4],
                        Applications_Notifications.PACKAGE_NAME, values, SQLiteDatabase.CONFLICT_IGNORE);
                database.setTransactionSuccessful();
                database.endTransaction();
                if (notifications_id > 0) {
                    Uri notificationsUri = ContentUris.withAppendedId(
                            Applications_Notifications.CONTENT_URI,
                            notifications_id);
                    getContext().getContentResolver().notifyChange(
                            notificationsUri, null);
                    return notificationsUri;
                }
                throw new SQLException("Failed to insert row into " + uri);
            case ERROR:
                database.beginTransaction();
                long error_id = database.insertWithOnConflict(DATABASE_TABLES[5],
                        Applications_Crashes.PACKAGE_NAME, values, SQLiteDatabase.CONFLICT_IGNORE);
                database.setTransactionSuccessful();
                database.endTransaction();
                if (error_id > 0) {
                    Uri errorsUri = ContentUris.withAppendedId(
                            Applications_Crashes.CONTENT_URI, error_id);
                    getContext().getContentResolver().notifyChange(errorsUri, null);
                    return errorsUri;
                }
                throw new SQLException("Failed to insert row into " + uri);
            case DEVICE_INFO:
                database.beginTransaction();
                long dev_id = database.insertWithOnConflict(DATABASE_TABLES[6],
                        Aware_Device.DEVICE_ID, values, SQLiteDatabase.CONFLICT_IGNORE);
                database.setTransactionSuccessful();
                database.endTransaction();
                if (dev_id > 0) {
                    Uri devUri = ContentUris.withAppendedId(
                            Aware_Device.CONTENT_URI, dev_id);
                    getContext().getContentResolver().notifyChange(devUri, null);
                    return devUri;
                }
                throw new SQLException("Failed to insert row into " + uri);
            case SETTING:
                database.beginTransaction();
                long sett_id = database.insertWithOnConflict(DATABASE_TABLES[7],
                        Aware_Settings.SETTING_KEY, values, SQLiteDatabase.CONFLICT_IGNORE);
                database.setTransactionSuccessful();
                database.endTransaction();
                if (sett_id > 0) {
                    Uri settUri = ContentUris.withAppendedId(
                            Aware_Settings.CONTENT_URI, sett_id);
                    getContext().getContentResolver().notifyChange(settUri, null);
                    return settUri;
                }
                throw new SQLException("Failed to insert row into " + uri);
            case PLUGIN:
                database.beginTransaction();
                long plug_id = database.insertWithOnConflict(DATABASE_TABLES[8],
                        Aware_Plugins.PLUGIN_NAME, values, SQLiteDatabase.CONFLICT_IGNORE);
                database.setTransactionSuccessful();
                database.endTransaction();
                if (plug_id > 0) {
                    Uri settUri = ContentUris.withAppendedId(
                            Aware_Plugins.CONTENT_URI, plug_id);
                    getContext().getContentResolver().notifyChange(settUri, null);
                    return settUri;
                }
                throw new SQLException("Failed to insert row into " + uri);
            case SENSOR_DEV:
                database.beginTransaction();
                long barometer_id = database.insertWithOnConflict(DATABASE_TABLES[9],
                        Barometer_Sensor.DEVICE_ID, values, SQLiteDatabase.CONFLICT_IGNORE);
                database.setTransactionSuccessful();
                database.endTransaction();
                if (barometer_id > 0) {
                    Uri accelUri = ContentUris.withAppendedId(
                            Barometer_Sensor.CONTENT_URI, barometer_id);
                    getContext().getContentResolver().notifyChange(accelUri, null);
                    return accelUri;
                }
                throw new SQLException("Failed to insert row into " + uri);
            case SENSOR_DATA:
                database.beginTransaction();
                long barometer_data_id = database.insertWithOnConflict(DATABASE_TABLES[10],
                        Barometer_Data.DEVICE_ID, values, SQLiteDatabase.CONFLICT_IGNORE);
                database.setTransactionSuccessful();
                database.endTransaction();
                if (barometer_data_id > 0) {
                    Uri accelDataUri = ContentUris.withAppendedId(
                            Barometer_Data.CONTENT_URI, barometer_data_id);
                    getContext().getContentResolver().notifyChange(accelDataUri,
                            null);
                    return accelDataUri;
                }
                throw new SQLException("Failed to insert row into " + uri);
            case BATTERY:
                database.beginTransaction();
                long battery_id = database.insertWithOnConflict(DATABASE_TABLES[11], Battery_Data.TECHNOLOGY, values, SQLiteDatabase.CONFLICT_IGNORE);
                database.setTransactionSuccessful();
                database.endTransaction();
                if (battery_id > 0) {
                    Uri batteryUri = ContentUris.withAppendedId(Battery_Data.CONTENT_URI, battery_id);
                    getContext().getContentResolver().notifyChange(batteryUri, null);
                    return batteryUri;
                }
                throw new SQLException("Failed to insert row into " + uri);
            case BATTERY_DISCHARGE:
                database.beginTransaction();
                long battery_d_id = database.insertWithOnConflict(DATABASE_TABLES[12], Battery_Discharges.DEVICE_ID, values, SQLiteDatabase.CONFLICT_IGNORE);
                database.setTransactionSuccessful();
                database.endTransaction();
                if (battery_d_id > 0) {
                    Uri batteryUri = ContentUris.withAppendedId(
                            Battery_Discharges.CONTENT_URI, battery_d_id);
                    getContext().getContentResolver()
                            .notifyChange(batteryUri, null);
                    return batteryUri;
                }
                throw new SQLException("Failed to insert row into " + uri);
            case BATTERY_CHARGE:
                database.beginTransaction();
                long battery_c_id = database.insertWithOnConflict(DATABASE_TABLES[13],
                        Battery_Charges.DEVICE_ID, values, SQLiteDatabase.CONFLICT_IGNORE);
                database.setTransactionSuccessful();
                database.endTransaction();
                if (battery_c_id > 0) {
                    Uri batteryUri = ContentUris.withAppendedId(
                            Battery_Charges.CONTENT_URI, battery_c_id);
                    getContext().getContentResolver()
                            .notifyChange(batteryUri, null);
                    return batteryUri;
                }
                throw new SQLException("Failed to insert row into " + uri);



            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    /**
     * Batch insert for high performance sensors (e.g., accelerometer, etc)
     * @param uri
     * @param values
     * @return values.length
     */
    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        if( ! initializeDB() ) {
            Log.w(AUTHORITY,"Database unavailable...");
            return 0;
        }

        int count = 0;
        switch ( sUriMatcher.match(uri) ) {
            case ACCEL_DEV:
                database.beginTransaction();
                for (ContentValues v : values) {
                    long id;
                    try {
                        id = database.insertOrThrow( DATABASE_TABLES[0], Accelerometer_Sensor.DEVICE_ID, v );
                    } catch ( SQLException e ) {
                        id = database.replace( DATABASE_TABLES[0], Accelerometer_Sensor.DEVICE_ID, v );
                    }
                    if( id <= 0 ) {
                        Log.w(Accelerometer.TAG, "Failed to insert/replace row into " + uri);
                    } else {
                        count++;
                    }
                }
                database.setTransactionSuccessful();
                database.endTransaction();
                getContext().getContentResolver().notifyChange(uri, null);
                return count;
            case ACCEL_DATA:
                database.beginTransaction();
                for (ContentValues v : values) {
                    long id;
                    try {
                        id = database.insertOrThrow( DATABASE_TABLES[1], Accelerometer_Data.DEVICE_ID, v );
                    } catch ( SQLException e ) {
                        id = database.replace( DATABASE_TABLES[1], Accelerometer_Data.DEVICE_ID, v );
                    }
                    if( id <= 0 ) {
                        Log.w(Accelerometer.TAG, "Failed to insert/replace row into " + uri);
                    } else {
                        count++;
                    }
                }
                database.setTransactionSuccessful();
                database.endTransaction();
                getContext().getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public boolean onCreate() {
        //AUTHORITY = getContext().getPackageName() + ".provider.accelerometer";

        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(AUTHORITY,
                DATABASE_TABLES[0], ACCEL_DEV);
        sUriMatcher.addURI(AUTHORITY, DATABASE_TABLES[0]
                + "/#", ACCEL_DEV_ID);
        sUriMatcher.addURI(AUTHORITY,
                DATABASE_TABLES[1], ACCEL_DATA);
        sUriMatcher.addURI(AUTHORITY, DATABASE_TABLES[1]
                + "/#", ACCEL_DATA_ID);
        sUriMatcher.addURI(AUTHORITY, DATABASE_TABLES[2],
                FOREGROUND);
        sUriMatcher.addURI(AUTHORITY, DATABASE_TABLES[2]
                + "/#", FOREGROUND_ID);
        sUriMatcher.addURI(AUTHORITY, DATABASE_TABLES[3],
                APPLICATIONS);
        sUriMatcher.addURI(AUTHORITY, DATABASE_TABLES[3]
                + "/#", APPLICATIONS_ID);
        sUriMatcher.addURI(AUTHORITY, DATABASE_TABLES[4],
                NOTIFICATIONS);
        sUriMatcher.addURI(AUTHORITY, DATABASE_TABLES[4]
                + "/#", NOTIFICATIONS_ID);
        sUriMatcher.addURI(AUTHORITY, DATABASE_TABLES[5],
                ERROR);
        sUriMatcher.addURI(AUTHORITY, DATABASE_TABLES[5]
                + "/#", ERROR_ID);
        sUriMatcher.addURI(AUTHORITY, DATABASE_TABLES[6], DEVICE_INFO);
        sUriMatcher.addURI(AUTHORITY, DATABASE_TABLES[6] + "/#", DEVICE_INFO_ID);
        sUriMatcher.addURI(AUTHORITY, DATABASE_TABLES[7], SETTING);
        sUriMatcher.addURI(AUTHORITY, DATABASE_TABLES[7] + "/#", SETTING_ID);
        sUriMatcher.addURI(AUTHORITY, DATABASE_TABLES[8], PLUGIN);
        sUriMatcher.addURI(AUTHORITY, DATABASE_TABLES[8] + "/#", PLUGIN_ID);
        sUriMatcher.addURI(AUTHORITY, DATABASE_TABLES[9],
                SENSOR_DEV);
        sUriMatcher.addURI(AUTHORITY, DATABASE_TABLES[9]
                + "/#", SENSOR_DEV_ID);
        sUriMatcher.addURI(AUTHORITY, DATABASE_TABLES[10],
                SENSOR_DATA);
        sUriMatcher.addURI(AUTHORITY, DATABASE_TABLES[10]
                + "/#", SENSOR_DATA_ID);
        sUriMatcher.addURI(AUTHORITY, DATABASE_TABLES[11],
                BATTERY);
        sUriMatcher.addURI(AUTHORITY, DATABASE_TABLES[11]
                + "/#", BATTERY_ID);
        sUriMatcher.addURI(AUTHORITY, DATABASE_TABLES[12],
                BATTERY_DISCHARGE);
        sUriMatcher.addURI(AUTHORITY, DATABASE_TABLES[12]
                + "/#", BATTERY_DISCHARGE_ID);
        sUriMatcher.addURI(AUTHORITY, DATABASE_TABLES[13],
                BATTERY_CHARGE);
        sUriMatcher.addURI(AUTHORITY, DATABASE_TABLES[13]
                + "/#", BATTERY_CHARGE_ID);


        accelDeviceMap = new HashMap<String, String>();
        accelDeviceMap.put(Accelerometer_Sensor._ID, Accelerometer_Sensor._ID);
        accelDeviceMap.put(Accelerometer_Sensor.TIMESTAMP,
                Accelerometer_Sensor.TIMESTAMP);
        accelDeviceMap.put(Accelerometer_Sensor.DEVICE_ID,
                Accelerometer_Sensor.DEVICE_ID);
        accelDeviceMap.put(Accelerometer_Sensor.MAXIMUM_RANGE,
                Accelerometer_Sensor.MAXIMUM_RANGE);
        accelDeviceMap.put(Accelerometer_Sensor.MINIMUM_DELAY,
                Accelerometer_Sensor.MINIMUM_DELAY);
        accelDeviceMap
                .put(Accelerometer_Sensor.NAME, Accelerometer_Sensor.NAME);
        accelDeviceMap.put(Accelerometer_Sensor.POWER_MA,
                Accelerometer_Sensor.POWER_MA);
        accelDeviceMap.put(Accelerometer_Sensor.RESOLUTION,
                Accelerometer_Sensor.RESOLUTION);
        accelDeviceMap
                .put(Accelerometer_Sensor.TYPE, Accelerometer_Sensor.TYPE);
        accelDeviceMap.put(Accelerometer_Sensor.VENDOR,
                Accelerometer_Sensor.VENDOR);
        accelDeviceMap.put(Accelerometer_Sensor.VERSION,
                Accelerometer_Sensor.VERSION);

        accelDataMap = new HashMap<String, String>();
        accelDataMap.put(Accelerometer_Data._ID, Accelerometer_Data._ID);
        accelDataMap.put(Accelerometer_Data.TIMESTAMP,
                Accelerometer_Data.TIMESTAMP);
        accelDataMap.put(Accelerometer_Data.DEVICE_ID,
                Accelerometer_Data.DEVICE_ID);
        accelDataMap.put(Accelerometer_Data.VALUES_0,
                Accelerometer_Data.VALUES_0);
        accelDataMap.put(Accelerometer_Data.VALUES_1,
                Accelerometer_Data.VALUES_1);
        accelDataMap.put(Accelerometer_Data.VALUES_2,
                Accelerometer_Data.VALUES_2);
        accelDataMap.put(Accelerometer_Data.ACCURACY,
                Accelerometer_Data.ACCURACY);
        accelDataMap.put(Accelerometer_Data.LABEL, Accelerometer_Data.LABEL);

        foregroundMap = new HashMap<String, String>();
        foregroundMap.put(Applications_Foreground._ID,
                Applications_Foreground._ID);
        foregroundMap.put(Applications_Foreground.TIMESTAMP,
                Applications_Foreground.TIMESTAMP);
        foregroundMap.put(Applications_Foreground.DEVICE_ID,
                Applications_Foreground.DEVICE_ID);
        foregroundMap.put(Applications_Foreground.PACKAGE_NAME,
                Applications_Foreground.PACKAGE_NAME);
        foregroundMap.put(Applications_Foreground.APPLICATION_NAME,
                Applications_Foreground.APPLICATION_NAME);
        foregroundMap.put(Applications_Foreground.IS_SYSTEM_APP,
                Applications_Foreground.IS_SYSTEM_APP);

        applicationsMap = new HashMap<String, String>();
        applicationsMap.put(Applications_History._ID, Applications_History._ID);
        applicationsMap.put(Applications_History.TIMESTAMP,
                Applications_History.TIMESTAMP);
        applicationsMap.put(Applications_History.DEVICE_ID,
                Applications_History.DEVICE_ID);
        applicationsMap.put(Applications_History.PACKAGE_NAME,
                Applications_History.PACKAGE_NAME);
        applicationsMap.put(Applications_History.APPLICATION_NAME,
                Applications_History.APPLICATION_NAME);
        applicationsMap.put(Applications_History.PROCESS_IMPORTANCE,
                Applications_History.PROCESS_IMPORTANCE);
        applicationsMap.put(Applications_History.PROCESS_ID,
                Applications_History.PROCESS_ID);
        applicationsMap.put(Applications_History.END_TIMESTAMP,
                Applications_History.END_TIMESTAMP);
        applicationsMap.put(Applications_History.IS_SYSTEM_APP,
                Applications_History.IS_SYSTEM_APP);

        notificationMap = new HashMap<String, String>();
        notificationMap.put(Applications_Notifications._ID,
                Applications_Notifications._ID);
        notificationMap.put(Applications_Notifications.TIMESTAMP,
                Applications_Notifications.TIMESTAMP);
        notificationMap.put(Applications_Notifications.DEVICE_ID,
                Applications_Notifications.DEVICE_ID);
        notificationMap.put(Applications_Notifications.PACKAGE_NAME,
                Applications_Notifications.PACKAGE_NAME);
        notificationMap.put(Applications_Notifications.APPLICATION_NAME,
                Applications_Notifications.APPLICATION_NAME);
        notificationMap.put(Applications_Notifications.TEXT,
                Applications_Notifications.TEXT);
        notificationMap.put(Applications_Notifications.SOUND,
                Applications_Notifications.SOUND);
        notificationMap.put(Applications_Notifications.VIBRATE,
                Applications_Notifications.VIBRATE);
        notificationMap.put(Applications_Notifications.FLAGS,
                Applications_Notifications.FLAGS);
        notificationMap.put(Applications_Notifications.DEFAULTS,
                Applications_Notifications.DEFAULTS);

        crashesMap = new HashMap<String, String>();
        crashesMap.put(Applications_Crashes._ID, Applications_Crashes._ID);
        crashesMap.put(Applications_Crashes.TIMESTAMP,
                Applications_Crashes.TIMESTAMP);
        crashesMap.put(Applications_Crashes.DEVICE_ID,
                Applications_Crashes.DEVICE_ID);
        crashesMap.put(Applications_Crashes.PACKAGE_NAME,
                Applications_Crashes.PACKAGE_NAME);
        crashesMap.put(Applications_Crashes.APPLICATION_NAME,
                Applications_Crashes.APPLICATION_NAME);
        crashesMap.put(Applications_Crashes.APPLICATION_VERSION,
                Applications_Crashes.APPLICATION_VERSION);
        crashesMap.put(Applications_Crashes.ERROR_SHORT,
                Applications_Crashes.ERROR_SHORT);
        crashesMap.put(Applications_Crashes.ERROR_LONG,
                Applications_Crashes.ERROR_LONG);
        crashesMap.put(Applications_Crashes.ERROR_CONDITION,
                Applications_Crashes.ERROR_CONDITION);
        crashesMap.put(Applications_Crashes.IS_SYSTEM_APP,
                Applications_Crashes.IS_SYSTEM_APP);

        deviceMap = new HashMap<String, String>();
        deviceMap.put(Aware_Device._ID, Aware_Device._ID);
        deviceMap.put(Aware_Device.TIMESTAMP, Aware_Device.TIMESTAMP);
        deviceMap.put(Aware_Device.DEVICE_ID, Aware_Device.DEVICE_ID);
        deviceMap.put(Aware_Device.BOARD, Aware_Device.BOARD);
        deviceMap.put(Aware_Device.BRAND, Aware_Device.BRAND);
        deviceMap.put(Aware_Device.DEVICE, Aware_Device.DEVICE);
        deviceMap.put(Aware_Device.BUILD_ID, Aware_Device.BUILD_ID);
        deviceMap.put(Aware_Device.HARDWARE, Aware_Device.HARDWARE);
        deviceMap.put(Aware_Device.MANUFACTURER, Aware_Device.MANUFACTURER);
        deviceMap.put(Aware_Device.MODEL, Aware_Device.MODEL);
        deviceMap.put(Aware_Device.PRODUCT, Aware_Device.PRODUCT);
        deviceMap.put(Aware_Device.SERIAL, Aware_Device.SERIAL);
        deviceMap.put(Aware_Device.RELEASE, Aware_Device.RELEASE);
        deviceMap.put(Aware_Device.RELEASE_TYPE, Aware_Device.RELEASE_TYPE);
        deviceMap.put(Aware_Device.SDK, Aware_Device.SDK);
        deviceMap.put(Aware_Device.LABEL, Aware_Device.LABEL);

        settingsMap = new HashMap<String, String>();
        settingsMap.put(Aware_Settings.SETTING_ID, Aware_Settings.SETTING_ID);
        settingsMap.put(Aware_Settings.SETTING_KEY, Aware_Settings.SETTING_KEY);
        settingsMap.put(Aware_Settings.SETTING_VALUE,Aware_Settings.SETTING_VALUE);
        settingsMap.put(Aware_Settings.SETTING_PACKAGE_NAME, Aware_Settings.SETTING_PACKAGE_NAME);

        pluginsMap = new HashMap<String, String>();
        pluginsMap.put(Aware_Plugins.PLUGIN_ID, Aware_Plugins.PLUGIN_ID);
        pluginsMap.put(Aware_Plugins.PLUGIN_PACKAGE_NAME,Aware_Plugins.PLUGIN_PACKAGE_NAME);
        pluginsMap.put(Aware_Plugins.PLUGIN_NAME, Aware_Plugins.PLUGIN_NAME);
        pluginsMap.put(Aware_Plugins.PLUGIN_VERSION,Aware_Plugins.PLUGIN_VERSION);
        pluginsMap.put(Aware_Plugins.PLUGIN_STATUS, Aware_Plugins.PLUGIN_STATUS);
        pluginsMap.put(Aware_Plugins.PLUGIN_AUTHOR, Aware_Plugins.PLUGIN_AUTHOR);
        pluginsMap.put(Aware_Plugins.PLUGIN_ICON, Aware_Plugins.PLUGIN_ICON);
        pluginsMap.put(Aware_Plugins.PLUGIN_DESCRIPTION, Aware_Plugins.PLUGIN_DESCRIPTION);

        sensorMap = new HashMap<String, String>();
        sensorMap.put(Barometer_Sensor._ID, Barometer_Sensor._ID);
        sensorMap.put(Barometer_Sensor.TIMESTAMP, Barometer_Sensor.TIMESTAMP);
        sensorMap.put(Barometer_Sensor.DEVICE_ID, Barometer_Sensor.DEVICE_ID);
        sensorMap.put(Barometer_Sensor.MAXIMUM_RANGE,
                Barometer_Sensor.MAXIMUM_RANGE);
        sensorMap.put(Barometer_Sensor.MINIMUM_DELAY,
                Barometer_Sensor.MINIMUM_DELAY);
        sensorMap.put(Barometer_Sensor.NAME, Barometer_Sensor.NAME);
        sensorMap.put(Barometer_Sensor.POWER_MA, Barometer_Sensor.POWER_MA);
        sensorMap.put(Barometer_Sensor.RESOLUTION, Barometer_Sensor.RESOLUTION);
        sensorMap.put(Barometer_Sensor.TYPE, Barometer_Sensor.TYPE);
        sensorMap.put(Barometer_Sensor.VENDOR, Barometer_Sensor.VENDOR);
        sensorMap.put(Barometer_Sensor.VERSION, Barometer_Sensor.VERSION);

        sensorDataMap = new HashMap<String, String>();
        sensorDataMap.put(Barometer_Data._ID, Barometer_Data._ID);
        sensorDataMap.put(Barometer_Data.TIMESTAMP, Barometer_Data.TIMESTAMP);
        sensorDataMap.put(Barometer_Data.DEVICE_ID, Barometer_Data.DEVICE_ID);
        sensorDataMap.put(Barometer_Data.AMBIENT_PRESSURE,
                Barometer_Data.AMBIENT_PRESSURE);
        sensorDataMap.put(Barometer_Data.ACCURACY, Barometer_Data.ACCURACY);
        sensorDataMap.put(Barometer_Data.LABEL, Barometer_Data.LABEL);

        batteryProjectionMap = new HashMap<String, String>();
        batteryProjectionMap.put(Battery_Data._ID, Battery_Data._ID);
        batteryProjectionMap
                .put(Battery_Data.TIMESTAMP, Battery_Data.TIMESTAMP);
        batteryProjectionMap
                .put(Battery_Data.DEVICE_ID, Battery_Data.DEVICE_ID);
        batteryProjectionMap.put(Battery_Data.STATUS, Battery_Data.STATUS);
        batteryProjectionMap.put(Battery_Data.LEVEL, Battery_Data.LEVEL);
        batteryProjectionMap.put(Battery_Data.SCALE, Battery_Data.SCALE);
        batteryProjectionMap.put(Battery_Data.VOLTAGE, Battery_Data.VOLTAGE);
        batteryProjectionMap.put(Battery_Data.TEMPERATURE,
                Battery_Data.TEMPERATURE);
        batteryProjectionMap.put(Battery_Data.PLUG_ADAPTOR,
                Battery_Data.PLUG_ADAPTOR);
        batteryProjectionMap.put(Battery_Data.HEALTH, Battery_Data.HEALTH);
        batteryProjectionMap.put(Battery_Data.TECHNOLOGY,
                Battery_Data.TECHNOLOGY);

        batteryDischargesMap = new HashMap<String, String>();
        batteryDischargesMap
                .put(Battery_Discharges._ID, Battery_Discharges._ID);
        batteryDischargesMap.put(Battery_Discharges.TIMESTAMP,
                Battery_Discharges.TIMESTAMP);
        batteryDischargesMap.put(Battery_Discharges.DEVICE_ID,
                Battery_Discharges.DEVICE_ID);
        batteryDischargesMap.put(Battery_Discharges.BATTERY_START,
                Battery_Discharges.BATTERY_START);
        batteryDischargesMap.put(Battery_Discharges.BATTERY_END,
                Battery_Discharges.BATTERY_END);
        batteryDischargesMap.put(Battery_Discharges.END_TIMESTAMP,
                Battery_Discharges.END_TIMESTAMP);

        batteryChargesMap = new HashMap<String, String>();
        batteryChargesMap.put(Battery_Charges._ID, Battery_Charges._ID);
        batteryChargesMap.put(Battery_Charges.TIMESTAMP,
                Battery_Charges.TIMESTAMP);
        batteryChargesMap.put(Battery_Charges.DEVICE_ID,
                Battery_Charges.DEVICE_ID);
        batteryChargesMap.put(Battery_Charges.BATTERY_START,
                Battery_Charges.BATTERY_START);
        batteryChargesMap.put(Battery_Charges.BATTERY_END,
                Battery_Charges.BATTERY_END);
        batteryChargesMap.put(Battery_Charges.END_TIMESTAMP,
                Battery_Charges.END_TIMESTAMP);

        return true;
    }

    /**
     * Query entries from the database
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        if( ! initializeDB() ) {
            Log.w(AUTHORITY,"Database unavailable...");
            return null;
        }

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        switch (sUriMatcher.match(uri)) {
            case ACCEL_DEV:
                qb.setTables(DATABASE_TABLES[0]);
                qb.setProjectionMap(accelDeviceMap);
                break;
            case ACCEL_DATA:
                qb.setTables(DATABASE_TABLES[1]);
                qb.setProjectionMap(accelDataMap);
                break;
            case FOREGROUND:
                qb.setTables(DATABASE_TABLES[2]);
                qb.setProjectionMap(foregroundMap);
                break;
            case APPLICATIONS:
                qb.setTables(DATABASE_TABLES[3]);
                qb.setProjectionMap(applicationsMap);
                break;
            case NOTIFICATIONS:
                qb.setTables(DATABASE_TABLES[4]);
                qb.setProjectionMap(notificationMap);
                break;
            case ERROR:
                qb.setTables(DATABASE_TABLES[5]);
                qb.setProjectionMap(crashesMap);
                break;
            case DEVICE_INFO:
                qb.setTables(DATABASE_TABLES[6]);
                qb.setProjectionMap(deviceMap);
                break;
            case SETTING:
                qb.setTables(DATABASE_TABLES[7]);
                qb.setProjectionMap(settingsMap);
                break;
            case PLUGIN:
                qb.setTables(DATABASE_TABLES[8]);
                qb.setProjectionMap(pluginsMap);
                break;
            case SENSOR_DEV:
                qb.setTables(DATABASE_TABLES[9]);
                qb.setProjectionMap(sensorMap);
                break;
            case SENSOR_DATA:
                qb.setTables(DATABASE_TABLES[10]);
                qb.setProjectionMap(sensorDataMap);
                break;
            case BATTERY:
                qb.setTables(DATABASE_TABLES[11]);
                qb.setProjectionMap(batteryProjectionMap);
                break;
            case BATTERY_DISCHARGE:
                qb.setTables(DATABASE_TABLES[12]);
                qb.setProjectionMap(batteryDischargesMap);
                break;
            case BATTERY_CHARGE:
                qb.setTables(DATABASE_TABLES[13]);
                qb.setProjectionMap(batteryChargesMap);
                break;



            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        try {
            Cursor c = qb.query(database, projection, selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(getContext().getContentResolver(), uri);
            return c;
        } catch (IllegalStateException e) {
            if (Aware.DEBUG)
                Log.e(Aware.TAG, e.getMessage());
            return null;
        }
    }

    /**
     * Update application on the database
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        if( ! initializeDB() ) {
            Log.w(AUTHORITY,"Database unavailable...");
            return 0;
        }

        int count = 0;
        switch (sUriMatcher.match(uri)) {
            case ACCEL_DEV:
                database.beginTransaction();
                count = database.update(DATABASE_TABLES[0], values, selection,
                        selectionArgs);
                database.setTransactionSuccessful();
                database.endTransaction();
                break;
            case ACCEL_DATA:
                database.beginTransaction();
                count = database.update(DATABASE_TABLES[1], values, selection,
                        selectionArgs);
                database.setTransactionSuccessful();
                database.endTransaction();
                break;

            case FOREGROUND:
                database.beginTransaction();
                count = database.update(DATABASE_TABLES[2], values, selection,
                        selectionArgs);
                database.setTransactionSuccessful();
                database.endTransaction();
                break;
            case APPLICATIONS:
                database.beginTransaction();
                count = database.update(DATABASE_TABLES[3], values, selection,
                        selectionArgs);
                database.setTransactionSuccessful();
                database.endTransaction();
                break;
            case NOTIFICATIONS:
                database.beginTransaction();
                count = database.update(DATABASE_TABLES[4], values, selection,
                        selectionArgs);
                database.setTransactionSuccessful();
                database.endTransaction();
                break;
            case ERROR:
                database.beginTransaction();
                count = database.update(DATABASE_TABLES[5], values, selection,
                        selectionArgs);
                database.setTransactionSuccessful();
                database.endTransaction();
                break;
            case DEVICE_INFO:
                database.beginTransaction();
                count = database.update(DATABASE_TABLES[6], values, selection,
                        selectionArgs);
                database.setTransactionSuccessful();
                database.endTransaction();
                break;
            case SETTING:
                database.beginTransaction();
                count = database.update(DATABASE_TABLES[7], values, selection,
                        selectionArgs);
                database.setTransactionSuccessful();
                database.endTransaction();
                break;
            case PLUGIN:
                database.beginTransaction();
                count = database.update(DATABASE_TABLES[8], values, selection,
                        selectionArgs);
                database.setTransactionSuccessful();
                database.endTransaction();
                break;
            case SENSOR_DEV:
                database.beginTransaction();
                count = database.update(DATABASE_TABLES[9], values, selection,
                        selectionArgs);
                database.setTransactionSuccessful();
                database.endTransaction();
                break;
            case SENSOR_DATA:
                database.beginTransaction();
                count = database.update(DATABASE_TABLES[10], values, selection,
                        selectionArgs);
                database.setTransactionSuccessful();
                database.endTransaction();
                break;
            case BATTERY:
                database.beginTransaction();
                count = database.update(DATABASE_TABLES[11], values, selection,
                        selectionArgs);
                database.setTransactionSuccessful();
                database.endTransaction();
                break;
            case BATTERY_DISCHARGE:
                database.beginTransaction();
                count = database.update(DATABASE_TABLES[12], values, selection,
                        selectionArgs);
                database.setTransactionSuccessful();
                database.endTransaction();
                break;
            case BATTERY_CHARGE:
                database.beginTransaction();
                count = database.update(DATABASE_TABLES[13], values, selection,
                        selectionArgs);
                database.setTransactionSuccessful();
                database.endTransaction();
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
