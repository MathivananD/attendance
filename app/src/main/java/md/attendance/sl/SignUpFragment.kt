package md.attendance.sl

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

class SignUpFragment : Fragment() {

    companion object {
        fun newInstance() = SignUpFragment()
    }

    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onResume() {
        super.onResume()
        requireActivity().window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        )
    }
    override fun onPause() {
        super.onPause()
        // Reset to default when leaving the fragment so it doesn't affect other screens
        requireActivity().window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 'view' here is your ScrollView (the root of your fragment)
        // We want to apply padding to the ConstraintLayout inside it
        val container = view.findViewById<View>(R.id.signUpContainer) // Add an ID to your ConstraintLayout

        ViewCompat.setOnApplyWindowInsetsListener(view) { v, windowInsets ->
            // Get the height of the keyboard (ime) and the system bars (navigation bar)
            val insets = windowInsets.getInsets(
                WindowInsetsCompat.Type.ime() or WindowInsetsCompat.Type.systemBars()
            )

            // Apply the bottom inset as padding to your inner layout
            // This ensures the last button is always above the keyboard/nav bar
            v.updatePadding(bottom = insets.bottom)

            windowInsets
        }
    }
}