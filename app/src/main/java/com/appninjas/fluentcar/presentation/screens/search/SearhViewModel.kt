package com.appninjas.fluentcar.presentation.screens.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appninjas.domain.model.Offer
import com.appninjas.domain.usecase.RespontOfferUseCase
import com.appninjas.domain.usecase.SearchOfferUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearhViewModel(private val searchOfferUseCase: SearchOfferUseCase, private val respontOfferUseCase: RespontOfferUseCase) : ViewModel() {

    private val _foundOffers: MutableLiveData<List<Offer>> = MutableLiveData()
    val foundOffers: LiveData<List<Offer>> = _foundOffers

    fun searchOffers(query: String, status: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = searchOfferUseCase.invoke(query, status)
            _foundOffers.postValue(result)
        }
    }

    fun respondToOffer(offer: Offer) {
        viewModelScope.launch(Dispatchers.IO) {
            respontOfferUseCase.invoke(offer)
        }
    }

}