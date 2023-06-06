package com.example.makeupstore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.makeupstore.R
import com.example.makeupstore.databinding.ProfileProductBinding
import com.example.makeupstore.models.ProfileProduct

class ProfileProductAdapter(private val data: List<ProfileProduct?>) :
    RecyclerView.Adapter<ProfileProductAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val itemBinding: ProfileProductBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: ProfileProduct) {
            itemBinding.profileProduct = item
            Glide.with(itemBinding.root)
                .load(item.image_link)
                .placeholder(R.drawable.placeholder)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(itemBinding.prodImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val listItemBinding = ProfileProductBinding.inflate(inflater, parent, false)
        return MyViewHolder(listItemBinding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        data[position]?.let { holder.bind(it) }
    }
}