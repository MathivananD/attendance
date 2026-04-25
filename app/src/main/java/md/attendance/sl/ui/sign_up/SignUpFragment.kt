package md.attendance.sl.ui.sign_up

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import androidx.core.view.doOnLayout
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import md.attendance.sl.R
import md.attendance.sl.data.UserEntity
import md.attendance.sl.databinding.FragmentSignUpBinding
import md.attendance.sl.di.Extension.applySafeArea
import md.attendance.sl.di.Extension.setupToolbar
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar(binding.toolbarLayout.toolbar, "Sign Up", true)


        ViewCompat.setOnApplyWindowInsetsListener(binding.signUpScrollView) { v, insets ->
            val imeInset = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            val navInset = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
            v.updatePadding(bottom = maxOf(imeInset, navInset))
            insets
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
