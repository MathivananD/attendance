package md.attendance.sl.ui.map.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import md.attendance.sl.databinding.MapSearchBinding

class SearchPlacesRecycleView(val list: MutableList<String>) : RecyclerView.Adapter<SearchPlacesRecycleView.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val binding = MapSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        holder.binding.searchText.text = list[position]
    }

    override fun getItemCount(): Int {
        return  list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<String>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
    inner class ViewHolder(val binding: MapSearchBinding) : RecyclerView.ViewHolder(binding.root)
}