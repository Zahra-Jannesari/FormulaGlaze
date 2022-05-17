package com.zarisa.formulaglaze

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.zarisa.formulaglaze.adapters.MaterialListAdapter
import com.zarisa.formulaglaze.adapters.deleteMaterialItem
import com.zarisa.formulaglaze.adapters.getConvertedAmount
import com.zarisa.formulaglaze.database.Formula
import com.zarisa.formulaglaze.databinding.FragmentAddOrEditFormulaBinding
import com.zarisa.formulaglaze.model.Material
import com.zarisa.formulaglaze.vmodel.MainViewModel
import java.util.*

const val EDIT = "is edit time?"
const val FormulaNAME = "formulaName"

class AddOrEditFormulaFragment : Fragment() {
    lateinit var binding: FragmentAddOrEditFormulaBinding
    private val viewModel: MainViewModel by viewModels()
    private val materialAdapter = MaterialListAdapter({ material -> editMaterial(material) },
        { material -> deleteMaterial(material) })
    private var theFormula = Formula("", mutableListOf())
    var isEditTime = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddOrEditFormulaBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isEditTime = requireArguments().getBoolean(EDIT, false)
        if (isEditTime) {
            binding.EditTextFormulaName.isEnabled = false
            theFormula = viewModel.getFormulaByName(requireArguments().getString(FormulaNAME, ""))
            binding.EditTextFormulaName.setText(theFormula.formulaName)
        }
        putDataForEdit()
        onClicks()
    }

    private fun deleteMaterial(material: Material) {
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            setMessage("ماده حذف خواهد شد.")
            setTitle("حذف ماده")
            setPositiveButton("ادامه") { _, _ ->
                theFormula.formulaMaterials.remove(material)
                setNewData()
            }
            setNegativeButton("توقف") { _, _ ->
            }
        }
        builder.create().show()
    }

    private fun editMaterial(material: Material) {
        //add dialog to get the items and ave them
    }

    private fun onClicks() {
        binding.EditTextRatio.doOnTextChanged { inputText, _, _, _ ->
            if (!inputText.isNullOrBlank()) {
                for (i in theFormula.formulaMaterials) {
                    var tempAmount = i.materialAmount * inputText.toString().toDouble()
                    for (material in theFormula.formulaMaterials)
                        tempAmount /= material.materialAmount
                    i.convertedAmount = tempAmount
                    setNewData()
                }
            }
        }
        binding.imageButtonAddNewMaterial.setOnClickListener {
            addNewMaterial()
        }
        binding.btnSaveChange.setOnClickListener {
            if (binding.EditTextFormulaName.text.isNullOrBlank())
                Toast.makeText(requireContext(), "نام فرمول را وارد کنید.", Toast.LENGTH_SHORT)
                    .show()
            else {
                theFormula.formulaName = binding.EditTextFormulaName.text.toString()
                if (isEditTime) {
                    viewModel.updateFormula(theFormula)
                } else {
                    viewModel.insertNewFormula(theFormula)
                }
                findNavController().navigate(R.id.action_addOrEditFormulaFragment_to_formulasFragment)
            }
        }
    }

    private fun setNewData() {
        materialAdapter.submitList(theFormula.formulaMaterials)
        materialAdapter.notifyDataSetChanged()
    }

    private fun addNewMaterial() {
        if (validateData()) {
            theFormula.formulaMaterials.add(
                Material(
                    binding.EditTextNewMaterialName.text.toString(),
                    Integer.parseInt(binding.EditTextNewMaterialAmount.text.toString()),
                    Integer.parseInt(binding.EditTextNewMaterialAmount.text.toString()).toDouble()
                )
            )
            binding.EditTextNewMaterialName.setText("")
            binding.EditTextNewMaterialAmount.setText("")
        } else
            Toast.makeText(requireContext(), "نام و مقدار ماده را وارد کنید.", Toast.LENGTH_SHORT)
                .show()
    }

    private fun validateData(): Boolean {
        return (!binding.EditTextNewMaterialName.text.isNullOrBlank() && !binding.EditTextNewMaterialAmount.text.isNullOrBlank())

    }

    private fun putDataForEdit() {
        binding.recyclerview.adapter = materialAdapter
        materialAdapter.submitList(theFormula.formulaMaterials)
    }
}