package com.example.trainapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.trainapp.R
import com.example.trainapp.model.Trip
import com.example.trainapp.util.TimeHelper
import kotlinx.android.synthetic.main.item_trip.view.*

class TripAdapter(private val trips: List<Trip>, private val onClick: (Trip) -> Unit)
    : RecyclerView.Adapter<TripAdapter.ViewHolder>()  {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripAdapter.ViewHolder {
        context = parent.context
        return this.ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_trip, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return trips.size
    }

    override fun onBindViewHolder(holder: TripAdapter.ViewHolder, position: Int) = holder.bind(trips[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener { onClick(trips[adapterPosition]) }
        }
        fun bind(trip: Trip) {
            val plannedDeparture = TimeHelper.dateTimeToTime(trip.legs.first().origin.plannedDateTime)
            val actualDeparture = TimeHelper.dateTimeToTime(trip.legs.first().origin.actualDateTime)
            val plannedArrival = TimeHelper.dateTimeToTime(trip.legs.last().destination.plannedDateTime)
            val actualArrival = TimeHelper.dateTimeToTime(trip.legs.last().destination.actualDateTime)
            val departureDelay = TimeHelper.calculateDelay(plannedDeparture, actualDeparture)
            val arrivalDelay = TimeHelper.calculateDelay(plannedArrival, actualArrival)

            if (departureDelay != null && departureDelay > 0) {
                itemView.tvDepartureTime.text = TimeHelper.dateTimeToTime(trip.legs.first().origin.plannedDateTime) + " +" + departureDelay.toString()
                itemView.tvDepartureTime.setTextColor(ContextCompat.getColor(context, R.color.danger))
            } else {
                itemView.tvDepartureTime.text = TimeHelper.dateTimeToTime(trip.legs.first().origin.plannedDateTime)
            }

            if (arrivalDelay != null && arrivalDelay > 0) {
                itemView.tvArrivalTime.text = TimeHelper.dateTimeToTime(trip.legs.last().destination.plannedDateTime) + " +" + arrivalDelay.toString()
                itemView.tvArrivalTime.setTextColor(ContextCompat.getColor(context, R.color.danger))
            } else {
                itemView.tvArrivalTime.text = TimeHelper.dateTimeToTime(trip.legs.last().destination.plannedDateTime)
            }

            if (trip.status == "CANCELLED") {
                Glide.with(context).load(R.drawable.ic_baseline_cancel).into(itemView.ivStatus)
                itemView.tvTrack.text = "Cancelled"
                itemView.tvTrack.setTextColor(ContextCompat.getColor(context, R.color.danger))
            } else {
                Glide.with(context).load(R.drawable.ic_baseline_green_check).into(itemView.ivStatus)
                itemView.tvTrack.text = context.getString(R.string.spoor, trip.legs.first().origin.plannedTrack)

            }
        }
    }
}