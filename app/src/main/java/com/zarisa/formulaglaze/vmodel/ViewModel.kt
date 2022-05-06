package com.zarisa.formulaglaze.vmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.zarisa.formulaglaze.database.Formula

class MainViewModel(app: Application) : AndroidViewModel(app) {
    var formulaCounterLiveData: LiveData<Int>
    var formulaListLiveData:LiveData<List<Formula>>
    init {
        Repository.initDB(app.applicationContext)
        formulaCounterLiveData=Repository.getCountOfFormulas()
        formulaListLiveData=Repository.getLiveDataListFormula()
        Repository.addTestDate()
    }
    fun getFormulaById(id: Int): Formula {
        return Repository.getFormulaById(id)
    }
}