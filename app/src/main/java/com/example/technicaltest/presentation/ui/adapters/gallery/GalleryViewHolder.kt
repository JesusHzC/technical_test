package com.example.technicaltest.presentation.ui.adapters.gallery

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.technicaltest.databinding.ItemGalleryBinding

class GalleryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding: ItemGalleryBinding = ItemGalleryBinding.bind(itemView)

    fun bind(urlImage: String) {
        Glide.with(itemView)
            .load(urlImage)
            .into(binding.ivPhoto)
    }
}