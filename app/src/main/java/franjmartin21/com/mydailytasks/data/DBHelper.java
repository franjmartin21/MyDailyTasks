package franjmartin21.com.mydailytasks.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mydailytasks.db";

    private static final int DATABASE_VERSION = 1;

    private static DBHelper sInstance;

    private DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createTaskTable(sqLiteDatabase);
        createTaskOcurrenceTable(sqLiteDatabase);
    }

    private void createTaskTable(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_TASK_TABLE = "CREATE TABLE " +
                DBContract.TaskEntry.TABLE_NAME + " (" +
                DBContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DBContract.TaskEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                DBContract.TaskEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_TASK_TABLE);
    }


    private void createTaskOcurrenceTable(SQLiteDatabase sqLiteDatabase){
        final String SQL_CREATE_TASKOCURRENCE_TABLE = "CREATE TABLE " +
                DBContract.TaskOcurrenceEntry.TABLE_NAME + " (" +
                DBContract.TaskOcurrenceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DBContract.TaskOcurrenceEntry.COLUMN_TASK_ID + " INTEGER NOT NULL, " +
                DBContract.TaskOcurrenceEntry.COLUMN_DATE + " TEXT NOT NULL, " +
                DBContract.TaskOcurrenceEntry.COLUMN_ORDER + " INTEGER NOT NULL, " +
                DBContract.TaskOcurrenceEntry.COLUMN_COMPLETED + " INTEGER NOT NULL, " +
                DBContract.TaskOcurrenceEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY(" + DBContract.TaskOcurrenceEntry.COLUMN_TASK_ID + ") REFERENCES " + DBContract.TaskEntry.TABLE_NAME + "(" + DBContract.TaskEntry._ID + "))";

        sqLiteDatabase.execSQL(SQL_CREATE_TASKOCURRENCE_TABLE);
    }


    //todo: (v0.2) onUpdate should not delete but update without making the user lose its data
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // For now simply drop the table and create a new one. This means if you change the
        // DATABASE_VERSION the table will be dropped.
        // In a production app, this method might be modified to ALTER the table
        // instead of dropping it, so that existing data is not deleted.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBContract.TaskOcurrenceEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public static synchronized DBHelper getInstance(Context context) {

        if (sInstance == null) {
            sInstance = new DBHelper(context.getApplicationContext());
        }
        return sInstance;
    }
}
