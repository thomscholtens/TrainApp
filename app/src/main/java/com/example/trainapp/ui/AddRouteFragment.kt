package com.example.trainapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.trainapp.R
import com.example.trainapp.adapter.RouteAdapter
import com.example.trainapp.model.Route
import com.example.trainapp.viewmodel.RouteViewModel
import kotlinx.android.synthetic.main.fragment_add_route.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddRouteFragment : Fragment() {

    private val viewModel: RouteViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_route, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnAddRoute.setOnClickListener {
            onAddRoute()
        }
    }

    private fun onAddRoute() {
        val fromStation = etFrom.text.toString()
        val toStation = etTo.text.toString()
        viewModel.insertRoute(Route(fromStation, toStation))
        findNavController().popBackStack()

    }
}