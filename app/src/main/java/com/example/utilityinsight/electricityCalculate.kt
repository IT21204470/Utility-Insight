package com.example.utilityinsight

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class electricityCalculate : AppCompatActivity() {

    val tariffcat = arrayOf("Select Tariff Category", "Domestic", "Industrial", "General")

    private lateinit var etaccnumber: EditText
    private lateinit var etDatePicker: EditText
    private lateinit var etDatePicker2: EditText
    private lateinit var etunits: EditText
    private lateinit var btncalculate: Button
    private lateinit var ans: TextView
    private lateinit var btnstore: Button
    private lateinit var progressBar2: ProgressBar

    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_electricity_calculate)

        val backButton = findViewById<ImageButton>(R.id.back_btn)
        backButton.setOnClickListener {
            val intent = Intent(this, electricityHome::class.java)
            startActivity(intent)
        }

        etDatePicker = findViewById(R.id.last_reading)
        etDatePicker2 = findViewById(R.id.current_reading)

        val myCalendar = Calendar.getInstance()
        val datePicker =  DatePickerDialog.OnDateSetListener {view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLable(myCalendar)
        }

        val myCalendar2 = Calendar.getInstance()
        val datePicker2 =  DatePickerDialog.OnDateSetListener {view, year, month, dayOfMonth ->
            myCalendar2.set(Calendar.YEAR, year)
            myCalendar2.set(Calendar.MONTH, month)
            myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLable2(myCalendar2)
        }

        etDatePicker.setOnClickListener{
            DatePickerDialog(this, datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        etDatePicker2.setOnClickListener{
            DatePickerDialog(this, datePicker2, myCalendar2.get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH), myCalendar2.get(Calendar.DAY_OF_MONTH)).show()
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
        btnstore = findViewById(R.id.store_btn)
        progressBar2 = findViewById(R.id.progressBar2)

        progressBar2.visibility = View.INVISIBLE
        btnstore.visibility = View.INVISIBLE

        btncalculate.setOnClickListener {

            val accnumber = etaccnumber.text.toString().trim()
            val unitsText = etunits.text.toString().trim()
            val lastReading = etDatePicker.text.toString().trim()
            val currentReading = etDatePicker2.text.toString().trim()

            // Check if any of the fields are empty
            if (accnumber.isEmpty() || unitsText.isEmpty() || lastReading.isEmpty() || currentReading.isEmpty()) {
                Toast.makeText(applicationContext, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
                btnstore.visibility = View.INVISIBLE
                return@setOnClickListener
            }

            btnstore.visibility = View.VISIBLE
            val units: Int
            try {
                units = unitsText.toInt()
            } catch (e: NumberFormatException) {
                Toast.makeText(applicationContext, "Invalid input for units", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Calculate the number of days between the last reading and current reading
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.UK)
            val lastReadingDate: Date
            val currentReadingDate: Date
            try {
                lastReadingDate = dateFormat.parse(lastReading)!!
                currentReadingDate = dateFormat.parse(currentReading)!!
            } catch (e: ParseException) {
                Toast.makeText(applicationContext, "Invalid date format", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val diffInMillis = currentReadingDate.time - lastReadingDate.time
            val diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis)



            //calculations
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

            ans.text = "Bill Period: $diffInDays days\nImport Charge: $importcharge LKR\nFixed Charge: $fixedcharge LKR\nTotal Bill Amount: $totalcharge LKR"
        }


        btnstore.setOnClickListener{
            ans.visibility = View.INVISIBLE
            progressBar2.visibility = View.VISIBLE
            val userid = UUID.randomUUID().toString()
            val myspinner = spinner.selectedItem.toString().trim()
            val accnumber = etaccnumber.text.toString().trim()
            val units = etunits.text.toString().trim()
            val lastreading = etDatePicker.text.toString().trim()
            val currentreading = etDatePicker2.text.toString().trim()
            val calculation = ans.text.toString().trim()

            val userMap = hashMapOf(
                "User ID" to userid,
                "Tariff Category" to myspinner,
                "Account Number" to accnumber,
                "Last Reading Date" to lastreading,
                "Current Reading Date" to currentreading,
                "No of units" to units,
                "Total Amount" to calculation
            )

            db.collection("eCalculations").document(userid).set(userMap)
                .addOnSuccessListener {
                    etaccnumber.text.clear()
                    etunits.text.clear()
                    Toast.makeText(this, "Record Successfully Added", Toast.LENGTH_SHORT).show()
                    val i = Intent(this, electricityEntries::class.java)
                    startActivity(i)
                    finish()
                }
        }

    }
    private fun updateLable(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK )
        etDatePicker.setText(sdf.format(myCalendar.time))
    }

    private fun updateLable2(myCalendar2: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK )
        etDatePicker2.setText(sdf.format(myCalendar2.time))
    }


}