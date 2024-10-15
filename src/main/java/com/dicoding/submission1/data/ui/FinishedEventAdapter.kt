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

class FinishedEventAdapter(private val listReview: List<ListEventsItem>, private val onClick: (Int) -> Unit): RecyclerView.Adapter<FinishedEventAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val finishedName: TextView = view.findViewById(R.id.tvNameFin)
        val finishedSummary: TextView = view.findViewById(R.id.tvSummaryFin)
        val imgFinished: ImageView = view.findViewById(R.id.imgFin)
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_finished, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listReview.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val finished = listReview[position]
        viewHolder.finishedName.text = finished.name
        viewHolder.finishedSummary.text = finished.summary
        Glide.with(viewHolder.itemView.context)
            .load(finished.mediaCover)
            .into(viewHolder.imgFinished)

        viewHolder.itemView.setOnClickListener {
            if (finished.id != null) {
                onClick(finished.id)
            }
        }
    }
}