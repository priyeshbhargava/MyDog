package com.ramsoft.mydog.data.api

import com.ramsoft.mydog.ui.alldogs.model.AllDogsBreedsResponse
import com.ramsoft.mydog.ui.alldogs.model.DogsByBreedsResponse
import com.ramsoft.mydog.ui.favouritedog.model.DogImageApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DogApiService {
    @GET("breeds/image/random")
    suspend fun getRandomDogImage(): DogImageApiResponse

    @GET("breeds/list/all")
    suspend fun getAllBreedsList(): AllDogsBreedsResponse

    @GET("breed/{breed}/images/random/{imgCount}")
    suspend fun getAllDogsImgByBreed(@Path("breed") breed: String,@Path("imgCount") imgCount: String): DogsByBreedsResponse

    @GET("breed/{breed}/images/random")
    suspend fun getRandomDogImgByBreed(@Path("breed") breed: String): DogImageApiResponse

    @GET("breed/{breed}/images")
    suspend fun getAllDogsImgByBreed(@Path("breed") breed: String): DogsByBreedsResponse

}
