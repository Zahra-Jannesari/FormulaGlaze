package com.zarisa.formulaglaze.vmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.zarisa.formulaglaze.adapters.FormulaListAdapter

class MainViewModel(app: Application) : AndroidViewModel(app) {
    var formulaCounterLiveData: LiveData<Int>
    val formulaAdapter = FormulaListAdapter()
    init {
        Repository.initDB(app.applicationContext)
        formulaCounterLiveData=Repository.getCountOfFormulas()
    }

    fun listForFormulaList() {
        Repository.addTestDate()
        formulaAdapter.submitList(Repository.getFormulaList())
    }
}