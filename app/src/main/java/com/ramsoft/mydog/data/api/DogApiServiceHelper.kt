package com.ramsoft.mydog.data.api

import com.ramsoft.mydog.data.database.model.DogCollection
import com.ramsoft.mydog.ui.alldogs.model.AllDogsBreedsResponse
import com.ramsoft.mydog.ui.alldogs.model.DogsByBreedsResponse
import com.ramsoft.mydog.ui.favouritedog.model.DogImageApiResponse

interface DogApiServiceHelper {
    suspend fun getRandomDogImage(): DogImageApiResponse
    suspend fun getAllBreedsList(): AllDogsBreedsResponse
    suspend fun getAllDogsImgByBreed(breed: String,imgCount: String): DogsByBreedsResponse
    suspend fun getAllDogsImgByBreed(breed: String): DogsByBreedsResponse
    suspend fun addFavouriteDogImg(img: String)
    suspend fun addFavouriteBreedName(breedName: String)
    suspend fun getFavouriteDogImg():String
    suspend fun getFavouriteBreedName():String
    suspend fun getRandomDogImgByBreed(breed: String): DogImageApiResponse
    suspend fun addDogImgToCollection(imgUrl: String)
    suspend fun getDogCollection():List<DogCollection>
}
