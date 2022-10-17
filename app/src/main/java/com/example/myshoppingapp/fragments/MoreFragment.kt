package com.example.myshoppingapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.AllOrderAdapter
import com.example.myshoppingapp.R
import com.example.myshoppingapp.databinding.FragmentHome2Binding
import com.example.myshoppingapp.databinding.FragmentMoreBinding
import com.example.myshoppingapp.modal.OrderDetailsModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MoreFragment : Fragment() {

    private lateinit var binding : FragmentMoreBinding
    private lateinit var list: ArrayList<OrderDetailsModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMoreBinding.inflate(layoutInflater)

        list = ArrayList()

      val preferences = requireContext().getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)

        Firebase.firestore.collection("allOrders").whereEqualTo("userId",preferences.getString("number","")!!)
            .get().addOnSuccessListener {
            list.clear()
            for(doc in it){
                val data = doc.toObject(OrderDetailsModel::class.java)
                list.add(data)
            }
            binding.recyclerview.adapter = AllOrderAdapter(list, requireContext())
        }

        return binding.root
    }

}