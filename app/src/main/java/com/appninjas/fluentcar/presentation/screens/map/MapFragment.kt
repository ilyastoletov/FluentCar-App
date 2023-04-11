package com.appninjas.fluentcar.presentation.screens.map

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.PointF
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.appninjas.fluentcar.R
import com.appninjas.fluentcar.databinding.FragmentMapBinding
import com.appninjas.fluentcar.presentation.utils.MyLocation
import com.google.android.gms.location.LocationResult
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.map.*
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.image.ImageProvider
import com.yandex.runtime.ui_view.ViewProvider
import java.util.Locale


class MapFragment : Fragment() {

    private lateinit var binding: FragmentMapBinding
    private lateinit var mapView: MapView
    private lateinit var locationMapKit: UserLocationLayer

    private val pointersCount = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        mapView = binding.yandexMapView
        MapKitFactory.initialize(context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestLocationPermission()
        initializeMaps()
    }

    private fun initializeMaps() {
        val mapKit: MapKit = MapKitFactory.getInstance()
        locationMapKit = mapKit.createUserLocationLayer(mapView.mapWindow)
        locationMapKit.apply {
            isVisible = true
            setObjectListener(userLocationObjectListener)
        }
        /*if (locationMapKit.cameraPosition() != null) {
            mapView.map.move(
                locationMapKit.cameraPosition()!!,
                Animation(Animation.Type.SMOOTH, 0.0f),
                null
            )
        }*/
        mapView.map.addInputListener(mapInputListener)
    }

    private val userLocationObjectListener = object : UserLocationObjectListener {
        override fun onObjectAdded(userLocationView: UserLocationView) {
            locationMapKit.setAnchor(
                PointF((mapView.width() * 0.5).toFloat(), (mapView.height() * 0.5).toFloat()),
                PointF((mapView.width() * 0.83).toFloat(), (mapView.height() * 0.83).toFloat())
            )
            userLocationView.arrow.setIcon(ImageProvider.fromResource(context, R.drawable.baseline_my_location_24))
            val pinIcon = userLocationView.pin.useCompositeIcon()
            pinIcon.setIcon("icon", ImageProvider.fromResource(context, R.drawable.nothing), IconStyle()
                .setAnchor(PointF(0f, 0f))
                .setRotationType(RotationType.ROTATE)
                .setZIndex(0f)
                .setScale(1f)
            )
            pinIcon.setIcon("pin", ImageProvider.fromResource(context, R.drawable.baseline_my_location_24), IconStyle()
                .setAnchor(PointF(0.5f, 0.5f))
                .setRotationType(RotationType.ROTATE)
                .setZIndex(1f)
                .setScale(0.5f)
            )
            userLocationView.accuracyCircle.fillColor = Color.BLUE and -0x66000001
        }

        override fun onObjectRemoved(p0: UserLocationView) {
            Log.d("WHY", "Why i need to implement this")
        }

        override fun onObjectUpdated(p0: UserLocationView, p1: ObjectEvent) {
            Log.d("WHY", "Why i need to implement this")
        }

    }

    private fun requestLocationPermission() {
        if(ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 0)
            return
        }
    }

    private val mapInputListener = object : InputListener {
        override fun onMapTap(map: Map, point: Point) {
            val pointerView = View(requireContext()).apply {
                background = requireActivity().getDrawable(R.drawable.baseline_location_on_24)
            }
            val geocoder = Geocoder(requireContext(), Locale.ENGLISH)
            val marker = mapView.map.mapObjects.addPlacemark(point, ViewProvider(pointerView))
            marker.isVisible = true
            marker.userData = "${point.latitude} : ${point.longitude}"
        }

        override fun onMapLongTap(map: Map, point: Point) {
            Log.d("FUCK", "I'm no need to implement this")
        }
    }

    override fun onStart() {
        mapView.onStart()
        MapKitFactory.getInstance().onStart()
        super.onStart()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onDestroy() {
        mapView.map.removeInputListener(mapInputListener)
        super.onDestroy()
    }

}