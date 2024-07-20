package com.ramsoft.mydog.ui.alldogs.viewstate

import com.ramsoft.mydog.data.database.model.DogCollection
/**
 * @author Priyesh Bhargava
 */
sealed class AllDogsState {
    data object LoadingState: AllDogsState()
    data class FetchAllDogsBreeds(val allBreeds:Map<String,List<String>>): AllDogsState()
    data class DogsByBreed(val allDogByBreed:List<String>): AllDogsState()
    data class DogsCollection(val allDogByBreed:List<DogCollection>): AllDogsState()
    data class GetImageCount(val count: String): AllDogsState()


}