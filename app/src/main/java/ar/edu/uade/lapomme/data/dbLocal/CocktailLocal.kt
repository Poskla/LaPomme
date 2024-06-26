package ar.edu.uade.lapomme.data.dbLocal

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cocktails")
data class CocktailLocal (
    var strDrinkThumb: String,
    @PrimaryKey val idDrink: String,
    var strDrink: String,
    var strAlcoholic: String
)
