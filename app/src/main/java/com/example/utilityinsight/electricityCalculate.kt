package com.example.utilityinsight

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class electricityCalculate : AppCompatActivity() {

    val tariffcat = arrayOf("Select Tariff Category", "Domestic", "Industrial", "General")

    private lateinit var etDatePicker: EditText
    private lateinit var etDatePicker2: EditText
    private lateinit var etaccnumber: EditText
    private lateinit var etunits: EditText
    private lateinit var btncalculate: Button
    private lateinit var ans: TextView

    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_electricity_calculate)

        etDatePicker = findViewById(R.id.last_reading)
        etDatePicker2 = findViewById(R.id.current_reading)

        val myCalendar = Calendar.getInstance()
        val datePicker =  DatePickerDialog.OnDateSetListener {view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLable(myCalendar)
            updateLable2(myCalendar)

        }

        etDatePicker.setOnClickListener{
            DatePickerDialog(this, datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        etDatePicker2.setOnClickListener{
            DatePickerDialog(this, datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }


        //implementing spinner
        val spinner = findViewById<Spinner>(R.id.spinner2)
        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tariffcat)
        spinner.adapter = arrayAdapter
        spinner.setSelection(0, false)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(applicationContext, "Tariff Category: " + tariffcat[p2], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(applicationContext, "Please select an item", Toast.LENGTH_SHORT).show()
            }
        }

        //assigning variables to id
        etaccnumber = findViewById(R.id.acc_number)
        etunits = findViewById(R.id.unit_amount)
        btncalculate = findViewById(R.id.calculate_btn)
        ans = findViewById(R.id.result)

        btncalculate.setOnClickListener {
            val accnumber = etaccnumber.text.toString().trim()
            val unitsText = etunits.text.toString().trim()

            // Check if any of the fields are empty
            if (accnumber.isEmpty() || unitsText.isEmpty()) {
                Toast.makeText(applicationContext, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validate units field
            val units: Int
            try {
                units = unitsText.toInt()
            } catch (e: NumberFormatException) {
                Toast.makeText(applicationContext, "Invalid input for units", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            var importcharge = 0f
            var fixedcharge = 0f
            var totalcharge = 0f

            if (units < 24) {
                importcharge = units * 42f
                fixedcharge = 550f
                totalcharge = importcharge + fixedcharge
            } else if (units < 48) {
                importcharge = (42 * 24) + (units - 24) * 50f
                fixedcharge = 550f
                totalcharge = importcharge + fixedcharge
            } else {
                importcharge = (42 * 24) + (50 * 24) + (units - 48) * 75f
                fixedcharge = 800f
                totalcharge = importcharge + fixedcharge
            }

            ans.text = "Import Charge: $importcharge\nFixed Charge: $fixedcharge\nTotal Bill Amount: $totalcharge"
        }

        /*
        val userid = UUID.randomUUID().toString()
        val myspinner = spinner.selectedItem.toString().trim()
        val accnumber = etaccnumber.text.toString().trim()
        val days = etdays.text.toString().trim()
        val units = etunits.text.toString().trim()

        val userMap = hashMapOf(
            "User ID" to userid,
            "Tariff Category" to myspinner,
            "Account Number" to accnumber,
            "No of days" to days,
            "No of units" to units,
        )

        db.collection("eCalculations").document(userid).set(userMap)
            .addOnSuccessListener {
                etaccnumber.text.clear()
                etdays.text.clear()
                etunits.text.clear()
                progressBar.visibility = View.INVISIBLE
                Toast.makeText(this, "Calculation Succeeded", Toast.LENGTH_SHORT).show()
                val i = Intent(this, electricityCalculate::class.java)
                startActivity(i)
                finish()
            }*/
        }
    private fun updateLable(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK )
        etDatePicker.setText(sdf.format(myCalendar.time))
    }

    private fun updateLable2(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK )
        etDatePicker2.setText(sdf.format(myCalendar.time))
    }

}
