package com.ramsoft.mydog.ui.favouritedog.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ramsoft.mydog.R
import com.ramsoft.mydog.ui.alldogs.view.AllBreedsActivity
import com.ramsoft.mydog.ui.common.ViewModelFactory
import com.ramsoft.mydog.ui.favouritedog.intent.FavouriteDogIntent
import com.ramsoft.mydog.ui.favouritedog.viewmodel.FavouriteDogViewModel
import com.ramsoft.mydog.ui.favouritedog.viewstate.FavouriteDogState
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

/**
 * @author Priyesh Bhargava
 * If they have not set a favorite picture, use a random dog picture from the favorite breed
 * If they have not specified their favorite breed, show a picture of a random dog
 */
class FavouriteDogActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var favouriteDogViewModel: FavouriteDogViewModel
    private lateinit var dogImageView: ImageView
    private lateinit var btnNext:AppCompatButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_dog)
        /**
         * view initialization
         */
        dogImageView = findViewById(R.id.dogImageView)
        btnNext = findViewById(R.id.btnNext)
        /**
         * all listener initialization
         */
        btnNext.setOnClickListener(this)

        /**
         * Viewmodel init with ViewModelFactory
         */
        favouriteDogViewModel =
            ViewModelProvider(
                this,
                ViewModelFactory(this)
            )[FavouriteDogViewModel::class.java]

        /**
         * Launching the default intent to fetch Dog image
         */
        lifecycleScope.launch {
            favouriteDogViewModel.favouriteDogIntent.send(FavouriteDogIntent.LoadingIntent)
        }

        /**
         * Callback from viewModel as per the states received
         */

        lifecycleScope.launch {
            favouriteDogViewModel.state.collect {

                when (it) {
                    is FavouriteDogState.DogSuccessState -> {
                        Picasso.get().load(it.url).placeholder(R.drawable.progress_animation).into(dogImageView)
                    }

                    is FavouriteDogState.DogFailureState -> {
                        Toast.makeText(
                            this@FavouriteDogActivity,
                            "Unable to fetch your favourite Dog!",
                            Toast.LENGTH_LONG
                        )
                    }

                    is FavouriteDogState.NavigateToAllDog -> {
                        finish()
                        startActivity(
                            Intent(
                                this@FavouriteDogActivity,
                                AllBreedsActivity::class.java
                            )
                        )
                    }

                    else -> {

                    }
                }
            }

        }

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnNext->{
                lifecycleScope.launch {
                    favouriteDogViewModel.favouriteDogIntent.send(FavouriteDogIntent.GotoNextScreen)
                }
            }
        }
    }
}
