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

class FinishedHomeAdapter(private val listReview: List<ListEventsItem>, private val onClick: (Int) -> Unit): RecyclerView.Adapter<FinishedHomeAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val finishedNameHome: TextView = view.findViewById(R.id.tvNameFinHome)
        val finishedSummaryHome: TextView = view.findViewById(R.id.tvSummaryFinHome)
        val imgFinishedHome: ImageView = view.findViewById(R.id.imgFinHome)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_finished_home, viewGroup, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return listReview.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val finished = listReview[position]
        holder.finishedNameHome.text = finished.name
        holder.finishedSummaryHome.text = finished.summary
        Glide.with(holder.itemView.context)
            .load(finished.mediaCover)
            .into(holder.imgFinishedHome)

        holder.itemView.setOnClickListener{
            if (finished.id != null){
                onClick(finished.id)
            }
        }
    }
}