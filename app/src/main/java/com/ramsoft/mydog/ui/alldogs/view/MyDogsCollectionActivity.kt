package com.ramsoft.mydog.ui.alldogs.view

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ramsoft.mydog.R
import com.ramsoft.mydog.ui.alldogs.intent.AllDogsIntent
import com.ramsoft.mydog.ui.alldogs.view.viewholder.DogsByBreedAdapter
import com.ramsoft.mydog.ui.alldogs.viewmodel.AllDogsViewModel
import com.ramsoft.mydog.ui.alldogs.viewstate.AllDogsState
import com.ramsoft.mydog.ui.common.ViewModelFactory
import kotlinx.coroutines.launch

/**
 * @author Priyesh Bhargava
 */
class MyDogsCollectionActivity:AppCompatActivity(), View.OnClickListener {
    private lateinit var breedName:String
    private lateinit var tvBreed:TextView
    private lateinit var allDogsViewModel: AllDogsViewModel
    private lateinit var rvAllDogs: RecyclerView
    private lateinit var dogsByBreedAdapter: DogsByBreedAdapter
    private lateinit var ivBack: ImageView
    private lateinit var ivImgCount: ImageView
    private lateinit var tvNoData:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dogs_by_breeds)
        ivBack = findViewById(R.id.ivBack)
        tvBreed = findViewById(R.id.tvBreed)
        tvBreed.text = "My Dog Collection"
        rvAllDogs = findViewById(R.id.rvAllDogs)
        ivImgCount = findViewById(R.id.ivImgCount)
        tvNoData = findViewById(R.id.tvNoData)
        dogsByBreedAdapter = DogsByBreedAdapter(this@MyDogsCollectionActivity, listOf())
        rvAllDogs.adapter = dogsByBreedAdapter
        rvAllDogs.layoutManager = GridLayoutManager(this,3)
        ivBack.setOnClickListener(this)
        ivImgCount.visibility = GONE

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
            allDogsViewModel.allDogIntent.send(AllDogsIntent.FetchDogsCollection)
        }

        /**
         * Callback from viewModel as per the states received
         */

        lifecycleScope.launch {
            allDogsViewModel.state.collect {

                when (it) {

                    is AllDogsState.DogsCollection -> {
                      val imgList =  it.allDogByBreed.map { data->
                          data.url
                        }
                        println("imgList>>"+imgList)
                        dogsByBreedAdapter.setDogsImgList(imgList)
                        if(imgList.isEmpty()){
                            tvNoData.visibility = VISIBLE
                            rvAllDogs.visibility = GONE
                        }
                        else {
                            tvNoData.visibility = GONE
                            rvAllDogs.visibility = VISIBLE
                        }
                    }
                    else -> {

                    }
                }
            }

        }

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ivBack->{
                finish()
            }
        }

    }
}