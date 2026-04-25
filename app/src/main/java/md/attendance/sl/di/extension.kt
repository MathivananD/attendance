package md.attendance.sl.di

import android.view.View
import android.widget.ImageButton
import android.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import androidx.core.view.doOnLayout
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import md.attendance.sl.R

object Extension {

    fun View.applySafeArea() {
        val initialTop = paddingTop
        val initialBottom = paddingBottom

        ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            view.setPadding(
                paddingLeft,
                initialTop + bars.top,
                paddingRight,
                initialBottom + bars.bottom
            )
            insets
        }
    }

    fun Fragment.setupToolbar(
        toolbar: androidx.appcompat.widget.Toolbar,
        title: String,
        showBack: Boolean = true
    ) {
        ViewCompat.setOnApplyWindowInsetsListener(toolbar) { v, insets ->
            val topInset = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            v.updatePadding(top = topInset)
            insets
        }
        toolbar.title = title
//        toolbar.doOnLayout {
//            val navButton = toolbar.children
//                .filterIsInstance<ImageButton>()
//                .firstOrNull()
//
//            navButton?.setPadding(0, 0, 0, 30) // left, top, right, bottom
//        }
        toolbar.setNavigationIcon(
            R.drawable.baseline_arrow_back_ios_24
        )

        toolbar.navigationIcon?.setTint(
            ContextCompat.getColor(requireContext(), R.color.white)
        )
        if (showBack) {
            toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_ios_24)
            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

}