package md.attendance.sl.custom_components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import md.attendance.sl.R
import md.attendance.sl.databinding.AttendanceItemBinding

class AttendanceItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val binding = AttendanceItemBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    fun setTime(time: String) {

        binding.tvTime.text = time
    }

    fun setLabel(label: String) {

        binding.tvLabel.text = label
    }

    init {

        orientation = HORIZONTAL

        if (isInEditMode) {

            setData(
                icon = R.drawable.calendar,
                color = context.getColor(R.color.accent),

                label = "Clock-in"
            )
        }
    }

    fun setData(
        icon: Int,
        color: Int,
        label: String
    ) {

        binding.imgIcon.setImageResource(icon)

        binding.imgIcon.setColorFilter(color)



        binding.tvLabel.text = label
    }
}

object AttendanceItemBindingAdapters {

    @JvmStatic
    @BindingAdapter("title")
    fun setTitle(
        view: AttendanceItemView,
        value: String?
    ) {

        view.setTime(value ?: "")
    }
}