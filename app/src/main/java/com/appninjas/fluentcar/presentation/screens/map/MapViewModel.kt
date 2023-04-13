package com.appninjas.fluentcar.presentation.screens.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appninjas.domain.model.Offer
import com.appninjas.domain.usecase.CreateOfferUseCase
import com.appninjas.domain.usecase.GeocodeCoordinatesToAdressUseCase
import com.appninjas.domain.usecase.ReverseGeocodeUseCase
import com.appninjas.fluentcar.presentation.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapViewModel(private val geocodeCoordinatesToAdressUseCase: GeocodeCoordinatesToAdressUseCase,
                   private val reverseGeocodeUseCase: ReverseGeocodeUseCase,
                   private val createOfferUseCase: CreateOfferUseCase) : ViewModel() {

    private val _address: MutableLiveData<String> = MutableLiveData()
    val address: LiveData<String> = _address

    private val _coordinates: MutableLiveData<Map<String, Double>> = MutableLiveData()
    val coordinates: LiveData<Map<String, Double>> = _coordinates

    fun convertAddressToCoordinates(address: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = reverseGeocodeUseCase.invoke(address)
            _coordinates.postValue(mapOf(
                "latitude" to result.latitude,
                "longitude" to result.longitude
            ))
        }
    }

    fun convertCoordinatesToAddress(lat: Double, lon: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = geocodeCoordinatesToAdressUseCase.invoke(lat, lon)
            _address.postValue(result.address)
        }
    }

    fun createOffer(offer: Offer) {
        viewModelScope.launch(Dispatchers.IO) {
            createOfferUseCase.invoke(offer)
        }
    }

}