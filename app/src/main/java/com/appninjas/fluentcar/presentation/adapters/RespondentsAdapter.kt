package com.appninjas.fluentcar.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appninjas.domain.model.UserInfo
import com.appninjas.fluentcar.databinding.RespondentItemBinding

class RespondentsAdapter(private val respondentsList: List<UserInfo>) : RecyclerView.Adapter<RespondentsAdapter.Holder>() {

    inner class Holder(private val binding: RespondentItemBinding, itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(model: UserInfo) {
            with(binding) {
                nameRespondentTextView.text = "Имя: " + model.name
                numberRespondentTextView.text = "Номер телефона: " + model.phone
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = RespondentItemBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding, binding.root)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(respondentsList[position])
    }

    override fun getItemCount(): Int = respondentsList.size

}