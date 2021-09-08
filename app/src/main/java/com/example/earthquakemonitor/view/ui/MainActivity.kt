package com.example.earthquakemonitor.view.ui

import android.animation.ValueAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieAnimationView
import com.example.earthquakemonitor.R
import com.example.earthquakemonitor.model.data.Earthquake
import com.example.earthquakemonitor.view.adapter.EqAdapter
import com.example.earthquakemonitor.viewmodel.MainViewModel
import com.example.earthquakemonitor.databinding.ActivityMainBinding
import com.example.earthquakemonitor.model.network.ApiResponseStatus
import com.example.earthquakemonitor.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity(){
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var handle: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(application)
        ).get(MainViewModel::class.java)

        binding.eqRecycler.layoutManager = LinearLayoutManager(this)

        val adapter = EqAdapter(this)
        binding.eqRecycler.adapter = adapter

        viewModel.eqList.observe(this, Observer { eqListEarthquakes ->
            adapter.setListData(eqListEarthquakes)
            handlerEmptyView(eqListEarthquakes)
        })

        viewModel.status.observe(this, Observer { apiResponseStatus ->




            when (apiResponseStatus) {
                ApiResponseStatus.LOADING -> {

                    binding.progressResponseApi.visibility = View.VISIBLE
                  }
                ApiResponseStatus.DONE -> {
                    binding.progressResponseApi.visibility = View.GONE
                }
                else -> {
                    binding.progressResponseApi.visibility = View.GONE
                }
            }

        })
/*        val eqList: MutableList<Earthquake> = mutableListOf<Earthquake>()

           eqList.add(Earthquake("1","Colombia",4.1,13231321L,28.575,-102.5654))
           eqList.add(Earthquake("2","México",3.3,13231321L,28.575,-102.5654))
           eqList.add(Earthquake("3","Perú",5.3,13231321L,28.575,-102.5654))
           eqList.add(Earthquake("4","Argentina",2.3,13231321L,28.575,-102.5654))
           eqList.add(Earthquake("5","Colombia",4.3,13231321L,28.575,-102.5654))*/

        adapter.onItemClickListener = {
            dataEarthquake(it)
        }
    }

    private fun handlerEmptyView(eqListEarthquakes: MutableList<Earthquake>) {
        if (eqListEarthquakes.isEmpty()) {
            binding.noItemView.visibility = View.VISIBLE
        } else {
            binding.noItemView.visibility = View.GONE
        }
    }

    private fun dataEarthquake(earthquake: Earthquake) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EQ_KEY, earthquake)
        startActivity(intent)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mainMenuSortMagnitude -> {
                Log.d("MainActivity", "Magnitude")
                viewModel.reloadEarthquakesFromDatabase(true)
            }
            R.id.mainMenuSortTime -> {
                viewModel.reloadEarthquakesFromDatabase(false)
            }
        }
        return super.onOptionsItemSelected(item)
    }


}