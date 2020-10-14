package com.example.trainapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.trainapp.R
import com.example.trainapp.model.Route
import kotlinx.android.synthetic.main.item_route.view.*

class RouteAdapter(private val routes: List<Route>, private val onClick: (Route) -> Unit)
    : RecyclerView.Adapter<RouteAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_route, parent, false)
        )
    }

    override fun getItemCount(): Int = routes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(routes[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener { onClick(routes[adapterPosition]) }
        }
        fun bind(route: Route) {
            itemView.tvFrom.text = route.fromStation
            itemView.tvTo.text = route.toStation
        }
    }

}