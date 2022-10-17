package com.example.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myshoppingapp.databinding.AllOrderItemLayoutBinding
import com.example.myshoppingapp.modal.OrderDetailsModel


import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AllOrderAdapter(val list: ArrayList<OrderDetailsModel>, val context: Context)
    : RecyclerView.Adapter<AllOrderAdapter.AllOrderViewHolder>(){

    inner class AllOrderViewHolder(val binding: AllOrderItemLayoutBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllOrderViewHolder {
        return AllOrderViewHolder(
            AllOrderItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        )
    }

    override fun onBindViewHolder(holder: AllOrderViewHolder, position: Int) {
        holder.binding.productTitle.text = list[position].name
        holder.binding.productPrice.text = list[position].price

        when(list[position].status){
            "Ordered" -> {
                holder.binding.orderStatus.text = "Ordered"
            }
            "Dispatched" -> {
                holder.binding.orderStatus.text = "Dispatched"
            }
            "Delivered" -> {
                holder.binding.orderStatus.text = "Delivered"
            }
            "Canceled" -> {
                holder.binding.orderStatus.text = "Canceled"
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}