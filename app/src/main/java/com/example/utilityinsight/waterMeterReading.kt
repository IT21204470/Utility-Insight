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
    private lateinit var etPreviousMeterReading: EditText
    private lateinit var etCurrentReading: EditText
    private lateinit var etNumberOfUnits: EditText
    private lateinit var btnCalculate: Button

    private var db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water_meter_reading)

        etPreviousMeterReading = findViewById(R.id.previous_meter_read)
        etCurrentReading = findViewById(R.id.current_read_date)
        etNumberOfUnits = findViewById(R.id.number_of_units)
        btnCalculate = findViewById(R.id.txt_calculate1)

        btnCalculate.setOnClickListener {

            val sPreviousMeterReading = etPreviousMeterReading.toString().trim()
            val sCurrentReading = etCurrentReading.toString().trim()
            val sNumberOfUnits = etNumberOfUnits.toString().trim()

            val userMap = hashMapOf(
                "previousMeterReading" to sPreviousMeterReading,
                "currentReading" to sCurrentReading,
                "numberOfunits" to sNumberOfUnits
            )

            val userId = FirebaseAuth.getInstance().currentUser!!.uid

            db.collection("user").document(userId).set(userMap)
                .addOnSuccessListener {
                    Toast.makeText(this, "Successfully Added!", Toast.LENGTH_SHORT).show()
                    etPreviousMeterReading.text.clear()
                    etCurrentReading.text.clear()
                    etNumberOfUnits.text.clear()
                }
                .addOnFailureListener {
                    Toast.makeText(this,"Failed!", Toast.LENGTH_SHORT).show()

                }
        }
    }
}