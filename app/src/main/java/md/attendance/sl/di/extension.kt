package md.attendance.sl.di

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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
}