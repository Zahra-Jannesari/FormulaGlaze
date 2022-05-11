package com.zarisa.formulaglaze

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.zarisa.formulaglaze.adapters.MaterialListAdapter
import com.zarisa.formulaglaze.database.Formula
import com.zarisa.formulaglaze.databinding.FragmentAddOrEditFormulaBinding
import com.zarisa.formulaglaze.model.Material
import com.zarisa.formulaglaze.vmodel.MainViewModel

const val EDIT = "is edit time?"
const val FormulaNAME = "formulaName"

class AddOrEditFormulaFragment : Fragment() {
    lateinit var binding: FragmentAddOrEditFormulaBinding
    private val viewModel: MainViewModel by viewModels()
    private val materialAdapter = MaterialListAdapter()
    private lateinit var theFormula: Formula
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddOrEditFormulaBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (requireArguments().getBoolean(EDIT, false)) {
            theFormula = viewModel.getFormulaByName(requireArguments().getString(FormulaNAME, ""))
            putDataForEdit()
        }
        onClicks()
    }

    private fun onClicks() {
        binding.imageButtonAddNewMaterial.setOnClickListener {
            addNewMaterial()
        }
        binding.btnSaveChange.setOnClickListener {
        }
    }

    private fun addNewMaterial() {
        if (validateData()) {
            theFormula.formulaMaterials.add(
                Material(
                    binding.EditTextNewMaterialName.text.toString(),
                    Integer.parseInt(binding.EditTextNewMaterialAmount.text.toString())
                )
            )
            viewModel.updateFormula(theFormula)
            binding.EditTextNewMaterialName.setText("")
            binding.EditTextNewMaterialAmount.setText("")
            materialAdapter.submitList(theFormula.formulaMaterials)
        } else
            Toast.makeText(requireContext(), "data not valid.", Toast.LENGTH_SHORT).show()
    }

    private fun validateData(): Boolean {
        return (!binding.EditTextNewMaterialName.text.toString()
            .isNullOrBlank() && !binding.EditTextNewMaterialAmount.text.toString().isNullOrBlank())
        var isValid = true
    }

    private fun putDataForEdit() {
        binding.EditTextFormulaName.setText(theFormula.formulaName)
        binding.recyclerview.adapter = materialAdapter
        materialAdapter.submitList(theFormula.formulaMaterials)
    }
}