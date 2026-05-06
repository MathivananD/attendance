package md.attendance.sl.ui.history.recycleview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import md.attendance.sl.data.history.HistoryEntity
import md.attendance.sl.databinding.CustomHistoryCardBinding

class HistoryRecycleView(val list: List<HistoryEntity>) :
    RecyclerView.Adapter<HistoryRecycleView.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HistoryRecycleView.ViewHolder {

        val binding = CustomHistoryCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryRecycleView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return list.size

    }

    class ViewHolder(val binidng: CustomHistoryCardBinding) : RecyclerView.ViewHolder(binidng.root)


}