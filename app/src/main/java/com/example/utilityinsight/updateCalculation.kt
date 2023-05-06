package com.example.utilityinsight

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_calculation)

        etDatePicker = findViewById(R.id.ulast_reading)
        etDatePicker2 = findViewById(R.id.ucurrent_reading)

        val myCalendar = Calendar.getInstance()
        val datePicker =  DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLable(myCalendar)
        }

        val myCalendar2 = Calendar.getInstance()
        val datePicker2 =  DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
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

        //assigning variables to id
        userID = findViewById(R.id.textView)
        etaccnumber = findViewById(R.id.uacc_number)
        etunits = findViewById(R.id.uunit_amount)
        btncalculate = findViewById(R.id.ucalculate_btn)
        ans = findViewById(R.id.uresult)
        btnstore = findViewById(R.id.ustore_btn)
        progressBar2 = findViewById(R.id.uprogressBar2)
        finaltot = findViewById(R.id.utextView6)
        etDatePicker = findViewById(R.id.ulast_reading)
        etDatePicker2 = findViewById(R.id.ucurrent_reading)

        userID.visibility = View.INVISIBLE
        ans.visibility = View.INVISIBLE
        finaltot.visibility = View.INVISIBLE
        progressBar2.visibility = View.INVISIBLE
        btnstore.visibility = View.INVISIBLE

        etaccnumber.text = intent.getStringExtra("accNumber").toString().toEditable()
        etDatePicker.text = intent.getStringExtra("lastDate").toString().toEditable()
        etDatePicker2.text = intent.getStringExtra("currentDate").toString().toEditable()
        etunits.text = intent.getStringExtra("noOfUnits").toString().toEditable()
        finaltot.text = intent.getStringExtra("billAmount").toString().toEditable()
        userID.text = intent.getStringExtra("uID").toString().toEditable()


        btncalculate.setOnClickListener {

            val accnumber = etaccnumber.text.toString().trim()
            val unitsText = etunits.text.toString().trim()
            val lastReading = etDatePicker.text.toString().trim()
            val currentReading = etDatePicker2.text.toString().trim()

            if (accnumber.isEmpty() || unitsText.isEmpty() || lastReading.isEmpty() || currentReading.isEmpty()) {
                Toast.makeText(applicationContext, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
                ans.visibility = View.INVISIBLE
                finaltot.visibility = View.INVISIBLE
                btnstore.visibility = View.INVISIBLE
                return@setOnClickListener
            }

            if (!isValidAccountNumber(accnumber)) {
                Toast.makeText(applicationContext, "Account number should have 10 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            ans.visibility = View.VISIBLE
            btnstore.visibility = View.VISIBLE

            val units: Int
            try {
                units = unitsText.toInt()
            } catch (e: NumberFormatException) {
                Toast.makeText(applicationContext, "Invalid input for units", Toast.LENGTH_SHORT).show()
                ans.visibility = View.INVISIBLE
                finaltot.visibility = View.INVISIBLE
                btnstore.visibility = View.INVISIBLE
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
                ans.visibility = View.INVISIBLE
                finaltot.visibility = View.INVISIBLE
                btnstore.visibility = View.INVISIBLE
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
            finaltot.text = "$totalcharge LKR"
        }

        btnstore.setOnClickListener{
            ans.visibility = View.INVISIBLE
            progressBar2.visibility = View.VISIBLE

            val userIID = userID.text.toString()
            val accnumber = etaccnumber.text.toString().trim()
            val units = etunits.text.toString().trim()
            val lastreading = etDatePicker.text.toString().trim()
            val currentreading = etDatePicker2.text.toString().trim()
            val calculation = finaltot.text.toString().trim()

            val userupdateMap = mapOf(
                "accountNumber" to accnumber,
                "lastDate" to lastreading,
                "currentDate" to currentreading,
                "units" to units,
                "totalAmount" to calculation
            )

            db.collection("eCalculations").document(userIID).update(userupdateMap)
                .addOnSuccessListener {
                    etaccnumber.text.clear()
                    etDatePicker.text.clear()
                    etDatePicker2.text.clear()
                    etunits.text.clear()

                    Toast.makeText(this, "Record Successfully Updated", Toast.LENGTH_SHORT).show()
                    val i = Intent(this, electricityEntries::class.java)
                    startActivity(i)
                    finish()
                }
        }

    }
    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

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

    private fun isValidAccountNumber(accountNumber: String): Boolean {
        val regex = "\\d{10}".toRegex()
        return accountNumber.matches(regex)
    }

}