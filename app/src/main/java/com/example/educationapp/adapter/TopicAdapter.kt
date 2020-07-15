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
import com.example.educationapp.models.Topics
import com.example.educationapp.userPages.ViewStudyActivity


class TopicAdapter(private val topicList:MutableList<Topics>, val context: Context) : RecyclerView.Adapter<TopicAdapter.MyHolder>() {

    var imgVideoList:List<Int>?=null
    var imgPdfList:List<Int>?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val itemview= LayoutInflater.from(parent.context).inflate(R.layout.row_subject,parent,false)
        imgVideoList= listOf(R.drawable.videotopic1,R.drawable.videotopic2)
        imgPdfList= listOf(R.drawable.pdftopic1,R.drawable.pdftopic2)
        return MyHolder(itemview)
    }

    override fun getItemCount()=topicList.size

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val currentItem =topicList[position]
        val rnds = (0..1).random()
        if(currentItem.type.equals("pdf")){
            holder.img.setImageResource(imgPdfList!!.get(rnds))
        }
        else if (currentItem.type.equals("video")){
            holder.img.setImageResource(imgVideoList!!.get(rnds))
        }
        else{
            holder.img.setImageResource(R.drawable.sampleimg)
        }
        holder.name.text=currentItem.name

        holder.img.setOnClickListener {
            //set id to medium shared phref
            if(currentItem.type.equals("pdf")){
                val intent = Intent(context, ViewStudyActivity::class.java)
                intent.putExtra("type",currentItem.type.toString())
                intent.putExtra("pdfUrl",currentItem.pdfUrl.toString())
                context.startActivity(intent)
            }
            else if(currentItem.type.equals("video")){
                val intent = Intent(context, ViewStudyActivity::class.java)
                intent.putExtra("type",currentItem.type.toString())
                intent.putExtra("videoUrl",currentItem.videoUrl.toString())
                context.startActivity(intent)
            }
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