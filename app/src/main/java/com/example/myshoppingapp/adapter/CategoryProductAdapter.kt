package com.example.myshoppingapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myshoppingapp.activity.ProductDetailActivity
import com.example.myshoppingapp.databinding.CategoryProductLayoutBinding
import com.example.myshoppingapp.databinding.LayoutProductItemsBinding
import com.example.myshoppingapp.modal.AddProductModal

class CategoryProductAdapter (val context: Context, private val list : ArrayList<AddProductModal>)
    :RecyclerView.Adapter<CategoryProductAdapter.CategoryProductViewHolder>(){
    inner class CategoryProductViewHolder(val binding: CategoryProductLayoutBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryProductViewHolder {
        val binding = CategoryProductLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return CategoryProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryProductViewHolder, position: Int) {
        Glide.with(context).load(list[position].productCoverImg).into(holder.binding.imageView3)
        holder.binding.textView6.text = list[position].productName
        holder.binding.textView7.text = list[position].productSp

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("id",list[position].productId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
       return list.size
    }
}