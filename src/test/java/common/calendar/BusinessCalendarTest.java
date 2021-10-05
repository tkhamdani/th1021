package common.calendar;

import common.constants.StringFormats;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BusinessCalendarTest {

    private SimpleDateFormat formatter = new SimpleDateFormat(StringFormats.MM_dd_yyyy);
    private Date date = new Date();
    
    @Test
    public void isWeekdayTests() throws ParseException {
        // Monday October 4th 2021
        date = formatter.parse("10/04/2021");
        Assert.assertTrue(BusinessCalendar.isWeekday(date));

        // Tuesday October 5th 2021
        date = formatter.parse("10/05/2021");
        Assert.assertTrue(BusinessCalendar.isWeekday(date));

        // Wednesday October 6th 2021
        date = formatter.parse("10/06/2021");
        Assert.assertTrue(BusinessCalendar.isWeekday(date));

        // Thursday October 7th 2021
        date = formatter.parse("10/07/2021");
        Assert.assertTrue(BusinessCalendar.isWeekday(date));

        // Friday October 8th 2021
        date = formatter.parse("10/08/2021");
        Assert.assertTrue(BusinessCalendar.isWeekday(date));

        // Saturday October 9th 2021
        date = formatter.parse("10/09/2021");
        Assert.assertFalse(BusinessCalendar.isWeekday(date));

        // Sunday October 10th 2021
        date = formatter.parse("10/10/2021");
        Assert.assertFalse(BusinessCalendar.isWeekday(date));

    }

    @Test
    public void isWeekendTests() throws ParseException {
        // Monday October 4th 2021
        date = formatter.parse("10/04/2021");
        Assert.assertFalse(BusinessCalendar.isWeekend(date));

        // Tuesday October 5th 2021
        date = formatter.parse("10/05/2021");
        Assert.assertFalse(BusinessCalendar.isWeekend(date));

        // Wednesday October 6th 2021
        date = formatter.parse("10/06/2021");
        Assert.assertFalse(BusinessCalendar.isWeekend(date));

        // Thursday October 7th 2021
        date = formatter.parse("10/07/2021");
        Assert.assertFalse(BusinessCalendar.isWeekend(date));

        // Friday October 8th 2021
        date = formatter.parse("10/08/2021");
        Assert.assertFalse(BusinessCalendar.isWeekend(date));

        // Saturday October 9th 2021
        date = formatter.parse("10/09/2021");
        Assert.assertTrue(BusinessCalendar.isWeekend(date));

        // Sunday October 10th 2021
        date = formatter.parse("10/10/2021");
        Assert.assertTrue(BusinessCalendar.isWeekend(date));

    }

    @Test
    public void getIndependenceDayTests() throws ParseException {
        // Independence Day:    Sunday July 4th 2021
        // Public Holiday:      Monday July 5th 2021
        int year = 2021;
        date = formatter.parse("07/05/2021");
        Assert.assertTrue(DateUtils.isSameDay(date, BusinessCalendar.getIndependenceDayForYear(year)));

        // Independence Day:    Saturday July 4th 2020
        // Public Holiday:      Friday July 3rd 2020
        year = 2020;
        date = formatter.parse("07/03/2020");
        Assert.assertTrue(DateUtils.isSameDay(date, BusinessCalendar.getIndependenceDayForYear(year)));

        // Independence Day:    Thursday July 4th 2019
        // Public Holiday:      Thursday July 4th 2019
        year = 2019;
        date = formatter.parse("07/04/2019");
        Assert.assertTrue(DateUtils.isSameDay(date, BusinessCalendar.getIndependenceDayForYear(year)));

        // Independence Day:    Wednesday July 4th 2018
        // Public Holiday:      Wednesday July 4th 2018
        year = 2018;
        date = formatter.parse("07/04/2018");
        Assert.assertTrue(DateUtils.isSameDay(date, BusinessCalendar.getIndependenceDayForYear(year)));

        // Independence Day:    Tuesday July 4th 2017
        // Public Holiday:      Tuesday July 4th 2017
        year = 2017;
        date = formatter.parse("07/04/2017");
        Assert.assertTrue(DateUtils.isSameDay(date, BusinessCalendar.getIndependenceDayForYear(year)));
    }

    @Test
    public void getLaborDayTests() throws ParseException {
        int year = 2021;
        date = formatter.parse("09/06/2021");
        Assert.assertTrue(DateUtils.isSameDay(date, BusinessCalendar.getLaborDayForYear(year)));

        year = 2020;
        date = formatter.parse("09/07/2020");
        Assert.assertTrue(DateUtils.isSameDay(date, BusinessCalendar.getLaborDayForYear(year)));

        year = 2019;
        date = formatter.parse("09/02/2019");
        Assert.assertTrue(DateUtils.isSameDay(date, BusinessCalendar.getLaborDayForYear(year)));

        year = 2018;
        date = formatter.parse("09/03/2018");
        Assert.assertTrue(DateUtils.isSameDay(date, BusinessCalendar.getLaborDayForYear(year)));

        year = 2017;
        date = formatter.parse("09/04/2017");
        Assert.assertTrue(DateUtils.isSameDay(date, BusinessCalendar.getLaborDayForYear(year)));
    }

    @Test
    public void isWithinRangeTests() throws ParseException {
        Date startDate1;
        Date endDate1;
        Date startDate2;
        Date endDate2;

        // Case 1:
        //      Date Range 1             |   |
        //      Date Range 2     |   |
        startDate1 = formatter.parse("10/08/2021");
        endDate1 = BusinessCalendar.getTimeToBeforeMidnight(formatter.parse("10/12/2021"));
        startDate2 = formatter.parse("10/01/2021");
        endDate2 = BusinessCalendar.getTimeToBeforeMidnight(formatter.parse("10/05/2021"));
        Assert.assertFalse(BusinessCalendar.isWithinRange(startDate1, endDate1, startDate2, endDate2));

        // Case 2:
        //      Date Range 1     |   |
        //      Date Range 2        |   |
        startDate1 = formatter.parse("10/01/2021");
        endDate1 = BusinessCalendar.getTimeToBeforeMidnight(formatter.parse("10/09/2021"));
        startDate2 = formatter.parse("10/05/2021");
        endDate2 = BusinessCalendar.getTimeToBeforeMidnight(formatter.parse("10/15/2021"));
        Assert.assertTrue(BusinessCalendar.isWithinRange(startDate1, endDate1, startDate2, endDate2));

        // Case 3:
        //      Date Range 1     |          |
        //      Date Range 2        |   |
        startDate1 = formatter.parse("10/01/2021");
        endDate1 = BusinessCalendar.getTimeToBeforeMidnight(formatter.parse("10/20/2021"));
        startDate2 = formatter.parse("10/05/2021");
        endDate2 = BusinessCalendar.getTimeToBeforeMidnight(formatter.parse("10/10/2021"));
        Assert.assertTrue(BusinessCalendar.isWithinRange(startDate1, endDate1, startDate2, endDate2));

        // Case 4:
        //      Date Range 1    |       |
        //      Date Range 2          |   |
        startDate1 = formatter.parse("10/01/2021");
        endDate1 = BusinessCalendar.getTimeToBeforeMidnight(formatter.parse("10/09/2021"));
        startDate2 = formatter.parse("10/05/2021");
        endDate2 = BusinessCalendar.getTimeToBeforeMidnight(formatter.parse("10/20/2021"));
        Assert.assertTrue(BusinessCalendar.isWithinRange(startDate1, endDate1, startDate2, endDate2));

        // Case 5:
        //      Date Range 1    |   |
        //      Date Range 2          |   |
        startDate1 = formatter.parse("10/01/2021");
        endDate1 = BusinessCalendar.getTimeToBeforeMidnight(formatter.parse("10/05/2021"));
        startDate2 = formatter.parse("10/10/2021");
        endDate2 = BusinessCalendar.getTimeToBeforeMidnight(formatter.parse("10/20/2021"));
        Assert.assertFalse(BusinessCalendar.isWithinRange(startDate1, endDate1, startDate2, endDate2));

    }
}
