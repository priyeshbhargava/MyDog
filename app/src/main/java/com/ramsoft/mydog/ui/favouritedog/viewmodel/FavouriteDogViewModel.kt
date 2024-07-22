package com.ramsoft.mydog.ui.favouritedog.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramsoft.mydog.data.repository.DogRepository
import com.ramsoft.mydog.ui.favouritedog.intent.FavouriteDogIntent
import com.ramsoft.mydog.ui.favouritedog.viewstate.FavouriteDogState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

/**
 * @author Priyesh Bhargava
 * This is the middle layer to set and get data related to Favourite Dog and it's breed
 */
class FavouriteDogViewModel(private val repository: DogRepository) : ViewModel() {
   private val favouriteDogIntent = Channel<FavouriteDogIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<FavouriteDogState>(FavouriteDogState.LoadingState)
    val state: StateFlow<FavouriteDogState>
        get() = _state

    init {
        handleIntent()
    }
    fun sendIntent(intent: FavouriteDogIntent) {
        viewModelScope.launch {
            favouriteDogIntent.send(intent)
        }
    }
    private fun handleIntent() {
        viewModelScope.launch {
            favouriteDogIntent.consumeAsFlow().collect {
                when (it) {
                    is FavouriteDogIntent.LoadingIntent -> {
                        getFavouriteDogImage()
                    }

                    is FavouriteDogIntent.GotoNextScreen -> {
                        _state.value =
                            FavouriteDogState.NavigateToAllDog
                    }

                    else -> {

                    }
                }
            }
        }
    }

    /**
     * This method will fetch your favourite dog
     * If they have not set a favorite picture, use a random dog picture from the favorite breed
     * If they have not specified their favorite breed, show a picture of a random dog
     */
    private fun getFavouriteDogImage() {
        viewModelScope.launch {
            try {
                val responseFavImg = repository.getFavouriteDogImg()
                val responseFavBreed = repository.getFavouriteBreedName()
                if (responseFavImg.isNotEmpty()) {
                    _state.value = FavouriteDogState.DogSuccessState(responseFavImg)
                } else if (responseFavBreed.isNotEmpty()) {
                    val randomDogBreed = repository.getRandomDogImgByBreed(responseFavBreed)
                    _state.value = FavouriteDogState.DogSuccessState(randomDogBreed.message)
                } else {
                    val response = repository.getRandomDogImage()
                    if (response?.message?.isNotEmpty() == true) {
                        _state.value = FavouriteDogState.DogSuccessState(response?.message)
                    } else {
                        _state.value = FavouriteDogState.DogFailureState
                    }
                }

            } catch (e: Exception) {
                _state.value = FavouriteDogState.DogFailureState
            }

        }
    }

}
