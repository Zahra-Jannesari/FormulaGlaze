package com.zarisa.formulaglaze.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FormulaDao{
    @Query("SELECT * From Formula Where formulaName LIKE '%' || :text || '%'")
    fun getMatches(text:String):List<Formula>
    @Query("SELECT * FROM Formula WHERE formulaId in (:wantedId)")
    fun getFormulaById(wantedId:Int):Formula
    @Query("SELECT count(*) FROM Formula")
    fun getCountOfFormulas(): LiveData<Int>
    @Query("SELECT count(*) FROM Formula")
    fun formulaListSize():Int
    @Query("SELECT * From formula")
    fun getFormulaListLivData():LiveData<List<Formula>>
    @Query("SELECT * From formula")
    fun getFormulaList():List<Formula>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(formula: Formula)
    @Update
    fun updateFormula(formula: Formula)
    @Delete
    fun deleteFormula(formula: Formula)
    @Delete
    fun deleteAllFormulaList(list:List<Formula>)
}