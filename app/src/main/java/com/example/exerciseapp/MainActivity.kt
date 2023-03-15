package com.example.exerciseapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import com.example.exerciseapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding:ActivityMainBinding? = null

    //findviewbyid dziala na wszystkie pliki xml i przez przypadek mozemy sie odwolac do zmiennej z innego pliku
    //binding dziala tylko w obrebie jednego pliku xml

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        binding?.flStart?.setOnClickListener(){
            val intent = Intent(this,ExerciseActivity::class.java)
            startActivity(intent)
        }

        binding?.flBMI?.setOnClickListener {
            val intent = Intent(this,BMIActivity::class.java)
            startActivity(intent)
        }

        binding?.flHistory?.setOnClickListener{
            val intent = Intent(this,HistoryActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onDestroy() {
        super.onDestroy() //resetujemy binding do nulla zeby nie napotkać błędów (memory leak)
        binding = null
    }
}