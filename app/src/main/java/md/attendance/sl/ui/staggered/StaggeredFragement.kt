package md.attendance.sl.ui.staggered

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import md.attendance.sl.databinding.StaggeredLayoutBinding

class StaggeredFragement : Fragment() {

    private var _binding: StaggeredLayoutBinding? = null
    private val binding get() = _binding!!

    private lateinit var photoRVAdapter: PhotoRVAdapter
    private lateinit var photoList: ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = StaggeredLayoutBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize list
        photoList = ArrayList()

        // Adapter
        photoRVAdapter = PhotoRVAdapter(photoList)

        // Staggered Grid Layout Manager
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(
                2,
                LinearLayoutManager.VERTICAL
            )

        binding.idRVPhotos.layoutManager =
            staggeredGridLayoutManager

        binding.idRVPhotos.adapter =
            photoRVAdapter

        // Add Images
        photoList.add("https://pbs.twimg.com/media/FV6-TWhUsAY92R_.jpg")
        photoList.add("https://pbs.twimg.com/media/FV6-TWhUsAY92R_.jpg")
        photoList.add("https://pbs.twimg.com/media/FV6-TWhUsAY92R_.jpg")
        photoList.add("https://pbs.twimg.com/media/FV6-TWhUsAY92R_.jpg")
        photoList.add("https://pbs.twimg.com/media/FV6-TWhUsAY92R_.jpg")
        photoList.add("https://pbs.twimg.com/media/FV6-TWhUsAY92R_.jpg")
        photoList.add("https://pbs.twimg.com/media/FV6-TWhUsAY92R_.jpg")
        photoList.add("https://videocdn.geeksforgeeks.org/geeksforgeeks/FirstandFollowinCompilerDesign/FirstFollowinCompilerDesign20220624172015-small.png")

        // Refresh adapter
        photoRVAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
