package com.example.utilityinsight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class gasCalculate : AppCompatActivity() {

    private lateinit var gaccountno: EditText
    private lateinit var gcalcdate: EditText
    private lateinit var gcalunits: EditText
    private lateinit var gcalcdays: EditText

    private lateinit var gcalcbtn: Button
    private lateinit var gcaloutouttxt: TextView
    private lateinit var gcaloutoutans: TextView

    private var db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gas_calculate)

        //assigning variables to id
        gaccountno= findViewById(R.id.gas_calc_accnoinputx)
        gcalcdate= findViewById(R.id.gas_calc_date)
        gcalunits= findViewById(R.id.gas_unit_countinput)
        gcalcdays= findViewById(R.id.gas_days_count)
        gcalcbtn= findViewById(R.id.gas_cal_btn)
        gcaloutouttxt= findViewById(R.id.gas_calc_amount_txt)
        gcaloutoutans= findViewById(R.id.gas_calamount_output)

        gcalcbtn.setOnClickListener(){

            val gasaccountno = gaccountno.text.toString().trim()
            val  gascalcdate= gcalcdate.text.toString().trim()
            val  gascalunits= gcalunits.text.toString().trim()
            val  gascalcdays= gcalcdays.text.toString().trim()

            val gasuserMap = hashMapOf(
                "AccountNo" to gasaccountno,
                "Date" to gascalcdate,
                "Units" to gascalunits,
                "Days" to gascalcdays
            )

            val userId = FirebaseAuth.getInstance().currentUser!!.uid

            db.collection("gascalculate").document(userId).set(gasuserMap)
                .addOnSuccessListener {
                    Toast.makeText(this,"Successfully added!", Toast.LENGTH_SHORT).show()
                    gaccountno.text.clear()
                    gcalcdate.text.clear()
                    gcalunits.text.clear()
                    gcalcdays.text.clear()
                }

                .addOnFailureListener{
                    Toast.makeText(this,"Failed!", Toast.LENGTH_SHORT).show()
                }




        }




    }
}