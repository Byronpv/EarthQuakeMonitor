package com.example.earthquakemonitor.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.example.earthquakemonitor.R
import com.example.earthquakemonitor.databinding.ActivityDetailBinding
import com.example.earthquakemonitor.model.data.Earthquake
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EQ_KEY = "earthquake"
        private const val TIME_FORMAT = "dd/MMM/yyyy HH:mm:ss"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val earthquake = intent?.extras?.getParcelable<Earthquake>(EQ_KEY)!!
        binding.magnitudeText.text = getString(R.string.magnitude_format, earthquake.magnitude)
        binding.longitude.text = earthquake.longitude.toString()
        binding.latitude.text = earthquake.latitude.toString()
        binding.place.text = earthquake.place
        setupTime(binding, earthquake)
    }


    override fun onSupportNavigateUp():Boolean{
        onBackPressed()
        return  true
    }


    private fun setupTime(
        binding: ActivityDetailBinding,
        earthquake: Earthquake
    ) {
        val dateFormat = SimpleDateFormat(TIME_FORMAT, Locale.getDefault())
        val date = Date(earthquake.time)
        binding.date.text = dateFormat.format(date)
    }
}