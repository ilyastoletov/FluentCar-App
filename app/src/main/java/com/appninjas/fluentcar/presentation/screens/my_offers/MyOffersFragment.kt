package com.appninjas.fluentcar.presentation.screens.my_offers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.appninjas.fluentcar.databinding.FragmentMyOffersBinding
import com.appninjas.fluentcar.presentation.adapters.UserOffersAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyOffersFragment : Fragment() {

    private lateinit var binding: FragmentMyOffersBinding

    private val viewModel by viewModel<MyOffersViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMyOffersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        viewModel.getOffers()
        viewModel.myOffers.observe(viewLifecycleOwner) { offersList ->
            val userOffersAdapter = UserOffersAdapter(offersList)
            binding.userOffersRecyclerView.apply {
                adapter = userOffersAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

}