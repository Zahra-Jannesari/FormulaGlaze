package com.zarisa.formulaglaze.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zarisa.formulaglaze.databinding.MaterialItemBinding
import com.zarisa.formulaglaze.model.Material

typealias deleteMaterialItem=(material:Material)->Unit
typealias editMaterialItem=(material:Material)->Unit
typealias getConvertedAmount=(materialAmount:Int)->String

class MaterialListAdapter(var onEditItemL:editMaterialItem,var onDeleteItem:deleteMaterialItem): ListAdapter<Material, MaterialListAdapter.MaterialHolder>(MaterialDiffCallback) {
    inner class MaterialHolder(private val binding: MaterialItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Material) {
            try {
                binding.editTextMaterialName.text = item.materialName
                binding.editTextMaterialAmount.text = item.materialAmount.toString()
                binding.editTextMaterialConvertAmount.text =item.convertedAmount.toString()
                binding.btnDeleteItem.setOnClickListener { onDeleteItem(item) }
                binding.btnEditItem.setOnClickListener { onEditItemL(item) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaterialListAdapter.MaterialHolder {
        val binding =
            MaterialItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MaterialHolder(binding)
    }

    override fun onBindViewHolder(holder: MaterialListAdapter.MaterialHolder, position: Int) {
        val material = getItem(position)
        holder.bind(material)
    }
}

object MaterialDiffCallback : DiffUtil.ItemCallback<Material>() {
    override fun areItemsTheSame(oldItem: Material, newItem: Material): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Material, newItem: Material): Boolean {
        return oldItem == newItem
    }
}