package com.zarisa.formulaglaze.model

import androidx.room.TypeConverter

class MaterialListConverter {
    @TypeConverter
    fun stringFromMaterialList(materials: List<Material>): String {
        var materialListString =
            "${materials[0].materialName},${materials[0].materialAmount}"
        if (materials.size > 1) {
            for (i in 1 until materials.size) {
                materialListString += "/${materials[i].materialName},${materials[i].materialAmount}"
            }
        }
        return materialListString
    }

    @TypeConverter
    fun stringToMaterialList(materialListString: String): List<Material> {
        var materialList = arrayListOf<Material>()
        var materialStrings = materialListString.split('/')
        for (str in materialStrings) {
            var material = str.split(',')
            materialList.add(Material(material[0], material[1].toInt()))
        }
        return materialList
    }
}