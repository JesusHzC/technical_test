package com.example.technicaltest.presentation.ui.fragments.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.technicaltest.R
import com.example.technicaltest.databinding.FragmentMoviesBinding
import com.example.technicaltest.domain.movie.Movie
import com.example.technicaltest.domain.util.MovieType
import com.example.technicaltest.presentation.ui.adapters.movies.MoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.getPopularMovies()
            viewModel.getTopRatedMovies()
            viewModel.getUpcomingMovies()

            viewModel.state.collect { state ->
                state.isLoading.let { isLoading ->
                    if (isLoading) {
                        binding.progress.visibility = View.VISIBLE
                        binding.content.visibility = View.GONE
                    } else {
                        binding.progress.visibility = View.GONE
                        binding.content.visibility = View.VISIBLE
                    }
                }
                state.error?.let { error ->
                    binding.content.visibility = View.GONE
                    binding.tvError.visibility = View.VISIBLE
                    binding.tvError.text = error
                }
                state.type?.let { type ->
                    when (type) {
                        MovieType.POPULAR -> {
                            initRecyclerViewPopularMovies(state.movies)
                        }
                        MovieType.TOP_RATED -> {
                            initRecyclerViewTopRatedMovies(state.movies)
                        }
                        MovieType.UPCOMING -> {
                            initRecyclerViewUpcomingMovies(state.movies)
                        }
                        else -> { Unit }
                    }
                }
            }
        }
    }

    // Top movies
    private fun initRecyclerViewTopRatedMovies(movies: List<Movie>) {
        binding.rvTopRatedMovies.adapter = MoviesAdapter(movies)
    }

    // Popular movies
    private fun initRecyclerViewPopularMovies(movies: List<Movie>) {
        binding.rvPopularMovies.adapter = MoviesAdapter(movies)
    }

    // Upcoming movies
    private fun initRecyclerViewUpcomingMovies(movies: List<Movie>) {
        binding.rvUpcomingMovies.adapter = MoviesAdapter(movies, true)
    }

}