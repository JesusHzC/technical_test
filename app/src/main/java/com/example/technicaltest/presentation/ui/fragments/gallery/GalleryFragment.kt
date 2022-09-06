package com.example.technicaltest.presentation.ui.fragments.gallery

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.esafirm.imagepicker.features.ImagePickerLauncher
import com.esafirm.imagepicker.features.registerImagePicker
import com.example.technicaltest.databinding.FragmentGalleryBinding
import com.example.technicaltest.presentation.ui.adapters.gallery.GalleryAdapter
import kotlinx.coroutines.launch

class GalleryFragment : Fragment() {

    // Binding
    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    //ViewModel
    private val viewModel: GalleryViewModel by viewModels()

    // List of images for the gallery adapter
    private var gallery: MutableList<String> = mutableListOf()

    // Verify Camera permission
    private var hasCameraPermission = false
    // Verify Read storage permission
    private var hasReadStoragePermission = false
    // Permission launcher
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    // Image picker launcher
    private var imageLauncher: ImagePickerLauncher? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Validate camera permission
        hasCameraPermission = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        // Validate read storage permission
        hasReadStoragePermission = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        // Register permission launcher
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            hasCameraPermission = it[Manifest.permission.CAMERA] ?: false
            hasReadStoragePermission = it[Manifest.permission.READ_EXTERNAL_STORAGE] ?: false

            if (!hasCameraPermission || !hasReadStoragePermission) {
                showAlertDialog()
            }
        }

        // Register image picker launcher
        imageLauncher = registerImagePicker { images ->
            images.forEach { image ->
                viewModel.uploadImage(image.uri, image.name) {
                    gallery.add(it)
                    binding.rvGallery.adapter?.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerViewGallery()
        pickImage()

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                state.isLoading.let { isLoading ->
                    if (isLoading) {
                        binding.content.visibility = View.GONE
                        binding.progress.visibility = View.VISIBLE
                    } else {
                        binding.content.visibility = View.VISIBLE
                        binding.progress.visibility = View.GONE
                    }
                }
                state.message?.let { message ->
                    binding.content.visibility = View.GONE
                    binding.tvHelper.text = message
                    binding.tvHelper.visibility = View.VISIBLE
                }
                state.gallery.forEach { image ->
                    gallery.add(image)
                }
            }
        }
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Permisos denegados")
        builder.setMessage("Tu necesitas dar permisos para poder usar la cámara y la galería")
        builder.setPositiveButton("Ok") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    private fun initRecyclerViewGallery() {
        val gridLayoutManager = GridLayoutManager(context, 3)
        binding.rvGallery.layoutManager = gridLayoutManager
        binding.rvGallery.adapter = GalleryAdapter(gallery)
    }

    private fun pickImage() {
        binding.addPhoto.setOnClickListener {
            if (hasCameraPermission && hasReadStoragePermission) {
                imageLauncher?.launch()
            } else {
                permissionLauncher.launch(arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                ))
            }
        }
    }

}