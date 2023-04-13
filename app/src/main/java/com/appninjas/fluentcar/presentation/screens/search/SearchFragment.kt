package com.appninjas.fluentcar.presentation.screens.search

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.appninjas.fluentcar.databinding.FragmentSearchBinding
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.appninjas.domain.model.Offer
import com.appninjas.fluentcar.presentation.adapters.ForeignerOfferAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel by viewModel<SearhViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.foundOffers.observe(viewLifecycleOwner) {foundOffers ->
            val newSearchAdapter = ForeignerOfferAdapter(foundOffers, phoneClickListener)
            binding.foreignersOffersRecyclerView.apply {
                adapter = newSearchAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }
        initUI()
    }

    private fun initUI() {
        binding.offersSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("SEARCH", newText ?: "")
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                searchEngine(query)
                return true
            }
        })
    }

    private fun searchEngine(query: String?) {
        if (query == null) return

        if (validateSearch()) {
            viewModel.searchOffers(query, !binding.driverRadioSearch.isChecked)
        }
    }

    private val phoneClickListener = object : ForeignerOfferAdapter.OnPhoneClicked {
        override fun onClick(model: Offer) {
            showDriverData(model.driverName, model.driverPhone)
            viewModel.respondToOffer(model)
        }
    }

    private fun validateSearch(): Boolean {
        return if (binding.offersSearchView.query.isEmpty()) {
            Toast.makeText(context, "Введите поисковой запрос", Toast.LENGTH_SHORT).show()
            false
        } else if (binding.driverRadioSearch.isChecked == false && binding.passengerRadioSearch.isChecked == false) {
            Toast.makeText(context, "Выберите кого искать", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }

    private fun showDriverData(driverName: String, driverPhone: String) {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Данные о водителе")
            .setMessage("Вы успешно откликнулись на заявку, водитель получит уведомление. Вот контактные данные водителя, позвоните ему и договоритесь о поездке:\n\nИмя: $driverName\nТелефон: $driverPhone")
            .setPositiveButton("Хорошо") { dialog, action ->
                when (action) {
                    DialogInterface.BUTTON_POSITIVE -> dialog.cancel()
                }
            }
            .setCancelable(false)
            .create()

        dialog.show()
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.parseColor("#53C7C0"))
    }

}