package com.ramsoft.mydog.ui.alldogs.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ramsoft.mydog.R
import com.ramsoft.mydog.ui.alldogs.intent.AllDogsIntent
import com.ramsoft.mydog.ui.alldogs.view.viewholder.AllBreedsAdapter
import com.ramsoft.mydog.ui.alldogs.view.viewholder.OnClickListener
import com.ramsoft.mydog.ui.alldogs.viewmodel.AllDogsViewModel
import com.ramsoft.mydog.ui.alldogs.viewstate.AllDogsState
import com.ramsoft.mydog.ui.common.ViewModelFactory
import kotlinx.coroutines.launch
/**
 * @author Priyesh Bhargava
 */
class AllBreedsActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var allDogsViewModel: AllDogsViewModel
    private lateinit var rvAllDogs: RecyclerView
    private lateinit var allBreedsAdapter: AllBreedsAdapter
    private lateinit var ivBack:ImageView
    private lateinit var ivCollection: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_breeds)
        /**
         * View initialization
         */

        ivBack = findViewById(R.id.ivBack)
        rvAllDogs = findViewById(R.id.rvAllDogs)
        ivCollection = findViewById(R.id.ivCollection)

        /**
         * adapter setup
         */
        allBreedsAdapter = AllBreedsAdapter(this@AllBreedsActivity, HashMap())
        rvAllDogs.adapter = allBreedsAdapter
        rvAllDogs.layoutManager = LinearLayoutManager(this)

        /**
         * click listener
         */
        ivBack.setOnClickListener(this)
        ivCollection.setOnClickListener(this)
        allBreedsAdapter.setClickListener(object : OnClickListener {
            override fun onClick(view: View, position: Int) {
                val i = Intent(this@AllBreedsActivity, DogsByBreedsActivity::class.java)
                i.putExtra("breed_name", allBreedsAdapter.allBreeds.keys.toList()[position])
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
            allDogsViewModel.allDogIntent.send(AllDogsIntent.FetchAllBreeds)
        }

        /**
         * Callback from viewModel as per the states received
         */

        lifecycleScope.launch {
            allDogsViewModel.state.collect {

                when (it) {
                    is AllDogsState.FetchAllDogsBreeds -> {
                        allBreedsAdapter.setBreedsList(it.allBreeds)
                    }
                    else -> {}
                }
            }

        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ivBack->{
                finish()
            }
            R.id.ivCollection ->{
                startActivity(Intent(this@AllBreedsActivity,MyDogsCollectionActivity::class.java))
            }
        }

    }

}