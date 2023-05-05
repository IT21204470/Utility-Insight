package com.example.utilityinsight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class user_feedback : AppCompatActivity() {

    private lateinit var edtEmail:EditText
    private lateinit var edtTxt:EditText
    private lateinit var btnFeedback:Button

    private var fdb = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_feedback)

        edtEmail = findViewById(R.id.editEmailAddress)
        edtTxt = findViewById(R.id.editTextMultiLine)
        btnFeedback = findViewById(R.id.btnSubmit)



        btnFeedback.setOnClickListener{

            val email = edtEmail.text.toString().trim()
            val fdBack = edtTxt.text.toString().trim()

            val feedMap = hashMapOf(

                "Email" to email,
                "Feedback" to fdBack

            )

            val fdID = FirebaseAuth.getInstance().currentUser!!.uid

            fdb.collection("feedback").document(fdID).set(feedMap)
                .addOnSuccessListener{
                    Toast.makeText(this,"Feddback Sent", Toast.LENGTH_SHORT).show()
                    edtEmail.text.clear()
                    edtTxt.text.clear()
                }

                .addOnFailureListener{
                    Toast.makeText(this,"Try Again", Toast.LENGTH_SHORT).show()
                }
        }


    }


}