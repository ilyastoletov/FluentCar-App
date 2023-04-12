package com.appninjas.fluentcar.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.appninjas.fluentcar.R
import com.appninjas.fluentcar.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        initBottomNavView()
    }

    private fun initBottomNavView() {
        val btNav: BottomNavigationView = binding.bottomNavView
        val navControllerFragment = supportFragmentManager.findFragmentById(R.id.nav_controller_fragment) as NavHostFragment
        val navController = navControllerFragment.navController
        val appBarConf = AppBarConfiguration(setOf(R.id.mapFragment, R.id.myOffersFragment, R.id.searchFragment))

        setupActionBarWithNavController(navController, appBarConf)
        btNav.setupWithNavController(navController)
    }

}