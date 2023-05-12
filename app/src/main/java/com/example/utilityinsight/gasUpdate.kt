package com.example.utilityinsight

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class gasUpdate : AppCompatActivity() {

    // declare the views and data source
    private lateinit var gaccnumber: EditText
    private lateinit var gdays: EditText
    private lateinit var gunits: EditText
    private lateinit var btnSub:Button
    private lateinit var userID:TextView

    private lateinit var newDate: EditText
    private lateinit var totall: TextView

    private var db = Firebase.firestore
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gas_update)

        val backButton = findViewById<ImageButton>(R.id.ug_cal_page_back_btn)
        backButton.setOnClickListener {
            val intent = Intent(this, gas_home::class.java)
            startActivity(intent)
        }


        //initializing variables
        gaccnumber = findViewById(R.id.ug_acc_number)
        gdays = findViewById(R.id.ug_number_of_days)
        gunits = findViewById(R.id.ug_number_of_units)
        btnSub = findViewById(R.id.ug_home_store)
        userID = findViewById(R.id.uid)
        newDate = findViewById(R.id.ug_number_of_units2)
        totall = findViewById(R.id.textView8)


        gaccnumber.text = intent.getStringExtra("AccountNo").toString().toEditable()
        gdays.text = intent.getStringExtra("Date").toString().toEditable()
        gunits.text = intent.getStringExtra("Units").toString().toEditable()
        userID.text = intent.getStringExtra("uid").toString().toEditable()
        newDate.text = intent.getStringExtra("Days").toString().toEditable()


        btnSub.setOnClickListener {

            val accountNumber = gaccnumber.text.toString().trim()
            val numberOfDays = gdays.text.toString().trim()
            val numberOfUnits = gunits.text.toString().trim()

            val gasupdateMap = mapOf(
                "AccountNo" to accountNumber,
                "Days" to numberOfDays,
                "Units" to numberOfUnits,
            )

            db.collection("gascalculate").document(userID.text.toString()).update(gasupdateMap)

            Toast.makeText(this,"Successfully Edited", Toast.LENGTH_SHORT).show()

            val i = Intent(this, gasEntries::class.java)
            startActivity(i)
            finish()
        }

    }


    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
}