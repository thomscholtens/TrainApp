package com.example.trainapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trainapp.R
import com.example.trainapp.ui.adapter.RouteAdapter
import com.example.trainapp.model.Route
import com.example.trainapp.viewmodel.RouteViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_routes.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class RoutesFragment : Fragment() {
    private val viewModel: RouteViewModel by activityViewModels()
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var routeAdapter: RouteAdapter
    private var routes: ArrayList<Route> = arrayListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_routes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeRoute()
        initToolBar()
    }

    private fun initToolBar() {
        activity?.title = getString(R.string.routes_title)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun initRecyclerView() {
        routeAdapter = RouteAdapter(routes, ::onRouteClick, ::onSwapClick)
        viewManager = LinearLayoutManager(activity)
        rvRoutes.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = routeAdapter
        }
        createItemTouchHelper().attachToRecyclerView(rvRoutes)
    }

    private fun onSwapClick(route: Route) {
        val toStation = route.toStation
        route.toStation = route.fromStation;
        route.fromStation = toStation
        viewModel.updateRoute(route)
    }

    private fun onRouteClick(route: Route) {
        viewModel.selectRoute(route)
        viewModel.getTripsFromTo(route.fromStation, route.toStation)
        findNavController().navigate(
            R.id.action_myRoutesFragment_to_ridesFragment
        )
    }

    private fun observeRoute() {
        viewModel.routes.observe(viewLifecycleOwner, Observer { routes ->
            this@RoutesFragment.routes.clear()
            this@RoutesFragment.routes.addAll(routes)
            routeAdapter.notifyDataSetChanged()
        })
    }

    private fun createItemTouchHelper(): ItemTouchHelper {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val routeToDelete = routes[position]
                viewModel.deleteRoute(routeToDelete)
                Snackbar.make(
                    rvRoutes,
                    getString(R.string.route_delete_succes),
                    Snackbar.LENGTH_LONG
                ).setAction(getString(R.string.undo)) { viewModel.insertRoute(routeToDelete) }.show()
            }
        }
        return ItemTouchHelper(callback)
    }
}