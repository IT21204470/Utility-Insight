package com.example.utilityinsight

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class updateCalculation : AppCompatActivity() {

    val tariffcat = arrayOf("Select Tariff Category", "Domestic", "Industrial", "General")

    private lateinit var etaccnumber: EditText
    private lateinit var etDatePicker: EditText
    private lateinit var etDatePicker2: EditText
    private lateinit var etunits: EditText
    private lateinit var btncalculate: Button
    private lateinit var ans: TextView
    private lateinit var btnstore: Button
    private lateinit var progressBar2: ProgressBar
    private lateinit var finaltot: TextView
    private lateinit var userID: TextView

    private var db = Firebase.firestore

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_calculation)

        //assigning variables to id
        userID = findViewById(R.id.textView)
        etaccnumber = findViewById(R.id.uacc_number)
        etunits = findViewById(R.id.uunit_amount)
        btncalculate = findViewById(R.id.ucalculate_btn)
        ans = findViewById(R.id.uresult)
        btnstore = findViewById(R.id.ustore_btn)
        progressBar2 = findViewById(R.id.uprogressBar2)
        finaltot = findViewById(R.id.utextView6)

        ans.visibility = View.INVISIBLE
        finaltot.visibility = View.INVISIBLE
        progressBar2.visibility = View.INVISIBLE
        btnstore.visibility = View.INVISIBLE

        etaccnumber.text = intent.getStringExtra("accNumber").toString().toEditable()
        etunits.text = intent.getStringExtra("noOfUnits").toString().toEditable()
        //etDatePicker.text = intent.getStringExtra("lastDate").toInt().toEditable()
        //etDatePicker2.text = intent.getStringExtra("cDate").toString().toEditable()
        ans.text = intent.getStringExtra("total").toString().toEditable()
        userID.text = intent.getStringExtra("uID").toString().toEditable()


    }
    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

}