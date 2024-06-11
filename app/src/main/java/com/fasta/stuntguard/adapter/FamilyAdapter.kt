package com.fasta.stuntguard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fasta.stuntguard.data.response.Child
import com.fasta.stuntguard.databinding.ItemFamilyBinding

class FamilyAdapter(private val listChild: ArrayList<Child>) :
    RecyclerView.Adapter<FamilyAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding = ItemFamilyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    class ListViewHolder(var binding: ItemFamilyBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val child = listChild[position]

        holder.binding.apply {
            tvItemName.text = child.childName
            tvItemUmur.text = "${child.childAge.toString()} tahun"
        }
    }

    override fun getItemCount(): Int = listChild.size

}