package com.thatsmanmeet.calculator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thatsmanmeet.calculator.room.History

class RecyclerViewAdapter(private val historyList: MutableList<History>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val historyTitle: TextView = itemView.findViewById(R.id.tvHistory)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_layout,parent,false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.historyTitle.text = historyList[position].result
    }
    override fun getItemCount(): Int {
        return historyList.size
    }
}