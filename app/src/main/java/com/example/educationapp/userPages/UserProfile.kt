package com.example.educationapp.userPages

import android.app.PendingIntent.getActivity
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.educationapp.R
import com.example.educationapp.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.activity_user_profile.editText_area
import kotlinx.android.synthetic.main.activity_user_profile.editText_birthDate
import kotlinx.android.synthetic.main.activity_user_profile.editText_name
import kotlinx.android.synthetic.main.activity_user_profile.editText_phone

class UserProfile : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    var userData: User?=null
    var uid:String ?=""
    var databaseRequest: DatabaseReference?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        supportActionBar!!.hide()
        mAuth = FirebaseAuth.getInstance()
        databaseRequest= FirebaseDatabase.getInstance().getReference("Users")

        backBtn.setOnClickListener {
            this.onBackPressed();
        }

        button_medium.setOnClickListener {
            startActivity(Intent(this, StandardPage::class.java))
        }

        button_update.setOnClickListener {
            //update data
            validateData()
            this.onBackPressed()
        }

    }


    private fun validateData() {
        var name :String=editText_name.text.toString();    var area :String=editText_area.text.toString()
        var phone :String=editText_phone.text.toString();  var birthDate :String=editText_birthDate.text.toString()
        if(!(name != "" && area != "" && phone != "" && birthDate != "")){
            //data not provided
            Toast.makeText(this,"fill all details",Toast.LENGTH_SHORT).show()
        }
        else{
          val usermodel =User(uid,name,area,phone,birthDate)
            upDateData(usermodel!!)
        }

    }

    private fun upDateData(usermodel: User) {
        databaseRequest!!.child(uid!!).setValue(usermodel)
    }


    override fun onStart() {
        super.onStart()
        if (mAuth.currentUser == null) {
            startActivity(Intent(this, RegisterUser::class.java))
        }else {
//            startActivity(Intent(this, DashBoard::class.java))
            uid=mAuth.currentUser!!.uid.toString()
            databaseRequest= FirebaseDatabase.getInstance().getReference("Users")
            getUserData()
            setView()
        }
    }

    private fun setView() {
//        Log.e("details",userData.toString())
//        editText_name.setText(userData!!.name)
//        editText_area.setText(userData!!.area)
//        editText_birthDate.setText(userData!!.dateofbrith)
//        editText_phone.setText(userData!!.phone)
    }

    private fun getUserData() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot!!.exists()){
                    for (e in dataSnapshot.children){
                        val name=e.child("name").getValue().toString()
                        val id=e.child("uid").getValue().toString()
                        val area=e.child("area").getValue().toString()
                        val phone=e.child("phone").getValue().toString()
                        val dateofbrith=e.child("dateofbrith").getValue().toString()
                        if(id.equals(uid)){
                            Log.e("area",area)
                            userData=User(id,name,area,phone,dateofbrith)
                            Log.e("area",userData!!.area)
                            editText_name.setText(userData!!.name)
                            editText_area.setText(userData!!.area)
                            editText_birthDate.setText(userData!!.dateofbrith)
                            editText_phone.setText(userData!!.phone)

                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        databaseRequest?.addValueEventListener(postListener)

    }
}