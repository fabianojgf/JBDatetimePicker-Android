package jb.org.jbdatetimepicker;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fabiano on 23/01/18.
 */

public class DatetimePickerUtil {
    public static String getDatetimeString(Date date, String pattern) {
        SimpleDateFormat formater = new SimpleDateFormat(pattern);
        return formater.format(date);
    }
}
