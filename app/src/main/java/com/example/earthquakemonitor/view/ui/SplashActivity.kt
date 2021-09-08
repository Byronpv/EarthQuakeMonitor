package com.example.earthquakemonitor.view.ui

import android.animation.ValueAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.earthquakemonitor.R
import com.example.earthquakemonitor.databinding.ActivityDetailBinding
import com.example.earthquakemonitor.databinding.ActivitySplashBinding
import com.example.earthquakemonitor.databinding.ActivitySplashBindingImpl
import kotlinx.coroutines.handleCoroutineException

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var handle: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        binding.earthAnimation.setAnimation(R.raw.earth_animation)
        binding.earthAnimation.playAnimation()



        handle = Handler(Looper.myLooper()!!)

        handle.postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }, 5000)


    }
}