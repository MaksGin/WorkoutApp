package com.example.exerciseapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.exerciseapp.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter(val item: ArrayList<ExerciseModel>): RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemExerciseStatusBinding): RecyclerView.ViewHolder(binding.root){
        val tvItem = binding.tvItem


    }
    //Binds each item in the ArrayList to a view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemExerciseStatusBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: ExerciseModel = item[position]
        holder.tvItem.text = model.getId().toString() //pobieramy id z naszego modelu jako string
        //za pomoca holdera mozemy dostac sie do tvItem
        when{
            model.getisSelected() ->{
                ContextCompat.getDrawable(holder.itemView.context,R.drawable.item_circular_black_background)
                holder.tvItem.setTextColor(Color.parseColor("#ffffff"))
            }
            model.getisCompleted() ->{
                ContextCompat.getDrawable(holder.itemView.context,R.drawable.item_circular_green_background)
                holder.tvItem.setTextColor(Color.parseColor("#212121"))

            }else -> {
                ContextCompat.getDrawable(holder.itemView.context,R.drawable.item_circular_background)
                holder.tvItem.setTextColor(Color.parseColor("#212121"))
            }
        }
    }
}