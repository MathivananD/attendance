package md.attendance.sl.custom_components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import md.attendance.sl.R
import md.attendance.sl.databinding.AttendanceItemBinding

class AttendanceItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val binding = AttendanceItemBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    init {
        orientation = HORIZONTAL   // 🔥 IMPORTANT

        if (isInEditMode) {
            setData(
                icon = R.drawable.calendar,   // your icon
                color = context.getColor(R.color.accent),
                time = "08.31",
                label = "Clock-in"
            )
        }
    }

    fun setData(icon: Int, color: Int, time: String, label: String) {
        binding.imgIcon.setImageResource(icon)
        binding.imgIcon.setColorFilter(color)

        binding.tvTime.text = time
        binding.tvLabel.text = label
    }
}