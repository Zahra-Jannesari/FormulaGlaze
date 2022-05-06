package com.zarisa.formulaglaze

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.zarisa.formulaglaze.adapters.FormulaListAdapter
import com.zarisa.formulaglaze.database.Formula
import com.zarisa.formulaglaze.databinding.FragmentFormulasBinding
import com.zarisa.formulaglaze.vmodel.MainViewModel
import com.zarisa.formulaglaze.vmodel.Repository
import java.util.*


class FormulasFragment : Fragment() {
    lateinit var binding: FragmentFormulasBinding
    private val viewModel: MainViewModel by viewModels()
    var formulAdapter = FormulaListAdapter(){formula -> seeFormula(formula) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFormulasBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()
        adaptRecyclerView()
        onClicks()
    }
    private fun setObserver() {
        viewModel.formulaCounterLiveData.observe(viewLifecycleOwner) {
            binding.textViewCounter.text = it?.toString() ?: "0"
        }
    }
    private fun adaptRecyclerView() {
        binding.recyclerview.adapter = formulAdapter
        submitFormulaList(Repository.getFormulaList())
    }
    private fun onClicks() {
        binding.editTextSearch.doOnTextChanged { inputText, _, _, _ ->
            filter(inputText.toString().lowercase(Locale.getDefault()))
        }
//        binding.textFieldSearch.setEndIconOnClickListener {
//        }
        binding.fabAdd.setOnClickListener {
            val bundle = bundleOf(EDIT to false)
            findNavController().navigate(
                R.id.action_formulasFragment_to_addOrEditFormuliaFragment,
                bundle
            )
        }
    }
    private fun submitFormulaList(list: List<Formula>) {
        formulAdapter.submitList(list)
    }
    private fun filter(text: String) {
        text.let {
            if (it.isNullOrBlank())
                submitFormulaList(Repository.getFormulaList())
            else submitFormulaList(Repository.getSearchMatchList(it))
        }
    }
    fun seeFormula(formula: Formula) {
//        val bundle = bundleOf(EDIT to true, FormulaID to formula)
//        findNavController().navigate(R.id.action_formulasFragment_to_addOrEditFormuliaFragment, bundle
//        )
    }
}