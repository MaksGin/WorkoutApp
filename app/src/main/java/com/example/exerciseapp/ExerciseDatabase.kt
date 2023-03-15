package com.example.exerciseapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [ExerciseEntity::class], version = 1)
abstract class ExerciseDatabase: RoomDatabase() {

    abstract fun exerciseDao(): ExerciseDao

    companion object { //obiekt towarzyszący, który zawiera metody statyczne i stałe, które można wywoływać bez konieczności tworzenia obiektu klasy

        @Volatile //jest zmienną wrażliwą na wątki, co oznacza, że może być odczytywana i zapisywana przez wiele wątków jednocześnie.
        private var INSTANCE: ExerciseDatabase? =
            null //zmienna która przechowuje instancje bazy danych

        fun getInstance(context: Context): ExerciseDatabase {

            synchronized(this) { //synchronizuje dostęp do zmiennej INSTANCE, co zapobiega jednoczesnym zapisom z różnych wątków.
                var instance = INSTANCE

                if (instance == null) {
                    //tworzy nową instancję bazy danych z podanymi parametrami, w tym kontekstem aplikacji, nazwą bazy danych i klasą bazy danych.
                    // Metoda fallbackToDestructiveMigration informuje bibliotekę Room, że jeśli wersja bazy danych zostanie zaktualizowana,
                    // należy usunąć wszystkie dane i utworzyć nową tabelę pracowników. Metoda build tworzy instancję bazy danych na podstawie podanych parametrów.
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ExerciseDatabase::class.java,
                        "exercise_database"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }
                return instance
            }

        }

    }
}