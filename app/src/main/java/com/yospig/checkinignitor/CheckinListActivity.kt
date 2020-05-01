package com.yospig.checkinignitor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.yospig.checkinignitor.entities.IgnitorFirebaseUserAuth
import kotlinx.android.synthetic.main.activity_checkin_list.*

class CheckinListActivity : AppCompatActivity() {

    private val TAG = "CheckinListActivity"
    // Access a Cloud Firestore instance from your Activity
    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkin_list)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            val intent = Intent(this, CheckInOutActivity::class.java).apply{}
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val auth = IgnitorFirebaseUserAuth()
        auth.user.displayName?.let{ it ->
            displayView(it)
        }
    }

    // view menu
    override fun onCreateOptionsMenu(menu: Menu?):Boolean{
        super.onCreateOptionsMenu(menu)
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
                Log.d(TAG, "Logout.")
                Toast.makeText(
                    this,
                    "Logout.",
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(this,MainActivity::class.java).apply{}
                startActivity(intent)
                return true
            }
            else -> {return super.onOptionsItemSelected(item)}
        }
    }

    private fun displayView(name: String){
        val userName:TextView = findViewById(R.id.userName)
        userName.text = name
        fetchOwnCheckinList(name)
    }

    private fun fetchOwnCheckinList(user: String){
        val docsRef = db.collection("attendance_user").document(user).collection("date")
        var attendanceArray = arrayOf<String>()
        val listView = findViewById<ListView>(R.id.attendanceList)
        docsRef.get().addOnSuccessListener { docs ->
            for(doc in docs){
                Log.d(TAG, "${doc.id} => ${doc.data}")
                // TODO:use dto
                val line = doc.id + " [in] " + doc.data["in_time_str"] + " [out] " + doc.data["out_time_str"]
                attendanceArray += line
            }
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, attendanceArray)
            listView.adapter = arrayAdapter
        }.addOnFailureListener{ exception ->
            Log.w(TAG, "Error getting documents: ", exception)
        }
    }
}
