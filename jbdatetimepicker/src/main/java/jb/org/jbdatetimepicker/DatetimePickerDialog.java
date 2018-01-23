package jb.org.jbdatetimepicker;

import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by fabiano on 16/01/18.
 */

public class DatetimePickerDialog extends DialogFragment implements DatePicker.OnDateChangedListener, TimePicker.OnTimeChangedListener, Button.OnClickListener {
    private TabHost tabHost;
    private TabHost.TabSpec tabSpec1, tabSpec2;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private Button buttonOk, buttonCancel;

    private DatetimeMode mode;
    private OnDatetimePickerValueSet onDatetimePickerValueSet;
    private View callerView;

    private String datetimeStringPattern;
    private String dateStringPattern;
    private String timeStringPattern;

    private static String DEFAULT_DATETIME_PATTERN = "dd/MM/yyyy hh:mm";
    private static String DEFAULT_DATE_PATTERN = "dd/MM/yyyy";
    private static String DEFAULT_TIME_PATTERN = "hh:mm";

    public DatetimePickerDialog() {
        datetimeStringPattern = DEFAULT_DATETIME_PATTERN;
        dateStringPattern = DEFAULT_DATE_PATTERN;
        timeStringPattern = DEFAULT_TIME_PATTERN;

        mode = DatetimeMode.DATETIME;
    }

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.datetime_picker_fragment, container, false);
        getDialog().setTitle("DialogFragment Tutorial");

        Calendar today = Calendar.getInstance();
        datePicker = rootView.findViewById(R.id.datePicker);
        timePicker = rootView.findViewById(R.id.timePicker);

        datePicker.init(
                today.get(Calendar.YEAR),
                today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH), this);
        timePicker.setOnTimeChangedListener(this);

        tabHost = rootView.findViewById(R.id.tabHost);
        tabHost.setup();

        //Tab 1
        if(DatetimeMode.isDateAvailable(mode)) {
            tabSpec1 = tabHost.newTabSpec("Date");
            tabSpec1.setContent(R.id.tab1);
            tabSpec1.setIndicator(DatetimePickerUtil.getDatetimeString(getSelectedDate(), dateStringPattern));
            tabHost.addTab(tabSpec1);
            datePicker.setVisibility(View.VISIBLE);
        }

        //Tab 2
        if(DatetimeMode.isTimeAvailable(mode)) {
            tabSpec2 = tabHost.newTabSpec("Time");
            tabSpec2.setContent(R.id.tab2);
            tabSpec2.setIndicator(DatetimePickerUtil.getDatetimeString(getSelectedDate(), timeStringPattern));
            tabHost.addTab(tabSpec2);
            timePicker.setVisibility(View.VISIBLE);
        }

        buttonOk = rootView.findViewById(R.id.buttonOk);
        buttonOk.setOnClickListener(this);
        buttonCancel = rootView.findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(this);

        return rootView;
    }

    public String getDatetimeStringPattern() {
        return datetimeStringPattern;
    }

    public void setDatetimeStringPattern(String datetimeStringPattern) {
        this.datetimeStringPattern = datetimeStringPattern;
    }

    public String getDateStringPattern() {
        return dateStringPattern;
    }

    public void setDateStringPattern(String dateStringPattern) {
        this.dateStringPattern = dateStringPattern;
    }

    public String getTimeStringPattern() {
        return timeStringPattern;
    }

    public void setTimeStringPattern(String timeStringPattern) {
        this.timeStringPattern = timeStringPattern;
    }

    public DatetimeMode getMode() {
        return mode;
    }

    public void setMode(DatetimeMode mode) {
        this.mode = mode;
    }

    public OnDatetimePickerValueSet getOnDatetimePickerValueSet() {
        return onDatetimePickerValueSet;
    }

    public void setOnDatetimePickerValueSet(OnDatetimePickerValueSet onDatetimePickerValueSet) {
        this.onDatetimePickerValueSet = onDatetimePickerValueSet;
    }

    public View getCallerView() {
        return callerView;
    }

    public void setCallerView(View callerView) {
        this.callerView = callerView;
    }

    public Date getSelectedDate() {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        Calendar calendar = Calendar.getInstance();

        if(mode == DatetimeMode.DATETIME) {
            calendar.set(year, month, day, hour, minute);
        }
        else if(mode == DatetimeMode.DATE) {
            calendar.set(year, month, day);
        }
        else {
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
        }

        return calendar.getTime();
    }

    @Override
    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
        ViewGroup vg = (ViewGroup) tabHost.getTabWidget().getChildAt(0);
        TextView tv = (TextView) vg.getChildAt(1);
        tv.setText(DatetimePickerUtil.getDatetimeString(getSelectedDate(), dateStringPattern));
    }

    @Override
    public void onTimeChanged(TimePicker timePicker, int i, int i1) {
        ViewGroup vg = (ViewGroup) tabHost.getTabWidget().getChildAt(tabHost.getTabWidget().getChildCount() > 1 ? 1 : 0);
        TextView tv = (TextView) vg.getChildAt(1);
        tv.setText(DatetimePickerUtil.getDatetimeString(getSelectedDate(), timeStringPattern));
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == buttonCancel.getId()) {
            dismiss();
        }
        else if(view.getId() == buttonOk.getId()) {
            onDatetimePickerValueSet.updateDatetimeWidget(getCallerView(), getSelectedDate());
            dismiss();
        }
        else {
            dismiss();
        }
    }
}
