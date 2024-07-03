package ar.edu.uade.lapomme.ui

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import ar.edu.uade.lapomme.R
import ar.edu.uade.lapomme.model.Cocktail
import com.bumptech.glide.Glide
import java.util.Locale

class MainAdapter : RecyclerView.Adapter<MainViewHolder>() {

    var items: MutableList<Cocktail> = ArrayList<Cocktail>()
    var itemsBusq: MutableList<Cocktail> = ArrayList<Cocktail>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cocktail_item, parent, false)
        return MainViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d("THECOCKTAILDBAPI", "Size: " + items.size)
        return items.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        Log.d("THECOCKTAILDBAPI", "Position: " + position)
        holder.name.text = items[position].strDrink

        Glide.with(holder.itemView)
            .load(items[position].strDrinkThumb)
            .into(holder.img)

        holder.itemView.setOnClickListener {
            val id = items[position].idDrink
            val intent = Intent(holder.itemView.context, CocktailActivity::class.java)
            intent.putExtra("id", id)
            holder.itemView.context.startActivity(intent)
        }
    }

    fun showItems(List: ArrayList<Cocktail>) {
        this.itemsBusq = List
        items.addAll(itemsBusq)
        notifyDataSetChanged()
    }

    fun searchItems(busqueda: String) {
        items.clear()
        if (busqueda.isEmpty()) {
            items.addAll(itemsBusq)
        } else {
            val searchText = busqueda.lowercase(Locale.getDefault())
            for (item in itemsBusq) {
                if (item.strDrink.lowercase(Locale.getDefault()).contains(searchText)) {
                    items.add(item)
                }
                if (item.idDrink == searchText) {
                    items.add(item)
                }
                if (item.strIngredient1.lowercase(Locale.getDefault()).contains(searchText)) {
                    items.add(item)
                }
                if (item.strIngredient2.lowercase(Locale.getDefault()).contains(searchText)) {
                    items.add(item)
                }
                if (item.strIngredient3.lowercase(Locale.getDefault()).contains(searchText)) {
                    items.add(item)
                }
            }
        }
        notifyDataSetChanged()
    }

    fun Update(lista: MutableList<Cocktail>) {
        items = lista
        notifyDataSetChanged()
    }
}