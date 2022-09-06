package com.example.technicaltest.presentation.ui.adapters.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.technicaltest.R
import com.example.technicaltest.domain.movie.Movie
import com.example.technicaltest.presentation.ui.adapters.movies.MovieViewHolder

class MovieAdapter(
    private val movies: List<Movie>,
) : RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_person, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindMoviePerson(movies[position])
    }

    override fun getItemCount(): Int = movies.size
}