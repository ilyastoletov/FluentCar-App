package com.appninjas.fluentcar.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appninjas.domain.model.Offer
import com.appninjas.fluentcar.databinding.ForeignerOfferItemBinding

class ForeignerOfferAdapter(private var offersList: List<Offer>, private val phoneClickListener: OnPhoneClicked) : RecyclerView.Adapter<ForeignerOfferAdapter.Holder>() {

    inner class Holder(private val binding: ForeignerOfferItemBinding, itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(model: Offer) {
            with(binding) {
                routeHeaderTextView.text = model.route
                routeHeaderTextView.textSize = getFontSize(model.route.length)
                priceTextView.text = "Цена: " + model.price + "₽"
                maxPassengersTextView.text = "Макс. пассажиров: " + model.maxPassengers
                statusTextView.text = "Статус: " + if (model.status) "Водитель" else "Пассажир"
                responsesTextView.text = "Откликов: " + model.responseCount.toString()
            }
            binding.showDataButton.setOnClickListener {
                phoneClickListener.onClick(model)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ForeignerOfferItemBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding, binding.root)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(offersList[position])
    }

    override fun getItemCount(): Int = offersList.size

    private fun getFontSize(length: Int): Float {
        return when (length) {
            in 0..50 -> 14.0f
            in 50..100 -> 13.5f
            in 100..150 -> 12.5f
            in 150..200 -> 12.0f
            else -> 11.5f
        }
    }

    interface OnPhoneClicked {
        fun onClick(model: Offer)
    }

}