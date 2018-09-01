package com.sandra.dupre.nicegallery.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sandra.dupre.nicegallery.R

class ColorAdapter : RecyclerView.Adapter<ColorViewHolder>() {
    var colorList: List<Pair<Int, String>> = emptyList()
    var listener: (String) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder = ColorViewHolder(
            LayoutInflater.from(parent.context).inflate(ColorViewHolder.layout, parent, false),
            listener
    )

    override fun getItemCount(): Int = colorList.size

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.bind(colorList[position])
    }

}

class ColorViewHolder(itemView: View,
                      private val listener: (String) -> Unit) : RecyclerView.ViewHolder(itemView) {
    companion object {
        const val layout = R.layout.cell_color
    }

    fun bind(color: Pair<Int, String>) {
        itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, color.first))
        itemView.setOnClickListener { listener(color.second) }
    }
}