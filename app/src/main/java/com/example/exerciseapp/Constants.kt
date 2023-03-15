package com.example.exerciseapp
// zwraca listę obiektów klasy ExerciseModel, która zawiera informacje o ćwiczeniach
object Constants {

    fun defaultExList(): ArrayList<ExerciseModel>{
        val exerciseList = ArrayList<ExerciseModel>()
        val squats = ExerciseModel(
            1,
            "SQUATS",
            R.drawable.squatsimage,
            false,
            false
        )
        exerciseList.add(squats)

        val plank = ExerciseModel(
            2,
            "PLANK",
            R.drawable.plank1,
            false,
            false
        )
        exerciseList.add(plank)

        val abs = ExerciseModel(
            3,
            "ABS",
            R.drawable.absex,
            false,
            false
        )
        exerciseList.add(abs)
        return  exerciseList
    }
}