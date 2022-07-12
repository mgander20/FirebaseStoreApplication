package com.madelyngander.firebasestoreapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.madelyngander.firebasestoreapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding : ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }
}
