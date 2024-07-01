package ar.edu.uade.lapomme.data.dbLocal

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CocktailsDAO {
    @Query("SELECT * FROM cocktails")
    fun getAll() : List<CocktailLocal>

    @Query("SELECT * FROM cocktails WHERE idDrink = :idDrink LIMIT 1")
    fun getByPK(idDrink: String) : CocktailLocal

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(vararg cocktail: CocktailLocal)

    @Delete
    fun delete(cocktail: CocktailLocal)
}