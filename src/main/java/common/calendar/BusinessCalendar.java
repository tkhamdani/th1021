package common.calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BusinessCalendar {

    public static final int NORMAL_INDEPENDENCE_DAY_OF_THE_MONTH = 4;
    public static final int NORMAL_LABOR_DAY_OF_THE_MONTH = 1;

    public BusinessCalendar() {}

    public static boolean isWeekday(Date date) {
        Calendar calendar = getCalendar(date);
        return isWeekday(calendar);
    }

    public static boolean isWeekend(Date date) {
        Calendar calendar = getCalendar(date);;
        return isWeekend(calendar);
    }

    public static boolean isHoliday(Date date) {
        Calendar calendar = getCalendar(date);;
        return isHoliday(calendar);
    }

    public static boolean isIndependenceDay(Date date) {
        Calendar calendar = getCalendar(date);
        return isIndependenceDay(calendar);
    }

    public static boolean isLaborDay(Date date) {
        Calendar calendar = getCalendar(date);
        return isLaborDay(calendar);
    }

    public static Date getIndependenceDayForYear(int year) {
        return getIndependenceDayCalForYear(year).getTime();
    }

    public static Date getLaborDayForYear(int year) {
        return getLaborDayCalForYear(year).getTime();
    }

    public static boolean isWithinRange(Date startDate1, Date endDate1, Date startDate2, Date endDate2) {
        if(startDate1.compareTo(endDate1) > 0) {
            Date tmp = endDate1;
            endDate1 = startDate1;
            startDate1 = tmp;
        }

        if(startDate2.compareTo(endDate2) > 0) {
            Date tmp = endDate2;
            endDate2 = startDate2;
            startDate2 = tmp;
        }

        boolean isWithinRange = true;
        if(startDate1.compareTo(endDate2) > 0 || endDate1.compareTo(startDate2) < 0) {
            isWithinRange = false;
        }

        return isWithinRange;
    }

    public static boolean isWeekday(Calendar dayOfCalendar) {
        boolean isWeekday = false;

        switch(dayOfCalendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
            case Calendar.TUESDAY:
            case Calendar.WEDNESDAY:
            case Calendar.THURSDAY:
            case Calendar.FRIDAY:
                isWeekday = true;
                break;
        }

        return isWeekday;
    }

    public static boolean isWeekend(Calendar dayOfCalendar) {
        boolean isWeekday = false;

        switch(dayOfCalendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SATURDAY:
            case Calendar.SUNDAY:
                isWeekday = true;
                break;
        }

        return isWeekday;
    }

    public static boolean isHoliday(Calendar dayOfCalendar) {
        if(isIndependenceDay(dayOfCalendar)) {
            return true;
        }

        if(isLaborDay(dayOfCalendar)) {
            return true;
        } 
        
        return false;
    }

    public static boolean isIndependenceDay(Calendar dayOfCalendar) {
        boolean isIndependenceDay = false;

        Calendar independenceDay = getIndependenceDayCalForYear(dayOfCalendar.get(Calendar.YEAR));

        if(dayOfCalendar.get(Calendar.DAY_OF_WEEK) == independenceDay.get(Calendar.DAY_OF_WEEK) &&
                dayOfCalendar.get(Calendar.DAY_OF_MONTH) == independenceDay.get(Calendar.DAY_OF_MONTH) &&
                dayOfCalendar.get(Calendar.YEAR) == independenceDay.get(Calendar.YEAR)) {
            isIndependenceDay = true;
        }

        return isIndependenceDay;
    }


    public static boolean isLaborDay(Calendar dayOfCalendar) {
        boolean isLaborDay = false;

        Calendar laborDay = getLaborDayCalForYear(dayOfCalendar.get(Calendar.YEAR));

        if(dayOfCalendar.get(Calendar.DAY_OF_WEEK) == laborDay.get(Calendar.DAY_OF_WEEK) &&
                dayOfCalendar.get(Calendar.DAY_OF_MONTH) == laborDay.get(Calendar.DAY_OF_MONTH) &&
                dayOfCalendar.get(Calendar.YEAR) == laborDay.get(Calendar.YEAR)) {
            isLaborDay = true;
        }

        return isLaborDay;
    }

    public static Calendar getIndependenceDayCalForYear(int year) {
        Calendar independenceDay = Calendar.getInstance();
        independenceDay.set(Calendar.DAY_OF_MONTH, NORMAL_INDEPENDENCE_DAY_OF_THE_MONTH);
        independenceDay.set(Calendar.MONTH, Calendar.JULY);
        independenceDay.set(Calendar.YEAR, year);
        independenceDay.set(Calendar.HOUR_OF_DAY, 0);
        independenceDay.set(Calendar.MINUTE, 0);
        independenceDay.set(Calendar.SECOND, 0);

        if(isWeekend(independenceDay)) {
            switch (independenceDay.get(Calendar.DAY_OF_WEEK)) {
                case Calendar.SATURDAY:
                    independenceDay.add(Calendar.DATE, -1);
                    break;
                case Calendar.SUNDAY:
                    independenceDay.add(Calendar.DATE, 1);
                    break;
            }
        }

        return independenceDay;
    }

    public static Calendar getLaborDayCalForYear(int year) {
        Calendar laborDay = Calendar.getInstance();
        laborDay.set(Calendar.DAY_OF_MONTH, NORMAL_LABOR_DAY_OF_THE_MONTH);
        laborDay.set(Calendar.MONTH, Calendar.SEPTEMBER);
        laborDay.set(Calendar.YEAR, year);
        laborDay.set(Calendar.HOUR_OF_DAY, 0);
        laborDay.set(Calendar.MINUTE, 0);
        laborDay.set(Calendar.SECOND, 0);

        while(Calendar.MONDAY != laborDay.get(Calendar.DAY_OF_WEEK)) {
            laborDay.add(Calendar.DATE, 1);
        }

        return laborDay;
    }

    public static Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Date getTimeToBeforeMidnight(Date date) {
        Calendar endDate = BusinessCalendar.getCalendar(date);
        endDate.set(Calendar.HOUR_OF_DAY, 23);
        endDate.set(Calendar.MINUTE, 59);
        endDate.set(Calendar.SECOND, 59);
        return endDate.getTime();
    }

    public static Date parse(String date, String format) throws ParseException {
        return new SimpleDateFormat(format).parse(date);
    }

    public static int compare(Date date1, Date date2) {
        Calendar cal1 = BusinessCalendar.getCalendar(date1);
        Calendar cal2 = BusinessCalendar.getCalendar(date2);

        if(cal1.get(Calendar.YEAR) < cal2.get(Calendar.YEAR)) {
            return -1;
        } else if(cal1.get(Calendar.YEAR) > cal2.get(Calendar.YEAR)) {
            return 1;
        } else if(cal1.get(Calendar.MONTH) < cal2.get(Calendar.MONTH)) {
            return -1;
        } else if(cal1.get(Calendar.MONTH) > cal2.get(Calendar.MONTH)) {
            return 1;
        } else if(cal1.get(Calendar.DAY_OF_YEAR) < cal2.get(Calendar.DAY_OF_YEAR)) {
            return -1;
        } else if(cal1.get(Calendar.DAY_OF_YEAR) > cal2.get(Calendar.DAY_OF_YEAR)) {
            return 1;
        } else {
            return 0;
        }
    }
}
