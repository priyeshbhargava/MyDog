package com.ramsoft.mydog.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ramsoft.mydog.data.database.model.DogCollection
import com.ramsoft.mydog.data.database.model.FavouriteBreed
import com.ramsoft.mydog.data.database.model.FavouriteDog
import kotlinx.coroutines.flow.Flow

@Dao
interface DogDao {
    //Favourite Dog
    @Insert
    suspend fun insertFavouriteDog(favouriteDog: FavouriteDog)

    @Update
    suspend fun updateFavouriteDog(favouriteDog: FavouriteDog)

    @Query("SELECT * FROM favourite_dog")
     fun getFavouriteDog(): Flow<List<FavouriteDog>>


    //Favourite Breed
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteBreed(favouriteBreed: FavouriteBreed)

    @Update
    suspend fun updateFavouriteBreed(favouriteBreed: FavouriteBreed)

    @Query("SELECT * FROM favourite_breed")
    fun getFavouriteBreed(): Flow<List<FavouriteBreed>>

    //Collection

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDogCollection(dogCollection: DogCollection)

    @Update
    suspend fun updateDogCollection(dogCollection: DogCollection)

    @Query("SELECT * FROM dog_collection")
    fun getDogCollection(): Flow<List<DogCollection>>

}
