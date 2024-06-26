package ar.edu.uade.lapomme.data.dbLocal

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [CocktailLocal::class],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun cocktailsDao() : CocktailsDAO

    companion object {
        @Volatile
        private var _instance: AppDataBase? = null

        fun getInstance(context: Context) : AppDataBase = _instance ?: synchronized(this) {
            _instance ?: buildDatabase(context)
        }

        private fun buildDatabase(context: Context) : AppDataBase = Room.databaseBuilder(context, AppDataBase::class.java, "app_database")
            .fallbackToDestructiveMigration()
            .build()

        suspend fun clean(context: Context) = coroutineScope {
            launch(Dispatchers.IO) {
                getInstance(context).clearAllTables()
            }
        }
    }

}