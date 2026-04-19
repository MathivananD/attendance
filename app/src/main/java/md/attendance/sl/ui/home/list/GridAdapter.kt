package md.attendance.sl.ui.home.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import md.attendance.sl.databinding.ItemGridBinding

class GridAdapter(private val list: List<String>) :
    RecyclerView.Adapter<GridAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemGridBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGridBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvTitle.text = list[position]
    }
}