package md.attendance.sl.ui.login

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import md.attendance.sl.R

class LoginFragment : Fragment(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setOnApplyWindowInsetsListener(requireView()) { view, insets ->

            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val ime = insets.getInsets(WindowInsetsCompat.Type.ime())
            Log.d("fffffffffff","${systemBars.bottom} ${ime.bottom}")
            val bottom = maxOf(0, ime.bottom)

            view.setPadding(0, 0, 0, bottom-systemBars.bottom)

            insets
        }

        observeUi()
    }



    private fun observeUi() {

    }
}
