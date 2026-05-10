package md.attendance.sl.ui.history

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import md.attendance.sl.R
import md.attendance.sl.databinding.FragmentEditHistoryBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EditHistory : Fragment() {

    lateinit var binding: FragmentEditHistoryBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.checkInText.setOnClickListener {
//            openCalendar()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentEditHistoryBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }


//    fun openCalendar() {
//        val calendar = Calendar.getInstance()
//
//        DatePickerDialog(
//            requireContext(),
//            { _, year, month, dayOfMonth ->
//
//                calendar.set(year, month, dayOfMonth)
//
//                TimePickerDialog(
//                    requireContext(),
//                    { _, hour, minute ->
//
//                        calendar.set(Calendar.HOUR_OF_DAY, hour)
//                        calendar.set(Calendar.MINUTE, minute)
//
//                        val format =
//                            SimpleDateFormat(
//                                "dd MMM yyyy hh:mm a",
//                                Locale.getDefault()
//                            )
//
////                        binding.editText.setText(
////                            format.format(calendar.time)
////                        )
//
//                    },
//                    calendar.get(Calendar.HOUR_OF_DAY),
//                    calendar.get(Calendar.MINUTE),
//                    false
//                ).show()
//
//            },
//            calendar.get(Calendar.YEAR),
//            calendar.get(Calendar.MONTH),
//            calendar.get(Calendar.DAY_OF_MONTH)
//        ).show()
//    }

}