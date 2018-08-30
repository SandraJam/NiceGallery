package com.sandra.dupre.nicegallery.detail.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sandra.dupre.nicegallery.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cell_detail.view.*

class DetailAdapter: RecyclerView.Adapter<DetailViewHolder>() {
    private val listFullScreen: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder =
            DetailViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                            DetailViewHolder.layout,
                            parent,
                            false
                    )
            )

    override fun getItemCount(): Int = listFullScreen.size

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.bind(listFullScreen[position])
    }

    fun replace(listFullScreen: List<String>) {
        val diff = DiffUtil.calculateDiff(DetailDiffUtil(listFullScreen, this.listFullScreen))
        this.listFullScreen.clear()
        this.listFullScreen.addAll(listFullScreen)
        diff.dispatchUpdatesTo(this)
    }
}

class DetailViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    companion object {
        const val layout = R.layout.cell_detail
    }

    fun bind(url: String) {
        Picasso.get()
                .load(url)
                .fit()
                .centerCrop()
                .into(itemView.fullScreenImageView)
    }
}

class DetailDiffUtil(
        private val newDetail: List<String>,
        private val oldDetail: List<String>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldDetail.size

    override fun getNewListSize(): Int = newDetail.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            newDetail[newItemPosition] === oldDetail[oldItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            newDetail[newItemPosition] == oldDetail[oldItemPosition]
}