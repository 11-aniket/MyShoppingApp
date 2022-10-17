package com.example.myshoppingapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myshoppingapp.activity.CheckOutActivity
import com.example.myshoppingapp.activity.ProductDetailActivity
import com.example.myshoppingapp.databinding.LayoutProductItemsBinding
import com.example.myshoppingapp.fragments.CartFragment
import com.example.myshoppingapp.modal.AddProductModal

class ProductAdapter (val context: Context, private val list : ArrayList<AddProductModal>)
        :RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    inner class ProductViewHolder(val binding:LayoutProductItemsBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = LayoutProductItemsBinding.inflate(LayoutInflater.from(context),parent,false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val data = list[position]
        Glide.with(context).load(data.productCoverImg).into(holder.binding.imageView2)
        holder.binding.textView3.text = data.productName
        holder.binding.textView4.text = data.productMrp
        holder.binding.textView5.text = data.productDescription
        holder.binding.button2.text = data.productSp


        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("id",list[position].productId)
            context.startActivity(intent)
        }
        holder.binding.button.setOnClickListener {
            val intent = Intent (context, CheckOutActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}
