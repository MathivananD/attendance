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
import androidx.navigation.fragment.findNavController
import md.attendance.sl.databinding.FragmentSignUpBinding
import kotlin.math.max

class SignUpFragment : Fragment() {

   lateinit var binding: FragmentSignUpBinding;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentSignUpBinding.inflate(inflater,container,false);
        return binding.root
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

        val initialBottomPadding = view.paddingBottom

        ViewCompat.setOnApplyWindowInsetsListener(view) { v, windowInsets ->
            val imeBottom = windowInsets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            val systemBarsBottom = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom

            v.updatePadding(bottom = initialBottomPadding + max(imeBottom, systemBarsBottom))

            windowInsets
        }

        binding.signUpButton.setOnClickListener { it->
            findNavController().navigate(R.id.homeFragment, )
        }
    }
}
