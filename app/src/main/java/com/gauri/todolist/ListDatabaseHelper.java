package com.gauri.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import static android.content.ContentValues.TAG;

public class ListDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ListDatabase";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_TASKS = "tasks";

    // Taskname Table Columns
    private static final String KEY_TASKNAME_ID = "id";
    private static final String KEY_TASKNAME = "taskName";
    private static final String KEY_PRIORITY = "priority";
    private static final String KEY_CHECKED = "checked";

    public ListDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_TASKS = "CREATE TABLE " + TABLE_TASKS +
                "(" +
                KEY_TASKNAME_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                KEY_TASKNAME + " TEXT," +
                KEY_PRIORITY + " INT," +
                KEY_CHECKED + " INT" +
                ")";
        db.execSQL(CREATE_TABLE_TASKS);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if (i != i1) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
            onCreate(db);
        }
    }

    public void editTask(ListItem task) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {

            ContentValues values = new ContentValues();
            values.put(KEY_TASKNAME, task.taskName);
            values.put(KEY_PRIORITY, task.priority);
            values.put(KEY_CHECKED, task.isChecked);

            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.update(TABLE_TASKS, values, KEY_TASKNAME_ID + "=" + task.id,null);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d(TAG, "Error while trying to edit task in database");
        } finally {
            db.endTransaction();
        }
    }

    public void deleteTask(ListItem task){
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_TASKS, KEY_TASKNAME_ID + "=" + task.id,null);
            db.setTransactionSuccessful();
        }
        catch (Exception e) {
            Log.d(TAG, "Error while trying to delete task to database");
        }
        finally {
            db.endTransaction();
        }
    }

    public ArrayList<ListItem> readTask() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_TASKS;
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<ListItem> cursorData = new ArrayList<ListItem>();
        int i = 0;
            if (cursor.moveToFirst()) {
            do {
                ListItem cursorItem = new ListItem(cursor.getString(1),cursor.getInt(2));
                cursorItem.id = cursor.getInt(0);
                cursorItem.isChecked = cursor.getInt(3);
                cursorData.add(cursorItem);

            } while (cursor.moveToNext());
        }
        db.close();
        return  cursorData;
    }

    public int addTask(ListItem task) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {

            ContentValues values = new ContentValues();
            values.put(KEY_TASKNAME, task.taskName);
            values.put(KEY_PRIORITY, task.priority);
            values.put(KEY_CHECKED, task.isChecked);

            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            task.id = (int) db.insertOrThrow(TABLE_TASKS, null, values);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add task to database");
        } finally {
            db.endTransaction();
            return task.id;
        }
    }
}

