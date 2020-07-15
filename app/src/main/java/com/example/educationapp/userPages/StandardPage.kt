package com.example.educationapp.userPages

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.educationapp.R
import com.example.educationapp.adapter.StandardAdapter
import com.example.educationapp.models.Standard
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_user_profile.*

class StandardPage : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    var recyclerView : RecyclerView?=null
    var standardAdapter  : StandardAdapter?=null
    var user: FirebaseUser?=null
    var standardList:MutableList<Standard>?=null
    var databaseRequest: DatabaseReference?=null
    var listOfSubject:MutableList<String>?=null


    private fun getData() {
        // requestList=null
//        var query  = databaseRequest?.child(user?.uid.toString())

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val list = ArrayList<Request>()
                if(dataSnapshot!!.exists()){
                    for (e in dataSnapshot.children){
//                        val post = e.getValue(Request::class.java)
                        val nameGrade=e.child("name").getValue().toString()
                        val id=e.child("id").getValue().toString()

                        var item= Standard(
                            id,
                            nameGrade
                        )
//                        Log.e("s",item.toString())
                        standardList?.add(item!!)
                    }
                    Log.e("f","finish")
                    standardAdapter= StandardAdapter(standardList!!,this@StandardPage)
                    recyclerView?.adapter=standardAdapter
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        databaseRequest?.addValueEventListener(postListener)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grade_page)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView=findViewById<RecyclerView>(R.id.recyclerView_grade)
        recyclerView?.layoutManager=linearLayoutManager
        recyclerView?.setHasFixedSize(true)
        supportActionBar!!.hide()
        standardList= mutableListOf()
        databaseRequest= FirebaseDatabase.getInstance().getReference("standard");
        user= FirebaseAuth.getInstance().currentUser
        if(user!=null){
            getData()
        }


        recyclerView?.adapter=standardAdapter

        backBtn.setOnClickListener {
            this.onBackPressed();
        }
    }
}