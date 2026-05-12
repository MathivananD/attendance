package md.attendance.sl.custom_components

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import md.attendance.sl.R
import md.attendance.sl.databinding.TitleSubtitleBinding
import org.w3c.dom.Attr
import androidx.core.content.withStyledAttributes

@SuppressLint("CustomViewStyleable")
class TitleSubTitleItem @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attr, defStyleAttr) {
    var binding: TitleSubtitleBinding = TitleSubtitleBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    fun setTitle(title: String) {
        binding.title.text = title
    }

    fun setSubTitle(subTitle: String) {
        binding.subTitle.text = subTitle
    }

    init {

        attr?.let {

            context.withStyledAttributes(
                it,
                R.styleable.TitleSubTitleItemView
            ) {

                val title =
                    getString(
                        R.styleable
                            .TitleSubTitleItemView_titleText
                    )

                val subTitle =
                    getString(
                        R.styleable
                            .TitleSubTitleItemView_subTitleText
                    )
                if (title != null) {
                    setTitle(title)
                    binding.title.text =
                        title ?: ""
                }
                if (subTitle != null) {
                    setSubTitle(subTitle)

                }


            }
        }
    }
}