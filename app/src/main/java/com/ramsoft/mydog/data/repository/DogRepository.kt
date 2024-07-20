package com.ramsoft.mydog.data.repository

import android.content.Context
import com.ramsoft.mydog.ui.alldogs.model.AllDogsBreedsResponse
import com.ramsoft.mydog.ui.alldogs.model.DogsByBreedsResponse
import com.ramsoft.mydog.data.api.DogApiService
import com.ramsoft.mydog.data.api.DogApiServiceHelper
import com.ramsoft.mydog.data.database.model.DogCollection
import com.ramsoft.mydog.data.database.DogDatabaseManager
import com.ramsoft.mydog.data.database.model.FavouriteBreed
import com.ramsoft.mydog.data.database.model.FavouriteDog
import com.ramsoft.mydog.ui.favouritedog.model.DogImageApiResponse
import kotlinx.coroutines.flow.first

class DogRepository(context: Context, private val dogApiService: DogApiService) :
    DogApiServiceHelper {
    private val dogDatabaseManager = DogDatabaseManager.getDatabase(context)
    override suspend fun getRandomDogImage(): DogImageApiResponse {
        return dogApiService.getRandomDogImage();
    }

    override suspend fun getAllBreedsList(): AllDogsBreedsResponse {
        return dogApiService.getAllBreedsList()
    }

    override suspend fun getAllDogsImgByBreed(
        breed: String,
        imgCount: String
    ): DogsByBreedsResponse {
        return dogApiService.getAllDogsImgByBreed(breed, imgCount)
    }

    override suspend fun getAllDogsImgByBreed(breed: String): DogsByBreedsResponse {
        return dogApiService.getAllDogsImgByBreed(breed)
    }

    override suspend fun addFavouriteDogImg(img: String) {
        dogDatabaseManager.dogDao().getFavouriteDog().collect {
            if (it.isEmpty()) {
                dogDatabaseManager.dogDao().insertFavouriteDog(FavouriteDog(1, img))
            } else {
                dogDatabaseManager.dogDao().updateFavouriteDog(FavouriteDog(1, img))
            }
        }
    }

    override suspend fun addFavouriteBreedName(breedName: String) {
        dogDatabaseManager.dogDao().getFavouriteBreed().collect {
            if (it.isEmpty()) {
                dogDatabaseManager.dogDao().insertFavouriteBreed(FavouriteBreed(1, breedName))
            } else {
                dogDatabaseManager.dogDao().updateFavouriteBreed(FavouriteBreed(1, breedName))
            }
        }
    }

    override suspend fun getFavouriteDogImg(): String {
        val favImg = dogDatabaseManager.dogDao().getFavouriteDog()
        return if (favImg.first().isEmpty()) "" else favImg.first()[0].url
    }

    override suspend fun getFavouriteBreedName(): String {
        val favImg = dogDatabaseManager.dogDao().getFavouriteBreed()
        return if (favImg.first().isEmpty()) "" else favImg.first()[0].breedName
    }

    override suspend fun getRandomDogImgByBreed(breed: String): DogImageApiResponse {
        return dogApiService.getRandomDogImgByBreed(breed)
    }

    override suspend fun addDogImgToCollection(imgUrl: String) {
        dogDatabaseManager.dogDao().insertDogCollection(DogCollection(url = imgUrl))
    }

    override suspend fun getDogCollection(): List<DogCollection> {
        return dogDatabaseManager.dogDao().getDogCollection().first()
    }
}