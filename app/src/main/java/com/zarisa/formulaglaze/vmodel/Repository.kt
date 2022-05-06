package com.zarisa.formulaglaze.vmodel

import android.content.Context
import androidx.lifecycle.LiveData
import com.zarisa.formulaglaze.database.AppDatabase
import com.zarisa.formulaglaze.database.Formula
import com.zarisa.formulaglaze.database.FormulaDao
import com.zarisa.formulaglaze.model.Material

object Repository {
    lateinit var formulaDao: FormulaDao
    fun initDB(context: Context) {
        formulaDao = AppDatabase.getAppDataBase(context).formulaDao()
    }

    fun getCountOfFormulas(): LiveData<Int> {
        return formulaDao.getCountOfFormulas()
    }
    fun addTestDate() {
        formulaDao.deleteAllFormulaList(formulaDao.getFormulaList())
        for (i in 1..5)
        formulaDao.insert(
            Formula(
                i, "blue$i", arrayListOf(
                    Material("a$i", 1.2+i), Material("a${i+1}", 5.42+i)
                )
            )
        )
    }
    fun getSearchMatchList(text:String):List<Formula>{
        return formulaDao.getMatches(text)
    }

    fun getFormulaById(id: Int): Formula {
        return formulaDao.getFormulaById(id)
    }

    fun getLiveDataListFormula(): LiveData<List<Formula>> {
        return formulaDao.getFormulaListLivData()
    }
}