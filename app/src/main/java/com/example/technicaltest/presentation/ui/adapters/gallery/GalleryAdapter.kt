package com.example.technicaltest.presentation.ui.adapters.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.technicaltest.R

class GalleryAdapter(private var list: List<String>) : RecyclerView.Adapter<GalleryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return GalleryViewHolder(layoutInflater.inflate(R.layout.item_gallery, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.bind(list[position])
    }
}