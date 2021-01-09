package com.pradeep.blueroseassignmentapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pradeep.blueroseassignmentapp.databinding.FactItemBinding
import com.pradeep.blueroseassignmentapp.roomdb.entities.FactItem

class FactsListAdapter(var factItems: List<FactItem>) :
    RecyclerView.Adapter<FactsListAdapter.FactsItemViewHolder>() {

    inner class FactsItemViewHolder(val binding: FactItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactsItemViewHolder {
        val view = FactItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FactsItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: FactsItemViewHolder, position: Int) {
        val curFactItem = factItems[position]
        holder.binding.tvFactTitle.text = curFactItem.title
        holder.binding.tvFactDesc.text = curFactItem.description
    }

    override fun getItemCount(): Int {
        return factItems.size
    }

}