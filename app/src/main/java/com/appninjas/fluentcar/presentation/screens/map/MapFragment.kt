package com.appninjas.fluentcar.presentation.screens.map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.PointF
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.appninjas.domain.model.Offer
import com.appninjas.fluentcar.R
import com.appninjas.fluentcar.databinding.FragmentMapBinding
import com.appninjas.fluentcar.presentation.utils.MapValidator
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
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Queue


class MapFragment : Fragment() {

    private lateinit var binding: FragmentMapBinding
    private lateinit var mapView: MapView
    private lateinit var locationMapKit: UserLocationLayer

    private var selectedPointer: PlacemarkMapObject? = null
    private var userData: Offer? = null

    private lateinit var firstPointer: PlacemarkMapObject
    private lateinit var secondPointer: PlacemarkMapObject

    private val viewModel by viewModel<MapViewModel>()

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

        viewModel.coordinates.observe(viewLifecycleOwner) { coords ->
            changePlacemarkPosition(Point(coords["latitude"]!!, coords["longitude"]!!))
        }

        viewModel.address.observe(viewLifecycleOwner) { address ->
            if (selectedPointer == firstPointer) {
                binding.firstAddressLineEditText.setText(address, TextView.BufferType.EDITABLE)
            } else {
                binding.secondAddressLineEditText.setText(address, TextView.BufferType.EDITABLE)
            }
        }

        createPointers()
        initUI()
    }

    private fun initializeMaps() {
        val mapKit: MapKit = MapKitFactory.getInstance()
        locationMapKit = mapKit.createUserLocationLayer(mapView.mapWindow)
        locationMapKit.apply {
            isVisible = true
            setObjectListener(userLocationObjectListener)
        }
        mapView.map.addInputListener(mapInputListener)
    }

    private fun createPointers() {
        val pointerView = View(requireContext()).apply {
            background = requireActivity().getDrawable(R.drawable.baseline_location_on_24)
        }
        firstPointer = mapView.map.mapObjects.addPlacemark(Point(0.0, 0.0), ViewProvider(pointerView))
        firstPointer.isVisible = false
        secondPointer = mapView.map.mapObjects.addPlacemark(Point(0.0, 0.0), ViewProvider(pointerView))
        secondPointer.isVisible = false
        selectedPointer = firstPointer
    }

    private fun initUI() {
        binding.firstAddressLineEditText.setOnKeyListener(onKeyListener)
        binding.secondAddressLineEditText.setOnKeyListener(onKeyListener)
        binding.nextButton.setOnClickListener(proceedOfferClickListener)
        binding.offerFormLayout.doneOfferButton.setOnClickListener(offerDoneClickListener)
    }

    private val onKeyListener = View.OnKeyListener { view, keyCode, event ->
        if (event.action == KeyEvent.ACTION_DOWN || keyCode == KeyEvent.KEYCODE_ENTER) {
            val editText: EditText = if (view.id == binding.firstAddressLineEditText.id) {
                binding.firstAddressLineEditText
            } else {
                binding.secondAddressLineEditText
            }
            viewModel.convertAddressToCoordinates(editText.text.toString())
        }
        true
    }

    private val proceedOfferClickListener = View.OnClickListener { view ->
        val validator = MapValidator(binding, requireContext())
        if (validator.validateAddressFields()) {
            if (validator.validateStatusRadioButtons()) {
                userData = Offer(
                    route = "${binding.firstAddressLineEditText.text} - ${binding.secondAddressLineEditText.text}",
                    status = binding.driverRadio.isChecked,
                    maxPassengers = 0,
                    price = 0,
                )
                binding.addressForm.visibility = View.GONE
                binding.offerFormLayout.root.visibility = View.VISIBLE
            }
        }
    }

    private val offerDoneClickListener = View.OnClickListener {view ->
        val validator = MapValidator(binding, requireContext())
        if (validator.validatePriceField()) {
            if (validator.validatePassengersCount()) {
                Toast.makeText(requireContext(), "Форма заполнена, ура!!", Toast.LENGTH_SHORT).show()
                userData!!.apply {
                    maxPassengers = binding.offerFormLayout.humanInCarLimitPassenger.text.toString().toInt()
                    price = binding.offerFormLayout.costOfTripEditTextPassenger.text.toString().toInt()
                }
                Log.d("OFFER", userData.toString())
            }
        }
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
            viewModel.convertCoordinatesToAddress(point.latitude, point.longitude)
            changePlacemarkPosition(point)
        }

        override fun onMapLongTap(map: Map, point: Point) {
            Log.d("FUCK", "I'm no need to implement this")
        }
    }

    private fun changePlacemarkPosition(point: Point) {
        if (selectedPointer == firstPointer) {
            Log.d("MAP", "selected pointer: first")
            firstPointer.apply {
                geometry = point
                isVisible = true
            }
            selectedPointer = secondPointer
        } else if (selectedPointer == secondPointer) {
            Log.d("MAP", "selected pointer: second")
            secondPointer.apply {
                geometry = point
                isVisible = true
            }
            selectedPointer = firstPointer
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
        selectedPointer = null
        mapView.map.removeInputListener(mapInputListener)
        super.onDestroy()
    }

}