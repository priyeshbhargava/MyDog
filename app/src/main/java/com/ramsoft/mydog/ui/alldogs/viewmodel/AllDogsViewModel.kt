package com.ramsoft.mydog.ui.alldogs.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramsoft.mydog.data.repository.DogRepository
import com.ramsoft.mydog.ui.alldogs.intent.AllDogsIntent
import com.ramsoft.mydog.ui.alldogs.viewstate.AllDogsState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

/**
 * @author Priyesh Bhargava
 */
class AllDogsViewModel(private val repository: DogRepository) : ViewModel() {
    val allDogIntent = Channel<AllDogsIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<AllDogsState>(AllDogsState.LoadingState)
    val state: StateFlow<AllDogsState>
        get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            allDogIntent.consumeAsFlow().collect {
                when (it) {

                    is AllDogsIntent.FetchAllBreeds -> {
                        fetchAllBreeds()
                    }

                    is AllDogsIntent.FetchDogByBreed -> {
                        fetchDogsByBreed(it.breed, it.imgCount)
                    }

                    is AllDogsIntent.SetImageCount -> {
                        _state.value = AllDogsState.GetImageCount(it.imgCount)
                    }

                    is AllDogsIntent.FetchDogsCollection -> {
                        fetchDogsCollection()
                    }

                    is AllDogsIntent.AddFavouriteBreedName -> {
                        repository.addFavouriteBreedName(it.breedName)
                    }

                    is AllDogsIntent.AddFavouriteDogImg -> {
                        repository.addFavouriteDogImg(it.img)
                    }

                    is AllDogsIntent.AddDogImgToCollection -> {
                        repository.addDogImgToCollection(it.imgUrl)
                    }

                    is AllDogsIntent.FetchAllDogByBreed -> {
                        fetchAllDogsByBreed(it.breed)
                    }
                }
            }
        }
    }

    private fun fetchAllDogsByBreed(breedName: String) {
        viewModelScope.launch {
            try {
                val response = repository.getAllDogsImgByBreed(breedName)
                _state.value = AllDogsState.DogsByBreed(response.message)
            } catch (e: Exception) {
                // _state.value = AllDogsState.DogFailureState
            }

        }
    }

    private fun fetchDogsCollection() {
        viewModelScope.launch {
            try {
                val response = repository.getDogCollection()
                _state.value = AllDogsState.DogsCollection(response)
            } catch (e: Exception) {

            }
        }
    }

    private fun fetchDogsByBreed(breedName: String, imgCount: String) {
        viewModelScope.launch {
            try {
                val response = repository.getAllDogsImgByBreed(breedName, imgCount)
                _state.value = AllDogsState.DogsByBreed(response.message)
            } catch (e: Exception) {
                println("E" + e)
            }

        }
    }

    private fun fetchAllBreeds() {
        viewModelScope.launch {
            try {
                val response = repository.getAllBreedsList();
                _state.value = AllDogsState.FetchAllDogsBreeds(
                    response.message
                )

            } catch (e: Exception) {

            }

        }
    }

}
