package md.attendance.sl.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import md.attendance.sl.R
import md.attendance.sl.custom_components.HorizontalSpaceItemDecoration
import md.attendance.sl.custom_components.VerticalSpaceItemDecoration
import md.attendance.sl.data.ui_state.UiState
import md.attendance.sl.databinding.FragmentAttendanceHistoryBinding
import md.attendance.sl.di.Extension.setupToolbar
import md.attendance.sl.ui.history.recycleview.HistoryRecycleView
import md.attendance.sl.ui.history.view_model.HistoryViewModel

@AndroidEntryPoint
class AttendanceHistory : Fragment() {
    lateinit var binding: FragmentAttendanceHistoryBinding

    val viewModel: HistoryViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(binding.toolbarLayout.toolbar, "Attendance History", true)
        binding.chipRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewModel.load()
        observeUi()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAttendanceHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun observeUi() {
        viewModel.states.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {
                    binding.progressBar.progressBar.visibility = View.VISIBLE
                    binding.scrollView.visibility = View.GONE

                }

                is UiState.Success -> {
                    binding.progressBar.progressBar.visibility = View.GONE
                    binding.scrollView.visibility = View.VISIBLE
                    binding.chipRecyclerView.adapter = HistoryRecycleView(it.data)

                    val space = resources.getDimensionPixelSize(R.dimen.spacing_12)
                    binding.chipRecyclerView.addItemDecoration(
                        VerticalSpaceItemDecoration(space)
                    )
                }

                is UiState.Error -> {
                    binding.progressBar.progressBar.visibility = View.GONE
                }

                else -> {

                }
            }
        }
    }
}
