package franjmartin21.com.mydailytasks.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    /* Context used to perform operations on the database and create TaskDB */
    private final Context mContext = InstrumentationRegistry.getTargetContext();
    /* Class reference to help load the constructor on runtime */
    private final Class mDbHelperClass = DBHelper.class;

    private SQLiteOpenHelper dbHelper;

    private SQLiteDatabase database;

    @Before
    public void setUp() throws Exception{
        deleteTheDatabase();
        dbHelper = (SQLiteOpenHelper) mDbHelperClass.getMethod("getInstance",Context.class).invoke(this, mContext);
        database = dbHelper.getWritableDatabase();
    }

    @Test
    public void createDatabaseTest() throws Exception {

        /* We think the database is open, let's verify that here */
        String databaseIsNotOpen = "The database should be open and isn't";
        assertEquals(databaseIsNotOpen,
                true,
                database.isOpen());

        /* This Cursor will contain the names of each table in our database */
        Cursor tableNameCursor = database.rawQuery(
                "SELECT name FROM sqlite_master WHERE type='table' AND name='" +
                        DBContract.TaskEntry.TABLE_NAME + "'",
                null);

        /*
         * If tableNameCursor.moveToFirst returns false from this query, it means the database
         * wasn't created properly. In actuality, it means that your database contains no tables.
         */
        String errorInCreatingDatabase = "Error: This means that the database has not been created correctly";
        assertTrue(errorInCreatingDatabase, tableNameCursor.moveToFirst());

        /* If this fails, it means that your database doesn't contain the expected table(s) */
        assertEquals("Error: Your database was created without the expected tables.",
                DBContract.TaskEntry.TABLE_NAME, tableNameCursor.getString(0));

        /* Always close a cursor when you are done with it */
        tableNameCursor.close();
    }

    @Test
    public void insertRecordTaskTest() throws Exception{

        ContentValues testValues = new ContentValues();
        String VALUE_TITLE = "First Task";

        testValues.put(DBContract.TaskEntry.COLUMN_TITLE, VALUE_TITLE);

        /* Insert ContentValues into database and get first row ID back */
        long firstRowId = database.insert(
                DBContract.TaskEntry.TABLE_NAME,
                null,
                testValues);

        /* If the insert fails, database.insert returns -1 */
        assertNotEquals("Unable to insert into the database", -1, firstRowId);

        /*
         * Query the database and receive a Cursor. A Cursor is the primary way to interact with
         * a database in Android.
         */
        Cursor wCursor = database.query(
                /* Name of table on which to perform the query */
                DBContract.TaskEntry.TABLE_NAME,
                /* Columns; leaving this null returns every column in the table */
                null,
                /* Optional specification for columns in the "where" clause above */
                null,
                /* Values for "where" clause */
                null,
                /* Columns to group by */
                null,
                /* Columns to filter by row groups */
                null,
                /* Sort order to return in Cursor */
                null);

        /* Cursor.moveToFirst will return false if there are no records returned from your query */
        String emptyQueryError = "Error: No Records returned from query";
        assertTrue(emptyQueryError, wCursor.moveToFirst());
        assertEquals(VALUE_TITLE, wCursor.getString(wCursor.getColumnIndex(DBContract.TaskEntry.COLUMN_TITLE)));
        /* Close cursor and database */
        wCursor.close();
    }

    @Test
    public void insertRecordTaskOcurrenceTest() throws Exception{
        insertRecordTaskTest();

        ContentValues testValues = new ContentValues();
        int TASK_ID= 1;
        Date TASK_DATE = new Date();
        int TASK_ORDER = 1;
        boolean TASK_COMPLETE = true;

        testValues.put(DBContract.TaskOcurrenceEntry.COLUMN_TASK_ID, TASK_ID);
        testValues.put(DBContract.TaskOcurrenceEntry.COLUMN_DATE, DBContract.getStringForDatabase(TASK_DATE));
        testValues.put(DBContract.TaskOcurrenceEntry.COLUMN_COMPLETED, TASK_COMPLETE);
        testValues.put(DBContract.TaskOcurrenceEntry.COLUMN_ORDER, TASK_ORDER);

        long firstRowId = database.insert(
                DBContract.TaskOcurrenceEntry.TABLE_NAME,
                null,
                testValues);

        assertNotEquals("Unable to insert into the database", -1, firstRowId);

        Cursor wCursor = database.query(
                DBContract.TaskOcurrenceEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        /* Cursor.moveToFirst will return false if there are no records returned from your query */
        String emptyQueryError = "Error: No Records returned from query";
        assertTrue(emptyQueryError, wCursor.moveToFirst());
        assertEquals(TASK_ID, wCursor.getInt(wCursor.getColumnIndex(DBContract.TaskOcurrenceEntry.COLUMN_TASK_ID)));
        assertEquals(DBContract.getStringForDatabase(TASK_DATE), wCursor.getString(wCursor.getColumnIndex(DBContract.TaskOcurrenceEntry.COLUMN_DATE)));
        assertEquals(TASK_ORDER, wCursor.getInt(wCursor.getColumnIndex(DBContract.TaskOcurrenceEntry.COLUMN_ORDER)));
        assertEquals(TASK_COMPLETE, wCursor.getInt(wCursor.getColumnIndex(DBContract.TaskOcurrenceEntry.COLUMN_COMPLETED)) == 1? true: false);
        /* Close cursor and database */
        wCursor.close();
    }

    void deleteTheDatabase() {
        try {
            /* Use reflection to get the database name from the db helper class */
            Field f = mDbHelperClass.getDeclaredField("DATABASE_NAME");
            f.setAccessible(true);
            mContext.deleteDatabase((String) f.get(null));
        } catch (NoSuchFieldException ex) {
            fail("Make sure you have a member called DATABASE_NAME in the DBHelper");
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @After
    public void afterTest(){
        dbHelper.close();
    }
}