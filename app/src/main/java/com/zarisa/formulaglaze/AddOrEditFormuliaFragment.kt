package com.zarisa.formulaglaze

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.zarisa.formulaglaze.adapters.MaterialListAdapter
import com.zarisa.formulaglaze.databinding.FragmentAddOrEditFormulaBinding
import com.zarisa.formulaglaze.vmodel.MainViewModel

const val EDIT = "is edit time?"
const val FormulaNAME = "formulaName"

class AddOrEditFormulaFragment : Fragment() {
    lateinit var binding: FragmentAddOrEditFormulaBinding
    private val viewModel: MainViewModel by viewModels()
    private val materialAdapter = MaterialListAdapter()
    private lateinit var theFormulaName: String
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
            theFormulaName = requireArguments().getString(FormulaNAME, "")
            putDataForEdit()
        }
        onClicks()
    }

    private fun onClicks() {
        binding.btnSaveChange.setOnClickListener {
        }
    }

    private fun putDataForEdit() {
        binding.EditTextFormulaName.setText(theFormulaName)
        binding.recyclerview.adapter = materialAdapter
        viewModel.setFormulaMaterialsLiveData(theFormulaName)
        viewModel.materialListLiveData.observe(viewLifecycleOwner) {
            materialAdapter.submitList(it)
        }
    }
}