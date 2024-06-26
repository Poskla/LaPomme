package ar.edu.uade.lapomme.data

import android.util.Log
import ar.edu.uade.lapomme.model.Cocktail
import com.google.firebase.firestore.FirebaseFirestore
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CocktailsDataSource {

    companion object {
        private val API_BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"
        private val api : CocktailsAPI
        private val db = FirebaseFirestore.getInstance()

        init {
            api = Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(CocktailsAPI::class.java)
        }

        suspend fun getCocktailsbyname(name: String) : ArrayList<Cocktail> {
            Log.d("THECOCKTAILDBAPI", "Tragos DataSource Getbyname")

            var result = api.getCocktailsbyname(name).execute()

            return if (result.isSuccessful) {
                Log.d("THECOCKTAILDBAPI", "Tragos DataSource Resultado Exitoso")
                result.body()?.drinks ?: ArrayList<Cocktail>()
            } else {
                Log.e("THECOCKTAILDBAPI", "Tragos DataSource Error en llamado a la API: " + result.message())
                ArrayList<Cocktail>()
            }
        }

        suspend fun getCocktailsbyingredient(ingredient: String) : ArrayList<Cocktail> {
            Log.d("THECOCKTAILDBAPI", "Tragos DataSource Getbyingredient")

            var result = api.getCocktailsbyingredient(ingredient).execute()

            return if (result.isSuccessful) {
                Log.d("THECOCKTAILDBAPI", "Tragos DataSource Resultado Exitoso")
                result.body()?.drinks ?: ArrayList<Cocktail>()
            } else {
                Log.e("THECOCKTAILDBAPI", "Tragos DataSource Error en llamado a la API: " + result.message())
                ArrayList<Cocktail>()
            }
        }

        suspend fun getCocktailbyid(id: String) : Cocktail? {
            Log.d("THECOCKTAILDBAPI", "Tragos DataSource Getcocktailbyid")

            /*var trago = suspendCoroutine<Cocktail?> { continuation ->
                db.collection("cocktails").document(id).get().addOnCompleteListener {
                    if (it.isSuccessful) {
                        var c = it.result.toObject(Cocktail::class.java)
                        continuation.resume(c)
                    } else {
                        continuation.resume(null)
                    }
                }
            }

            if (trago != null) {
                return trago
            }

            delay(1000)*/
            var result = api.getCocktailbyid(id).execute()

            if (result.isSuccessful) {
                Log.d("THECOCKTAILDBAPI", "Tragos DataSource Resultado Exitoso")
                val cocktails = result.body()?.drinks ?: return null
                val cocktail = cocktails.singleOrNull()
                if (cocktail != null) {
                    db.collection("cocktails").document(id).set(cocktail)
                } else {
                    Log.d("THECOCKTAILDBAPI", "Tragos DataSource Resultado NULL")
                }
                return cocktail
            } else {
                Log.e("THECOCKTAILDBAPI", "Tragos DataSource Error en llamado a API: " + result.message())
                return null
            }
        }
    }
}