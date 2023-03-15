package com.example.exerciseapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exerciseapp.databinding.ItemExerciseHistoryBinding
import com.example.exerciseapp.databinding.ItemExerciseStatusBinding

class HistoryAdapter(private var items: ArrayList<String>): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {


    class ViewHolder(binding: ItemExerciseHistoryBinding): RecyclerView.ViewHolder(binding.root){
        val tvId = binding.tvId
        val tvItem = binding.tvItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemExerciseHistoryBinding.inflate(LayoutInflater.from(parent.context)
            ,parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date: String = items.get(position)
        holder.tvId.text = (position + 1).toString()
        holder.tvItem.text = date



    }
}