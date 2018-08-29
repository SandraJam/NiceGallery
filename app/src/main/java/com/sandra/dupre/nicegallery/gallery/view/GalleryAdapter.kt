package com.sandra.dupre.nicegallery.gallery.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sandra.dupre.nicegallery.R
import androidx.recyclerview.widget.DiffUtil
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cell_gallery.view.*


class GalleryAdapter: RecyclerView.Adapter<GalleryViewHolder>() {
    private val picturesList: MutableList<PictureViewModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder =
            GalleryViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                            GalleryViewHolder.layout,
                            parent,
                            false
                    )
            )

    override fun getItemCount(): Int = picturesList.size

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.bind(picturesList[position])
    }

    fun replace(picturesList: List<PictureViewModel>) {
        val diff = DiffUtil.calculateDiff(GalleryDiffUtil(picturesList, this.picturesList))
        this.picturesList.clear()
        this.picturesList.addAll(picturesList)
        diff.dispatchUpdatesTo(this)
    }
}

class GalleryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    companion object {
        const val layout = R.layout.cell_gallery
    }

    fun bind(pictureViewModel: PictureViewModel) {
        Picasso.get()
                .load(pictureViewModel.previewUrl)
                .fit()
                .centerCrop()
                .into(itemView.galleryImageView)
    }
}

class GalleryDiffUtil(
        private val newGallery: List<PictureViewModel>,
        private val oldGallery: List<PictureViewModel>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldGallery.size

    override fun getNewListSize(): Int = newGallery.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            newGallery[newItemPosition].id == oldGallery[oldItemPosition].id &&
                    newGallery[newItemPosition].previewUrl == oldGallery[oldItemPosition].previewUrl

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            newGallery[newItemPosition] == oldGallery[oldItemPosition]
}