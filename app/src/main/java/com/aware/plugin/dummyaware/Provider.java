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

    //tables, edit
    public static final String[] DATABASE_TABLES = { "sensor_accelerometer", "accelerometer", "applications_foreground",
            "applications_history", "applications_notifications", "applications_crashes" };


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
                    + Applications_Crashes.DEVICE_ID + ")"


    };

    private static UriMatcher sUriMatcher = null;
    private static HashMap<String, String> accelDeviceMap = null;
    private static HashMap<String, String> accelDataMap = null;
    private static HashMap<String, String> foregroundMap = null;
    private static HashMap<String, String> applicationsMap = null;
    private static HashMap<String, String> notificationMap = null;
    private static HashMap<String, String> crashesMap = null;
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
                count = database.delete(DATABASE_TABLES[0], selection,
                        selectionArgs);
                database.setTransactionSuccessful();
                database.endTransaction();
                break;
            case APPLICATIONS:
                database.beginTransaction();
                count = database.delete(DATABASE_TABLES[1], selection,
                        selectionArgs);
                database.setTransactionSuccessful();
                database.endTransaction();
                break;
            case NOTIFICATIONS:
                database.beginTransaction();
                count = database.delete(DATABASE_TABLES[2], selection,
                        selectionArgs);
                database.setTransactionSuccessful();
                database.endTransaction();
                break;
            case ERROR:
                database.beginTransaction();
                count = database.delete(DATABASE_TABLES[3], selection,
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
                long foreground_id = database.insertWithOnConflict(DATABASE_TABLES[0],
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
                long applications_id = database.insertWithOnConflict(DATABASE_TABLES[1],
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
                long notifications_id = database.insertWithOnConflict(DATABASE_TABLES[2],
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
                long error_id = database.insertWithOnConflict(DATABASE_TABLES[3],
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

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
