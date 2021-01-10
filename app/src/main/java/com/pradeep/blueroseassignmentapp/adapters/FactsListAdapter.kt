package com.pradeep.blueroseassignmentapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.pradeep.blueroseassignmentapp.R
import com.pradeep.blueroseassignmentapp.databinding.FactItemBinding
import com.pradeep.blueroseassignmentapp.glide.GlideApp
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

        GlideApp.with(holder.binding.ivFactImage.context)
            .load(curFactItem.imageHref)
            .centerCrop()
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.binding.ivFactImage)
    }

    override fun getItemCount(): Int {
        return factItems.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}