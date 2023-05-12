package com.example.utilityinsight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserRegistration : AppCompatActivity() {

    private lateinit var edtName:EditText
    private lateinit var edtUserName: EditText
    private lateinit var edtEmail:EditText
    private lateinit var edtPassword:EditText
    private lateinit var btnRegistration:Button


    private lateinit var auth : FirebaseAuth
    private var udb = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_registration)

        auth = Firebase.auth

        edtName = findViewById(R.id.edtName)
        edtUserName = findViewById(R.id.edtUserName)
        edtEmail = findViewById(R.id.edtEmail)
        edtPassword = findViewById(R.id.edtPassword)
        btnRegistration = findViewById(R.id.btnSignUp)


        val UserRegistrationValidation = SignupValidation()

        btnRegistration.setOnClickListener {

            val sEmail = edtEmail.text.toString().trim()
            val sPassword = edtPassword.text.toString().trim()
            val sName= edtName.text.toString().trim()
            val sUser = edtUserName.text.toString().trim()

            if(UserRegistrationValidation.validateData(sName,sUser,sEmail,sPassword)){

                auth.createUserWithEmailAndPassword(sEmail,sPassword)
                    .addOnCompleteListener(this){
                            task->
                        if(task.isSuccessful){

                            //Sign in success
                            auth.currentUser?.sendEmailVerification()
                                ?.addOnSuccessListener {
                                    Toast.makeText(this, "Please Verify Email",Toast.LENGTH_SHORT).show()
                                    updateUI()
                                    //passing data to the firestore collection
                                    val user = hashMapOf(

                                        "Email" to sEmail,
                                        "Name" to sName,
                                        "UserName" to sUser,
                                        "Password" to sPassword


                                    )

                                    val userID = FirebaseAuth.getInstance().currentUser!!.uid

                                    udb.collection("user").document(userID).set(user)
                                        .addOnSuccessListener{
                                            Toast.makeText(this,"User Registered", Toast.LENGTH_SHORT).show()
                                            edtEmail.text.clear()
                                            edtName.text.clear()
                                            edtUserName.text.clear()
                                            edtPassword.text.clear()
                                        }

                                        .addOnFailureListener{
                                            Toast.makeText(this,"Try Again", Toast.LENGTH_SHORT).show()
                                        }

                                }
                                ?.addOnFailureListener {
                                    Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                                }

                        }else{

                            //Sign in fails
                            Toast.makeText(
                                baseContext,"Authentication Failed", Toast.LENGTH_SHORT
                            ).show()
                            updateUI()
                        }
                    }

            }
            else{
                Toast.makeText(this, "Fill All The Fields",Toast.LENGTH_LONG).show()
            }

        }


    }

     fun updateUI(){
        val intent = Intent(this, UserLogin::class.java)
        startActivity(intent)
    }
}