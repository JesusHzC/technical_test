package com.example.technicaltest.presentation.ui.fragments.movies

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.technicaltest.data.repository.MovieRepository
import com.example.technicaltest.domain.util.MovieState
import com.example.technicaltest.domain.util.MovieType
import com.example.technicaltest.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRepository: MovieRepository
) : ViewModel() {

    private var _state = MutableStateFlow(MovieState())
    var state = _state.asStateFlow()
        private set


    fun getPopularMovies() = viewModelScope.launch {

        _state.update { it.copy(isLoading = true, type = null) }

        when (val result = moviesRepository.getPopularMovies()) {
            is Resource.Success -> {
                _state.update { it.copy(
                    isLoading = false,
                    movies = result.data ?: emptyList(),
                    error = null,
                    type = MovieType.POPULAR
                ) }
            }
            is Resource.Error -> {
                _state.update { it.copy(
                    isLoading = false,
                    movies = emptyList(),
                    error = "Error, could not load movies",
                    type = MovieType.POPULAR
                ) }
            }
        }
    }

    fun getTopRatedMovies() = viewModelScope.launch {

        _state.update { it.copy(isLoading = true, type = null) }

        when (val result = moviesRepository.getTopRatedMovies()) {
            is Resource.Success -> {
                _state.update { it.copy(
                    isLoading = false,
                    movies = result.data ?: emptyList(),
                    error = null,
                    type = MovieType.TOP_RATED
                ) }
            }
            is Resource.Error -> {
                _state.update { it.copy(
                    isLoading = false,
                    movies = emptyList(),
                    error = "Error, could not load movies",
                    type = MovieType.TOP_RATED
                ) }
            }
        }

    }

    fun getUpcomingMovies() = viewModelScope.launch {

        _state.update { it.copy(isLoading = true, type = null) }

        when (val result = moviesRepository.getUpcomingMovies()) {
            is Resource.Success -> {
                _state.update { it.copy(
                    isLoading = false,
                    movies = result.data ?: emptyList(),
                    error = null,
                    type = MovieType.UPCOMING
                ) }
            }
            is Resource.Error -> {
                _state.update { it.copy(
                    isLoading = false,
                    movies = emptyList(),
                    error = "Error, could not load movies",
                    type = MovieType.UPCOMING
                ) }
            }
        }

    }

}