package com.example.exerciseapp

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.exerciseapp.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class FinishActivity : AppCompatActivity() {

    private var binding: ActivityFinishBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarFinishActivity)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolbarFinishActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.btnFinish?.setOnClickListener{
            finish()
        }
        val exerciseDao = (application as WorkoutApp).db.exerciseDao()
        addExercisetoDatabase(exerciseDao)
    }

    private fun addExercisetoDatabase(exerciseDao: ExerciseDao){

        val c = Calendar.getInstance()
        val dateTime = c.time

        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date = sdf.format(dateTime)

        lifecycleScope.launch{
            exerciseDao.insert(ExerciseEntity(date))
        }

    }
}