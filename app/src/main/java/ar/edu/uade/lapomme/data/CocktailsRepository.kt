package ar.edu.uade.lapomme.data

import android.content.Context
import android.util.Log
import ar.edu.uade.lapomme.model.Cocktail

class CocktailsRepository {

    suspend fun getCocktailsByGlass(glass: String, context: Context) : ArrayList<Cocktail> {
        Log.d("THECOCKTAILDBAPI", "Cocktails repo")
        return CocktailsDataSource.getCocktailsByGlass(glass, context)
    }

    suspend fun getCocktailsbyname(name: String, context: Context) : ArrayList<Cocktail> {
        return CocktailsDataSource.getCocktailsbyname(name, context)
    }

    suspend fun getCocktailsbyingredient(ingredient: String, context: Context) : ArrayList<Cocktail> {
        return CocktailsDataSource.getCocktailsbyingredient(ingredient, context)
    }

    suspend fun getCocktailbyid(id: String, context: Context) : Cocktail? {
        return CocktailsDataSource.getCocktailbyid(id, context)
    }

    suspend fun addFav(id: String) {
        return CocktailsDataSource.addFav(id)
    }

    suspend fun deleteFav(id: String) {
        return CocktailsDataSource.deleteFav(id)
    }

    suspend fun getCocktailsDB() {
        return CocktailsDataSource.getCocktailsDB()
    }
}