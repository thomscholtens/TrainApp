package com.example.trainapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.trainapp.R
import com.example.trainapp.model.Route
import com.example.trainapp.viewmodel.RouteViewModel
import kotlinx.android.synthetic.main.fragment_route_edit.*
import kotlinx.android.synthetic.main.fragment_trips.*

/**
 * A simple [Fragment] subclass.
 * Use the [RouteEditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RouteEditFragment : Fragment() {
    private val viewModel: RouteViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_route_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeEditRoute()
    }

    private fun observeEditRoute() {
        viewModel.editRoute.observe(viewLifecycleOwner, Observer {
            acFromEdit.setText(it.fromStation)
            acToEdit.setText(it.toStation)
        })
    }


}