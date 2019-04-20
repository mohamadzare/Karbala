/**************************************************************************
 *
 * Copyright (C) 2012-2015 Alex Taradov <alex@taradov.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *************************************************************************/

package com.zare.karbala;

import java.text.DateFormat;
import java.util.Date;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.text.TextWatcher;
import android.text.Editable;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ListView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.content.Intent;
import android.content.Context;
import android.content.DialogInterface;

import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import Adapter.CalendarTool;
import Model.sharedprefrencce;
import calendar.CivilDate;
import calendar.DateConverter;
import calendar.PersianDate;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class EditAlarm extends Activity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private EditText mTitle;

    private CheckBox mAlarmEnabled;

    private Spinner mOccurence;


    private Button mDateButton;

    private Button mTimeButton;

    private Alarm mAlarm;

    private DateTime mDateTime;

    private GregorianCalendar mCalendar;

    private int mYear;

    private int mMonth;

    private int mDay;

    private int mHour;

    private int mMinute;

    FragmentTransaction ft;

    static final int DATE_DIALOG_ID = 0;
    static final int TIME_DIALOG_ID = 1;
    static final int DAYS_DIALOG_ID = 2;

    DatePickerDialog datePickerDialog;

    Button cancel,done;

    TextView note;

    String Day_Month_Year;

    private static final String TIMEPICKER = "TimePickerDialog";

    private CheckBox mode24Hours;

    SharedPreferences prefs_s, prefs;

    String lan;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.edit);

        note = (TextView) findViewById(R.id.note);

        cancel = (Button) findViewById(R.id.cancel);

        done = (Button) findViewById(R.id.done);


        prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

        String MyToken = prefs.getString(sharedprefrencce.MyToken, "");

        if ((MyToken.isEmpty())) {


        } else {

            SharedPreferences prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

            lan = prefs.getString(sharedprefrencce.MyToken, "");

            if (lan.equalsIgnoreCase("arab")) {


                done.setText("تسجيل");

                cancel.setText("إلغاء");

                note.setText("مذكرة");

            } else if (lan.equalsIgnoreCase("kingdom")) {


                note.setText("note");


                done.setText("Set");

                cancel.setText("cancel");

            } else if (lan.equalsIgnoreCase("iran")) {

                note.setText("یادداشت");

                done.setText("ثبت");

                cancel.setText("بی خیال");
            }

        }


        mTitle = (EditText) findViewById(R.id.title);

        mAlarmEnabled = (CheckBox) findViewById(R.id.alarm_checkbox);

        mOccurence = (Spinner) findViewById(R.id.occurence_spinner);

        mDateButton = (Button) findViewById(R.id.date_button);

        mTimeButton = (Button) findViewById(R.id.time_button);


        mode24Hours = findViewById(R.id.mode_24_hours);

        mAlarm = new Alarm(this);
        mAlarm.fromIntent(getIntent());

        mDateTime = new DateTime(this);

        mTitle.setText(mAlarm.getTitle());
        mTitle.addTextChangedListener(mTitleChangedListener);

        mOccurence.setSelection(mAlarm.getOccurence());
        mOccurence.setOnItemSelectedListener(mOccurenceSelectedListener);

        mAlarmEnabled.setChecked(mAlarm.getEnabled());
        mAlarmEnabled.setOnCheckedChangeListener(mAlarmEnabledChangeListener);


        mCalendar = new GregorianCalendar();

        mCalendar.setTimeInMillis(mAlarm.getDate());

//
//        mYear = mCalendar.get(Calendar.YEAR);
//
//        mMonth = mCalendar.get(Calendar.MONTH);
//
//        mDay = mCalendar.get(Calendar.DAY_OF_MONTH);
////
////
//        CalendarTool ct = new CalendarTool(mYear, mMonth, mDay);
//
//        Day_Month_Year = ct.getIranianDate();
//
//        String[] fc
//                = Day_Month_Year.split("/");
//
//        String d, m, y;
//
//        d = fc[0];
//        m = fc[1];
//        if (m.length() == 1) {
//            m = 0 + m;
//        }
//        y = fc[2];
//        if (y.length() == 1) {
//            y = 0 + y;
//        }
//        Day_Month_Year = y + "/" + m + "/" + d;
//
//        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
//
//
//        mYear = Integer.parseInt(d);
//
//        mMonth = Integer.parseInt(m);
//
//        mMonth = mMonth + 1;
//
//        mDay = Integer.parseInt(y);


        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = mCalendar.get(Calendar.MINUTE);

        updateButtons();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(EditAlarm.this, AlarmMe.class));
        finish();
    }

