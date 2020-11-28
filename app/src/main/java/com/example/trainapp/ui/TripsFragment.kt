package com.example.trainapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trainapp.R
import com.example.trainapp.adapter.TripAdapter
import com.example.trainapp.model.Trip
import com.example.trainapp.viewmodel.RouteViewModel
import kotlinx.android.synthetic.main.fragment_trips.*



/**
 * A simple [Fragment] subclass.
 * Use the [TripsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TripsFragment : Fragment() {

    private val viewModel: RouteViewModel by activityViewModels()
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var tripAdapter: TripAdapter
    private var trips: ArrayList<Trip> = arrayListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trips, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loading.observe(viewLifecycleOwner, Observer {
            pbLoading.isVisible = it
            rvTrips.isVisible = !it
        })
        btnLoadMore.setOnClickListener {
            loadMore()
        }
        initRecyclerView()
        observeTrip()
        intView()

    }

    private fun intView() {
        tvRide.text = getString(R.string.trips_header, viewModel.selectedRoute.value?.fromStation,
            viewModel.selectedRoute.value?.toStation)
    }

    private fun loadMore() {
        viewModel.loadMore(viewModel.selectedRoute.value!!.fromStation, viewModel.selectedRoute.value!!.toStation, viewModel.scrollContext.value!!)
    }

    private fun initRecyclerView() {
        tripAdapter = TripAdapter(trips, ::onTripClick)
        viewManager = LinearLayoutManager(activity)
        rvTrips.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = tripAdapter
        }
    }

    private fun observeTrip() {
        viewModel.trips.observe(viewLifecycleOwner, Observer { trips ->
            this@TripsFragment.trips.clear()
            this@TripsFragment.trips.addAll(trips)
            tripAdapter.notifyDataSetChanged()
        })
    }

    private fun onTripClick(trip: Trip) {
        viewModel.selectTrip(trip)
        findNavController().navigate(
            R.id.action_ridesFragment_to_tripInfoFragment
        )
    }

}