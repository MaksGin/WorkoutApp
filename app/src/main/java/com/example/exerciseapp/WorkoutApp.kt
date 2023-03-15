package com.example.exerciseapp

import android.app.Application

class WorkoutApp:Application() {
    val db by lazy{ //obiekt EmployeeDatabase zostanie utworzony tylko wtedy, gdy jest potrzebny, a nie w momencie tworzenia klasy WorkoutApp.
        ExerciseDatabase.getInstance(this)
    }
}

//Kod ten wykorzystuje wzorzec projektowy Singleton, który zapewnia,
// że istnieje tylko jedna instancja klasy EmployeeDatabase w całej aplikacji,
// co poprawia wydajność i zapobiega powielaniu bazy danych.