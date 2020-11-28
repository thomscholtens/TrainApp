package com.example.trainapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.trainapp.R
import com.example.trainapp.model.Trip
import com.example.trainapp.helpers.TimeFormatter
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
            itemView.tvDepartureTime.text = TimeFormatter.dateTimeToTime(trip.legs.first().origin.plannedDateTime)
            itemView.tvArrivalTime.text = TimeFormatter.dateTimeToTime(trip.legs.last().destination.plannedDateTime)
            itemView.tvTrack.text = context.getString(R.string.spoor, trip.legs.first().origin.plannedTrack)
            Glide.with(context).load(R.drawable.ic_baseline_check_circle_outline).into(itemView.ivStatus)
        }
    }
}