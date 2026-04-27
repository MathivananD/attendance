package md.attendance.sl.di

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateTimeHelper {

    private const val DEFAULT_DATE_TIME_FORMAT =
        "dd MMM yyyy hh:mm a"

    private const val DEFAULT_DATE_FORMAT =
        "dd MMM yyyy"

    private const val DEFAULT_TIME_FORMAT =
        "hh:mm a"

    /**
     * Current date time
     */
    fun getCurrentDateTime(
        pattern: String = DEFAULT_DATE_TIME_FORMAT
    ): String {

        return formatDate(
            Date(),
            pattern
        )
    }

    /**
     * Current date
     */
    fun getCurrentDate(): String {

        return getCurrentDateTime(
            DEFAULT_DATE_FORMAT
        )
    }

    /**
     * Current time
     */
    fun getCurrentTime(): String {

        return getCurrentDateTime(
            DEFAULT_TIME_FORMAT
        )
    }

    /**
     * Current timestamp
     */
    fun getCurrentTimestamp(): Long {

        return System.currentTimeMillis()
    }

    /**
     * Format date
     */
    fun formatDate(
        date: Date,
        pattern: String
    ): String {

        val formatter = SimpleDateFormat(
            pattern,
            Locale.getDefault()
        )

        return formatter.format(date)
    }

    /**
     * Convert timestamp to date
     */
    fun timestampToDate(
        timestamp: Long,
        pattern: String = DEFAULT_DATE_TIME_FORMAT
    ): String {

        return formatDate(
            Date(timestamp),
            pattern
        )
    }

    /**
     * Get current year
     */
    fun getCurrentYear(): Int {

        return Calendar.getInstance()
            .get(Calendar.YEAR)
    }

    /**
     * Get current month
     */
    fun getCurrentMonth(): Int {

        return Calendar.getInstance()
            .get(Calendar.MONTH) + 1
    }

    /**
     * Get current day
     */
    fun getCurrentDay(): Int {

        return Calendar.getInstance()
            .get(Calendar.DAY_OF_MONTH)
    }
}