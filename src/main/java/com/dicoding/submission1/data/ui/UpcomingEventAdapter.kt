package com.dicoding.submission1.data.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submission1.R
import com.dicoding.submission1.data.response.ListEventsItem

class UpcomingEventAdapter(private val listReview: List<ListEventsItem>, private val onClick: (Int) -> Unit) : RecyclerView.Adapter<UpcomingEventAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_upcoming, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val upcoming = listReview[position]
        viewHolder.upComingName.text = upcoming.name
        viewHolder.upComingSummary.text = upcoming.summary
        Glide.with(viewHolder.itemView.context)
            .load(upcoming.mediaCover)
            .into(viewHolder.imgUpComing)

        viewHolder.itemView.setOnClickListener{
            if (upcoming.id != null) {
                onClick(upcoming.id) // Pass the ID of the clicked item
            }
        }
    }

    override fun getItemCount(): Int {
        return listReview.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val upComingName: TextView = view.findViewById(R.id.tvNameUpc)
        val upComingSummary: TextView = view.findViewById(R.id.tvSummaryUpc)
        val imgUpComing: ImageView = view.findViewById(R.id.imgUpc)

    }

}