package com.example.myshoppingapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myshoppingapp.activity.AddressActivity
import com.example.myshoppingapp.adapter.CartAdapter
import com.example.myshoppingapp.databinding.FragmentCartBinding
import com.example.myshoppingapp.roomdb.AppDatabase
import com.example.myshoppingapp.roomdb.ProductModel

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var list: ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(layoutInflater)

        val preference = requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)
        val editor = preference.edit()
        editor.putBoolean("isCart", false)
        editor.apply()

        val dao = AppDatabase.getInstance(requireContext()).productDao()

        list = ArrayList()

        dao.getAllProduct().observe(requireActivity()){
            binding.cartRecycler.adapter = CartAdapter(requireContext(),it )

            list.clear()
            for (data in it){
                list.add(data.productId)
            }
            totalCost(it)
        }

        return binding.root
    }

    private fun totalCost(data: List<ProductModel>?) {
        var total = 0
        for(item in data!!){
            val result = item.productSp!!.toString()
            total += result.toInt()
        }
        binding.textView14.text = "Total item in cart is ${data.size}"
        binding.textView15.text = "Total Cost : $total"

        if(list.size > 0) {
            binding.checkout.setOnClickListener {
                val intent = Intent(context, AddressActivity::class.java)
                val b = Bundle()
                b.putStringArrayList("productIds", list)
                b.putString("totalCost", total.toString())
                intent.putExtras(b)
                startActivity(intent)
            }
        }
        else{
            Toast.makeText(context,"Please add atleast One product", Toast.LENGTH_SHORT).show()
        }
    }
}