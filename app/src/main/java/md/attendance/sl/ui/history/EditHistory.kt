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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import md.attendance.sl.R
import md.attendance.sl.databinding.FragmentEditHistoryBinding
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

    private val args:
            EditHistoryArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(binding.toolbarLayout.toolbar, "Edit History", true)
        viewModel.getAddress(requireContext(), args.history.latitude!!, args.history.longitude!!, {
            Log.d("HistoryViewModel", "getAddress: $it")
            binding.checkInLocation.setText(it)
        })
        viewModel.getAddress(
            requireContext(),
            args.history.checkOutLatitude!!,
            args.history.checkOutLongitude!!,
            {
                Log.d("HistoryViewModel", "getAddress checkOutLatitude: $it")
                binding.checkOutLocation.setText(it)
            })
        binding.checkInText.setText(args.history.checkInTime)
        binding.checkOutText.setText(args.history.checkoutTime)
        binding.checkInLocation.setText("${args.history.latitude}, ${args.history.longitude}")
        binding.checkOutLocation.setText("${args.history.checkOutLatitude}, ${args.history.checkOutLongitude}")
        binding.checkInText.setOnClickListener {
            val dd = DateTimeHelper.stringToDate(args.history.checkInTime)
            openCalendar(dd!!, { updatedTime ->
                binding.checkInText.setText(updatedTime)
            })
        }
        binding.checkOutText.setOnClickListener {
            val date = DateTimeHelper.stringToDate(args.history.checkoutTime)
            if (date != null) {
                openCalendar(date, { updatedTime ->
                    binding.checkOutText.setText(updatedTime)
                })
            } else {
                val checkInDate = DateTimeHelper.stringToDate(args.history.checkInTime)
                openCalendar(checkInDate, { updatedTime ->
                    binding.checkOutText.setText(updatedTime)
                })
            }

        }
        binding.updateBtn.setOnClickListener {

            val updatedEntity = args.history.copy(

                checkInTime = binding.checkInText.text.toString(),
                checkoutTime = binding.checkOutText.text.toString()
            )
            viewModel.updateEntity(updatedEntity)

            requireActivity()
                .onBackPressedDispatcher
        }
        binding.checkInLocation.setOnClickListener {
            val bundle =
                Bundle()

            bundle.putString(
                Constants.LATITUDE,
                args.history.latitude.toString()

            )
            bundle.putString(
                Constants.LONGITUDE,
                args.history.longitude.toString()

            )
            findNavController()
                .navigate(
                    R.id.mapScreen,
                    bundle
                )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentEditHistoryBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
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