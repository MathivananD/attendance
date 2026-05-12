package md.attendance.sl.custom_components

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import md.attendance.sl.R
import md.attendance.sl.databinding.CustomTextFieldBinding
import androidx.core.content.withStyledAttributes
import com.google.android.material.textfield.TextInputLayout

class CustomTextField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(
    context,
    attrs,
    defStyleAttr
) {

    private val binding =
        CustomTextFieldBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

    init {

        attrs?.let {

            val typedArray =
                context.obtainStyledAttributes(
                    it,
                    R.styleable.CustomTextField
                )

            val hint =
                typedArray.getString(
                    R.styleable.CustomTextField_hintText
                )

            binding.textInputLayout.hint =
                hint

            typedArray.recycle()

            context.withStyledAttributes(
                it,
                intArrayOf(android.R.attr.inputType)
            ) {

                val inputType =
                    getInt(
                        0,
                        InputType.TYPE_CLASS_TEXT
                    )

                if (
                    inputType and
                    InputType.TYPE_TEXT_VARIATION_PASSWORD
                    == InputType.TYPE_TEXT_VARIATION_PASSWORD
                ) {

                    binding.textInputLayout.endIconMode =
                        TextInputLayout.END_ICON_PASSWORD_TOGGLE
                }
                binding.editText.inputType =
                    inputType

            }
        }
    }

    fun getText(): String {
        return binding.editText.text.toString()
    }

    fun setText(value: String) {
        binding.editText.setText(value)
    }


    fun setError(errorMessage: String?) {
        binding.textInputLayout.error =
            errorMessage
    }

    fun clearError() {
        binding.textInputLayout.error =
            null
    }
}