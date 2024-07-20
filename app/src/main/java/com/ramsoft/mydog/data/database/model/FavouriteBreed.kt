package com.ramsoft.mydog.data.database.model


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_breed")
data class FavouriteBreed(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val breedName: String
)