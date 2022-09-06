package com.example.technicaltest.presentation.ui.adapters.movies

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.technicaltest.R
import com.example.technicaltest.di.ApiModule
import com.example.technicaltest.domain.movie.Movie

class MovieViewHolder(
    view: View,
) : RecyclerView.ViewHolder(view) {

    fun bind(movie: Movie) {
        Glide
            .with(itemView.context)
            .load(ApiModule.BASE_URL_IMAGE + movie.posterPath)
            .into(itemView.findViewById<ImageView>(R.id.ivPoster))
    }

}