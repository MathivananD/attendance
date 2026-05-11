package md.attendance.sl.ui.history

import android.Manifest
import android.os.Bundle
import androidx.core.app.NotificationManagerCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.core.app.NotificationCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import md.attendance.sl.R
import md.attendance.sl.custom_components.VerticalSpaceItemDecoration
import md.attendance.sl.data.ui_state.UiState
import md.attendance.sl.databinding.FragmentAttendanceHistoryBinding
import md.attendance.sl.di.Extension.setupToolbar
import md.attendance.sl.ui.history.recycleview.HistoryRecycleView
import md.attendance.sl.ui.history.view_model.HistoryViewModel
import md.attendance.sl.data.history.HistoryEntity

@AndroidEntryPoint
class AttendanceHistory : Fragment(), HistoryRecycleView.OnHistoryTapListener {
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
                    binding.chipRecyclerView.adapter = HistoryRecycleView(it.data, this)

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

    override fun onTap(
        item: HistoryEntity
    ) {

        Toast.makeText(
            requireContext(),
            "Tapped ${item.id}",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onEditTap(
        item: HistoryEntity
    ) {

        val action =
            AttendanceHistoryDirections
                .actionAttendanceHistoryToEditHistory(
                    item
                )

        findNavController().navigate(action)

//        findNavController().navigate(
//            R.id.editHistoryFragment
//        )
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onDeleteTap(
        item: HistoryEntity
    ) {
        showNotification()

//        MaterialAlertDialogBuilder(
//            requireContext()
//        )
//            .setTitle("Delete")
//            .setMessage(
//                "Are you sure want to delete?"
//            )
//            .setPositiveButton("Yes") { _, _ ->
//
////                viewModel.delete(item)
//            }
//            .setNegativeButton("No") { dialog, _ ->
//
//                dialog.dismiss()
//            }
//            .show()
//        viewModel.delete(item)
    }
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun showNotification() {

        val builder = NotificationCompat.Builder(
            requireContext(),
            "my_channel_id"
        )
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Attendance")
            .setContentText("Notification created successfully")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        NotificationManagerCompat
            .from(requireContext())
            .notify(1, builder.build())
    }
}
