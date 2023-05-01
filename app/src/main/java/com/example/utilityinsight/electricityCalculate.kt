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

    val tariffcat = arrayOf("Domestic", "Industrial", "General")

    private lateinit var etDatePicker: EditText
    private lateinit var etDatePicker2: EditText
    private lateinit var etaccnumber: EditText
    private lateinit var etdays: EditText
    private lateinit var etunits: EditText
    private lateinit var btncalculate: Button
    private lateinit var ans: TextView

    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_electricity_calculate)

        val myCalendar = Calendar.getInstance()
        val datePicker =  DatePickerDialog.OnDateSetListener {view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLable(myCalendar)

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
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(applicationContext, "Tariff Category: " + tariffcat[p2], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // Add your code here
                Toast.makeText(applicationContext, "Please select an item", Toast.LENGTH_SHORT).show()
            }
        }

        //assigning variables to id
        etaccnumber = findViewById(R.id.acc_number)
        etDatePicker = findViewById(R.id.last_reading)
        etDatePicker2 = findViewById(R.id.current_reading)
        etunits = findViewById(R.id.unit_amount)
        btncalculate = findViewById(R.id.calculate_btn)
        ans = findViewById(R.id.result)

        btncalculate.setOnClickListener {
            val units: Int = etunits.text.toString().trim().toInt()
            var importcharge = 0
            var fixedcharge = 0
            var totalcharge = 0

                if (units < 24) {
                    importcharge = (units*42)
                    fixedcharge = 550
                    totalcharge = (importcharge + fixedcharge)
                }else if (units < 48) {
                    importcharge = ((42*24) + (units-24)*50)
                    fixedcharge = 550
                    totalcharge = (importcharge + fixedcharge)
                }else {
                    importcharge = ((42*24) + (50*24) + (units-48)*75)
                    fixedcharge = 1400
                    totalcharge = (importcharge + fixedcharge)
                }
            ans.text = ("Import Charge: $importcharge\nFixed Charge: $fixedcharge\nTotal Bill Amount: $totalcharge").toString()
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

}
