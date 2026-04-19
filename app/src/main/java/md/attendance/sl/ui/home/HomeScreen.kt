package md.attendance.sl.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import md.attendance.sl.R
import md.attendance.sl.custom_components.GridSpacingItemDecoration
import md.attendance.sl.custom_components.HorizontalSpaceItemDecoration
import md.attendance.sl.databinding.FragmentHomeScreenBinding
import md.attendance.sl.ui.home.list.ChipRecycleView
import md.attendance.sl.ui.home.list.GridAdapter


class HomeScreen : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var binding: FragmentHomeScreenBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeScreenBinding.inflate(inflater, container, false)

        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chipList = listOf("Today", "Weekly", "Monthly")

        binding!!.chipRecyclerView.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = ChipRecycleView(chipList)
        }
        val space = resources.getDimensionPixelSize(R.dimen.spacing_12)

        binding!!.chipRecyclerView.addItemDecoration(
            HorizontalSpaceItemDecoration(space)
        )
        val spacing = resources.getDimensionPixelSize(R.dimen.spacing_12)
        val gridList = (1..10).map { "Item $it" }

        binding!!.gridRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 4)
            adapter = GridAdapter(gridList)
        }
        binding!!.gridRecyclerView.layoutManager =
            object : GridLayoutManager(requireContext(), 4) {
                override fun canScrollVertically(): Boolean = false
            }
        binding!!.gridRecyclerView.addItemDecoration(
            GridSpacingItemDecoration(4, spacing, false)
        )
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeScreen().apply {

            }
    }
}