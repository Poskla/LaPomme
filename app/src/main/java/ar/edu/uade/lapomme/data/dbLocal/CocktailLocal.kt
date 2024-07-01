package ar.edu.uade.lapomme.data.dbLocal

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cocktails")
data class CocktailLocal (
    var strDrinkThumb: String,
    @PrimaryKey var idDrink: String,
    var strDrink: String,
    var strAlcoholic: String,
    var strInstructions: String,
    var strIngredient1: String,
    var strIngredient2: String,
    var strIngredient3: String,
    var strIngredient4: String
)
