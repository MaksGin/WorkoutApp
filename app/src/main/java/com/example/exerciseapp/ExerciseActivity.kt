package com.example.exerciseapp

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.Toast
import android.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exerciseapp.databinding.ActivityExerciseBinding
import com.example.exerciseapp.databinding.DialogBackConfirmationBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Timer

class ExerciseActivity : AppCompatActivity() {



    private var binding: ActivityExerciseBinding? = null
    private var resTimer: CountDownTimer? = null
    private var restProgress = 0

    private var EXTimer: CountDownTimer? = null
    private var EXProgress = 0

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var EXposition = -1

    private var player: MediaPlayer? = null

    private var EXadapter: ExerciseStatusAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarEx)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        exerciseList = Constants.defaultExList()

        binding?.toolbarEx?.setNavigationOnClickListener {
            DialogBackButton()
        }
        setupRestView()
        setupExadapter()
    }

    override fun onBackPressed() {
        DialogBackButton()

    }

    private fun DialogBackButton() {
        val dialog = Dialog(this)
        val dialogBinding = DialogBackConfirmationBinding.inflate(layoutInflater) //binding dla dialog_back_confirmation.xml

        dialog.setContentView(dialogBinding.root)
        dialog.setCanceledOnTouchOutside(false) //user nie moze zcancelowac tego klikajac gdzies obok
        dialogBinding.btnYes.setOnClickListener {
            this@ExerciseActivity.finish()
            dialog.dismiss()
        }

        dialogBinding.btnNO.setOnClickListener {
            dialog.dismiss()
        }


        dialog.show()
    }

    private fun setupExadapter(){
        binding?.rvExercise?.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false) //przypisujemy LayoutManager do recycleView

        EXadapter = ExerciseStatusAdapter(exerciseList!!) //obiekt adaptera przypisywany do recycleView
        binding?.rvExercise?.adapter = EXadapter


    }


    private fun setRestProgressBar(){ //odmierzanie czasu przerwy miedzy cwiczeniami

        binding?.progressBar?.progress = restProgress

        resTimer = object: CountDownTimer(10000,1000){ //co sekunde metoda ontick wykona się
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                EXposition++ //po zakończeniu wyświetlamy kolejne ćwiczenie

                exerciseList!![EXposition].setisSelected(true)
                EXadapter!!.notifyDataSetChanged() //odświeżamy liste cwiczen

                setupExerciseView() //widok dla nowego ćwiczenia
            }

        }.start()

    }

    private fun setEXProgressBar() {
        binding?.progressBarExercise?.progress = EXProgress

        EXTimer = object : CountDownTimer(30000, 1000) { //co sekunde metoda ontick wykona się
            override fun onTick(millisUntilFinished: Long) {
                EXProgress++
                binding?.progressBarExercise?.progress = 30 - EXProgress
                binding?.tvTimerExercise?.text = (30 - EXProgress).toString()
            }

            override fun onFinish() {



                if(EXposition  <exerciseList?.size!! - 1){ //jesli mamy jeszcze cwiczenia pokaz ekran odpoczynku RESTVIEW
                    exerciseList!![EXposition].setisSelected(false)
                    exerciseList!![EXposition].setisCompleted(true)
                    EXadapter!!.notifyDataSetChanged()
                    setupRestView()
                }else{
                    finish()
                    val intent = Intent(this@ExerciseActivity,FinishActivity::class.java)
                    startActivity(intent)
                }
            }

        }.start()
    }



    private fun setupRestView(){
        //Odtwarzanie dzwieku po kazdym cwiczeniu
        try {
            val soundURI = (Uri.parse("android.resource://com.example.exerciseapp/"+R.raw.beep)) //parsujemy sciezke
            player = MediaPlayer.create(applicationContext,soundURI)
            player?.isLooping = false
            player?.start()

        }catch (e: java.lang.Exception){
            e.printStackTrace()
        }
        binding?.flProgressBar?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.tvExercise?.visibility = View.INVISIBLE
        binding?.ivImage?.visibility = View.INVISIBLE
        binding?.flProgressBarExercise?.visibility = View.INVISIBLE

        binding?.tvUpcomingEX?.visibility = View.VISIBLE
        binding?.tvUpcomingEXname?.visibility = View.VISIBLE

        if(resTimer != null){
            resTimer?.cancel()
            restProgress = 0
        }
        binding?.tvUpcomingEXname?.text = exerciseList!![EXposition+1].getName()


        setRestProgressBar()
    }

    private fun setupExerciseView(){

        binding?.flProgressBar?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.tvExercise?.visibility = View.VISIBLE
        binding?.ivImage?.visibility = View.VISIBLE
        binding?.flProgressBarExercise?.visibility = View.VISIBLE

        binding?.tvUpcomingEX?.visibility = View.INVISIBLE
        binding?.tvUpcomingEXname?.visibility = View.INVISIBLE

        if(EXTimer != null){
            EXTimer?.cancel()
            EXProgress = 0
        }
        binding?.ivImage?.setImageResource(exerciseList!![EXposition].getImage())
        binding?.tvExercise?.text = exerciseList!![EXposition].getName()
        setEXProgressBar()
    }

    override fun onDestroy() {


        if(resTimer != null){
            resTimer?.cancel()
            restProgress = 0
        }
        super.onDestroy()
        binding = null


        if(player != null){
            player?.stop()

        }
    }


}