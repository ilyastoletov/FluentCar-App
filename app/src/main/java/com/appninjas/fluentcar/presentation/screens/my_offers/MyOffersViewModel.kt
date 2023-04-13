package com.appninjas.fluentcar.presentation.screens.my_offers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appninjas.domain.model.Offer
import com.appninjas.domain.usecase.GetMyOffersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyOffersViewModel(private val myOffersUseCase: GetMyOffersUseCase) : ViewModel() {

    private val _myOffers: MutableLiveData<List<Offer>> = MutableLiveData()
    val myOffers: LiveData<List<Offer>> = _myOffers

    fun getOffers() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = myOffersUseCase.invoke()
            _myOffers.postValue(result)
        }
    }

}