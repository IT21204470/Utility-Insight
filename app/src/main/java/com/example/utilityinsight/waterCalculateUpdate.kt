package com.example.utilityinsight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class waterCalculateUpdate : AppCompatActivity() {

    // declare the views and data source
    private lateinit var waccnumber: EditText
    private lateinit var wdays: EditText
    private lateinit var wunits: EditText
    private lateinit var waccname: EditText
    private lateinit var btncal: Button
    private lateinit var btnupdate: Button
    private lateinit var progressbar: ProgressBar
    private lateinit var totcal: TextView
    private lateinit var billdb: TextView
    private lateinit var userID: TextView

    private var db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water_calculate_update)

        val backButton = findViewById<ImageButton>(R.id.uw_cal_page_back_btn)
        backButton.setOnClickListener {
            val intent = Intent(this, waterHome::class.java)
            startActivity(intent)
        }

        //initialize the views and data source
        waccname = findViewById(R.id.uw_acc_name)
        waccnumber = findViewById(R.id.uw_acc_number)
        wdays = findViewById(R.id.uw_number_of_days)
        wunits = findViewById(R.id.uw_number_of_units)
        btncal = findViewById(R.id.uw_home_calculate_btn)
        btnupdate = findViewById(R.id.uw_home_store)
        totcal = findViewById(R.id.uviewtotal)
        progressbar = findViewById(R.id.uwProgressBar)
        billdb = findViewById(R.id.utextView62)
        userID = findViewById(R.id.utextView)

        btnupdate.visibility = View.INVISIBLE
        userID.visibility = View.INVISIBLE
        billdb.visibility = View.INVISIBLE
        progressbar.visibility = View.INVISIBLE

        waccname.text = intent.getStringExtra("accountname").toString().toEditable()
        waccnumber.text = intent.getStringExtra("accountnumber").toString().toEditable()
        wdays.text = intent.getStringExtra("numberofdays").toString().toEditable()
        wunits.text = intent.getStringExtra("numberofunits").toString().toEditable()
        billdb.text = intent.getStringExtra("totalamount").toString().toEditable()
        userID.text = intent.getStringExtra("uid").toString().toEditable()




        btncal.setOnClickListener {
            btnupdate.visibility = View.VISIBLE

            val waterunitText = wunits.text.toString().trim()
            val waterdaysText = wdays.text.toString().trim()
            val wateraccountNumber = waccnumber.toString().trim()
            val wateraccountName = waccname.toString().trim()

            // Check if any of the fields are empty
            if (waterunitText.isEmpty() || waterdaysText.isEmpty() || wateraccountNumber.isEmpty() || wateraccountName.isEmpty()) {
                Toast.makeText(applicationContext, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener }

            val waterunit: Int
            try {
                waterunit = waterunitText.toInt()
            } catch (e: NumberFormatException) {
                Toast.makeText(applicationContext, "Invalid input for units", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            val waterdays: Int
            try {
                waterdays = waterdaysText.toInt()
            } catch (e: NumberFormatException) {
                Toast.makeText(applicationContext, "Invalid input for days", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            var wnormalcharge = 0
            var wfixedcharge = 0
            var wtotal = 0

            if (waterunit < 25) {
                wfixedcharge = 100
                wnormalcharge = waterunit * waterdays
                wtotal = wfixedcharge + wnormalcharge
            } else if (waterunit < 30) {
                wfixedcharge = 200
                wnormalcharge = waterunit * waterdays
                wtotal = wfixedcharge + wnormalcharge
            } else if (waterunit < 40) {
                wfixedcharge = 400
                wnormalcharge = waterunit * waterdays
                wtotal = wfixedcharge + wnormalcharge
            } else if (waterunit < 50) {
                wfixedcharge = 650
                wnormalcharge = waterunit * waterdays
                wtotal = wfixedcharge + wnormalcharge
            } else if (waterunit < 75) {
                wfixedcharge = 1000
                wnormalcharge = waterunit * waterdays
                wtotal = wfixedcharge + wnormalcharge
            } else {
                wfixedcharge = 1600
                wnormalcharge = (waterunit * waterdays)
                wtotal = wfixedcharge + wnormalcharge
            }

            totcal.text = "Bill Period: $waterdays days\nImport Charge: $wnormalcharge LKR\nFixed Charge: $wfixedcharge LKR\nTotal Bill Amount: $wtotal LKR"

            billdb.text = "$wtotal LKR"

        }


        btnupdate.setOnClickListener {
            totcal.visibility = View.INVISIBLE
            billdb.visibility = View.INVISIBLE
            progressbar.visibility = View.VISIBLE

            val uIID = userID.text.toString()
            val accountNumber = waccnumber.text.toString().trim()
            val accountName = waccname.text.toString().trim()
            val numberOfDays = wdays.text.toString().trim()
            val numberOfUnits = wunits.text.toString().trim()
            val totalAmount = billdb.text.toString().trim()

            val userupdateMap = mapOf(
                "accountname" to accountName,
                "accountnumber" to accountNumber,
                "numberofdays" to numberOfDays,
                "numberofunits" to numberOfUnits,
                "totalamount" to totalAmount
            )

            db.collection("wcalculate").document(uIID).update(userupdateMap)

            Toast.makeText(this,"Successfully Edited", Toast.LENGTH_SHORT).show()

            val i = Intent(this, waterEntries::class.java)
            startActivity(i)
            finish()
        }

    }

    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)





}