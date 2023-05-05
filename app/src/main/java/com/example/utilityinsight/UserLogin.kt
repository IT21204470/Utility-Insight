package com.example.utilityinsight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.common.base.MoreObjects.ToStringHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserLogin : AppCompatActivity() {

    private lateinit var btnLogin:Button
    private lateinit var btnForgot:Button
    private lateinit var btnReg:Button
    private lateinit var edtEmail:EditText
    private lateinit var edtPassword:EditText
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)

        auth = Firebase.auth

        btnLogin = findViewById(R.id.btnLogin)
        btnReg = findViewById(R.id.btnRegister)
        edtEmail = findViewById(R.id.getUserName)
        edtPassword = findViewById(R.id.getPassword)
        btnForgot = findViewById(R.id.btnforgot)

        btnLogin.setOnClickListener {

            var lemail = edtEmail.text.toString().trim()
            var lpassword =edtPassword.text.toString().trim()

            auth.signInWithEmailAndPassword(lemail,lpassword)
                .addOnCompleteListener(this){task->
                  if(task.isSuccessful){

                      val verification = auth.currentUser?.isEmailVerified

                      if(verification == true) {
                          val user = auth.currentUser
                          updateUI()
                      }
                      else{
                          Toast.makeText(this, "Please Verify Your Email", Toast.LENGTH_SHORT).show()
                      }
                    
                }else{
                    
                    //if sign in fail
                    Toast.makeText(baseContext, "Authentication Failed", Toast.LENGTH_SHORT).show()
                }
            
            
            }

        }

        btnReg.setOnClickListener {

            val intent = Intent(this, UserRegistration::class.java)
            startActivity(intent)
        }

        btnForgot.setOnClickListener {

            val intent = Intent(this, ForgotPassword::class.java)
            startActivity(intent)

        }

    }

    private fun updateUI() {

        val intent = Intent(this, MainMenu::class.java)
        startActivity(intent)
    }

    /*public override fun onStart() {
        super.onStart()

        //check if user is logged in and update ui accordingly
        val currentUser = auth.currentUser
        if(currentUser!=null){
            updateUI()
        }

    }*/
}