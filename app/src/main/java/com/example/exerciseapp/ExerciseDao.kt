package com.example.exerciseapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {

    @Insert
    suspend fun insert(exerciseModel: ExerciseEntity)

    @Query("SELECT * FROM `exercise-table`")
    fun fetchDate(): Flow<List<ExerciseEntity>>

}