package com.example.technicaltest.presentation.ui.fragments.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.technicaltest.data.repository.PeopleRepository
import com.example.technicaltest.domain.movie.People
import com.example.technicaltest.domain.util.PeopleState
import com.example.technicaltest.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val peopleRepository: PeopleRepository
) : ViewModel() {

    private var _state = MutableStateFlow(PeopleState())
    var state = _state.asStateFlow()
        private set

    private var _profile = MutableLiveData<People>()
    val profile = _profile

    fun getPopularPerson() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        when (val result = peopleRepository.getPopularPerson()) {
            is Resource.Success -> {
                _state.update { it.copy(
                    isLoading = false,
                    person = result.data,
                    message = null
                ) }
            }
            is Resource.Error -> {
                _state.update { it.copy(
                    isLoading = false,
                    person = null,
                    message = "Error, could not get popular person"
                ) }
            }
        }
    }

    fun getPersonImages(id: Int) = viewModelScope.launch {

        when (val result = peopleRepository.getImagesPerson(id)) {
            is Resource.Success -> {
                _state.update { it.copy(
                    images = result.data,
                    message = null
                ) }
            }
            is Resource.Error -> {
                _state.update { it.copy(
                    images = null,
                    message = "Error, could not get person images",
                ) }
            }
        }
    }

}