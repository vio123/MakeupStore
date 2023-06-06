package com.example.makeupstore.adapters

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.makeupstore.R
import com.example.makeupstore.database.AppDatabase
import com.example.makeupstore.databinding.CardProductBinding
import com.example.makeupstore.databinding.CartProductBinding
import com.example.makeupstore.models.CartProduct
import com.example.makeupstore.repository.CartRepository
import com.example.makeupstore.utils.OnItemClickListener

class CartProductAdapter(private var data:List<CartProduct>,private val listener: OnItemClickListener):RecyclerView.Adapter<CartProductAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val itemBinding:CartProductBinding):RecyclerView.ViewHolder(itemBinding.root){
        fun bind(item:CartProduct){
            itemBinding.cartProduct = item
            Glide.with(itemBinding.root)
                .load(item.image_link)
                .placeholder(R.drawable.placeholder)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(itemBinding.prodImg)
            itemBinding.prodCant.setText(item.quantity.toString())
            itemBinding.prodCant.addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    try {
                        itemBinding.prodPrice.text =String.format("%.2f", p0.toString().toInt() * item.price.toDouble())
                        listener.onUpdateQuantity(p0.toString().toInt(),item.id)
                    }catch (e:Exception){

                    }
                }

                override fun afterTextChanged(p0: Editable?) {
                }

            })
            itemBinding.prodPrice.text = String.format("%.2f", item.quantity * item.price.toDouble())
            itemBinding.ivClose.setOnClickListener {
                listener.onItemDeleteCart(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val listItemBinding = CartProductBinding.inflate(inflater,parent,false)
        return MyViewHolder(listItemBinding)
    }

    override fun getItemCount(): Int {
       return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position])
    }
}