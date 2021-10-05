package common.constants;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringFormats {
    public static final String MM_dd_yyyy = "MM/dd/yyyy";
    public static final String MM_dd_yy = "MM/dd/yy";
    public static final String ENGLISH_CURRENCY = "###,###,##0.00";

    public static String dateToString(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    public static String numberToCurrencyString(double value, String format) {
        return "$" + new DecimalFormat(format).format(value);
    }

    public static String numberToPercentString(int value) {
        return value + "%";
    }

    public static Double currencyToNumber(String currency) {
        return Double.valueOf(currency.substring(1));
    }
}
