package md.attendance.sl.ui.home.list
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import md.attendance.sl.databinding.ChipLayoutBinding

class ChipRecycleView(val list: List<String>) : RecyclerView.Adapter<ChipRecycleView.ViewHolder>() {
    private var selectedPosition = 0
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val binding = ChipLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        val item = list[position]

        holder.binding.chip.text = item
        // Highlight selected

        holder.binding.chip.isChecked = position == selectedPosition
        holder.binding.chip.setOnClickListener {

            val previous = selectedPosition
            selectedPosition = holder.absoluteAdapterPosition

            notifyItemChanged(previous)
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(val binding: ChipLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}
