package com.example.trainapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.trainapp.R
import com.example.trainapp.model.Legs
import com.example.trainapp.helpers.TimeFormatter
import kotlinx.android.synthetic.main.item_leg.view.*

class LegAdapter(private val legs: List<Legs>) : RecyclerView.Adapter<LegAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_leg, parent, false)
        )
    }

    override fun getItemCount(): Int = legs.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position == legs.size-1){
            holder.itemView.dividerLegs.isVisible = false
        }

        holder.bind(legs[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(leg: Legs) {
            itemView.tvLegStartTime.text = TimeFormatter.dateTimeToTime(leg.origin.plannedDateTime)
            itemView.tvLegArriveTime.text = TimeFormatter.dateTimeToTime(leg.destination.plannedDateTime)
            itemView.tvLegOrigin.text = leg.origin.name
            itemView.tvLegDestination.text = leg.destination.name
            itemView.tvLegOriginTrack.text = context.getString(R.string.spoor, leg.origin.plannedTrack)
            itemView.tvLegDestinationTrack.text = context.getString(R.string.spoor, leg.destination.plannedTrack)

        }
    }
}