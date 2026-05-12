package md.attendance.sl

import android.Manifest
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import md.attendance.sl.databinding.FragmentImagePickerBottomSheetListDialogItemBinding
import md.attendance.sl.databinding.FragmentImagePickerBottomSheetListDialogBinding
import md.attendance.sl.di.FileResult
import md.attendance.sl.di.PermissionHandler
import md.attendance.sl.di.SaveImageHandler

class ImagePickerBottomSheet(
    onCamera: (bitmap: String?) -> Unit,
    onGallery: (uri: String?) -> Unit
) :
    BottomSheetDialogFragment() {

    private var _binding: FragmentImagePickerBottomSheetListDialogBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var imagePath: FileResult? = null
    private val requestCameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->

            if (isGranted) {

                openCamera()

            } else {
                dismiss()
                Toast.makeText(
                    requireContext(),
                    "Camera permission denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    private val requestGalleryPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openGallery()
            } else {
                openGallery()
                Toast.makeText(
                    requireContext(),
                    "Gallery permission denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { bitmap ->
            dismiss()
            if(bitmap){
                onCamera(imagePath!!.path)
            }

        }
    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { bitmap ->
            dismiss()
            val path = SaveImageHandler.saveImageToFolder(requireContext(), bitmap!!)
            onGallery(path)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding =
            FragmentImagePickerBottomSheetListDialogBinding.inflate(inflater, container, false)
        return binding.root

    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        binding.tvCamera.setOnClickListener {

            PermissionHandler(this).checkCameraPermission({

                openCamera()

            }, {
                requestCameraPermission.launch(Manifest.permission.CAMERA)
            })
        }

        binding.tvGallery.setOnClickListener {
            PermissionHandler(this).checkGalleryPermission({
                openGallery()
            }, {
                requestGalleryPermission.launch(Manifest.permission.READ_MEDIA_IMAGES)
            })
        }
    }

    fun openCamera() {
        imagePath = SaveImageHandler.createImageFile(requireContext())
        if (imagePath != null) {
            cameraLauncher.launch(imagePath!!.uri)
        }

    }

    fun openGallery() {
        imagePath = null
        galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}