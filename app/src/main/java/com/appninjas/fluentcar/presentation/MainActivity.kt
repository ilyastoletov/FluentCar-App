package com.appninjas.fluentcar.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.appninjas.fluentcar.R
import com.appninjas.fluentcar.databinding.ActivityMainBinding
import com.yandex.mapkit.mapview.MapView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}