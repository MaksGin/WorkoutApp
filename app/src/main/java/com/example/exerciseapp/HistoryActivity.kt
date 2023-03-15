package com.example.exerciseapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exerciseapp.databinding.ActivityHistoryBinding
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    private var binding: ActivityHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarHistory)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "HISTORY"
        }
        binding?.toolbarHistory?.setNavigationOnClickListener{
            onBackPressed()
        }

        val exerciseDao = (application as WorkoutApp).db.exerciseDao()
        getAllDates(exerciseDao)
    }

    private fun getAllDates(exerciseDao: ExerciseDao){ //wyświetlenie dat zakończenia ćwiczeń użytkownika
        lifecycleScope.launch {
            exerciseDao.fetchDate().collect{    allcompletedDates ->
                if(allcompletedDates.isNotEmpty()){ //Jeśli strumień nie jest pusty, ustawiamy widoczność elementów
                    binding?.tvExcomp?.visibility = View.VISIBLE
                    binding?.recyclerViewHistory?.visibility = View.VISIBLE
                    binding?.tvdata?.visibility = View.INVISIBLE

                    binding?.recyclerViewHistory?.layoutManager = LinearLayoutManager(this@HistoryActivity)

                    val dates = ArrayList<String>()
                    for (date in allcompletedDates){
                        dates.add(date.date)
                    }
                    val historyAdapter = HistoryAdapter(dates) //adapter
                    binding?.recyclerViewHistory?.adapter = historyAdapter //adapter przypisujemy do recycleView


                }else{
                    binding?.tvExcomp?.visibility = View.GONE
                    binding?.recyclerViewHistory?.visibility = View.GONE
                    binding?.tvdata?.visibility = View.VISIBLE
                }

            }

        }
    }

    override fun onDestroy() { //zwolnienie widoków z pamięci (zapobieganie wyciekowi pamięci)
        super.onDestroy()
        binding = null
    }
}