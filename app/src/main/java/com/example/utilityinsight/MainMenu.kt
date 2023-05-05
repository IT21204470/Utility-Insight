package com.example.utilityinsight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainMenu : AppCompatActivity() {

    lateinit var toggle:ActionBarDrawerToggle
    private lateinit var imgSide:ImageView

    private var db = Firebase.firestore

    private lateinit var electricitybtn:Button
    private lateinit var waterbtn:Button
    private lateinit var gasbtn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        val userID = FirebaseAuth.getInstance().currentUser!!.uid
        val ref = db.collection("user").document(userID)



            val drawerLayout: DrawerLayout =findViewById(R.id.drawerLayout)
            val navView:NavigationView = findViewById(R.id.nav_view)

            toggle = ActionBarDrawerToggle(this,drawerLayout, R.string.open, R.string.close)
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            navView.setNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.electHome-> {
                        Toast.makeText(applicationContext,"Clicked Electricity", Toast.LENGTH_SHORT).show()
                        val electIntent = Intent(this, electricityHome::class.java)
                        startActivity(electIntent)
                    }
                    R.id.waterHome->{
                        Toast.makeText(applicationContext,"Clicked Water",Toast.LENGTH_SHORT).show()
                        val waterIntent = Intent(this, waterHome::class.java)
                        startActivity(waterIntent)
                    }
                    R.id.gasHome->{
                        Toast.makeText(applicationContext,"Clicked Gas",Toast.LENGTH_SHORT).show()

                    }
                    R.id.nav_logout->{
                        Toast.makeText(applicationContext,"Clicked Logout",Toast.LENGTH_SHORT).show()
                        Firebase.auth.signOut()
                        val intent = Intent(this, UserLogin::class.java)
                        startActivity(intent)

                    }


                    R.id.nav_delete->{

                        Toast.makeText(applicationContext,"Clicked Delete",Toast.LENGTH_SHORT).show()

                        val mapDelete = mapOf(
                            "Name" to FieldValue.delete()
                        )

                        db.collection("user").document(userID).update(mapDelete)
                            .addOnSuccessListener{
                                Toast.makeText(this, "Successfully Deleted", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, UserRegistration::class.java)
                                startActivity(intent)
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, "Deleting Failed", Toast.LENGTH_SHORT).show()
                            }

                    }

                    R.id.user_feedback->{

                        val feedIntent = Intent(this, user_feedback::class.java)
                        startActivity(feedIntent)

                    }
                    R.id.nav_profile->{
                        val gasIntent = Intent(this, userProfile::class.java)
                        startActivity(gasIntent)
                    }
                }
                true
            }



        electricitybtn = findViewById(R.id.btnElectricity)
        electricitybtn.setOnClickListener {
            val electIntent = Intent(this, electricityHome::class.java)
            startActivity(electIntent)
        }


        waterbtn = findViewById(R.id.btnWater)
        waterbtn.setOnClickListener {
            val waterIntent = Intent(this, waterHome::class.java)
            startActivity(waterIntent)
        }


        gasbtn = findViewById(R.id.btnGas)

        gasbtn.setOnClickListener {
            val gasIntent = Intent(this, userProfile::class.java)
            startActivity(gasIntent)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem):Boolean {

        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}