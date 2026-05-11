package md.attendance.sl.di

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateTimeHelper {

    private const val DEFAULT_DATE_TIME_FORMAT =
        "dd MMM yyyy hh:mm:ss a"

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
    fun getDateTime(
        date: String
    ): String {
        val parsedDate = stringToDate(date) ?: return "";

        return formatDate(
            parsedDate,
            DEFAULT_DATE_FORMAT
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
        pattern: String =DEFAULT_DATE_TIME_FORMAT
    ): String {

        val formatter = SimpleDateFormat(
            pattern,
            Locale.getDefault()
        )

        return formatter.format(date)
    }

    fun getFormatTime(
        date: String,

        ): String {

        val formatter = SimpleDateFormat(
            DEFAULT_TIME_FORMAT,
            Locale.getDefault()
        )
        val modifiedDate = stringToDate(date) ?: return ""

        return formatter.format(modifiedDate)
    }

    fun stringToDate(
        dateString: String,
        pattern: String = DEFAULT_DATE_TIME_FORMAT
    ): Date? {

        return try {

            val formatter = SimpleDateFormat(
                pattern,
                Locale.getDefault()
            )

            formatter.parse(dateString)

        } catch (e: Exception) {

            null
        }
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

    fun dateToMillis(
        dateString: String,
        pattern: String = DEFAULT_DATE_TIME_FORMAT
    ): Long? {

        return try {

            val formatter = SimpleDateFormat(
                pattern,
                Locale.getDefault()
            )

            formatter.parse(dateString)?.time

        } catch (e: Exception) {

            null
        }
    }

    fun getWorkedTime(
        checkInTimeMillis: Long,
        checkOutTimeMillis: Long? = null
    ): String {

        val endTime =
            checkOutTimeMillis
                ?: System.currentTimeMillis()

        val diff =
            endTime - checkInTimeMillis

        val totalSeconds =
            diff / 1000

        val hours =
            totalSeconds / 3600

        val minutes =
            (totalSeconds % 3600) / 60

        val seconds =
            totalSeconds % 60

        return String.format(
            "%02dh:%02dm:%02ds",
            hours ,
            minutes,
            seconds
        )
    }
}