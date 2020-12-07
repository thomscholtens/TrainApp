package com.example.trainapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.trainapp.R
import com.example.trainapp.model.Legs
import com.example.trainapp.util.TimeHelper
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
            val originPlannedTime = TimeHelper.dateTimeToTime(leg.origin.plannedDateTime)
            val originActualTime = TimeHelper.dateTimeToTime(leg.origin.actualDateTime)
            val destinationPlannedTime = TimeHelper.dateTimeToTime(leg.destination.plannedDateTime)
            val destinationActualTime = TimeHelper.dateTimeToTime(leg.destination.plannedDateTime)
            val originDelay = TimeHelper.calculateDelay(originPlannedTime, originActualTime)
            val destinationDelay = TimeHelper.calculateDelay(destinationPlannedTime, destinationActualTime)

            if (originDelay != null && originDelay > 0) {
                itemView.tvLegStartTime.text = TimeHelper.dateTimeToTime(leg.origin.plannedDateTime) + " +" + originDelay.toString()
                itemView.tvLegStartTime.setTextColor(ContextCompat.getColor(context, R.color.danger))

            } else {
                itemView.tvLegStartTime.text = TimeHelper.dateTimeToTime(leg.origin.plannedDateTime)
            }

            if (destinationDelay != null && destinationDelay > 0) {
                itemView.tvLegArriveTime.text = TimeHelper.dateTimeToTime(leg.destination.plannedDateTime) + " +" + destinationDelay.toString()
                itemView.tvLegArriveTime.setTextColor(ContextCompat.getColor(context, R.color.danger))
            } else {
                itemView.tvLegArriveTime.text = TimeHelper.dateTimeToTime(leg.destination.plannedDateTime)
            }

            itemView.tvLegOrigin.text = leg.origin.name
            itemView.tvLegDestination.text = leg.destination.name
            itemView.tvLegOriginTrack.text = context.getString(R.string.spoor, leg.origin.plannedTrack)
            itemView.tvLegDestinationTrack.text = context.getString(R.string.spoor, leg.destination.plannedTrack)

        }
    }
}