package md.attendance.sl.ui.home

import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import md.attendance.sl.data.SessionManager
import md.attendance.sl.R
import md.attendance.sl.custom_components.GridSpacingItemDecoration
import md.attendance.sl.custom_components.HorizontalSpaceItemDecoration
import md.attendance.sl.data.ui_state.HomeState
import md.attendance.sl.databinding.FragmentHomeScreenBinding
import md.attendance.sl.di.Extension.applySafeArea
import md.attendance.sl.ui.home.list.ChipRecycleView
import md.attendance.sl.ui.home.list.GridAdapter
import md.attendance.sl.ui.home.viewmodel.HomeViewModel
import java.io.File
import java.net.URL
import javax.inject.Inject

@AndroidEntryPoint
class HomeScreen : Fragment() {

    val homeViewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var sessionManager: SessionManager

    lateinit var binding: FragmentHomeScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeScreenBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.applySafeArea()
        binding.viewModel = homeViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        val chipList = listOf("Today", "Weekly", "Monthly")
        observeUi()
        binding.chipRecyclerView.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = ChipRecycleView(chipList)
        }
        val space = resources.getDimensionPixelSize(R.dimen.spacing_12)
        binding.chipRecyclerView.addItemDecoration(
            HorizontalSpaceItemDecoration(space)
        )
        val spacing = resources.getDimensionPixelSize(R.dimen.spacing_12)
        val gridList = listOf("Attendance")

        binding.gridRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = GridAdapter(gridList)
        }
        binding.gridRecyclerView.layoutManager =
            object : GridLayoutManager(requireContext(), 2) {
                override fun canScrollVertically(): Boolean = false
            }
        binding.gridRecyclerView.addItemDecoration(
            GridSpacingItemDecoration(4, spacing, false)
        )
        binding.notification.setOnClickListener {
            findNavController().navigate(R.id.attendanceHistory)
        }
        binding.logOut.setOnClickListener {
            sessionManager.logout()
            findNavController().navigate(
                R.id.loginFragment,
                null,
                NavOptions.Builder()
                    .setPopUpTo(R.id.my_nav, true)
                    .build()
            )
        }

        binding.dateView.tvDate.text = homeViewModel.getCurrentDateTime()
        homeViewModel.todayHistoryEntity.observe(viewLifecycleOwner) {
            binding.checkInOutView.checkIn = homeViewModel.getCheckInTime()
            binding.checkInOutView.checkOut = homeViewModel.getCheckOutTime()
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.user.collect { user ->

                    user?.let { it ->
                        val capitalized = it.name.replaceFirstChar { it.uppercase() }
                        binding.userName.text = getString(R.string.userNameText, capitalized)
                        val image = it.profileImage ?: ""
                        if (image.isNotEmpty()) {
                            binding.profileImage.setImageURI(Uri.fromFile(File(image)))
                        }
                    }

                }
            }
        }
        binding.profileImage.setOnClickListener {
            findNavController().navigate(R.id.profile)
        }

    }

    fun observeUi() {
        homeViewModel.states.observe(viewLifecycleOwner) { it ->
            when (it) {
                is HomeState.Loading -> {
                    binding.progressBar.progressBar.visibility = View.VISIBLE
                    binding.mainView.visibility = View.GONE

                }

                is HomeState.Success -> {

                    binding.progressBar.progressBar.visibility = View.GONE
                    binding.mainView.visibility = View.VISIBLE
                }

                is HomeState.Error -> {

                }

                else -> {

                }
            }
        }
    }


}
