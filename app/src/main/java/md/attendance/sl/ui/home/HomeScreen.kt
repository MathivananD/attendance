package md.attendance.sl.ui.home

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
import md.attendance.sl.databinding.FragmentHomeScreenBinding
import md.attendance.sl.di.Extension.applySafeArea
import md.attendance.sl.ui.home.list.ChipRecycleView
import md.attendance.sl.ui.home.list.GridAdapter
import md.attendance.sl.ui.home.viewmodel.HomeViewModel
import md.attendance.sl.ui.login.view_model.LoginViewModel
import javax.inject.Inject

@AndroidEntryPoint
class HomeScreen : Fragment() {

    val homeViewModel: HomeViewModel by viewModels()
    @Inject
    lateinit var sessionManager: SessionManager

   lateinit  var binding: FragmentHomeScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

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
        val chipList = listOf("Today", "Weekly", "Monthly")

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
        val gridList = (1..10).map { "Item $it" }

        binding.gridRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 4)
            adapter = GridAdapter(gridList)
        }
        binding.gridRecyclerView.layoutManager =
            object : GridLayoutManager(requireContext(), 4) {
                override fun canScrollVertically(): Boolean = false
            }
        binding.gridRecyclerView.addItemDecoration(
            GridSpacingItemDecoration(4, spacing, false)
        )
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
        homeViewModel.loadUser()
      binding.dateView.tvDate.text=homeViewModel.getCurrentDateTime()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.user.collect { user ->

                    user?.let {
                        val capitalized = it.name.replaceFirstChar { it.uppercase() }
                        binding.userName.text=getString(R.string.userNameText, capitalized)
                    }

                }
            }
        }
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeScreen().apply {

            }
    }
}
