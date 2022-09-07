package com.example.technicaltest.presentation.ui.fragments.gallery

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.technicaltest.dao.DBTest
import com.example.technicaltest.data.repository.local.LocalGalleyRepository
import com.example.technicaltest.domain.gallery.Gallery
import com.example.technicaltest.domain.util.GalleryState
import com.example.technicaltest.services.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val localGalleyRepository: LocalGalleyRepository
) : ViewModel() {

    init {
        getGallery()
    }

    private val firebaseStorage = FirebaseStorage()

    private var _state = MutableStateFlow(GalleryState())
    var state = _state.asStateFlow()
        private set

    private fun getGallery() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }

        var result = firebaseStorage.getAllImages()

        if (result.isEmpty()){
            CoroutineScope(Dispatchers.IO).launch {
                val newResult = localGalleyRepository.getAllImages()
                val listString = newResult.map { it.url }
                if (listString.isNotEmpty()) {
                    _state.update { it.copy(isLoading = false, gallery = listString, message = null) }
                } else {
                    _state.update { it.copy(message = "No hay imagenes") }
                }
            }
        } else {
            _state.update { it.copy(isLoading = false, gallery = result) }
        }
    }

    fun uploadImage(uri: Uri, name: String, callback: (String) -> Unit) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, gallery = emptyList()) }
        CoroutineScope(Dispatchers.IO).launch {
            localGalleyRepository.saveImage(Gallery(url = uri.toString()))
        }
        firebaseStorage.uploadImage(uri, name) { response ->
            _state.update { it.copy(isLoading = false, gallery = emptyList()) }
            callback(response)
        }
    }

}