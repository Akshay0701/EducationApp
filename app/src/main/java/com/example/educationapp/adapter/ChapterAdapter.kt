package com.example.educationapp.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.educationapp.R
import com.example.educationapp.models.Chapter
import com.example.educationapp.models.Subject
import com.example.educationapp.userPages.ChapterPage
import com.example.educationapp.userPages.ChapterSubTopic
import java.io.Serializable

class ChapterAdapter(private val chapterList:MutableList<Chapter>, val context: Context) : RecyclerView.Adapter<ChapterAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val itemview= LayoutInflater.from(parent.context).inflate(R.layout.row_chapter,parent,false)
        return MyHolder(itemview)
    }

    override fun getItemCount()=chapterList.size

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val currentItem =chapterList[position]
        holder.name.text=currentItem.name
        holder.name.setOnClickListener {
            //set id to medium shared phref
            val intent = Intent(context, ChapterSubTopic::class.java)
//            intent.putExtra("id",currentItem.id.toString())
            intent.putExtra("chapterName",currentItem.name)
            intent.putExtra("subjectName",currentItem.subject)
            context.startActivity(intent)
        }
    }


    class MyHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        var name=view.findViewById<TextView>(R.id.txt_chap_name)
        init {
            v.setOnClickListener(this)
        }
        override fun onClick(v: View) {

        }



        companion object {
            private val PHOTO_KEY = "PHOTO"
        }
    }



}