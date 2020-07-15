package com.example.educationapp.adapter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.educationapp.R
import com.example.educationapp.models.Standard
import com.example.educationapp.userPages.DashBoard

class StandardAdapter(private val standardList:MutableList<Standard>, val context: Context) : RecyclerView.Adapter<StandardAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val itemview= LayoutInflater.from(parent.context).inflate(R.layout.row_grade,parent,false)
        return MyHolder(itemview)
    }

    override fun getItemCount()=standardList.size

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val currentItem =standardList[position]
        holder.name.text=currentItem.name

        holder.name.setOnClickListener {
            //set id to medium shared phref
            val sharedPrefFile = "StandardPhref"
            val sharedPreferences: SharedPreferences = context.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor =  sharedPreferences.edit()
            editor.putString("standard",currentItem.id.toString())
            editor.apply()
            editor.commit()
            val intent = Intent(context, DashBoard::class.java)
            context.startActivity(intent)
        }
    }


    class MyHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        var name=view.findViewById<TextView>(R.id.txt_grade_name)
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