package comp127.weather.widgets;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Utilities to help widgets convert numbers and dates to strings.
 */
@SuppressWarnings("WeakerAccess")
public class FormattingHelpers {
    /**
     * Converts a number to a string with one digit past the decimal point, e.g. "312.3".
     */
    public static final DecimalFormat ONE_DECIMAL_PLACE = new DecimalFormat("#0.0");

    /**
     * Converts a date to a string showing the date and day of week in abbreviated form,
     * e.g. "Mon, Oct 14".
     */
    public static final DateFormat WEEKDAY_AND_NAME = new SimpleDateFormat("E, MMM d");

    /**
     * Converts a date to a string showing the 12-hour time of day, e.g. "1:46 PM".
     */
    public static final DateFormat TIME_OF_DAY = new SimpleDateFormat("h:mm a");

    // TODO: Add any other static helper methods your widgets might want to share
    public static String checkNull(Double decimal) {
        if (decimal != null) {
            return ONE_DECIMAL_PLACE.format(decimal);
        }
        else {
            return "-";
        } 
    }

    public static String windFormat(Double windSpeed) {
        // double windSpeed = currentConditions.getWindSpeed();
        return String.format("%.1f", windSpeed);
    }
    public static String formatTemperature(Double temperature) {
        if (temperature == null) {
            return "N/A";
        }
        return ONE_DECIMAL_PLACE.format(temperature);
    }
}
