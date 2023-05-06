package com.example.utilityinsight

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.exp

class paymentDetails : AppCompatActivity() {
    private lateinit var cardnumber: EditText
    private lateinit var cvv: EditText
    private lateinit var holdername: EditText
    private lateinit var paybtn: Button
    private lateinit var etDatePicker3: EditText
    private lateinit var finalbill: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_details)

        val backButton = findViewById<ImageButton>(R.id.back_btn)
        backButton.setOnClickListener {
            val intent = Intent(this, paymentMethod::class.java)
            startActivity(intent)
        }

        paybtn = findViewById(R.id.to_checkout)
        cardnumber = findViewById(R.id.number_fill)
        etDatePicker3 = findViewById(R.id.date_fill)
        cvv = findViewById(R.id.cvv_fill)
        holdername = findViewById(R.id.name_fill)
        finalbill = findViewById(R.id.textView41)

        val billedAmount = intent.getStringExtra("billAmount")
        finalbill.text = "$billedAmount LKR"

        val myCalendar3 = Calendar.getInstance()
        val datePicker3 =  DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar3.set(Calendar.YEAR, year)
            myCalendar3.set(Calendar.MONTH, month)
            myCalendar3.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLable3(myCalendar3)
        }

        etDatePicker3.setOnClickListener{
            DatePickerDialog(this, datePicker3, myCalendar3.get(Calendar.YEAR), myCalendar3.get(Calendar.MONTH), myCalendar3.get(Calendar.DAY_OF_MONTH)).show()
        }


        paybtn.setOnClickListener {
            val cardno = cardnumber.text.toString().trim()
            val expdate = etDatePicker3.text.toString().trim()
            val cvvno = cvv.text.toString().trim()
            val hname = holdername.text.toString().trim()

            if (!isValidCardNumber(cardno)) {

                if (!isValidCVV(cvvno)) {
                    Toast.makeText(applicationContext, "Payment details cannot be verified", Toast.LENGTH_SHORT).show()
                    val i = Intent(this, unsuccessfulMessage::class.java)
                    startActivity(i)
                }else {
                    Toast.makeText(applicationContext, "Invalid card number", Toast.LENGTH_SHORT).show()
                }


            } else{
                if(!isValidCVV(cvvno)){
                    Toast.makeText(applicationContext, "Invalid CVV", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "Payment Authenticated", Toast.LENGTH_SHORT).show()
                    val i = Intent(this, successfulMessage::class.java)
                    startActivity(i)
                }
            }

            // Check if any of the fields are empty
            if (cardno.isEmpty() || expdate.isEmpty() || cvvno.isEmpty() || hname.isEmpty()) {
                Toast.makeText(
                    applicationContext,
                    "Please fill in all the fields",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
        }
    }
    private fun isValidCardNumber(cardNumber: String): Boolean {
        val regex = "\\d{16}".toRegex()
        return cardNumber.matches(regex)
    }

    private fun isValidCVV(cardNumber: String): Boolean {
        val regex = "\\d{3}".toRegex()
        return cardNumber.matches(regex)
    }

    private fun updateLable3(myCalendar3: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK )
        etDatePicker3.setText(sdf.format(myCalendar3.time))
    }
}