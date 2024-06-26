package ar.edu.uade.lapomme.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ar.edu.uade.lapomme.R

class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val img: ImageView = itemView.findViewById(R.id.imgCocktail)
    val name: TextView = itemView.findViewById(R.id.txtName)
}