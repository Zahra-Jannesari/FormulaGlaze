package com.zarisa.formulaglaze.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zarisa.formulaglaze.database.Formula
import com.zarisa.formulaglaze.databinding.FormulaListItemBinding

typealias seeAndEditFormula = (Formula) -> Unit

class FormulaListAdapter(var onFormulaClick: seeAndEditFormula) :
    ListAdapter<Formula, FormulaListAdapter.Holder>(FormulaDiffCallback) {
    inner class Holder(private val binding: FormulaListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Formula) {
            try {
                binding.formula = item
                binding.root.setOnClickListener { onFormulaClick(item) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormulaListAdapter.Holder {
        val binding =
            FormulaListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: FormulaListAdapter.Holder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }
}

object FormulaDiffCallback : DiffUtil.ItemCallback<Formula>() {
    override fun areItemsTheSame(oldItem: Formula, newItem: Formula): Boolean {
        return oldItem.formulaName == newItem.formulaName
    }

    override fun areContentsTheSame(oldItem: Formula, newItem: Formula): Boolean {
        return oldItem.formulaName == newItem.formulaName
    }
}