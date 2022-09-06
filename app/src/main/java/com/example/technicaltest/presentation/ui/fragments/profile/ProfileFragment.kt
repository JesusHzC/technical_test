package com.example.technicaltest.presentation.ui.fragments.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.technicaltest.R
import com.example.technicaltest.databinding.FragmentProfileBinding
import com.example.technicaltest.di.ApiModule
import com.example.technicaltest.domain.movie.Movie
import com.example.technicaltest.presentation.ui.adapters.profile.ImagesAdapter
import com.example.technicaltest.presentation.ui.adapters.profile.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    //Binding
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    //ViewModel
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.getPopularPerson()

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
                state.message?.let { message ->
                    binding.tvError.text = message
                    binding.tvError.visibility = View.VISIBLE
                    binding.content.visibility = View.GONE
                }
                state.person?.let { person ->
                    binding.person = person

                    Glide.with(requireContext())
                        .load(ApiModule.BASE_URL_IMAGE + person.profilePath)
                        .into(binding.ivProfile)

                    person.id?.let { viewModel.getPersonImages(it) }

                    initRecyclerViewMovies(person.knownFor)
                }
                state.images?.let { images ->
                    val listStringImages = images.map { it.filePath }
                    initRecyclerView(listStringImages)
                }
            }
        }
    }

    private fun initRecyclerView(list: List<String?>) {
        binding.rvImages.apply {
            adapter = ImagesAdapter(list)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun initRecyclerViewMovies(list: List<Movie>) {
        binding.rvMovies.apply {
            adapter = MovieAdapter(list)
        }
    }

}