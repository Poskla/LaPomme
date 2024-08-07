package ar.edu.uade.lapomme.data

import android.content.Context
import android.util.Log
import ar.edu.uade.lapomme.data.dbLocal.AppDataBase
import ar.edu.uade.lapomme.data.dbLocal.toCocktailList
import ar.edu.uade.lapomme.data.dbLocal.toCocktailLocal
import ar.edu.uade.lapomme.model.Cocktail
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class CocktailsDataSource {

    companion object {
        private val API_BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"
        private val api : CocktailsAPI
        private var db = FirebaseFirestore.getInstance()

        init {
            api = Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(CocktailsAPI::class.java)
        }

        /*suspend fun getCocktailsByGlass(glass: String, context: Context) : ArrayList<Cocktail> {
            Log.d("THECOCKTAILDBAPI", "Tragos DataSource GetByGlass")

            // Traigo la información guardada localmente
            var dbLocal = AppDataBase.getInstance(context)
            var cocktailsLocal = dbLocal.cocktailsDao().getAll()
            if (cocktailsLocal.size > 0){
                Log.d("THECOCKTAILDBAPI", "Tragos DataSource GetByGlass Local")
                return cocktailsLocal.toCocktailList() as ArrayList<Cocktail>
            }
            delay(4000)

            // Traigo información de la API
            var result = api.getCocktailsByGlass(glass).execute()

            return if (result.isSuccessful) {
                Log.d("THECOCKTAILDBAPI", "Tragos DataSource GetByGlass API onSuccess")
                var cocktails = result.body()?.drinks ?: ArrayList<Cocktail>()
                if (cocktails.size > 0) {
                    for (cocktail in cocktails){
                        dbLocal.cocktailsDao().save(cocktail.toCocktailLocal())
                    }
                }
                cocktails
            } else {
                Log.e("THECOCKTAILDBAPI", "Tragos DataSource GetByGlass Error: " + result.message())
                ArrayList<Cocktail>()
            }
        }*/

        suspend fun getCocktailsbyname(name: String, context: Context) : ArrayList<Cocktail> {
            Log.d("THECOCKTAILDBAPI", "Tragos DataSource Getbyname")

            // Traigo la información guardada localmente
            var dbLocal = AppDataBase.getInstance(context)
            var cocktailsLocal = dbLocal.cocktailsDao().getAll()
            if (cocktailsLocal.size > 0){
                Log.d("THECOCKTAILDBAPI", "Tragos DataSource Getbyname Local")
                return cocktailsLocal.toCocktailList() as ArrayList<Cocktail>
            }
            delay(4000)

            // Traigo información de la API
            var result = api.getCocktailsbyname(name).execute()

            return if (result.isSuccessful) {
                Log.d("THECOCKTAILDBAPI", "Tragos DataSource Getbyname API onSuccess")
                var cocktails = result.body()?.drinks ?: ArrayList<Cocktail>()
                if (cocktails.size > 0) {
                    for (cocktail in cocktails){
                        dbLocal.cocktailsDao().save(cocktail.toCocktailLocal())
                    }
                }
                cocktails
            } else {
                Log.e("THECOCKTAILDBAPI", "Tragos DataSource Getbyname Error: " + result.message())
                ArrayList<Cocktail>()
            }
        }

        /*suspend fun getCocktailsbyingredient(ingredient: String, context: Context) : ArrayList<Cocktail> {
            Log.d("THECOCKTAILDBAPI", "Tragos DataSource Getbyingredient")

            // Traigo la información guardada localmente
            var dbLocal = AppDataBase.getInstance(context)
            var cocktailsLocal = dbLocal.cocktailsDao().getAll()
            if (cocktailsLocal.size > 0){
                Log.d("THECOCKTAILDBAPI", "Tragos DataSource Getbyingredient Local")
                return cocktailsLocal.toCocktailList() as ArrayList<Cocktail>
            }
            delay(4000)

            // Traigo información de la API
            var result = api.getCocktailsbyingredient(ingredient).execute()

            return if (result.isSuccessful) {
                Log.d("THECOCKTAILDBAPI", "Tragos DataSource Getbyingredient API onSuccess")
                var cocktails = result.body()?.drinks ?: ArrayList<Cocktail>()
                if (cocktails.size > 0) {
                    for (cocktail in cocktails){
                        dbLocal.cocktailsDao().save(cocktail.toCocktailLocal())
                    }
                }
                cocktails
            } else {
                Log.e("THECOCKTAILDBAPI", "Tragos DataSource Getbyingredient API Error: " + result.message())
                ArrayList<Cocktail>()
            }
        }*/

        fun getCocktailbyid(id: String, context: Context) : Cocktail? {
            Log.d("THECOCKTAILDBAPI", "Tragos DataSource Getbyid")

            // Traigo la información de la API
            var result = api.getCocktailbyid(id).execute()

            return if (result.isSuccessful) {
                Log.d("THECOCKTAILDBAPI", "Tragos DataSource Getbyid API onSuccess")
                val cocktails = result.body()?.drinks ?: return null
                val cocktail = cocktails.singleOrNull()
                cocktail
            } else {
                Log.e("THECOCKTAILDBAPI", "Tragos DataSource Getbyid API Error: " + result.message())
                null
            }
        }

        fun addFav(id: String) {
            // Con un botón agrego el trago a Favoritos en Firestore
            Log.d("THECOCKTAILDBAPI", "Cocktails AddFav")

            var result = api.getCocktailbyid(id).execute()
            val cocktails = result.body()?.drinks
            val cocktail = cocktails?.singleOrNull()
            if (cocktail != null) {
                db.collection("Favoritos").document(id).set(cocktail)
            }
            Log.d("THECOCKTAILDBAPI", "Se agregó a favoritos correctamente.")
        }

        fun deleteFav(id: String) {
            // Con un botón elimino el trago de Favoritos en Firestore
            Log.d("THECOCKTAILDBAPI", "Cocktails DeleteFav")

            var result = api.getCocktailbyid(id).execute()
            val cocktails = result.body()?.drinks
            val cocktail = cocktails?.singleOrNull()
            if (cocktail != null) {
                db.collection("Favoritos").document(id).delete()
            }
            Log.d("THECOCKTAILDBAPI", "Se eliminó de favoritos correctamente.")
        }

        suspend fun getCocktailsDB() : ArrayList<Cocktail>
        {
            return suspendCancellableCoroutine { continuation ->
                db.collection("Favoritos").get().addOnCompleteListener {
                    if (it.isSuccessful) {
                        val lista = ArrayList<Cocktail>()
                        for (id in it.result) {
                            val c = id.toObject(Cocktail::class.java)
                            lista.add(c)
                        }
                        Log.d("THECOCKTAILDBAPI", "GetCocktailsDB exitoso")
                        continuation.resume(lista)
                    } else {
                        Log.d("THECOCKTAILDBAPI", "GetCocktailsDB fallido")
                        continuation.resumeWithException(it.exception ?: Exception("Error"))
                    }
                }
            }
        }
    }
}