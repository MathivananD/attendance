package md.attendance.sl.ui.history

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import md.attendance.sl.R
import md.attendance.sl.data.history.HistoryEntity
import md.attendance.sl.data.model.LocationResult
import md.attendance.sl.databinding.FragmentEditHistoryBinding
import md.attendance.sl.di.AttendanceType
import md.attendance.sl.di.Constants
import md.attendance.sl.di.DateTimeHelper
import md.attendance.sl.di.Extension.setupToolbar
import md.attendance.sl.ui.history.view_model.HistoryViewModel

import java.util.Calendar
import java.util.Date

import kotlin.getValue

@AndroidEntryPoint
class EditHistory : Fragment() {

    lateinit var binding: FragmentEditHistoryBinding
    val viewModel: HistoryViewModel by viewModels()

    private var tappedType: AttendanceType? = null
    private val args:
            EditHistoryArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar(binding.toolbarLayout.toolbar, "Edit History", true)

        viewModel.initializeModel(requireContext(), args.history)
        setInitialData()
        val savedStateHandle = findNavController().currentBackStackEntry?.savedStateHandle
        savedStateHandle?.getLiveData<LocationResult>(Constants.LOCATION)
            ?.observe(viewLifecycleOwner) { address ->
                address?.let {
                    when (tappedType) {
                        AttendanceType.CHECK_IN -> viewModel.updateCheckInLocation(
                            it.latitude,
                            it.longitude,
                            requireContext()
                        )

                        AttendanceType.CHECK_OUT -> viewModel.updateCheckOutLocation(
                            it.latitude,
                            it.longitude,
                            requireContext()
                        )

                        else -> {}
                    }
                    tappedType = null
                    // Clear it so it doesn't re-trigger on config change
                    savedStateHandle.remove<LocationResult>(Constants.LOCATION)
                }
            }
        setupClickListeners()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentEditHistoryBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setInitialData() {

        val currentModel = viewModel.editedModel!!
        // 3. Collect UI Address States securely
        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                Log.d("dddddddddddddddd", "fffffff")
                launch {
                    viewModel.checkInAddress.collect { address ->
                        binding.checkInLocation.setText(
                            address ?: "${currentModel.latitude}, ${currentModel.longitude}"
                        )
                    }
                }
                launch {
                    viewModel.checkOutAddress.collect { address ->
                        binding.checkOutLocation.setText(
                            address
                                ?: "${currentModel.checkOutLatitude}, ${currentModel.checkOutLongitude}"
                        )
                    }
                }
            }
        }


        binding.checkInText.setText(currentModel.checkInTime)
        binding.checkOutText.setText(currentModel.checkoutTime)
        binding.checkInLocation.setText("${currentModel.latitude}, ${currentModel.longitude}")
        binding.checkOutLocation.setText("${currentModel.checkOutLatitude}, ${currentModel.checkOutLongitude}")


    }

    private fun setupClickListeners() {
        binding.checkInText.setOnClickListener {
            val dd = DateTimeHelper.stringToDate(viewModel.editedModel!!.checkInTime)
            openCalendar(dd) { updatedTime -> binding.checkInText.setText(updatedTime) }
        }

        binding.checkOutText.setOnClickListener {
            val date = DateTimeHelper.stringToDate(viewModel.editedModel!!.checkoutTime)
                ?: DateTimeHelper.stringToDate(viewModel.editedModel!!.checkInTime)
            openCalendar(date) { updatedTime -> binding.checkOutText.setText(updatedTime) }
        }

        binding.checkInLocation.onTap {
            tappedType = AttendanceType.CHECK_IN
            navigateToMap(viewModel.editedModel?.latitude, viewModel.editedModel?.longitude)
        }

        binding.checkOutLocation.onTap {
            tappedType = AttendanceType.CHECK_OUT
            navigateToMap(
                viewModel.editedModel?.checkOutLatitude,
                viewModel.editedModel?.checkOutLongitude
            )
        }

        binding.updateBtn.setOnClickListener {
            viewModel.updateTimes(
                binding.checkInText.text.toString(),
                binding.checkOutText.text.toString()
            )
            viewModel.editedModel?.let { viewModel.updateEntity(it) }
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun navigateToMap(lat: Double?, lng: Double?) {
        val bundle = Bundle().apply {
            putString(Constants.LATITUDE, lat?.toString())
            putString(Constants.LONGITUDE, lng?.toString())
        }
        findNavController().navigate(R.id.mapScreen, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

    fun openCalendar(date: Date?, onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        if (date != null) {
            calendar.time = date
        }
        DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->

                calendar.set(year, month, dayOfMonth)

                TimePickerDialog(
                    requireContext(),
                    { _, hour, minute ->

                        calendar.set(Calendar.HOUR_OF_DAY, hour)
                        calendar.set(Calendar.MINUTE, minute)
                        val format = DateTimeHelper.formatDate(calendar.time)
                        onDateSelected(
                            format
                        )

                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    false
                ).show()

            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

}