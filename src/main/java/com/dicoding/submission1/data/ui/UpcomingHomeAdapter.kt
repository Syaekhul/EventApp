package com.dicoding.submission1.data.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submission1.R
import com.dicoding.submission1.data.response.ListEventsItem

class UpcomingHomeAdapter(private val listReview: List<ListEventsItem>, private val onClick: (Int) -> Unit): RecyclerView.Adapter<UpcomingHomeAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val upcomingNameHome: TextView = view.findViewById(R.id.tvNameUpcHome)
        val imgUpcomingHome: ImageView = view.findViewById(R.id.imgUpcHome)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_upcoming_home, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val upcoming = listReview[position]
        holder.upcomingNameHome.text = upcoming.name
        Glide.with(holder.itemView.context)
            .load(upcoming.mediaCover)
            .into(holder.imgUpcomingHome)

        holder.itemView.setOnClickListener{
            if (upcoming.id != null){
                onClick(upcoming.id)
            }
        }
    }

    override fun getItemCount(): Int {
        return listReview.size
    }
}