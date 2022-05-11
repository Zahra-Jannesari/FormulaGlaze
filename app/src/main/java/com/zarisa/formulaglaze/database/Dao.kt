package com.zarisa.formulaglaze.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.zarisa.formulaglaze.model.Material

@Dao
interface FormulaDao {
    @Query("SELECT * From Formula Where formulaName LIKE '%' || :text || '%'")
    fun getMatches(text: String): List<Formula>

    @Query("SELECT formulaMaterials from Formula where formulaName in (:wantedName)")
    fun getFormulaMaterialsLiveData(wantedName: String): LiveData<List<Material>>

    @Query("SELECT * FROM Formula WHERE formulaName in (:wantedName)")
    fun getFormulaByName(wantedName: String): Formula

    @Query("SELECT count(*) FROM Formula")
    fun getCountOfFormulas(): LiveData<Int>

    @Query("SELECT count(*) FROM Formula")
    fun formulaListSize(): Int

    @Query("SELECT * From formula")
    fun getFormulaListLivData(): LiveData<List<Formula>>

    @Query("SELECT * From formula")
    fun getFormulaList(): List<Formula>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg formula: Formula)

    @Update
    fun updateFormula(formula: Formula)

    @Delete
    fun deleteFormula(formula: Formula)

    @Delete
    fun deleteAllFormulaList(list: List<Formula>)
}