package md.attendance.sl.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import md.attendance.sl.databinding.FragmentAttendanceHistoryBinding
import md.attendance.sl.di.Extension.setupToolbar
import md.attendance.sl.ui.home.viewmodel.HomeViewModel

@AndroidEntryPoint
class AttendanceHistory : Fragment() {
    lateinit var binding: FragmentAttendanceHistoryBinding

     val viewModel: HomeViewModel  by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(binding.toolbarLayout.toolbar, "Attendance History", true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAttendanceHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }
}