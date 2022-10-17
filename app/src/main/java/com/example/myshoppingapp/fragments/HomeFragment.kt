package com.example.myshoppingapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.myshoppingapp.R
import com.example.myshoppingapp.activity.ProductDetailActivity
import com.example.myshoppingapp.adapter.CategoryAdapter
import com.example.myshoppingapp.adapter.ProductAdapter
import com.example.myshoppingapp.databinding.FragmentHome2Binding
import com.example.myshoppingapp.modal.AddProductModal
import com.example.myshoppingapp.modal.CategoryModal
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHome2Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHome2Binding.inflate(layoutInflater)


//        binding.button2.setOnClickListener{
//            val intent = Intent(requireContext(),ProductDetailActivity::class.java)
//            startActivity(intent)
//        }
        val preference = requireContext().getSharedPreferences("info",AppCompatActivity.MODE_PRIVATE)

        if(preference.getBoolean("isCart", true))
            findNavController().navigate(R.id.action_homeFragment_to_cartFragment)


        getCategory()
        getSliderImage()
        getProducts()

        return binding.root
    }

    private fun getSliderImage() {
        Firebase.firestore.collection("slider").document("item")
            .get().addOnSuccessListener {
                Glide.with(requireContext()).load(it.get("img")).into(binding.sliderimage)
            }
    }

    private fun getProducts() {
        var list = ArrayList<AddProductModal>()
        Firebase.firestore.collection("products")
            .get().addOnSuccessListener {
                list.clear()
                for(doc in it.documents){
                    val data = doc.toObject(AddProductModal::class.java)
                    list.add(data!!)
                }
                binding.productRecycler.adapter = ProductAdapter(requireContext(),list)
            }
    }

    private fun getCategory() {
        var list = ArrayList<CategoryModal>()
        Firebase.firestore.collection("categories")
            .get().addOnSuccessListener {
                list.clear()
                for(doc in it.documents){
                    val data = doc.toObject(CategoryModal::class.java)
                    list.add(data!!)
                }
                binding.categoryRecycler.adapter = CategoryAdapter(requireContext(),list)
            }
    }


}