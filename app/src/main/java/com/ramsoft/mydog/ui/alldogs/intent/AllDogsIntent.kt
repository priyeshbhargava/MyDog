package com.ramsoft.mydog.ui.alldogs.intent

/**
 * @author Priyesh Bhargava
 */

sealed class AllDogsIntent {

    data object FetchAllBreeds : AllDogsIntent()
    data class FetchDogByBreed(val breed: String, val imgCount: String) : AllDogsIntent()
    data class FetchAllDogByBreed(val breed: String) : AllDogsIntent()
    data class SetImageCount(val imgCount: String) : AllDogsIntent()
    data object FetchDogsCollection : AllDogsIntent()
    data class AddFavouriteDogImg(val img: String) : AllDogsIntent()
    data class AddFavouriteBreedName(val breedName: String) : AllDogsIntent()
    data class AddDogImgToCollection(val imgUrl: String) : AllDogsIntent()
}