package ar.edu.uade.lapomme.data

import android.content.Context
import ar.edu.uade.lapomme.model.Cocktail

class CocktailsRepository {

    /*suspend fun getCocktailsByGlass(glass: String, context: Context) : ArrayList<Cocktail> {
        return CocktailsDataSource.getCocktailsByGlass(glass, context)
    }*/

    suspend fun getCocktailsbyname(name: String, context: Context) : ArrayList<Cocktail> {
        return CocktailsDataSource.getCocktailsbyname(name, context)
    }

    /*suspend fun getCocktailsbyingredient(ingredient: String, context: Context) : ArrayList<Cocktail> {
        return CocktailsDataSource.getCocktailsbyingredient(ingredient, context)
    }*/

    fun getCocktailbyid(id: String, context: Context) : Cocktail? {
        return CocktailsDataSource.getCocktailbyid(id, context)
    }

    fun addFav(id: String) {
        return CocktailsDataSource.addFav(id)
    }

    fun deleteFav(id: String) {
        return CocktailsDataSource.deleteFav(id)
    }

    suspend fun getCocktailsDB() : ArrayList<Cocktail> {
        return CocktailsDataSource.getCocktailsDB()
    }
}