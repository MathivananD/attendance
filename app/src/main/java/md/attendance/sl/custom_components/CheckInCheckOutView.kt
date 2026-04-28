package md.attendance.sl.custom_components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import md.attendance.sl.R

class CheckInCheckOutView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val checkInView: AttendanceItemView
    private val checkOutView: AttendanceItemView

    var checkIn: String = ""
        set(value) {
            field = value
            checkInView.setTime(value)
        }

    var checkOut: String = ""
        set(value) {
            field = value
            checkOutView.setTime(value)
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.check_in_check_out, this, true)
        checkInView = findViewById(R.id.checkInView)
        checkOutView = findViewById(R.id.checkOutView)

        checkInView.setData(
            icon = R.drawable.calendar,
            color = context.getColor(R.color.accent),
            label = "Clock-in"
        )
        checkOutView.setData(
            icon = R.drawable.calendar,
            color = context.getColor(R.color.accent),
            label = "Clock-out"
        )

        context.obtainStyledAttributes(attrs, R.styleable.CheckInCheckOutView).apply {
            try {
                checkIn = getString(R.styleable.CheckInCheckOutView_checkIn).orEmpty()
                checkOut = getString(R.styleable.CheckInCheckOutView_checkOut).orEmpty()
            } finally {
                recycle()
            }
        }
    }
}
