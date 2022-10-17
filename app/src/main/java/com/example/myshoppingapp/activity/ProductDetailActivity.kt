package com.example.myshoppingapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.myshoppingapp.MainActivity
import com.example.myshoppingapp.R
import com.example.myshoppingapp.databinding.ActivityProductDetailBinding
import com.example.myshoppingapp.roomdb.AppDatabase
import com.example.myshoppingapp.roomdb.ProductDao
import com.example.myshoppingapp.roomdb.ProductModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)

        getProductDetails(intent.getStringExtra("id"))
        setContentView(binding.root)
    }

    private fun getProductDetails(prodId: String?) {
        Firebase.firestore.collection("products")
            .document(prodId!!).get().addOnSuccessListener {
                val list = it.get("productImages") as ArrayList<String>
                val name = it.getString("productName")
                val productSp = it.getString("productSp")
                val productDesc = it.getString("productDescription")
                binding.textView8.text = it.getString("productName")
                binding.textView9.text = it.getString( "productSp")
                binding.textView10.text = it.getString("productDescription")


                val slideList = ArrayList<SlideModel>()
                for(data in list){
                    slideList.add(SlideModel(data,ScaleTypes.CENTER_INSIDE))
                }

                cartAction(prodId, name, productSp,it.getString("productCoverImg") )

                binding.imageSlider.setImageList(slideList)

            }.addOnFailureListener{
                Toast.makeText( this ,"Something Went Wrong ",Toast.LENGTH_SHORT).show()
            }
    }

    private fun cartAction(prodId: String, name: String?, productSp: String?, coverImg : String?) {

        val productDao = AppDatabase.getInstance(this).productDao()

        if(productDao.isExit(prodId) != null){
            binding.textView11.text = "Go to Cart"
        }else{
            binding.textView11.text = "Add to Cart"
        }

        binding.textView11.setOnClickListener {
            if(productDao.isExit(prodId) != null) {
                openCart()
            }else{
                addToCart(productDao,prodId,name,productSp,coverImg)
            }
        }
    }

    private fun addToCart(productDao: ProductDao, prodId: String, name: String?, productSp: String?, coverImg: String?) {

        val data = ProductModel(prodId,name,coverImg,productSp)
        lifecycleScope.launch(Dispatchers.IO){
            productDao.insertProduct(data)
            binding.textView11.text = "Go to Cart"
        }
    }

    private fun openCart() {
        val preference = this.getSharedPreferences("info", MODE_PRIVATE)
        val editor = preference.edit()
        editor.putBoolean("isCart",true)
        editor.apply()

        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}