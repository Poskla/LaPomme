package ar.edu.uade.lapomme.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ar.edu.uade.lapomme.data.CocktailsRepository
import ar.edu.uade.lapomme.model.Cocktail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlin.coroutines.CoroutineContext
import kotlin.properties.Delegates

class MainViewModel : ViewModel() {

    val cocktailRepo: CocktailsRepository = CocktailsRepository()
    var cocktails: MutableLiveData<ArrayList<Cocktail>> = MutableLiveData<ArrayList<Cocktail>>()

    var fav: Boolean = false
    var name = "e"

    private val coroutineContext: CoroutineContext = newSingleThreadContext("tragos")
    private val scope = CoroutineScope(coroutineContext)

    fun init(context: MainActivity) {
        scope.launch {
            kotlin.runCatching {
                if (fav){
                    cocktailRepo.getCocktailsDB()
                }
                else{
                    cocktailRepo.getCocktailsbyname(name, context)
                }
            }.onSuccess {
                Log.d("THECOCKTAILDBAPI", "Cocktails Main onSuccess")
                cocktails.postValue(it)
                Log.d("THECOCKTAILDBAPI", it[0].toString())
            }.onFailure {
                Log.e("THECOCKTAILDBAPI", "Cocktails Main Error: " + it)
            }
        }
    }
}