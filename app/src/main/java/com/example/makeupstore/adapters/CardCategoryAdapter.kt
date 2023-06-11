package com.example.makeupstore.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.makeupstore.R
import com.example.makeupstore.databinding.CardCategoryBinding
import com.example.makeupstore.models.CardCategory
import com.example.makeupstore.utils.OnItemClickListener
import com.example.makeupstore.viewmodels.HomeViewModel
import com.google.firebase.database.collection.LLRBNode

class CardCategoryAdapter(
    private val data: List<CardCategory>,
    private val listener: OnItemClickListener,
) : RecyclerView.Adapter<CardCategoryAdapter.MyViewHolder>() {
    private var selectedPosition: Int = RecyclerView.NO_POSITION

    inner class MyViewHolder(private val itemBinding: CardCategoryBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        @SuppressLint("ResourceType")
        fun bind(item: CardCategory, position: Int) {
            itemBinding.cardCategory = item
            Glide.with(itemBinding.root)
                .load(item.image)
                .placeholder(R.drawable.placeholder)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(itemBinding.imageView)
            itemBinding.card.setOnClickListener {
                val previousPosition = selectedPosition
                selectedPosition = position
                val recyclerView = itemBinding.root.parent as? RecyclerView
                val previousCard = recyclerView?.findViewHolderForAdapterPosition(previousPosition)
                val cardView = previousCard?.itemView?.findViewById<CardView>(R.id.card)

                val typedValue = TypedValue()
                val resolved = itemBinding.root.context.theme.resolveAttribute(R.attr.colorOnBackground, typedValue, true)
                if (resolved) {
                    val colorAttr = ContextCompat.getColor(itemBinding.root.context, typedValue.resourceId)
                    cardView?.setCardBackgroundColor(colorAttr)
                }
                // Set the background color of the current card to cyan
                itemBinding.card.setCardBackgroundColor(Color.CYAN)

                // Notify the listener of the item click
                listener.onItemClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val listItemBinding = CardCategoryBinding.inflate(inflater, parent, false)
        return MyViewHolder(listItemBinding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position],position)
    }
}