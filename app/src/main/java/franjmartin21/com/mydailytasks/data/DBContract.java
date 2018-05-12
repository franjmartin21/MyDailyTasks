package franjmartin21.com.mydailytasks.data;

import android.provider.BaseColumns;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DBContract {

    public static final DateFormat DB_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final DateFormat DB_DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Date getDateFromDBStrValue(String fieldValue){
        Date date = null;
        if(fieldValue == null) return null;

        try {
            date = DB_DATETIME_FORMAT.parse(fieldValue);
        } catch (ParseException ex){
            Log.e(DBContract.class.getName(), "Problem parsing value " + fieldValue);
        }
        return date;
    }

    public static String getStringForDatabase(Date fieldDate){
        if(fieldDate == null) return null;

        return DB_DATETIME_FORMAT.format(fieldDate);
    }


    public static final class TaskEntry implements BaseColumns {

        public static final String TABLE_NAME = "Task";

        public static final String COLUMN_TITLE = "title";

        public static final String COLUMN_TIMESTAMP = "timestamp";

    }

    public static final class TaskOcurrenceEntry implements BaseColumns{

        public static final String TABLE_NAME = "TaskOcurrence";

        public static final String COLUMN_TASK_ID = "task_Id";

        public static final String COLUMN_DATE = "dateTask";

        public static final String COLUMN_ORDER = "orderTask";

        public static final String COLUMN_COMPLETED = "completed";

        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
