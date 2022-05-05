package com.zarisa.formulaglaze.vmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.zarisa.formulaglaze.adapters.FormulaListAdapter
import com.zarisa.formulaglaze.database.Formula

class MainViewModel(app: Application) : AndroidViewModel(app) {
    var formulaCounterLiveData: LiveData<Int>
    val formulaAdapter = FormulaListAdapter()
    init {
        Repository.initDB(app.applicationContext)
        formulaCounterLiveData=Repository.getCountOfFormulas()
    }

    fun listForFormulaList() {
        Repository.addTestDate()
        submitFormulaList(Repository.getFormulaList())
    }

    fun filter(text: String) {
        text.let {
            if(it.isNullOrBlank())
                submitFormulaList(Repository.getFormulaList())
            else submitFormulaList(Repository.getSearchMatchList(it))
        }

    }
    fun submitFormulaList(list:List<Formula>){
        formulaAdapter.submitList(list)
    }
}