//    @Override
//    protected Dialog onCreateDialog(int id) {
//        if (DATE_DIALOG_ID == id) {
//            return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
//
//
//        } else if (TIME_DIALOG_ID == id)
//            return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute, mDateTime.is24hClock());
//        else if (DAYS_DIALOG_ID == id)
//            return DaysPickerDialog();
//        else
//            return null;
//    }
//
//    @Override
//    protected void onPrepareDialog(int id, Dialog dialog) {
//        if (DATE_DIALOG_ID == id)
//            ((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
//        else if (TIME_DIALOG_ID == id)
//            ((TimePickerDialog) dialog).updateTime(mHour, mMinute);
//    }

    public void onDateClick(View view) {
        ft = getFragmentManager()
                .beginTransaction();

        PersianCalendar persianCalendar = new PersianCalendar();

        datePickerDialog = DatePickerDialog.newInstance(
                EditAlarm.this,
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

    public void onTimeClick(View view) {
        PersianCalendar now = new PersianCalendar();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                EditAlarm.this,
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

    public void onDoneClick(View view) {
        Intent intent = new Intent();

        mAlarm.toIntent(intent);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onCancelClick(View view) {
        setResult(RESULT_CANCELED, null);
        finish();
    }

//    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
//        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//            mYear = year;
//            mMonth = monthOfYear;
//            mDay = dayOfMonth;
//
//            mCalendar = new GregorianCalendar(mYear, mMonth, mDay, mHour, mMinute);
//            mAlarm.setDate(mCalendar.getTimeInMillis());
//
//            updateButtons();
//        }
//    };
//
//    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
//        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//            mHour = hourOfDay;
//            mMinute = minute;
//
//            mCalendar = new GregorianCalendar(mYear, mMonth, mDay, mHour, mMinute);
//            mAlarm.setDate(mCalendar.getTimeInMillis());
//
//            updateButtons();
//        }
//    };

    private TextWatcher mTitleChangedListener = new TextWatcher() {
        public void afterTextChanged(Editable s) {
            mAlarm.setTitle(mTitle.getText().toString());
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    };

    private AdapterView.OnItemSelectedListener mOccurenceSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            mAlarm.setOccurence(position);
            updateButtons();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    private CompoundButton.OnCheckedChangeListener mAlarmEnabledChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            mAlarm.setEnabled(isChecked);
        }
    };

    private void updateButtons() {
        if (Alarm.ONCE == mAlarm.getOccurence())
            mDateButton.setText("انتخاب تاریخ");
        else if (Alarm.WEEKLY == mAlarm.getOccurence())
            mDateButton.setText("انتخاب تاریخ");
        mTimeButton.setText("انتخاب ساعت");
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    private Dialog DaysPickerDialog() {
        AlertDialog.Builder builder;
        final boolean[] days = mDateTime.getDays(mAlarm);
        final String[] names = mDateTime.getFullDayNames();

        builder = new AlertDialog.Builder(this);

        builder.setTitle("Week days");

        builder.setMultiChoiceItems(names, days, new DialogInterface.OnMultiChoiceClickListener() {
            public void onClick(DialogInterface dialog, int whichButton, boolean isChecked) {
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                mDateTime.setDays(mAlarm, days);
                updateButtons();
            }
        });

        builder.setNegativeButton("Cancel", null);

        return builder.create();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        mYear = year;
        mMonth = monthOfYear;
        mDay = dayOfMonth;

        PersianDate date = new PersianDate(mYear, mMonth + 1, mDay);

        CivilDate civilDate = DateConverter.persianToCivil(date);

        String time = (civilDate.toString());
        String[] fc
                = time.split("/");

        String d, m, y;

        y = fc[0];
        m = fc[1];
        d = fc[2];


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd", this.getResources().getConfiguration().locale);
        try {
            Date datess = formatter.parse(time);

            Calendar c = new GregorianCalendar();

            int ccc = datess.getDay();

            int mc = date.getMonth();

            int sc = date.getYear();

            mCalendar = new GregorianCalendar();

            mCalendar.setTimeInMillis(mAlarm.getDate());

            mYear = mCalendar.get(c.YEAR);

            mMonth = mCalendar.get(c.MONTH);

            mDay = mCalendar.get(c.DAY_OF_MONTH);

            long mss = c.getTimeInMillis();

            c.setTime(datess);

        } catch (Exception e) {
        }

//        mYear = Integer.parseInt(y);
//        mMonth = Integer.parseInt(m);
//        mDay = Integer.parseInt(d);

        mCalendar = new GregorianCalendar(mYear, mMonth , mDay, mHour, mMinute);


        mAlarm.setDate(mCalendar.getTimeInMillis());

        updateButtons();

        mTimeButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {

        mHour = hourOfDay;
        mMinute = minute;

//        PersianDate date = new PersianDate(mYear, mMonth, mDay);
//
//        CivilDate civilDate = DateConverter.persianToCivil(date);
//
//        civilDate.getDayOfMonth();
//        civilDate.getDayOfWeek();
//        civilDate.getDayOfMonth();


        mCalendar = new GregorianCalendar(mYear, mMonth, mDay, mHour, mMinute);
        mAlarm.setDate(mCalendar.getTimeInMillis());

        updateButtons();

    }
}

