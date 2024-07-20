package com.ramsoft.mydog.ui.common

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ramsoft.mydog.data.RetrofitInstance
import com.ramsoft.mydog.data.repository.DogRepository
import com.ramsoft.mydog.ui.alldogs.viewmodel.AllDogsViewModel
import com.ramsoft.mydog.ui.favouritedog.viewmodel.FavouriteDogViewModel

/**
 * @author Priyesh Bhargava
 */
class ViewModelFactory(val context: Context) : ViewModelProvider.Factory {

     override fun <T : ViewModel> create(modelClass: Class<T>): T {
         return when {
             modelClass.isAssignableFrom(FavouriteDogViewModel::class.java) -> {
                 (FavouriteDogViewModel(DogRepository(context,RetrofitInstance.api))) as T
             }

             modelClass.isAssignableFrom(AllDogsViewModel::class.java) -> {
                 (AllDogsViewModel(DogRepository(context,RetrofitInstance.api))) as T
             }

             else -> throw IllegalArgumentException("Unknown class name")
         }
    }

}