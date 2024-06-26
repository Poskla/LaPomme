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

class CocktailViewModel : ViewModel() {

    val cocktailRepo: CocktailsRepository = CocktailsRepository()
    var cocktail: MutableLiveData<Cocktail> = MutableLiveData<Cocktail>()

    private val coroutineContext: CoroutineContext = newSingleThreadContext("cocktail")
    private val scope = CoroutineScope(coroutineContext)

    fun init(id: String) {
        scope.launch {
            kotlin.runCatching {
                cocktailRepo.getCocktailbyid(id)
            }.onSuccess {
                Log.d("THECOCKTAILDBAPI", "Cocktail Info onSuccess")
                cocktail.postValue(it)
                Log.d("THECOCKTAILDBAPI", it.toString())
            }.onFailure {
                Log.e("THECOCKTAILDBAPI", "Cocktail Info Error: " + it)
            }
        }
    }
}