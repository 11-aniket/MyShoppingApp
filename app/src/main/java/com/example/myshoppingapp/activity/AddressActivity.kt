package com.example.myshoppingapp.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myshoppingapp.R
import com.example.myshoppingapp.databinding.ActivityAddressBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.razorpay.CheckoutActivity

class AddressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddressBinding
    private lateinit var preferences: SharedPreferences
    private lateinit var totalCost : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = this.getSharedPreferences("user", MODE_PRIVATE)

        totalCost = intent.getStringExtra("totalCost")!!
        loadUserInfo()

        binding.proceedtocheckout.setOnClickListener {
            validateData(
                binding.userNumber.text.toString(),
                binding.userName.text.toString(),
                binding.userAddress.text.toString(),
                binding.userPincode.text.toString(),
                binding.userCity.text.toString(),
                binding.userState.text.toString()
            )
        }
    }

    private fun validateData(number: String, name : String, address: String, pincode: String, city: String, state: String) {

        if(number.isEmpty()||name.isEmpty()||address.isEmpty()||pincode.isEmpty()||city.isEmpty()||state.isEmpty()) {
            Toast.makeText(this, "Please fill all details ", Toast.LENGTH_SHORT).show()
        }else{
            storeData(address,city,state,pincode)
        }

    }

    private fun storeData( address: String, city: String, state: String, pincode: String) {
        val map = HashMap<String,Any>()
        map["address"] = address
        map["city"] = city
        map["state"] = state
        map["pinCode"] = pincode

        Firebase.firestore.collection("users")
            .document(preferences.getString("number","")!!)
            .update(map).addOnSuccessListener {
                val b = Bundle()
                b.putString("totalCost",totalCost)
                b.putStringArrayList("productIds",intent.getStringArrayListExtra("productIds"))
                val intent = Intent(this,CheckOutActivity::class.java)
                intent.putExtras(b)
                startActivity(intent)


            }.addOnFailureListener{
                Toast.makeText(this,"Something went wrong", Toast.LENGTH_SHORT).show()
            }

    }

    private fun loadUserInfo() {

        Firebase.firestore.collection("users")
            .document(preferences.getString("number","")!!)
            .get().addOnSuccessListener {
                binding.userName.setText(it.getString("userName"))
                binding.userNumber.setText(it.getString("userPhoneNumber"))
                binding.userAddress.setText(it.getString("address"))
                binding.userCity.setText(it.getString("city"))
                binding.userState.setText(it.getString("state"))
                binding.userPincode.setText(it.getString("pinCode"))
            }
    }
}