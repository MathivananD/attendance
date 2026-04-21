package md.attendance.sl.ui.sign_up

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import md.attendance.sl.R
import md.attendance.sl.data.UserEntity
import md.attendance.sl.databinding.FragmentSignUpBinding
import md.attendance.sl.ui.login.view_model.LoginState
import md.attendance.sl.ui.login.view_model.LoginViewModel
import kotlin.getValue
import kotlin.math.max

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    lateinit var binding: FragmentSignUpBinding;
    val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
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
            val systemBarsBottom =
                windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom

            v.updatePadding(bottom = initialBottomPadding + max(imeBottom, systemBarsBottom))

            windowInsets
        }

        binding.signUpButton.setOnClickListener { it ->
            val userEntity = UserEntity(
                id = 0,
                name = binding.etUsername.text.toString(),
                email = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString(),
                mobileNumber = binding.etMobileNumber.text.toString()
            )
            viewModel.signUp(userEntity)
        }
        observeUi()
    }


    private fun observeUi() {
        viewModel.states.observe(viewLifecycleOwner) { state ->
            Log.e("SignUPState", state.toString())
            when (state) {

                is SignupState.FieldError -> {
                    binding.userName.error = state.userName
                    binding.emailId.error = state.email
                    binding.password.error = state.password
                    binding.mobileNumber.error = state.mobileNumber
                }

                is SignupState.Loading -> {
                    binding.userName.error = null
                    binding.emailId.error = null
                    binding.password.error = null
                    binding.mobileNumber.error = null
                }

                is SignupState.Success -> {
                    findNavController().navigate(R.id.homeFragment)
                    Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()

                }

                is SignupState.Error -> {
                    viewModel.reset()
                    Toast.makeText(context, state.error, Toast.LENGTH_LONG).show()
                }

                else -> {}
            }
        }
    }
}