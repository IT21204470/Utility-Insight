package com.example.utilityinsight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.UUID

class addConnection : AppCompatActivity() {

    private lateinit var etname: EditText
    private lateinit var etnumber: EditText
    private lateinit var etpremises: EditText
    private lateinit var btnsave: Button
    private lateinit var btndecline: Button
    private lateinit var progressBar: ProgressBar

    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_connection)

        //assigning variables to id
        etname = findViewById(R.id.acc_name)
        etnumber = findViewById(R.id.acc_no)
        etpremises = findViewById(R.id.premisesID)
        btnsave = findViewById(R.id.save_btn)
        btndecline = findViewById(R.id.decline_btn)
        progressBar = findViewById(R.id.wProgressBar)

        progressBar.visibility = View.INVISIBLE

        btnsave.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            val userid = UUID.randomUUID().toString()
            val accountName = etname.text.toString().trim()
            val accountNo = etnumber.text.toString().trim()
            val preID = etpremises.text.toString().trim()

            val userMap = hashMapOf(
                "User ID" to userid,
                "Account Name" to accountName,
                "Account Number" to accountNo,
                "Premises ID" to preID,
            )


            db.collection("accounts").document(userid).set(userMap)
                .addOnSuccessListener {
                    etname.text.clear()
                    etnumber.text.clear()
                    etpremises.text.clear()
                    progressBar.visibility = View.INVISIBLE
                    Toast.makeText(this, "Account added successfully", Toast.LENGTH_SHORT).show()
                    val i = Intent(this, electricityHome::class.java)
                    startActivity(i)
                    finish()
                }
        }
    }
}