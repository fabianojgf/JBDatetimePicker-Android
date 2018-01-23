package jb.org.jbdatetimepicker;

import android.view.View;

import java.util.Date;

/**
 * Created by fabiano on 17/01/18.
 */

public interface OnDatetimePickerValueSet {
    void updateDatetimeWidget(View v, Date date);
}
