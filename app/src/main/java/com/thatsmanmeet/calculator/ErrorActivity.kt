package com.thatsmanmeet.calculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thatsmanmeet.calculator.R
import com.thatsmanmeet.calculator.databinding.ActivityErrorBinding

class ErrorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityErrorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityErrorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set status bar and navigation bar color red
        window.statusBarColor = getColor(R.color.reddish_button)
        window.navigationBarColor = getColor(R.color.reddish_button)

        val error = intent.getStringExtra("error")
        binding.tvError.text = error
    }
}