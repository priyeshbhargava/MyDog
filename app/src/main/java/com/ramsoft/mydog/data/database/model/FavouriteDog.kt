package com.ramsoft.mydog.data.database.model


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_dog")
data class FavouriteDog(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val url: String
)