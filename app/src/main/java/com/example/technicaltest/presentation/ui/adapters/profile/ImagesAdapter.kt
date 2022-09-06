package com.example.technicaltest.presentation.ui.adapters.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.technicaltest.R
import com.example.technicaltest.presentation.ui.adapters.movies.MovieViewHolder

class ImagesAdapter(
    private val images: List<String?>
) : RecyclerView.Adapter<MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val image = images[position]
        holder.bindImage(image)
    }

    override fun getItemCount(): Int = images.size
}