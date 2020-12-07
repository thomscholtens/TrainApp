package com.example.trainapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trainapp.R
import com.example.trainapp.ui.adapter.LegAdapter
import com.example.trainapp.model.Legs
import com.example.trainapp.util.TimeHelper
import com.example.trainapp.viewmodel.RouteViewModel
import kotlinx.android.synthetic.main.fragment_trip_info.*


/**
 * A simple [Fragment] subclass.
 * Use the [TripInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TripInfoFragment : Fragment() {
    private val viewModel: RouteViewModel by activityViewModels()
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var legAdapter: LegAdapter
    private var legs: ArrayList<Legs> = arrayListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trip_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeLegs()
        initView()
        initToolBar()
    }

    private fun initView() {
        tvTripTransfers.text = viewModel.selectedTrip.value?.transfers.toString()
        tvTripFromTo.text = getString(R.string.trips_header, viewModel.selectedRoute.value!!.fromStation, viewModel.selectedRoute.value?.toStation)
        tvTripStartTime.text = TimeHelper.dateTimeToTime(viewModel.selectedTrip.value!!.legs.first().origin.plannedDateTime)
        tvTripArrivalTime.text = TimeHelper.dateTimeToTime(viewModel.selectedTrip.value!!.legs.last().destination.plannedDateTime)

        val plannedDuration = viewModel.selectedTrip.value!!.plannedDurationInMinutes
        val actualDuration = viewModel.selectedTrip.value!!.actualDurationInMinutes
        val delay = actualDuration - plannedDuration
        if (delay > 0 ) {
            tvTripDuration.text = getString(R.string.minute_duration, viewModel.selectedTrip.value?.plannedDurationInMinutes.toString() + " +" + delay.toString())
            tvTripDuration.setTextColor(resources.getColor(R.color.danger))

        }  else {
            tvTripDuration.text = getString(R.string.minute_duration, viewModel.selectedTrip.value?.actualDurationInMinutes.toString())
        }
    }

    private fun initRecyclerView() {
        legAdapter = LegAdapter(legs)
        viewManager = LinearLayoutManager(activity)
        rvLegs.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = legAdapter
        }
    }

    private fun initToolBar() {
        activity?.title = getString(R.string.trip_info_title)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun observeLegs() {
        viewModel.selectedTrip.observe(viewLifecycleOwner, Observer { trip ->
            this@TripInfoFragment.legs.clear()
            this@TripInfoFragment.legs.addAll(trip.legs)
            legAdapter.notifyDataSetChanged()
        })
    }
}