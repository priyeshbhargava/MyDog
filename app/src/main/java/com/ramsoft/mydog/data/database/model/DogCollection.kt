package com.ramsoft.mydog.data.database.model


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dog_collection")
data class DogCollection(
    @PrimaryKey(autoGenerate = true)
    val id: Int =0,
    val url: String
)