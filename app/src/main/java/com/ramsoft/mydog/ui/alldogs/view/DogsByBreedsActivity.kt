package com.ramsoft.mydog.ui.alldogs.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ramsoft.mydog.R
import com.ramsoft.mydog.ui.alldogs.intent.AllDogsIntent
import com.ramsoft.mydog.ui.alldogs.view.viewholder.DogsByBreedAdapter
import com.ramsoft.mydog.ui.alldogs.view.viewholder.OnClickListener
import com.ramsoft.mydog.ui.alldogs.viewmodel.AllDogsViewModel
import com.ramsoft.mydog.ui.alldogs.viewstate.AllDogsState
import com.ramsoft.mydog.ui.common.ViewModelFactory
import kotlinx.coroutines.launch

/**
 * @author Priyesh Bhargava
 */
class DogsByBreedsActivity:AppCompatActivity(), View.OnClickListener {
    private lateinit var breedName:String
    private lateinit var tvBreed:TextView
    private lateinit var allDogsViewModel: AllDogsViewModel
    private lateinit var rvAllDogs: RecyclerView
    private lateinit var dogsByBreedAdapter: DogsByBreedAdapter
    private lateinit var ivBack: ImageView
    private lateinit var ivImgCount: ImageView
    var dialog: AlertDialog? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dogs_by_breeds)
        ivBack = findViewById(R.id.ivBack)
        tvBreed = findViewById(R.id.tvBreed)
        rvAllDogs = findViewById(R.id.rvAllDogs)
        ivImgCount = findViewById(R.id.ivImgCount)
        dogsByBreedAdapter = DogsByBreedAdapter(this@DogsByBreedsActivity, listOf())
        rvAllDogs.adapter = dogsByBreedAdapter
        rvAllDogs.layoutManager = GridLayoutManager(this,3)
        ivBack.setOnClickListener(this)
        ivImgCount.setOnClickListener(this)
        if(intent.hasExtra("breed_name")){
            breedName = intent.getStringExtra("breed_name").toString()
            tvBreed.text = breedName
        }
        dogsByBreedAdapter.setClickListener(object : OnClickListener {
            override fun onClick(view: View, position: Int) {
                //Main detail
                val i = Intent(this@DogsByBreedsActivity,DogDetailsActivity::class.java)
                i.putExtra("breed_name",breedName)
                i.putExtra("dog_url",dogsByBreedAdapter.imgList[position])
                startActivity(i)
            }
        })


        /**
         * Viewmodel init with ViewModelFactory
         */
        allDogsViewModel =
            ViewModelProvider(
                this,
                ViewModelFactory(this)
            )[AllDogsViewModel::class.java]

        /**
         * Launching the default intent to fetch Dog image
         */
        lifecycleScope.launch {
            allDogsViewModel.allDogIntent.send(AllDogsIntent.FetchAllDogByBreed(breedName))
        }

        /**
         * Callback from viewModel as per the states received
         */

        lifecycleScope.launch {
            allDogsViewModel.state.collect {

                when (it) {
                    is AllDogsState.DogsByBreed -> {
                        println("it.allDogByBreed>>"+it.allDogByBreed.toList())
                        dogsByBreedAdapter.setDogsImgList(it.allDogByBreed.toList())
                    }

                    is AllDogsState.GetImageCount -> {
                        allDogsViewModel.allDogIntent.send(AllDogsIntent.FetchDogByBreed(breedName,it.count))
                    }

                    else -> {}
                }
            }

        }
        /**
         * Dailog to select number of images
         */

        // setup the alert builder
        var builder: AlertDialog.Builder? = AlertDialog.Builder(this)
        builder?.setTitle("Choose an image count")


// add a list
        val imgCount = arrayOf("10", "20", "30", "40", "50"," Show all images")
        builder?.setItems(imgCount
        ) { dialog, which ->
           val count =  imgCount[which]
            lifecycleScope.launch {
                if(imgCount.size-1 == which){
                    allDogsViewModel.allDogIntent.send(AllDogsIntent.FetchAllDogByBreed(breedName))
                }
                else{
                    allDogsViewModel.allDogIntent.send(AllDogsIntent.SetImageCount(count))
                }

            }
        }


// create and show the alert dialog
        dialog = builder?.create()

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ivBack->{
                finish()
            }
            R.id.ivImgCount->{
                dialog?.show()
            }
        }

    }
}