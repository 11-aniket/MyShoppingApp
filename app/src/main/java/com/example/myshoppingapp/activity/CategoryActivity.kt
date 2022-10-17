package com.example.myshoppingapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.myshoppingapp.R
import com.example.myshoppingapp.adapter.CategoryProductAdapter
import com.example.myshoppingapp.adapter.ProductAdapter
import com.example.myshoppingapp.modal.AddProductModal
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        getcategory(intent.getStringExtra("category"))
    }

    private fun getcategory(category:String?){
        var list = ArrayList<AddProductModal>()
        Firebase.firestore.collection("products").whereEqualTo("productCategory",category)
            .get().addOnSuccessListener {
                list.clear()
                for(doc in it.documents){
                    val data = doc.toObject(AddProductModal::class.java)
                    list.add(data!!)
                }
                val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
                recyclerView.adapter = CategoryProductAdapter(this,list)
            }
    }
}