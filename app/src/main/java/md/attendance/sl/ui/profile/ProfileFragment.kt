package md.attendance.sl.ui.profile

import android.net.Uri
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import md.attendance.sl.R
import md.attendance.sl.data.ui_state.HomeState
import md.attendance.sl.data.ui_state.UiState
import md.attendance.sl.databinding.FragmentProfileBinding
import md.attendance.sl.di.Extension.setupToolbar
import md.attendance.sl.ui.profile.viewmodel.ProfileViewModel
import java.io.File


@AndroidEntryPoint
class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding


    private val viewModel: ProfileViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(binding.toolbarLayout.toolbar, "Profile", true)
        binding.editButton.setOnClickListener {
            val state = viewModel.states.value

            if (state is UiState.Success) {
                val action =
                    ProfileFragmentDirections
                        .actionProfileToEditProfile(
                            state.data
                        )

                findNavController().navigate(action)
            }

        }
        viewModel.load()
        observeUI()

    }

    private fun observeUI() {
        viewModel.states.observe(viewLifecycleOwner) { it ->
            when (it) {
                is UiState.Loading -> {
                    binding.progressBar.progressBar.visibility = View.VISIBLE
                    binding.mainScreen.visibility = View.GONE

                }

                is UiState.Success -> {
                    binding.name.setSubTitle(it.data.name)
                    binding.email.setSubTitle(it.data.email)
                    binding.mobileNUmber.setSubTitle(it.data.mobileNumber)
                    val image = it.data.profileImage ?: ""
                    if (image.isNotEmpty()) {
                        binding.profileImage.setImageURI(Uri.fromFile(File(it.data.profileImage ?: "")))
                    }
                    binding.progressBar.progressBar.visibility = View.GONE
                    binding.mainScreen.visibility = View.VISIBLE
                }

                is UiState.Error -> {
                    binding.progressBar.progressBar.visibility = View.GONE
                    binding.mainScreen.visibility = View.VISIBLE
                }

                else -> {

                }
            }
        }
    }
}