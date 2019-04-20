package com.zare.karbala;

import android.app.AlarmManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import DataBase.db_class;
import DataBase.feilddb;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class add_note extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    EditText edit_title_log, edit_Text_log;


    long timeInMilliseconds;

    db_class dbClass;

    TextView set_date, set_time;

    DatePickerDialog datePickerDialog;


    private static final String TIMEPICKER = "TimePickerDialog";

    FragmentTransaction ft;

    private CheckBox mode24Hours;

    final static int RQS_1 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_add_note);


        dbClass = new db_class(add_note.this);

        edit_Text_log = (EditText) findViewById(R.id.edit_Text_log);

        edit_title_log = (EditText) findViewById(R.id.edit_title_log);


        mode24Hours = findViewById(R.id.mode_24_hours);

        set_date = (TextView) findViewById(R.id.set_date);

        set_time = (TextView) findViewById(R.id.set_time);


    }

    public void date(View view) {

        ft = getFragmentManager()
                .beginTransaction();

        PersianCalendar persianCalendar = new PersianCalendar();

        datePickerDialog = DatePickerDialog.newInstance(
                add_note.this,
                persianCalendar.getPersianYear(),
                persianCalendar.getPersianMonth(),
                persianCalendar.getPersianDay()
        );
        datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {

            }
        });
        datePickerDialog.show(ft, "Datepickerdialog");
    }

    public void time(View view) {

        PersianCalendar now = new PersianCalendar();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                add_note.this,
                now.get(PersianCalendar.HOUR_OF_DAY),
                now.get(PersianCalendar.MINUTE),
                mode24Hours.isChecked()
        );

        tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
            }
        });
        tpd.show(getFragmentManager(), TIMEPICKER);
    }

    public void save(View view) {

        feilddb fld = new feilddb();



        if (
                set_date.getText().toString().trim().equalsIgnoreCase("") ||
                        set_time.getText().toString().trim().equalsIgnoreCase("") ||
                        edit_title_log.getText().toString().trim().equalsIgnoreCase("") ||
                        edit_Text_log.getText().toString().trim().equalsIgnoreCase("")
                         ) {
            NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(add_note.this);
            dialogBuilder
                    .withTitle("توجه بفرمایید")
                    .withMessage("پر کردن تمامی موارد اجباری هست.")
                    .show();
        } else {
            fld.setDate(set_date.getText().toString().trim());

            fld.setTime(set_time.getText().toString());

            fld.setTitle(edit_title_log.getText().toString().trim());

            fld.setText(edit_Text_log.getText().toString().trim());

            dbClass.addnote(fld);

            edit_title_log.setText("");

            edit_Text_log.setText("");

            set_date.setText("");

            set_time.setText("");

            String time = set_date.getText().toString();


            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date mDate = sdf.parse(time);
                timeInMilliseconds = mDate.getTime();

            } catch (ParseException e) {
                e.printStackTrace();
            }


//            Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
//            PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
//            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//            alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMilliseconds, pendingIntent);
        }

    }


    public void yadasht_rozane (View view)
    {
        startActivity(new Intent(add_note.this, a_notpad.class));
        finish();
    }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "/" + "0" + (monthOfYear + 1) + "/" + year;
        set_date.setText(date);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(add_note.this, a_notpad.class));
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String time = hourString + ":" + minuteString;
        set_time.setText(time);
    }
}
