package com.example.educationapp.userPages

import android.app.ProgressDialog
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import android.webkit.WebView
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.example.educationapp.R
import kotlinx.android.synthetic.main.activity_view_study.*


class ViewStudyActivity : AppCompatActivity() {

    private var bar: ProgressDialog? = null
    private val path = "https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4"
    private var ctlr: MediaController? = null
    private var videoView: VideoView? = null

    var type:String?=null
    var url:String ?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_study)
        supportActionBar!!.hide()
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);

        type=intent.getStringExtra("type")
        if(type.equals("pdf")){
            //set pdf here
            setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            val webview = WebView(this)
            setContentView(webview)
            webview.settings.javaScriptEnabled = true
            url=intent.getStringExtra("pdfUrl")
//            var pdf = "http://www.adobe.com/devnet/acrobat/pdfs/pdf_open_parameters.pdf";
            webview.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + url)
        }
        else if(type.equals("video")){
            //set video here
            setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            url=intent.getStringExtra("videoUrl")
            bar= ProgressDialog(this)
            bar!!.setTitle("Connecting server")
            bar!!.setMessage("Please Wait... ")
            bar!!.setCancelable(false);
            bar!!.show();
            if(bar!!.isShowing()) {
                videoView = findViewById(R.id.videoView);
                var uri = Uri.parse(url)
                videoView!!.setVideoURI(uri)
                videoView!!.start();
                ctlr =  MediaController(this)
                ctlr!!.setMediaPlayer(videoView)
                videoView!!.setMediaController(ctlr)
                videoView!!.requestFocus();
            }
            bar!!.dismiss();
            backBtn.setOnClickListener {
                this.onBackPressed();
            }

        }
        }

}