package com.example.educationapp.userPages

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.educationapp.R
import com.example.educationapp.adapter.ChapterAdapter
import com.example.educationapp.models.Chapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chapter_page.*
import kotlinx.android.synthetic.main.activity_user_profile.backBtn


class ChapterPage : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth

    private val sharedPrefFile = "StandardPhref"
    var standard:String?=""
    var Subject:String?=""
    var SubjectName:String?=""

    private lateinit var linearLayoutManager: LinearLayoutManager
    var recyclerView : RecyclerView?=null
    var chapterAdapter  : ChapterAdapter?=null
    var user: FirebaseUser?=null
    var databaseRequest: DatabaseReference?=null
    var chapterList:MutableList<Chapter>?=null



    private fun getData() {
        // requestList=null
//        var query  = databaseRequest?.child(user?.uid.toString())

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val list = ArrayList<Request>()
                if(dataSnapshot!!.exists()){
                    chapterList!!.clear()
                    for (e in dataSnapshot.children){
//                        val post = e.getValue(Request::class.java)
                        val gradeSubjectAvailable=e.child("standard").getValue().toString()
//                        val id=e.child("id").getValue().toString()
                        val chapterName=e.child("name").getValue().toString()
                        val subjectName=e.child("subject").getValue().toString()

                        //getting no of topics of chapter
                        if(gradeSubjectAvailable.contains(standard!!)&&subjectName.equals(Subject!!)){
                            //add this to subject list
                            var item= Chapter( gradeSubjectAvailable, chapterName,  subjectName)
                            chapterList?.add(item!!)

                        }
                    }
                    Log.e("f","finish")
                    chapterAdapter= ChapterAdapter(chapterList!!,this@ChapterPage)
                    Log.e("r",chapterList.toString())
                    recyclerView?.adapter=chapterAdapter
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
        setContentView(R.layout.activity_chapter_page)
        supportActionBar!!.hide()

        mAuth = FirebaseAuth.getInstance()
        Subject=intent.getStringExtra("id")
        SubjectName=intent.getStringExtra("subjectName")
        txt_subject_name.setText(SubjectName)

        linearLayoutManager = LinearLayoutManager(this)
        recyclerView=findViewById<RecyclerView>(R.id.recyclerView_chapters)
        recyclerView?.layoutManager=linearLayoutManager
        recyclerView?.setHasFixedSize(true)

        chapterList= mutableListOf()
        databaseRequest= FirebaseDatabase.getInstance().getReference("Chapters");


        backBtn.setOnClickListener {
            this.onBackPressed();
        }
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
            standard=sharedGradeValue.toString()

            getData()
//            startActivity(Intent(this, DashBoard::class.java))
        }

    }
}