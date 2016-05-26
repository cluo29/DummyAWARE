package com.aware.plugin.dummyaware.providers;

/**
 * Created by Comet on 26/05/16.
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

import com.aware.Aware;
import com.aware.BuildConfig;
import com.aware.utils.DatabaseHelper;

import java.io.File;
import java.util.HashMap;

public class Mqtt_Provider extends ContentProvider {

    public static final int DATABASE_VERSION = 1;

    /**
     * Authority of MQTT content provider
     */
    public static String AUTHORITY = "com.aware.plugin.dummyaware.provider.mqtt";

    // ContentProvider query paths
    private static final int MQTT = 1;
    private static final int MQTT_ID = 2;
    private static final int MQTT_SUBSCRIPTION = 3;
    private static final int MQTT_SUBSCRIPTION_ID = 4;

    public static final class Mqtt_Messages implements BaseColumns {
        private Mqtt_Messages() {
        };

        public static final Uri CONTENT_URI = Uri.parse("content://"
                + Mqtt_Provider.AUTHORITY + "/mqtt_messages");
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.aware.plugin.dummyaware.mqtt.messages";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.aware.plugin.dummyaware.mqtt.messages";

        public static final String MQTT_ID = "_id";
        public static final String TIMESTAMP = "timestamp";
        public static final String DEVICE_ID = "device_id";
        public static final String TOPIC = "topic";
        public static final String MESSAGE = "message";
        public static final String STATUS = "status";
    }
    public static final class Mqtt_Subscriptions implements BaseColumns {
        private Mqtt_Subscriptions() {
        };

        public static final Uri CONTENT_URI = Uri.parse("content://"
                + Mqtt_Provider.AUTHORITY + "/mqtt_subscriptions");
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.aware.plugin.dummyaware.mqtt.subscriptions";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.aware.plugin.dummyaware.mqtt.subscriptions";

        public static final String MQTT_SUBSCRIPTION_ID = "_id";
        public static final String TIMESTAMP = "timestamp";
        public static final String DEVICE_ID = "device_id";
        public static final String TOPIC = "topic";
    }
    public static String DATABASE_NAME = "mqtt.db";

    public static final String[] DATABASE_TABLES = { "mqtt_messages",
            "mqtt_subscriptions" };
    public static final String[] TABLES_FIELDS = {
            // mqtt messages
            Mqtt_Messages._ID + " integer primary key autoincrement,"
                    + Mqtt_Messages.TIMESTAMP + " real default 0,"
                    + Mqtt_Messages.DEVICE_ID + " text default '',"
                    + Mqtt_Messages.TOPIC + " text default '',"
                    + Mqtt_Messages.MESSAGE + " text default '',"
                    + Mqtt_Messages.STATUS + " integer default 0," + "UNIQUE("
                    + Mqtt_Messages.TIMESTAMP + "," + Mqtt_Messages.DEVICE_ID
                    + ")",
            // mqtt subscriptions
            Mqtt_Subscriptions._ID + " integer primary key autoincrement,"
                    + Mqtt_Subscriptions.TIMESTAMP + " real default 0,"
                    + Mqtt_Subscriptions.DEVICE_ID + " text default '',"
                    + Mqtt_Subscriptions.TOPIC + " text default '',"
                    + "UNIQUE(" + Mqtt_Subscriptions.TIMESTAMP + ","
                    + Mqtt_Subscriptions.DEVICE_ID + ")" };

    private static UriMatcher sUriMatcher = null;
    private static HashMap<String, String> messagesMap = null;
    private static HashMap<String, String> subscriptionMap = null;
    private static DatabaseHelper databaseHelper = null;
    private static SQLiteDatabase database = null;

    private boolean initializeDB() {
        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper( getContext(), DATABASE_NAME, null, DATABASE_VERSION, DATABASE_TABLES, TABLES_FIELDS );
        }
        if( databaseHelper != null && ( database == null || ! database.isOpen() )) {
            database = databaseHelper.getWritableDatabase();
        }
        return( database != null && databaseHelper != null);
    }

    /**
     * Recreates the ContentProvider
     */
    public static void resetDB( Context c ) {
        Log.d("AWARE", "Resetting " + DATABASE_NAME + "...");

        File db = new File(DATABASE_NAME);
        db.delete();
        databaseHelper = new DatabaseHelper( c, DATABASE_NAME, null, DATABASE_VERSION, DATABASE_TABLES, TABLES_FIELDS);
        if( databaseHelper != null ) {
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
            case MQTT:
                database.beginTransaction();
                count = database.delete(DATABASE_TABLES[0], selection,
                        selectionArgs);
                database.setTransactionSuccessful();
                database.endTransaction();
                break;
            case MQTT_SUBSCRIPTION:
                database.beginTransaction();
                count = database.delete(DATABASE_TABLES[1], selection,
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
            case MQTT:
                return Mqtt_Messages.CONTENT_TYPE;
            case MQTT_ID:
                return Mqtt_Messages.CONTENT_ITEM_TYPE;
            case MQTT_SUBSCRIPTION:
                return Mqtt_Subscriptions.CONTENT_TYPE;
            case MQTT_SUBSCRIPTION_ID:
                return Mqtt_Subscriptions.CONTENT_ITEM_TYPE;
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
            case MQTT:
                database.beginTransaction();
                long mqtt_id = database.insertWithOnConflict(DATABASE_TABLES[0],
                        Mqtt_Messages.DEVICE_ID, values, SQLiteDatabase.CONFLICT_IGNORE);
                database.setTransactionSuccessful();
                database.endTransaction();
                if (mqtt_id > 0) {
                    Uri mqttUri = ContentUris.withAppendedId(
                            Mqtt_Messages.CONTENT_URI, mqtt_id);
                    getContext().getContentResolver().notifyChange(mqttUri, null);
                    return mqttUri;
                }
                throw new SQLException("Failed to insert row into " + uri);
            case MQTT_SUBSCRIPTION:
                database.beginTransaction();
                long mqtt_sub_id = database.insertWithOnConflict(DATABASE_TABLES[1],
                        Mqtt_Subscriptions.DEVICE_ID, values, SQLiteDatabase.CONFLICT_IGNORE);
                database.setTransactionSuccessful();
                database.endTransaction();
                if (mqtt_sub_id > 0) {
                    Uri mqttSubUri = ContentUris.withAppendedId(
                            Mqtt_Subscriptions.CONTENT_URI, mqtt_sub_id);
                    getContext().getContentResolver()
                            .notifyChange(mqttSubUri, null);
                    return mqttSubUri;
                }
                throw new SQLException("Failed to insert row into " + uri);
            default:

                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public boolean onCreate() {
        //AUTHORITY = getContext().getPackageName() + ".provider.mqtt";

        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(Mqtt_Provider.AUTHORITY, DATABASE_TABLES[0], MQTT);
        sUriMatcher.addURI(Mqtt_Provider.AUTHORITY, DATABASE_TABLES[0] + "/#",
                MQTT_ID);
        sUriMatcher.addURI(Mqtt_Provider.AUTHORITY, DATABASE_TABLES[1],
                MQTT_SUBSCRIPTION);
        sUriMatcher.addURI(Mqtt_Provider.AUTHORITY, DATABASE_TABLES[1] + "/#",
                MQTT_SUBSCRIPTION_ID);

        messagesMap = new HashMap<String, String>();
        messagesMap.put(Mqtt_Messages.MQTT_ID, Mqtt_Messages.MQTT_ID);
        messagesMap.put(Mqtt_Messages.TIMESTAMP, Mqtt_Messages.TIMESTAMP);
        messagesMap.put(Mqtt_Messages.DEVICE_ID, Mqtt_Messages.DEVICE_ID);
        messagesMap.put(Mqtt_Messages.MESSAGE, Mqtt_Messages.MESSAGE);
        messagesMap.put(Mqtt_Messages.TOPIC, Mqtt_Messages.TOPIC);
        messagesMap.put(Mqtt_Messages.STATUS, Mqtt_Messages.STATUS);

        subscriptionMap = new HashMap<String, String>();
        subscriptionMap.put(Mqtt_Subscriptions.MQTT_SUBSCRIPTION_ID,
                Mqtt_Subscriptions.MQTT_SUBSCRIPTION_ID);
        subscriptionMap.put(Mqtt_Subscriptions.TIMESTAMP,
                Mqtt_Subscriptions.TIMESTAMP);
        subscriptionMap.put(Mqtt_Subscriptions.DEVICE_ID,
                Mqtt_Subscriptions.DEVICE_ID);
        subscriptionMap.put(Mqtt_Subscriptions.TOPIC, Mqtt_Subscriptions.TOPIC);

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
            case MQTT:
                qb.setTables(DATABASE_TABLES[0]);
                qb.setProjectionMap(messagesMap);
                break;
            case MQTT_SUBSCRIPTION:
                qb.setTables(DATABASE_TABLES[1]);
                qb.setProjectionMap(subscriptionMap);
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
            case MQTT:
                database.beginTransaction();
                count = database.update(DATABASE_TABLES[0], values, selection,
                        selectionArgs);
                database.setTransactionSuccessful();
                database.endTransaction();
                break;
            case MQTT_SUBSCRIPTION:
                database.beginTransaction();
                count = database.update(DATABASE_TABLES[1], values, selection,
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
