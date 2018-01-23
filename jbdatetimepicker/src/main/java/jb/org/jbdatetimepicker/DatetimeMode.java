package jb.org.jbdatetimepicker;

/**
 * Created by fabiano on 17/01/18.
 */

public enum DatetimeMode {
    DATETIME, DATE, TIME;

    public static boolean isDateAvailable(DatetimeMode mode) {
        if(mode == DATETIME || mode == DATE)
            return true;
        return false;
    }

    public static boolean isTimeAvailable(DatetimeMode mode) {
        if(mode == DATETIME || mode == TIME)
            return true;
        return false;
    }
}
