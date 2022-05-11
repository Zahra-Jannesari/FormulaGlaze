package com.zarisa.formulaglaze.vmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zarisa.formulaglaze.database.Formula
import com.zarisa.formulaglaze.model.Material

class MainViewModel(app: Application) : AndroidViewModel(app) {
    var formulaCounterLiveData: LiveData<Int>
    var formulaListLiveData: LiveData<List<Formula>>
    var materialListLiveData = MutableLiveData<List<Material>>()

    init {
        Repository.initDB(app.applicationContext)
        formulaCounterLiveData = Repository.getCountOfFormulas()
        formulaListLiveData = Repository.getLiveDataListFormula()
        Repository.addTestDate()
    }

    fun getFormulaByName(name: String): Formula {
        return Repository.getFormulaByName(name)
    }

    fun setFormulaMaterialsLiveData(formulaName: String) {
        materialListLiveData.value = Repository.formulaMaterialsLiveData(formulaName).value
    }
}