package com.example.educationapp.userPages

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.educationapp.R
import com.google.firebase.auth.FirebaseAuth

class loadingScreem : AppCompatActivity() {


    lateinit var mAuth: FirebaseAuth
    private val sharedPrefFile = "StandardPhref"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading_screem)
        mAuth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        if (mAuth.currentUser == null) {
            startActivity(Intent(this, RegisterUser::class.java))
        }else {
//            startActivity(Intent(this, DashBoard::class.java))
            checkIsMediumAndGradeSet()
        }
    }

    private fun checkIsMediumAndGradeSet() {
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val sharedGradeValue = sharedPreferences.getString("standard","")
        if(sharedGradeValue.equals("")){
            startActivity(Intent(this, StandardPage::class.java))
        }
        else{
            startActivity(Intent(this, DashBoard::class.java))
        }

    }
}