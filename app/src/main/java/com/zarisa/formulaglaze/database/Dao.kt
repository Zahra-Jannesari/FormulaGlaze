package com.zarisa.formulaglaze.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FormulaDao{
    @Query("SELECT * FROM Formula WHERE formulaName in (:wantedName) LIMIT 1")
    fun getFormulaByName(wantedName:String):Formula?
    @Query("SELECT * FROM Formula WHERE formulaId in (:wantedId)")
    fun getFormulaById(wantedId:Int):Formula
    @Query("SELECT count(*) FROM Formula")
    fun getCountOfFormulas(): LiveData<Int>
    @Query("SELECT count(*) FROM Formula")
    fun formulaListSize():Int
    @Query("SELECT * From formula")
    fun getFormulaList():List<Formula>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(formula: Formula)
    @Update
    fun updateWord(formula: Formula)
    @Delete
    fun deleteWord(formula: Formula)
}