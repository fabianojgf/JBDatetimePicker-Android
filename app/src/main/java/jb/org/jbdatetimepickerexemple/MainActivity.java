package jb.org.jbdatetimepickerexemple;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Date;

import jb.org.jbdatetimepicker.DatetimeMode;
import jb.org.jbdatetimepicker.DatetimePickerDialog;
import jb.org.jbdatetimepicker.DatetimePickerUtil;
import jb.org.jbdatetimepicker.OnDatetimePickerValueSet;

public class MainActivity extends AppCompatActivity {
    FragmentManager fm = getFragmentManager();
    EditText editText;
    private String datetimeStringPattern = "dd/MM/yyyy hh:mm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        editText.setTextKeepState(DatetimePickerUtil.getDatetimeString(new Date(), datetimeStringPattern));
    }

    public void onDatetimePicker(final View view) {
        fm.beginTransaction();
        DatetimePickerDialog dialog = new DatetimePickerDialog();
        dialog.setCallerView(view);
        dialog.setMode(DatetimeMode.DATETIME);
        dialog.setOnDatetimePickerValueSet(new OnDatetimePickerValueSet() {
            @Override
            public void updateDatetimeWidget(View v, Date date) {
                ((EditText) view).setText(DatetimePickerUtil.getDatetimeString(date, datetimeStringPattern));
            }
        });
        dialog.show(fm, "Dialog Fragment");
    }
}
