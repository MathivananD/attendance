package md.attendance.sl.ui.login

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
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import md.attendance.sl.R
import md.attendance.sl.databinding.FragmentLoginBinding
import md.attendance.sl.ui.login.view_model.LoginState
import md.attendance.sl.ui.login.view_model.LoginViewModel

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
    lateinit var binding: FragmentLoginBinding;
    val viewModel: LoginViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setOnApplyWindowInsetsListener(requireView()) { view, insets ->

            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val ime = insets.getInsets(WindowInsetsCompat.Type.ime())
            Log.d("fffffffffff", "${systemBars.bottom} ${ime.bottom}")
            val bottom = maxOf(0, ime.bottom)

            view.setPadding(0, 0, 0, bottom - systemBars.bottom)

            insets

        }
        setUpButtonClickListener()
        observeUi()
    }

    private fun setUpButtonClickListener() {
        binding.loginButton.setOnClickListener {
            Log.d("fffffffffff", "login button clicked")
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            viewModel.login(username, password)

        }
        binding.signUpText.setOnClickListener {
            findNavController().navigate(R.id.signUpFragment, )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.root;
    }


    private fun observeUi() {
        viewModel.loginState.observe(viewLifecycleOwner) { state ->

            when (state) {

                is LoginState.FieldError -> {
                    binding.usernameLayout.error = state.userName
                    binding.passwordLayout.error = state.password
                }

                is LoginState.Loading -> {
                    binding.usernameLayout.error = null
                    binding.passwordLayout.error = null
                }

                is LoginState.Success -> {
                    findNavController().navigate(R.id.homeFragment, )
                }

                is LoginState.Error -> {
                    Log.d("wwwwwwwwwwwwwwww", "login button clicked")
                    viewModel.reset()

                    Toast.makeText(context, state.error, Toast.LENGTH_LONG).show()
                }

                else -> {}
            }
        }
    }
}
