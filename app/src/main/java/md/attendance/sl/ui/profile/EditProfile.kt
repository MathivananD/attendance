package md.attendance.sl.ui.profile

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import md.attendance.sl.ImagePickerBottomSheet
import md.attendance.sl.R
import md.attendance.sl.data.ui_state.SignupState
import md.attendance.sl.data.ui_state.UiState
import md.attendance.sl.databinding.FragmentEditProfileBinding
import md.attendance.sl.databinding.FragmentProfileBinding
import md.attendance.sl.di.Extension.setupToolbar
import md.attendance.sl.ui.profile.viewmodel.ProfileViewModel
import java.io.File

@AndroidEntryPoint
class EditProfile : Fragment() {
    lateinit var binding: FragmentEditProfileBinding

    private val viewModel: ProfileViewModel by viewModels()
    private val args:
            EditProfileArgs by navArgs()
    private var profileImagePath: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(binding.toolbarLayout.toolbar, "Edit Profile", true)
        binding.userNameField.setText(args.user.name)
        binding.email.setText(args.user.email)
        binding.mobileNumber.setText(args.user.mobileNumber)
        binding.password.setText(args.user.password)
        val image = args.user.profileImage ?: ""
        if (image.isNotEmpty()) {
            binding.imagePreview.setImageURI(Uri.fromFile(File(args.user.profileImage ?: "")))
        }

        binding.profileImage.setOnClickListener {
            ImagePickerBottomSheet({

                if (it != null) {
                    profileImagePath = it
                    binding.imagePreview.setImageURI(
                        Uri.fromFile(
                            File(it)
                        )
                    )
                }
            }, {
                if (it != null) {
                    profileImagePath = it
                    binding.imagePreview.setImageURI(
                        Uri.fromFile(
                            File(it)
                        )
                    )
                }

            })
                .show(
                    parentFragmentManager,
                    "ImagePickerBottomSheet"
                )
        }
        binding.editButton.setOnClickListener {
            val user = args.user.copy(
                name = binding.userNameField.getText(),
                email = binding.email.getText(),
                mobileNumber = binding.mobileNumber.getText(),
                password = binding.password.getText(),
                profileImage = profileImagePath ?: args.user.profileImage
            )

            viewModel.updateUser(user)
        }
        observeUi()
//        binding.
    }

    private fun observeUi() {
        viewModel.updateState.observe(viewLifecycleOwner) { state ->
            Log.e("SignUPState", state.toString())
            when (state) {

                is SignupState.FieldError -> {
                    binding.userNameField.setError(
                        state.userName
                    )
                    binding.email.setError(
                        state.email
                    )
                    binding.mobileNumber.setError(
                        state.mobileNumber
                    )

                }

                is SignupState.Loading -> {
                    binding.userNameField.setError(
                        null
                    )
                    binding.email.setError(
                        null
                    )
                    binding.mobileNumber.setError(
                        null
                    )

                }

                is SignupState.Success -> {
                    Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()

                }

                is SignupState.Error -> {

                    Toast.makeText(context, state.error, Toast.LENGTH_LONG).show()
                }

                else -> {}
            }
        }
    }
}