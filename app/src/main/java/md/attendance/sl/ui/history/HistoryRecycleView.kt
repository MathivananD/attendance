package md.attendance.sl.ui.history

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import md.attendance.sl.data.history.HistoryEntity
import md.attendance.sl.databinding.CustomHistoryCardBinding

class HistoryRecycleView(private val historyList: List<HistoryEntity>) :
    RecyclerView.Adapter<HistoryRecycleView.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryRecycleView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: HistoryRecycleView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class ViewHolder(val binding: CustomHistoryCardBinding) :
        RecyclerView.ViewHolder(binding.root)

}


