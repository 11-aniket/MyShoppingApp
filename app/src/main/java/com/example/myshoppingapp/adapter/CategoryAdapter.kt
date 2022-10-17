package com.example.myshoppingapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myshoppingapp.R
import com.example.myshoppingapp.activity.CategoryActivity
import com.example.myshoppingapp.databinding.CategoryItemsBinding
import com.example.myshoppingapp.modal.CategoryModal

class CategoryAdapter(var context: Context, private val list: ArrayList<CategoryModal>)
    : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(){
    inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var binding = CategoryItemsBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            LayoutInflater.from(context).inflate(R.layout.category_items,parent,false)
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.binding.textView2.text = list[position].category
        Glide.with(context).load(list[position].img).into(holder.binding.imageView)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, CategoryActivity::class.java)
            intent.putExtra("category",list[position].category)
            context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}