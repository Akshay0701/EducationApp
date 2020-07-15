package com.example.educationapp.userPages

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.educationapp.R
import com.example.educationapp.adapter.ChapterAdapter
import com.example.educationapp.adapter.TopicAdapter
import com.example.educationapp.models.Chapter
import com.example.educationapp.models.Topics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chapter_sub_topic.*
import kotlinx.android.synthetic.main.activity_chapter_sub_topic.backBtn
import kotlinx.android.synthetic.main.activity_user_profile.*

class ChapterSubTopic : AppCompatActivity() {

    var topicsList:MutableList<Topics>?=null
    var recyclerView : RecyclerView?=null
    var topicAdapter  : TopicAdapter?=null
    lateinit var mAuth: FirebaseAuth
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val sharedPrefFile = "StandardPhref"
    var Standard:String?=""
    var chapterName:String?=""
    var SubjectName:String?=""


    var databaseRequest: DatabaseReference?=null

    private fun checkIsMediumAndGradeSet() {
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val sharedGradeValue = sharedPreferences.getString("standard","")
        if(sharedGradeValue.equals("")){
            startActivity(Intent(this, StandardPage::class.java))
        }
        else{
            Standard=sharedGradeValue.toString()
            getData()
//            startActivity(Intent(this, DashBoard::class.java))
        }

    }

    private fun getData() {
        // requestList=null
//        var query  = databaseRequest?.child(user?.uid.toString())
        topicsList!!.clear()
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val list = ArrayList<Request>()
                if(dataSnapshot!!.exists()){
                    for (e in dataSnapshot.children){
                        val standardN=e.child("standard").getValue().toString()
                        val chapterN=e.child("chapter").getValue().toString()
                        val subjectN=e.child("subject").getValue().toString()
                        //getting no of topics of chapter
                        var topicName =e.child("name").getValue().toString()
                        var pdfUrl =e.child("pdfUrl").getValue().toString()
                        var type =e.child("type").getValue().toString()
                        var videoUrl =e.child("videoUrl").getValue().toString()
                        if(standardN.contains(Standard!!)&&subjectN.contains(SubjectName!!)&&chapterN.contains(chapterName!!)){
                            val topic =Topics( chapterN,standardN,topicName,pdfUrl,videoUrl,subjectN,type)
                            topicsList!!.add(topic)
                            topicAdapter= TopicAdapter(topicsList!!,this@ChapterSubTopic)
                            recyclerView?.adapter=topicAdapter
                            Log.e("s",topicsList.toString())
//                            chapterList?.add(item!!)

                        }
                    }
//                    Log.e("f","finish")
//                    chapterAdapter= ChapterAdapter(chapterList!!,this@ChapterPage)
//                    Log.e("r",chapterList.toString())
//                    Log.e("details",chapterList!!.get(0).topicsList.toString())
//                    recyclerView?.adapter=chapterAdapter
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        databaseRequest?.addValueEventListener(postListener)

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chapter_sub_topic)
        mAuth = FirebaseAuth.getInstance()
        chapterName = intent.getStringExtra("chapterName")
        SubjectName = intent.getStringExtra("subjectName")
        supportActionBar!!.hide()
        databaseRequest= FirebaseDatabase.getInstance().getReference("Topics");
        //init
        topicsList= mutableListOf()
        txt_chaptername.text=chapterName



        linearLayoutManager = LinearLayoutManager(this)
        recyclerView=findViewById<RecyclerView>(R.id.recyclerView_Topics)
        recyclerView?.layoutManager=linearLayoutManager
        recyclerView?.setHasFixedSize(true)

        //setting view
//        topicAdapter= TopicAdapter(topicsList!!,this@ChapterSubTopic)
        recyclerView?.adapter=topicAdapter

        backBtn.setOnClickListener {
            this.onBackPressed();
        }
        }
}