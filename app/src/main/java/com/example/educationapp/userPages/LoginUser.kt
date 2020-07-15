package com.example.educationapp.userPages

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.educationapp.R
import com.example.educationapp.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_dash_board.*
import kotlinx.android.synthetic.main.activity_login_user.*
import kotlinx.android.synthetic.main.activity_login_user.botton_Activate
import kotlinx.android.synthetic.main.activity_login_user.editText_phone
import kotlinx.android.synthetic.main.activity_login_user.editText_verficationKey
import kotlinx.android.synthetic.main.activity_login_user.verficationlinear
import kotlinx.android.synthetic.main.activity_register_user.*
import java.util.concurrent.TimeUnit

class LoginUser : AppCompatActivity() {

    lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var mAuth: FirebaseAuth
    var verificationId = ""
    val firebaseAuthSettings: FirebaseAuthSettings?=null

    private var mDatabase: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_user)
        mDatabase = FirebaseDatabase.getInstance().getReference("Users")
        supportActionBar!!.hide()
        mAuth=FirebaseAuth.getInstance()
        button_login.setOnClickListener {
            login()
        }

        botton_Activate.setOnClickListener {
            authenticate()
        }


        txt_login.setOnClickListener {
            startActivity(Intent(this, RegisterUser::class.java))
        }

    }

    private fun login() {
        var phoneTxt:String =editText_phone.text.toString()
        if(phoneTxt==""){
           toast("fill no")
        }else{
            verficationlinear.visibility= View.VISIBLE
            loginLinear.visibility=View.GONE
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
//                signIn(credential)
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Log.e("error",p0.toString())
            }
            override fun onCodeSent(verfication: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(verfication!!, p1!!)
                verificationId = verfication.toString()
//                authenticate()
            }

        }
    }
    private fun authenticate () {

        val verifiNo =editText_verficationKey.text.toString()

        val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(verificationId, verifiNo)

        signIn(credential)

    }


    private fun signIn (credential: PhoneAuthCredential) {
        Log.e("in1","in1")
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener {
                    task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    Log.e("in2","in2")
                    toast("Logged in Successfully :)")
                    //store data to database
                    var firebaseUser:FirebaseUser= mAuth.currentUser!!
                    var uid:String=firebaseUser.uid
                    var userData:User?=null
                    val userListener = object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if(dataSnapshot!!.exists()){
                                for (e in dataSnapshot.children){
                                    val name=e.child("name").getValue().toString()
                                    val id=e.child("uid").getValue().toString()
                                    val area=e.child("area").getValue().toString()
                                    val phone=e.child("phone").getValue().toString()
                                    val dateofbrith=e.child("dateofbrith").getValue().toString()
                                    if(id.equals(uid)){
                                        userData=User(id,name,area,phone,dateofbrith)
                                        mDatabase!!.child(uid!!).setValue(userData)
                                    }
                                }
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            // Getting Post failed, log a message
                            Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
                        }
                    }
                    mDatabase?.addValueEventListener(userListener)
                    //goto main screen
                    startActivity(Intent(this, DashBoard::class.java))
                }
                else{
                    Log.e("in2",task.exception.toString())
                }
            }
    }



    private fun toast (msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}