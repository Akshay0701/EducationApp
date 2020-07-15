package com.example.educationapp.userPages

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.educationapp.R
import com.example.educationapp.adapter.SubjectsAdapter
import com.example.educationapp.models.Subject
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_dash_board.*
import kotlinx.android.synthetic.main.activity_dash_board.button_medium
import java.util.*

class DashBoard : AppCompatActivity() {
    lateinit var mAuth: FirebaseAuth

    private val sharedPrefFile = "StandardPhref"
    var standard:String?=""
    var uid:String ?=""

    private lateinit var linearLayoutManager: LinearLayoutManager
    var recyclerView : RecyclerView?=null
    var subjectAdapter  : SubjectsAdapter?=null
    var user: FirebaseUser?=null
    var databaseRequest: DatabaseReference?=null
    var userDatabaseRequest: DatabaseReference?=null
    var subjectsList:MutableList<Subject>?=null


    private fun getData() {
        // requestList=null
//        var query  = databaseRequest?.child(user?.uid.toString())

        val userListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot!!.exists()){
                    for (e in dataSnapshot.children){
                        val name=e.child("name").getValue().toString()
                        val id=e.child("uid").getValue().toString()
                        if(id.equals(uid)){
                            txt_name.setText(name)
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        userDatabaseRequest?.addValueEventListener(userListener)


        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot!!.exists()){
                    subjectsList!!.clear()
                    for (e in dataSnapshot.children){
                        val standardSubjectAvailable=e.child("standard").getValue().toString()
                        val id=e.child("id").getValue().toString()
                        val subjectName=e.child("name").getValue().toString()
                        if(standardSubjectAvailable.contains(standard!!)){
                            //add this to subject list
                            var item= Subject(standardSubjectAvailable, id,subjectName)
                            subjectsList?.add(item!!)
                        }
                    }
                    subjectAdapter= SubjectsAdapter(subjectsList!!,this@DashBoard)
                    recyclerView?.adapter=subjectAdapter
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
        setContentView(R.layout.activity_dash_board)
        mAuth = FirebaseAuth.getInstance()


        linearLayoutManager = LinearLayoutManager(this)
        recyclerView=findViewById<RecyclerView>(R.id.recyclerView_dashBorad)
        recyclerView?.layoutManager=linearLayoutManager
        recyclerView?.setHasFixedSize(true)

        subjectsList= mutableListOf()
        databaseRequest= FirebaseDatabase.getInstance().getReference("subjects");

//        recyclerView?.adapter=gradeAdapter

        button_grade.setOnClickListener {
            startActivity(Intent(this, StandardPage::class.java))
        }
        button_userProfile.setOnClickListener {
            startActivity(Intent(this, UserProfile::class.java))
        }
        button_medium.setOnClickListener {
            startActivity(Intent(this, StandardPage::class.java))
        }
        button_logout.setOnClickListener {
            logoutUser()
        }

    }

    private fun logoutUser() {
        val builder = AlertDialog.Builder(this,R.style.AlertDialogCustom)
        //set title for alert dialog
        builder.setTitle("Logout!")
        //set message for alert dialog
        builder.setMessage("Click Yes To Logout..")
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        //performing positive action
        builder.setPositiveButton("Yes"){dialogInterface, which ->
            mAuth.signOut()
            startActivity(Intent(this, LoginUser::class.java))
        }
        //performing cancel action
        builder.setNeutralButton("No"){dialogInterface , which ->

        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }


    fun getGreeting(){
        val c: Calendar = Calendar.getInstance()
        val timeOfDay: Int = c.get(Calendar.HOUR_OF_DAY)
        if (timeOfDay >= 0 && timeOfDay < 12) {
            txt_greet.setText("Good Morning")
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            txt_greet.setText("Good Afternoon")
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            txt_greet.setText("Good Evening")
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            txt_greet.setText("Good Night")
        }
    }

    override fun onStart() {
        super.onStart()
        if (mAuth.currentUser == null) {
            startActivity(Intent(this, RegisterUser::class.java))
        }else {
//            startActivity(Intent(this, DashBoard::class.java))
            uid=mAuth.currentUser!!.uid.toString()
           userDatabaseRequest= FirebaseDatabase.getInstance().getReference("Users")
            checkIsMediumAndGradeSet()
            getGreeting()
        }
    }


    private fun checkIsMediumAndGradeSet() {
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

        val sharedGradeValue = sharedPreferences.getString("standard","")
        if(sharedGradeValue.equals("")){
            startActivity(Intent(this, StandardPage::class.java))
        }
        else{
            standard=sharedGradeValue.toString()

            txt_dash_grade.text=standard
            getData()
//            startActivity(Intent(this, DashBoard::class.java))
        }

    }
}