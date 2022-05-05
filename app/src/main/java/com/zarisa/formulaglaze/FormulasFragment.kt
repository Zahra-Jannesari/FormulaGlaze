package com.zarisa.formulaglaze

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.zarisa.formulaglaze.databinding.FragmentFormulasBinding
import com.zarisa.formulaglaze.vmodel.MainViewModel


class FormulasFragment : Fragment() {
    lateinit var binding:FragmentFormulasBinding
    private val viewModel: MainViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentFormulasBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()
        adaptRecyclerView()
    }

    private fun adaptRecyclerView() {
        viewModel.listForFormulaList()
        binding.recyclerview.adapter = viewModel.formulaAdapter
    }

    private fun setObserver() {
        viewModel.formulaCounterLiveData.observe(viewLifecycleOwner) {
            binding.textViewCounter.text = it?.toString() ?: "0"
        }
    }
}