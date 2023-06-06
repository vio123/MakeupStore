package com.example.makeupstore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.makeupstore.R
import com.example.makeupstore.databinding.CardProductBinding
import com.example.makeupstore.models.CardProduct
import com.example.makeupstore.utils.OnItemClickListener
import java.util.*

class CardProductAdapter(
    private var data: List<CardProduct>,
    private val listener: OnItemClickListener,
) : RecyclerView.Adapter<CardProductAdapter.MyViewHolder>(), Filterable {

    private var filteredList: List<CardProduct> = data

    inner class MyViewHolder(private val itemBinding: CardProductBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: CardProduct) {
            itemBinding.cardProduct = item
            Glide.with(itemBinding.root)
                .load(item.image_link)
                .placeholder(R.drawable.placeholder)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(itemBinding.imageView)
            itemBinding.cardViewProduct.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val listItemBinding = CardProductBinding.inflate(inflater, parent, false)
        return MyViewHolder(listItemBinding)
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(filteredList[position])
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint.isNullOrBlank()) {
                    filterResults.values = data
                } else {
                    val filterPattern = constraint.toString().toLowerCase().trim()
                    val filteredItems = data.filter { item ->
                        item.name?.lowercase(Locale.getDefault())?.contains(filterPattern) == true
                    }
                    filterResults.values = filteredItems
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as? List<CardProduct> ?: emptyList()
                notifyDataSetChanged()
            }

        }
    }
}