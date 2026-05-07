package md.attendance.sl.ui.history.recycleview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import md.attendance.sl.data.history.HistoryEntity
import md.attendance.sl.databinding.CustomHistoryCardBinding
import md.attendance.sl.di.DateTimeHelper

class HistoryRecycleView(private val historyList: List<HistoryEntity>) :
    RecyclerView.Adapter<HistoryRecycleView.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = CustomHistoryCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = historyList[position]

        holder.binding.checkIn.text = DateTimeHelper.getFormatTime(item.checkInTime)
        holder.binding.checkOut.text = DateTimeHelper.getFormatTime(item.checkoutTime)
        holder.binding.date.text = DateTimeHelper.getDateTime(item.checkInTime)
        val checkInMilliSeconds =
            DateTimeHelper.dateToMillis(item.checkInTime)
        val checkOutMilliSeconds =
            DateTimeHelper.dateToMillis(item.checkoutTime)
        if (checkInMilliSeconds != null && checkOutMilliSeconds==null) {
            holder.binding.workingHours.text = DateTimeHelper.getWorkedTime(checkInMilliSeconds)
        } else if (checkOutMilliSeconds != null) {
            holder.binding.workingHours.text =
                DateTimeHelper.getWorkedTime(checkInMilliSeconds!!, checkOutMilliSeconds)
        }

    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    inner class ViewHolder(val binding: CustomHistoryCardBinding) :
        RecyclerView.ViewHolder(binding.root)

}