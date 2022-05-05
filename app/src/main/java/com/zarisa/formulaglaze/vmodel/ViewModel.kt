package com.zarisa.formulaglaze.vmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class MainViewModel(app: Application) : AndroidViewModel(app) {
    var formulaCounterLiveData: LiveData<Int>
    init {
        Repository.initDB(app.applicationContext)
        formulaCounterLiveData=Repository.getCountOfFormulas()
    }
}