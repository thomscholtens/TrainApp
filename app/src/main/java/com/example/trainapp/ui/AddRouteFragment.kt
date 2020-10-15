package com.example.trainapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.trainapp.R
import com.example.trainapp.model.Route
import com.example.trainapp.viewmodel.RouteViewModel
import kotlinx.android.synthetic.main.fragment_add_route.*


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddRouteFragment : Fragment() {

    private val viewModel: RouteViewModel by activityViewModels()
    private var stations: Array<String> = arrayOf<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_route, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFab()
        observeStations()
    }

    private fun initFab() {
        btnAddRoute.setOnClickListener {
            onAddRoute()
        }
    }

    private fun onAddRoute() {
        val fromStation = acFrom.text.toString()
        val toStation = acTo.text.toString()
        if(fromStation.isNotBlank() && toStation.isNotBlank() && fromStation != toStation) {
            viewModel.insertRoute(Route(fromStation, toStation))
            findNavController().popBackStack()
        } else {
            Toast.makeText(activity,"Please fill in a valid start and end station"
                , Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeStations() {
        viewModel.stations.observe(viewLifecycleOwner, Observer {
            stations = it.map { it.namen.lang }.toTypedArray()
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line, stations
            )
            acFrom.setAdapter(adapter)
            acTo.setAdapter(adapter)
        })
    }
}