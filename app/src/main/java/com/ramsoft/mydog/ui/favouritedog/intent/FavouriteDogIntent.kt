package com.ramsoft.mydog.ui.favouritedog.intent
/**
 * @author Priyesh Bhargava
 * This is the intent for user action for Splash favourite screen
 */
sealed class FavouriteDogIntent {
    data object LoadingIntent : FavouriteDogIntent()
    data object IdleIntent : FavouriteDogIntent()
    data object DogListIntent : FavouriteDogIntent()
    data object GotoNextScreen : FavouriteDogIntent()
}