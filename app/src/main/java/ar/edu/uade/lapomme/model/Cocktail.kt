package ar.edu.uade.lapomme.model

data class Cocktail (
    var strDrinkThumb: String,
    var idDrink: String,
    var strDrink: String,
    var strAlcoholic: String,
    var strInstructions: String,
    var strIngredient1: String,
    var strIngredient2: String,
    var strIngredient3: String,
    var strIngredient4: String
)

data class CocktailResponse(
    val drinks: ArrayList<Cocktail>
)