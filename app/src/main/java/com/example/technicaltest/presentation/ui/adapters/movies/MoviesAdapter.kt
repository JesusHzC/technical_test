package com.example.technicaltest.presentation.ui.adapters.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.technicaltest.R
import com.example.technicaltest.domain.movie.Movie

class MoviesAdapter(
    private val movies: List<Movie>,
    private val isUpcomingMovie: Boolean = false
) : RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        if (isUpcomingMovie) {
            return MovieViewHolder(layoutInflater.inflate(R.layout.item_upcoming_movie, parent, false))
        }

        return MovieViewHolder(layoutInflater.inflate(R.layout.item_movie, parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount() = movies.size

}