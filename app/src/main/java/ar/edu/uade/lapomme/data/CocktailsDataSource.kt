package ar.edu.uade.lapomme.data

import android.content.Context
import android.util.Log
import ar.edu.uade.lapomme.data.dbLocal.AppDataBase
import ar.edu.uade.lapomme.data.dbLocal.toCocktailList
import ar.edu.uade.lapomme.data.dbLocal.toCocktailLocal
import ar.edu.uade.lapomme.model.Cocktail
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CocktailsDataSource {

    companion object {
        private val API_BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"
        private val api : CocktailsAPI
        private var db = FirebaseFirestore.getInstance()
        private var list: MutableList<Cocktail> = ArrayList<Cocktail>()

        init {
            api = Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(CocktailsAPI::class.java)
        }

        suspend fun getCocktailsByGlass(glass: String, context: Context) : ArrayList<Cocktail> {
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
        }

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

        suspend fun getCocktailsbyingredient(ingredient: String, context: Context) : ArrayList<Cocktail> {
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
        }

        suspend fun getCocktailbyid(id: String, context: Context) : Cocktail? {
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

        suspend fun addFav(id: String) {
            Log.d("THECOCKTAILDBAPI", "Cocktails AddFav")

            var result = api.getCocktailbyid(id).execute()
            val cocktails = result.body()?.drinks
            val cocktail = cocktails?.singleOrNull()
            if (cocktail != null) {
                db.collection("Favoritos").document(id).set(cocktail)
            }
            Log.d("THECOCKTAILDBAPI", "Se agregó a favoritos correctamente.")
        }

        suspend fun deleteFav(id: String) {
            Log.d("THECOCKTAILDBAPI", "Cocktails DeleteFav")

            var result = api.getCocktailbyid(id).execute()
            val cocktails = result.body()?.drinks
            val cocktail = cocktails?.singleOrNull()
            if (cocktail != null) {
                db.collection("Favoritos").document(id).delete()
            }
            Log.d("THECOCKTAILDBAPI", "Se eliminó de favoritos correctamente.")
        }

        /*fun getCocktailsDB() : ArrayList<Cocktail> {
            Log.d("THECOCKTAILDBAPI", "GetCocktailsDB")
            var tragos = ArrayList<Cocktail>()

            var c = db.collectionGroup("Favoritos").get()

            if (c.isSuccessful) {
                var cocktails = c.result.toObjects(Cocktail::class.java)
                tragos.addAll(cocktails)
                Log.d("THECOCKTAILDBAPI", "GetCocktailsDB exitoso")
            } else {
                Log.d("THECOCKTAILDBAPI", "GetCocktailsDB fallido")
            }

            return tragos
        }*/

        suspend fun getCocktailsDB() : ArrayList<Cocktail>
        {
            return suspendCancellableCoroutine { continuation ->
                db.collection("Favoritos").get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val lista = ArrayList<Cocktail>()
                        for (document in task.result) {
                            val c = document.toObject(Cocktail::class.java)
                            lista.add(c)
                        }
                        Log.d("THECOCKTAILDBAPI", "GetCocktailsDB exitoso")
                        continuation.resume(lista)
                    } else {
                        Log.d("THECOCKTAILDBAPI", "GetCocktailsDB fallido")
                        continuation.resumeWithException(task.exception ?: Exception("Error"))
                    }
                }
            }
        }
    }
}