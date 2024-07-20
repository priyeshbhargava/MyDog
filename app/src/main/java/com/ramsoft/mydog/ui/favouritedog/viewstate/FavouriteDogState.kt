package com.ramsoft.mydog.ui.favouritedog.viewstate
/**
 * @author Priyesh Bhargava
 * This is the state class to receive states on Splash/Favourite screen
 */
sealed class FavouriteDogState {
    data object LoadingState: FavouriteDogState()
    data class DogSuccessState(val url: String): FavouriteDogState()
    data object DogFailureState: FavouriteDogState()
    data object NavigateToAllDog: FavouriteDogState()
}