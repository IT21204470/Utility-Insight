package com.example.utilityinsight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class waterMeterReading : AppCompatActivity() {
    private lateinit var etWPreviousRead: EditText
    private lateinit var etWCurrentRead: EditText
    private lateinit var etWNumberOfUnits: EditText
    private lateinit var btnCalculate: Button

    private var db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water_meter_reading)

        etWPreviousRead = findViewById(R.id.previous_meter_read)
        etWCurrentRead = findViewById(R.id.current_read_date)
        etWNumberOfUnits = findViewById(R.id.number_of_units)
        btnCalculate = findViewById(R.id.txt_calculate1)

        btnCalculate.setOnClickListener {

            val sPreviousMeterRead = etWPreviousRead.toString().trim()
            val sCurrentRead = etWCurrentRead.toString().trim()
            val sNumberOfUnits = etWNumberOfUnits.toString().trim()

            val userMap = hashMapOf(
                "previousMeterRead" to sPreviousMeterRead,
                "currentRead" to sCurrentRead,
                "numberOfunits" to sNumberOfUnits
            )

            val userId = FirebaseAuth.getInstance().currentUser!!.uid

            db.collection("user").document(userId).set(userMap)
                .addOnSuccessListener {
                    Toast.makeText(this, "Successfully Added!", Toast.LENGTH_SHORT).show()
                    etWPreviousRead.text.clear()
                    etWCurrentRead.text.clear()
                    etWNumberOfUnits.text.clear()
                }
                .addOnFailureListener {
                    Toast.makeText(this,"Failed!", Toast.LENGTH_SHORT).show()

                }
        }
    }
}