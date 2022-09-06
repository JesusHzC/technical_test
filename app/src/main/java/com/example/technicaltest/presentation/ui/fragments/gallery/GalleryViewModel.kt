package com.example.technicaltest.presentation.ui.fragments.gallery

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.technicaltest.domain.util.GalleryState
import com.example.technicaltest.services.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GalleryViewModel : ViewModel() {

    init {
        getGallery()
    }

    private val firebaseStorage = FirebaseStorage()

    private var _state = MutableStateFlow(GalleryState())
    var state = _state.asStateFlow()
        private set

    private fun getGallery() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }

        val result = firebaseStorage.getAllImages()

        if (result.isEmpty()){
            _state.update { it.copy(isLoading = false, message = "No images found") }
        } else {
            _state.update { it.copy(isLoading = false, gallery = result) }
        }
    }

    fun uploadImage(uri: Uri, name: String, callback: (String) -> Unit) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, gallery = emptyList()) }
        firebaseStorage.uploadImage(uri, name) { response ->
            _state.update { it.copy(isLoading = false, gallery = emptyList()) }
            callback(response)
        }
    }

}