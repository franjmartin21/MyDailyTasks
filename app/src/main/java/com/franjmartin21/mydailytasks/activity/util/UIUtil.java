package com.franjmartin21.mydailytasks.activity.util;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.franjmartin21.mydailytasks.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UIUtil {

    private static UIUtil INSTANCE;

    private DateFormat dateFormat;

    private UIUtil(Context context){
        dateFormat = new SimpleDateFormat(context.getString(R.string.date_format_ui),new Locale(context.getString(R.string.date_locale_ui)));
    }

    public synchronized static UIUtil getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new UIUtil(context);
        }
        return INSTANCE;
    }

    public void openDateDialog(Context context, final View editText){
        final EditText editText1 = (EditText)editText;
        final Calendar calendarToday = Calendar.getInstance();
        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                editText1.setText(getDateFromYearMonthDay(year, month, day));
            }
        }, calendarToday.get(Calendar.YEAR), calendarToday.get(Calendar.MONTH),calendarToday.get(Calendar.DAY_OF_MONTH)).show();
    }
    
    private String getDateFromYearMonthDay(int year, int month, int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Date date = calendar.getTime();
        return getStrFromDate(date);
    }

    public String getStrFromDate(Date date){
        return dateFormat.format(date);
    }

    public Date getStrFromDate(String dateStr){
        Date dateReturn = null;
        try{
            dateReturn = dateFormat.parse(dateStr);
        } catch (Exception e){}
        return dateReturn;
    }
}
