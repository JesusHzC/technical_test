package com.example.technicaltest.presentation.ui.fragments.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.technicaltest.data.repository.MovieRepository
import com.example.technicaltest.data.repository.local.LocalMovieRepository
import com.example.technicaltest.domain.util.MovieState
import com.example.technicaltest.domain.util.MovieType
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
class MoviesViewModel @Inject constructor(
    private val moviesRepository: MovieRepository,
    private val localMovieRepository: LocalMovieRepository
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
                result.data?.map { movie ->
                    movie.type = MovieType.POPULAR
                    CoroutineScope(Dispatchers.IO).launch {
                        localMovieRepository.deleteByType(MovieType.POPULAR)
                        localMovieRepository.saveMovie(movie)
                    }
                }
            }
            is Resource.Error -> {
                CoroutineScope(Dispatchers.IO).launch {
                    localMovieRepository.getByType(MovieType.POPULAR).let { movies ->
                        _state.update { it.copy(
                            isLoading = false,
                            movies = movies,
                            error = null,
                            type = MovieType.POPULAR
                        ) }
                    }
                }
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
                result.data?.map { movie ->
                    movie.type = MovieType.TOP_RATED
                    CoroutineScope(Dispatchers.IO).launch {
                        localMovieRepository.deleteByType(MovieType.TOP_RATED)
                        localMovieRepository.saveMovie(movie)
                    }
                }
            }
            is Resource.Error -> {
                CoroutineScope(Dispatchers.IO).launch {
                    localMovieRepository.getByType(MovieType.TOP_RATED).let { movies ->
                        _state.update { it.copy(
                            isLoading = false,
                            movies = movies,
                            error = null,
                            type = MovieType.TOP_RATED
                        ) }
                    }
                }
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
                result.data?.map { movie ->
                    movie.type = MovieType.UPCOMING
                    CoroutineScope(Dispatchers.IO).launch {
                        localMovieRepository.deleteByType(MovieType.UPCOMING)
                        localMovieRepository.saveMovie(movie)
                    }
                }
            }
            is Resource.Error -> {
                CoroutineScope(Dispatchers.IO).launch {
                    localMovieRepository.getByType(MovieType.UPCOMING).let { movies ->
                        _state.update { it.copy(
                            isLoading = false,
                            movies = movies,
                            error = null,
                            type = MovieType.UPCOMING
                        ) }
                    }
                }
            }
        }

    }

}