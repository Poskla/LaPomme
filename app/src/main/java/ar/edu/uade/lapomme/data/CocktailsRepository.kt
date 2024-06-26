package ar.edu.uade.lapomme.data

import android.util.Log
import ar.edu.uade.lapomme.model.Cocktail

class CocktailsRepository {

    suspend fun getCocktailsbyname(name: String) : ArrayList<Cocktail> {
        Log.d("THECOCKTAILDBAPI", "Cocktails repo")
        return CocktailsDataSource.getCocktailsbyname(name)
    }

    suspend fun getCocktailsbyingredient(ingredient: String) : ArrayList<Cocktail> {
        return CocktailsDataSource.getCocktailsbyingredient(ingredient)
    }

    suspend fun getCocktailbyid(id: String) : Cocktail? {
        return CocktailsDataSource.getCocktailbyid(id)
    }
}