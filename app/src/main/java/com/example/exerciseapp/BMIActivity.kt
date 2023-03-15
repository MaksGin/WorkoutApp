package com.example.exerciseapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.exerciseapp.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    private var binding: ActivityBmiBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBmi)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "BMI CALCULATOR"
        }
        binding?.toolbarBmi?.setNavigationOnClickListener{
            onBackPressed()
        }

        binding?.BtnCalculate?.setOnClickListener {
            if(validateMetric()){
                val height: Float = binding?.inputHeight?.text.toString().toFloat() / 100
                val weight: Float = binding?.inputWeight?.text.toString().toFloat()

                val bmi = weight / (height*height)

                displayBMI(bmi)
            }else{
                Toast.makeText(this,"Please enter valid values!",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun displayBMI(bmi: Float){


        var bmiLabel: String = ""
        var bmiDesc: String = ""
        if(bmi.compareTo(15f) <= 0 ){
            bmiLabel = "UnderWeight"
            bmiDesc = "Take care of yourself! Eat more!"
        }else if(bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0){
            bmiLabel = "Normal"
            bmiDesc = "You are in good shape!"
        }else if(bmi.compareTo(35f) > 0 ){
            bmiLabel = "Obese"
            bmiDesc = "Dangerous condition! Act now!"
        }
        binding?.bmiValue?.visibility = View.VISIBLE
        val bmivalue = BigDecimal(bmi.toDouble()).setScale(2,RoundingMode.HALF_EVEN).toString()


        binding?.bmiValue?.text = bmivalue
        binding?.bmiResult?.text = bmiLabel
        binding?.tvMsg?.text = bmiDesc
    }


    private fun validateMetric(): Boolean{
        var isValid = true
        if(binding?.inputWeight?.text.toString().isEmpty()){
            isValid = false
        }else if(binding?.inputHeight?.text.toString().isEmpty()){
            isValid = false
        }
        return isValid
    }
}
