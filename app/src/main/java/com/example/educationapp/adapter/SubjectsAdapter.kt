package com.example.educationapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.educationapp.R
import com.example.educationapp.models.Subject
import com.example.educationapp.userPages.ChapterPage


class SubjectsAdapter(private val subjectsList:MutableList<Subject>, val context: Context) : RecyclerView.Adapter<SubjectsAdapter.MyHolder>() {

    var imgList:List<Int>?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val itemview= LayoutInflater.from(parent.context).inflate(R.layout.row_subject,parent,false)
        imgList= listOf(R.drawable.subject1,R.drawable.subject2,R.drawable.subject3,R.drawable.subject4,R.drawable.subject5)
        return MyHolder(itemview)
    }

    override fun getItemCount()=subjectsList.size

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val currentItem =subjectsList[position]
        if(imgList!!.get(position) !=null){
            holder.img.setImageResource(imgList!!.get(position))
        }
        else{
            holder.img.setImageResource(R.drawable.sampleimg)
        }
        holder.name.text=currentItem.name

        holder.img.setOnClickListener {
            //set id to medium shared phref
            val intent = Intent(context, ChapterPage::class.java)
            intent.putExtra("id",currentItem.id.toString())
            intent.putExtra("subjectName",currentItem.name.toString())
            context.startActivity(intent)
        }
    }


    class MyHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        var name=view.findViewById<TextView>(R.id.txt_meidum_name)
        var img=view.findViewById<ImageView>(R.id.img_medium)
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