package com.example.makeupstore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.makeupstore.R
import com.example.makeupstore.databinding.FavProductBinding
import com.example.makeupstore.models.FavProduct
import com.example.makeupstore.utils.OnItemClickListener

class FavProductAdapter(private var data: List<FavProduct>,private val listener:OnItemClickListener) :
    RecyclerView.Adapter<FavProductAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val itemBinding: FavProductBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: FavProduct) {
            itemBinding.favProduct = item
            Glide.with(itemBinding.root)
                .load(item.image_link)
                .placeholder(R.drawable.placeholder)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(itemBinding.prodImg)
            itemBinding.ivFav.setOnClickListener {
                listener.onItemDeleteFav(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val listItemBinding = FavProductBinding.inflate(inflater, parent, false)
        return MyViewHolder(listItemBinding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position])
    }
}