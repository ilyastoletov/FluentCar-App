package com.appninjas.fluentcar.presentation

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.appninjas.fluentcar.R
import com.appninjas.fluentcar.databinding.ActivityMainBinding
import com.appninjas.fluentcar.presentation.activities.AuthenticationActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkLogin()
        checkLocationEnabled()
        setSupportActionBar(binding.toolbar)
        initBottomNavView()
        subscribeToUserTopic()
    }

    private fun initBottomNavView() {
        val btNav: BottomNavigationView = binding.bottomNavView
        val navControllerFragment = supportFragmentManager.findFragmentById(R.id.nav_controller_fragment) as NavHostFragment
        val navController = navControllerFragment.navController
        val appBarConf = AppBarConfiguration(setOf(R.id.mapFragment, R.id.myOffersFragment, R.id.searchFragment))

        setupActionBarWithNavController(navController, appBarConf)
        btNav.setupWithNavController(navController)
    }

    private fun checkLogin() {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.currentUser ?: run {
            val intent = Intent(this@MainActivity, AuthenticationActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun subscribeToUserTopic() {
        val firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth.currentUser?.email != null) {
            FirebaseMessaging.getInstance().subscribeToTopic(firebaseAuth.currentUser!!.email!!.replace("@", "").replace(".", ""))
        }
    }

    private fun checkLocationEnabled() {
        val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this@MainActivity, "Включите геолокацию для корректной работы приложения", Toast.LENGTH_SHORT).show()
        }
    }

}