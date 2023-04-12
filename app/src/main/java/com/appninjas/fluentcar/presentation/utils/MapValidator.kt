package com.appninjas.fluentcar.presentation.utils

import android.content.Context
import android.widget.Toast
import com.appninjas.fluentcar.databinding.FragmentMapBinding

class MapValidator(private val binding: FragmentMapBinding, private val context: Context) {

    fun validateAddressFields(): Boolean {
        return if (binding.firstAddressLineEditText.text.isEmpty() || binding.secondAddressLineEditText.text.isEmpty()) {
            Toast.makeText(context, "Заполните поля адреса или выберете пункт назаначения", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }

    fun validateStatusRadioButtons(): Boolean {
        return if (!binding.passengerRadio.isChecked && !binding.driverRadio.isChecked) {
            Toast.makeText(context, "Выберите ваш статус: водитель или пассажир", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }

    fun validatePriceField(): Boolean {
        return if (binding.offerFormLayout.costOfTripEditTextPassenger.text.isEmpty()) {
            Toast.makeText(context, "Заполните поле цены поездки", Toast.LENGTH_SHORT).show()
            false
        } else if (binding.offerFormLayout.costOfTripEditTextPassenger.text.toString().toInt() >= 20000) {
            Toast.makeText(context, "Цена не должна превышать 20000 рублей", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }

    fun validatePassengersCount(): Boolean {
        val passengersCount = binding.offerFormLayout.humanInCarLimitPassenger.text.toString().toInt()
        return if (passengersCount > 4 || passengersCount < 1) {
            Toast.makeText(context, "Кол-во пассажиров не может быть больше четырех или меньше одного", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }

}