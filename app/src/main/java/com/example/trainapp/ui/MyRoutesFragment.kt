package com.example.trainapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trainapp.R
import com.example.trainapp.adapter.RouteAdapter
import com.example.trainapp.model.Route
import com.example.trainapp.viewmodel.RouteViewModel
import kotlinx.android.synthetic.main.fragment_my_routes.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MyRoutesFragment : Fragment() {
    private val viewModel: RouteViewModel by activityViewModels()
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var routeAdapter: RouteAdapter

    private var routes: ArrayList<Route> = arrayListOf()


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_routes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeRoute()
    }

    private fun initRecyclerView() {
        routeAdapter = RouteAdapter(routes, ::onRouteClick)
        viewManager = LinearLayoutManager(activity)
        rvRoutes.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = routeAdapter
        }
        createItemTouchHelper().attachToRecyclerView(rvRoutes)

    }

    private fun onRouteClick(route: Route) {
        Toast.makeText(
            activity,
            route.id.toString(), Toast.LENGTH_SHORT
        ).show()
    }

    private fun observeRoute() {
        viewModel.routes.observe(viewLifecycleOwner, Observer { games ->
            this@MyRoutesFragment.routes.clear()
            this@MyRoutesFragment.routes.addAll(games)
            routeAdapter.notifyDataSetChanged()
        })
    }

    private fun createItemTouchHelper(): ItemTouchHelper {
        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val reminderToDelete = routes[position]
                viewModel.deleteRoute(reminderToDelete)

            }
        }
        return ItemTouchHelper(callback)
    }
}