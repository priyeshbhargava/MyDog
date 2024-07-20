package com.ramsoft.mydog.data.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ramsoft.mydog.data.database.model.DogCollection
import com.ramsoft.mydog.data.database.model.FavouriteBreed
import com.ramsoft.mydog.data.database.model.FavouriteDog

@Database(entities = [FavouriteDog::class, FavouriteBreed::class, DogCollection::class], version = 1)
abstract class DogDatabaseManager : RoomDatabase() {
    abstract fun dogDao(): DogDao

    companion object {
        @Volatile
        private var INSTANCE: DogDatabaseManager? = null

        fun getDatabase(context: Context): DogDatabaseManager {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DogDatabaseManager::class.java,
                    "dog_database").build()
                INSTANCE = instance
                instance
            }
        }
    }
}
