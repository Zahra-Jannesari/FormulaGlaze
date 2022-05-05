package com.zarisa.formulaglaze.vmodel

import android.content.Context
import androidx.lifecycle.LiveData
import com.zarisa.formulaglaze.database.AppDatabase
import com.zarisa.formulaglaze.database.FormulaDao

object Repository {
    private lateinit var formulaDao: FormulaDao
    fun initDB(context: Context) {
        formulaDao = AppDatabase.getAppDataBase(context).formulaDao()
    }

    fun getCountOfFormulas(): LiveData<Int> {
        return formulaDao.getCountOfFormulas()
    }
}