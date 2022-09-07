package com.example.technicaltest.presentation.ui.fragments.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.technicaltest.data.repository.PeopleRepository
import com.example.technicaltest.data.repository.local.LocalImagesRepository
import com.example.technicaltest.data.repository.local.LocalMovieRepository
import com.example.technicaltest.data.repository.local.LocalPeopleRepositoryImp
import com.example.technicaltest.domain.movie.Movie
import com.example.technicaltest.domain.movie.People
import com.example.technicaltest.domain.util.PeopleState
import com.example.technicaltest.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val peopleRepository: PeopleRepository,
    private val localPeopleRepositoryImp: LocalPeopleRepositoryImp,
    private val localImagesRepository: LocalImagesRepository,
    private val localMovieRepository: LocalMovieRepository
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
                result.data?.let { people ->
                    CoroutineScope(Dispatchers.IO).launch {
                        localPeopleRepositoryImp.deletePeople()
                        localPeopleRepositoryImp.savePeople(people)
                    }
                    people.knownFor.map { movie ->
                        movie.peopleId = people.id
                        CoroutineScope(Dispatchers.IO).launch {
                            people.id?.let {
                                localMovieRepository.deleteByPeople(it)
                                localMovieRepository.saveMovie(movie)
                            }
                        }
                    }
                }
            }
            is Resource.Error -> {
                CoroutineScope(Dispatchers.IO).launch {
                    localPeopleRepositoryImp.getPeople().let { person ->
                        person.knownFor = localMovieRepository.getByPeople(person.id!!) as ArrayList<Movie>
                        _state.update { it.copy(
                            isLoading = false,
                            person = person,
                            message = null
                        ) }
                    }
                }
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
                result.data?.let { images ->
                    CoroutineScope(Dispatchers.IO).launch {
                        localImagesRepository.deleteImages()
                        localImagesRepository.saveImages(images)
                    }
                }
            }
            is Resource.Error -> {
                CoroutineScope(Dispatchers.IO).launch {
                    localImagesRepository.getImages().let { images ->
                        _state.update { it.copy(
                            images = images,
                            message = null
                        ) }
                    }
                }
            }
        }
    }

}