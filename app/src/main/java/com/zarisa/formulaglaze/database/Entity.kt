package com.zarisa.formulaglaze.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zarisa.formulaglaze.model.Material

@Entity
data class Formula(
    @PrimaryKey var formulaName: String,
    var formulaMaterials: MutableList<Material>
)
