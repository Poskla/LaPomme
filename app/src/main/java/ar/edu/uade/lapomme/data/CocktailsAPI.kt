package ar.edu.uade.lapomme.data

import ar.edu.uade.lapomme.model.CocktailResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call

interface CocktailsAPI {

    @GET("filter.php")
    fun getCocktailsByGlass(
        @Query("g") glass: String
    ) : Call<CocktailResponse>

    @GET("search.php")
    fun getCocktailsbyname(
        @Query("s") name: String
    ) : Call<CocktailResponse>

    @GET("search.php")
    fun getCocktailsbyingredient(
        @Query("i") ingredient: String
    ) : Call<CocktailResponse>

    @GET("lookup.php")
    fun getCocktailbyid(
        @Query("i") id: String
    ) : Call<CocktailResponse>

}
