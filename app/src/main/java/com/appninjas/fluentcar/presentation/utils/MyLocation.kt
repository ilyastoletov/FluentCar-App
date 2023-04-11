package com.appninjas.fluentcar.presentation.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.location.LocationRequest
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

private const val PERMISSION_ID = 0

class MyLocation(private val context: Context,
                 private val activity: Activity) {

    fun getLocation(): Map<String, Double>? {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        if (checkLocationPermission()) {
            return if (isLocationEnabled()) {
                var finalLocation: Map<String, Double>? = null
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        Log.d("MYLOCAT", "Location is null")
                    } else {
                        finalLocation = mapOf("latitude" to location.latitude, "longitude" to location.longitude)
                    }
                }
                finalLocation
            } else {
                Toast.makeText(context, "Включите геолокацию, чтобы получить доступ к своему местоположению на карте", Toast.LENGTH_SHORT).show()
                null
            }
        } else {
            requestLocationPermission()
            return null
        }
    }

    private fun checkLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_ID)
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

}