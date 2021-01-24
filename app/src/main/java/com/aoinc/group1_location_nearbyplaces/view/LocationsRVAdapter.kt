package com.aoinc.group1_location_nearbyplaces.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aoinc.group1_location_nearbyplaces.R
import com.aoinc.group1_location_nearbyplaces.model.data.Result
import com.aoinc.group1_location_nearbyplaces.view.LocationsRVAdapter.ResultsItemViewHolder
import com.bumptech.glide.Glide

class LocationsRVAdapter(private var locationList: List<Result>)
    : RecyclerView.Adapter<ResultsItemViewHolder>() {

    public fun updateLocationList(newLocations: List<Result>) {
        locationList = newLocations
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsItemViewHolder =
        ResultsItemViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.location_list_item, parent, false))

    override fun onBindViewHolder(holder: ResultsItemViewHolder, position: Int) {
        val location: Result = locationList[position]

        holder.apply {

            // photos variable may be unset if Places API did not return anything for it
            location.photos?.let {
                Glide.with(holder.itemView.context)
                    .load(it[0].photoReference)
                    .placeholder(R.drawable.ic_baseline_photo_24)
                    .into(locationPhoto)
            }

            locationName.text = location.name
            locationRating.rating = location.rating.toFloat()
        }
    }

    override fun getItemCount(): Int = locationList.size

    inner class ResultsItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val locationPhoto: ImageView = itemView.findViewById(R.id.item_photo_imageView)
        val locationName: TextView = itemView.findViewById(R.id.item_title_textView)
//        val locationAddress: TextView = itemView.findViewById(R.id.item_address_textView)
        val locationRating: RatingBar = itemView.findViewById(R.id.item_rating_bar)
    }
}