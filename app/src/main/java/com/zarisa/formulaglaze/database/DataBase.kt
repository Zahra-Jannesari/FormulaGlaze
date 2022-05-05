package com.zarisa.formulaglaze.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zarisa.formulaglaze.model.MaterialListConverter

@Database(entities = [Formula::class], version = 1)
@TypeConverters(MaterialListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun formulaDao(): FormulaDao

    companion object {
        var INSTANCE: AppDatabase? = null
        fun getAppDataBase(context: Context): AppDatabase {
            val _Instance = INSTANCE
            if (_Instance != null)
                return _Instance
            synchronized(AppDatabase::class) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "myDB"
                )
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}