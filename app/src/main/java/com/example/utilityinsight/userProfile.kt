package com.example.utilityinsight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class userProfile : AppCompatActivity() {

    private lateinit var viewName:TextView
    private lateinit var viewUser:TextView
    private lateinit var viewEmail:TextView
    private lateinit var btnSignOut :Button
    private lateinit var btnDelete:Button

    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        viewName = findViewById(R.id.viewName)
        viewUser = findViewById(R.id.viewUser)
        viewEmail = findViewById(R.id.viewEmail)
        btnSignOut = findViewById(R.id.btnSignOut)
        btnDelete = findViewById(R.id.btnDelete)

        val userID = FirebaseAuth.getInstance().currentUser!!.uid

        val ref = db.collection("user").document(userID)
        ref.get().addOnSuccessListener{
            if(it != null){
                val name = it.data?.get("Name")?.toString()
                val username = it.data?.get("UserName")?.toString()
                val email = it.data?.get("Email").toString()
            }
        }

            .addOnFailureListener{
                Toast.makeText(this, "Data Fetching Failed", Toast.LENGTH_LONG).show()
            }

        btnSignOut.setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(this, UserLogin::class.java)
            startActivity(intent)
        }

        btnDelete.setOnClickListener {

            val mapDelete = mapOf(
                "Name" to FieldValue.delete()
            )

            db.collection("user").document(userID).update(mapDelete)
                .addOnSuccessListener{
                    Toast.makeText(this, "Successfully Deleted", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, UserRegistration::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Deleting Failed", Toast.LENGTH_SHORT).show()
                }
        }

    }
}