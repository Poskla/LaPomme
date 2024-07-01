package ar.edu.uade.lapomme.data.dbLocal

import ar.edu.uade.lapomme.model.Cocktail

fun CocktailLocal.toCocktail() = Cocktail(
    strDrinkThumb ?:"",
    idDrink ?:"",
    strDrink ?:"",
    strAlcoholic ?:"",
strInstructions ?:"",
strIngredient1 ?:"",
strIngredient2 ?:"",
strIngredient3 ?:"",
strIngredient4 ?:""
)

fun List<CocktailLocal>.toCocktailList() = map(CocktailLocal::toCocktail)

fun Cocktail.toCocktailLocal() = CocktailLocal(
    strDrinkThumb,
    idDrink,
    strDrink,
    strAlcoholic,
    strInstructions,
    strIngredient1,
    strIngredient2 ?:"",
    strIngredient3 ?:"",
    strIngredient4 ?:""
)

fun List<Cocktail>.toCocktailLocalList() = map(Cocktail::toCocktailLocal)