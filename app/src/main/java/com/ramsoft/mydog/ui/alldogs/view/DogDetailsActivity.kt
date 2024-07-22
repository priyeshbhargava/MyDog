package com.ramsoft.mydog.ui.alldogs.view

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import com.ramsoft.mydog.R
import com.ramsoft.mydog.data.database.DogDao
import com.ramsoft.mydog.ui.alldogs.intent.AllDogsIntent
import com.ramsoft.mydog.ui.alldogs.viewmodel.AllDogsViewModel
import com.ramsoft.mydog.ui.common.ViewModelFactory
import com.ramsoft.mydog.utils.Utility
import com.squareup.picasso.Picasso

/**
 * @author Priyesh Bhargava
 */
class DogDetailsActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var dao: DogDao
    private lateinit var ivDog: ImageView
    private lateinit var ivBack: ImageView
    private lateinit var btnAddToFav: AppCompatButton
    private lateinit var btnAddToCollection: AppCompatButton
    private lateinit var btnShare: AppCompatButton
    private lateinit var btnAddAsFavBreed: AppCompatButton
    private lateinit var btnSave: AppCompatButton
    private lateinit var btnDogOfTheMonth: AppCompatButton

    private lateinit var breedName: String
    private lateinit var dogImg: String
    private lateinit var allDogsViewModel: AllDogsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dog_details)
        if (intent != null) {
            breedName = intent.getStringExtra("breed_name").toString()
            dogImg = intent.getStringExtra("dog_url").toString()
        }
        /**
         * Initialization of view
         */

        ivDog = findViewById(R.id.ivDog)
        ivBack = findViewById(R.id.ivBack)
        btnAddToFav = findViewById(R.id.btnAddToFav)
        btnAddToCollection = findViewById(R.id.btnAddToCollection)
        btnShare = findViewById(R.id.btnShare)
        btnAddAsFavBreed = findViewById(R.id.btnAddAsFavBreed)
        btnSave = findViewById(R.id.btnSave)
        btnDogOfTheMonth = findViewById(R.id.btnDogOfTheMonth)

        /**
         * adding the listeners
         */

        ivBack.setOnClickListener(this)
        btnAddToFav.setOnClickListener(this)
        btnAddToCollection.setOnClickListener(this)
        btnShare.setOnClickListener(this)
        btnAddAsFavBreed.setOnClickListener(this)
        btnSave.setOnClickListener(this)
        btnDogOfTheMonth.setOnClickListener(this)

        Picasso.get().load(dogImg).resize(300, 300).placeholder(R.drawable.progress_animation).into(ivDog)
        /**
         * Viewmodel init with ViewModelFactory
         */
        allDogsViewModel =
            ViewModelProvider(
                this,
                ViewModelFactory(this)
            )[AllDogsViewModel::class.java]
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivBack -> {
                finish()
            }

            R.id.btnAddToFav -> {
              allDogsViewModel.sendIntent(AllDogsIntent.AddFavouriteDogImg(dogImg))
            }

            R.id.btnAddToCollection -> {
                    allDogsViewModel.sendIntent(AllDogsIntent.AddDogImgToCollection(dogImg))
            }

            R.id.btnShare -> {
                Utility.shareIntent(this@DogDetailsActivity, dogImg, "Powered by Dog API app")
            }

            R.id.btnAddAsFavBreed -> {
                    allDogsViewModel.sendIntent(AllDogsIntent.AddFavouriteBreedName(breedName))

            }

            R.id.btnSave -> {
                Utility.savePhoto(this@DogDetailsActivity, dogImg)
            }

            R.id.btnDogOfTheMonth -> {

            }
        }
    }
}