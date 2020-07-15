package com.example.educationapp.userPages

import android.R.attr.phoneNumber
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.educationapp.R
import com.example.educationapp.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register_user.*
import java.util.concurrent.TimeUnit


class RegisterUser : AppCompatActivity() {

    lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var mAuth: FirebaseAuth
    var verificationId = ""
    val firebaseAuthSettings:FirebaseAuthSettings?=null

    private var mDatabase: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)
        supportActionBar!!.hide()
        registerlinear.visibility= View.VISIBLE
        verficationlinear.visibility=View.GONE
        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference("Users")
        button_register.setOnClickListener {
            validateData();
        }
        botton_Activate.setOnClickListener {
            authenticate()
        }
        txt_Register.setOnClickListener{
            startActivity(Intent(this, LoginUser::class.java))
        }

    }

    private fun validateData() {
    var name :String=editText_name.text.toString();    var area :String=editText_area.text.toString()
        var phone :String=editText_phone.text.toString();  var birthDate :String=editText_birthDate.text.toString()
        if(!(name != "" && area != "" && phone != "" && birthDate != "")){
            //data not provided
            toast("fill all details")
        }
        else{
            verficationlinear.visibility= View.VISIBLE
            registerlinear.visibility=View.GONE
            verify()
        }

    }


    private fun verify () {

        verificationCallbacks()

        val phnNo = "+91"+editText_phone.text.toString()

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phnNo,
            60,
            TimeUnit.SECONDS,
            this,
            mCallbacks
        )
    }

    private fun verificationCallbacks () {
        mCallbacks = object: PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//                progress.visibility = View.INVISIBLE
                //signIn(credential)
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Log.e("error",p0.toString())
            }
            override fun onCodeSent(verfication: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(verfication!!, p1!!)
                verificationId = verfication.toString()
//                progress.visibility = View.INVISIBLE
            }

        }
    }


    private fun signIn (credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener {
                    task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    toast("Logged in Successfully :)")
                    //store data to database
                    var firebaseUser:FirebaseUser= mAuth.currentUser!!
                    var uid:String=firebaseUser.uid
                    var name :String=editText_name.text.toString();    var area :String=editText_area.text.toString()
                    var phone :String=editText_phone.text.toString();  var birthDate :String=editText_birthDate.text.toString()
                    var userData=User(uid,name,area,phone,birthDate)
                    mDatabase!!.child(uid!!).setValue(userData)
                    //goto main screen
                    startActivity(Intent(this, DashBoard::class.java))
                }
            }
    }

    private fun authenticate () {

        val verifiNo = editText_verficationKey.text.toString()

        val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(verificationId, verifiNo)

        signIn(credential)

    }

    private fun toast (msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}