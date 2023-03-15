package com.example.exerciseapp

import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "exercise-table")
data class ExerciseEntity(
    @PrimaryKey
    val date: String



)
