package ar.edu.uade.lapomme.data.dbLocal

import ar.edu.uade.lapomme.model.Cocktail

fun CocktailLocal.toCocktail() = Cocktail(
    "",
    "",
    strDrink ?: "",
    "",
    "",
    "",
    "",
    "",
    ""
)

fun List<CocktailLocal>.toCocktailList() = map(CocktailLocal::toCocktail)

fun Cocktail.toCocktailLocal() = CocktailLocal(
    "",
    idDrink ?: "",
    strDrink ?: "",
    ""
)

fun List<Cocktail>.toCocktailLocalList() = map(Cocktail::toCocktailLocal)