package com.zarisa.formulaglaze

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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

    private fun onClicks() {
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

    private fun addNewMaterial() {
        if (validateData()) {
            theFormula.formulaMaterials.add(
                Material(
                    binding.EditTextNewMaterialName.text.toString(),
                    Integer.parseInt(binding.EditTextNewMaterialAmount.text.toString())
                )
            )
//            viewModel.updateFormula(theFormula)
            binding.EditTextNewMaterialName.setText("")
            binding.EditTextNewMaterialAmount.setText("")
        } else
            Toast.makeText(requireContext(), "نام و مقدار ماده را وارد کنید.", Toast.LENGTH_SHORT)
                .show()
    }

    private fun validateData(): Boolean {
        return (!binding.EditTextNewMaterialName.text.toString()
            .isNullOrBlank() && !binding.EditTextNewMaterialAmount.text.toString().isNullOrBlank())
        var isValid = true
    }

    private fun putDataForEdit() {
        binding.recyclerview.adapter = materialAdapter
        materialAdapter.submitList(theFormula.formulaMaterials)
    }
}