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
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.yospig.checkinignitor.entities.AttendanceInTime
import com.yospig.checkinignitor.entities.AttendanceUser
import kotlinx.android.synthetic.main.activity_checkin_list.*
import java.util.*

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

    override fun onStart() {
        super.onStart()
        getCurrentUser()
        val user = FirebaseAuth.getInstance().currentUser
        user?.let{
            val name = it.displayName
            val userName:TextView = findViewById(R.id.userName)
            userName.setText(name)
            if(name != null) {
                fetchOwnCheckinList(name)
            }
        }
    }

    private fun getCurrentUser(): FirebaseUser? {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let{ user ->
            var name = user.displayName
            var photoUrl = user.photoUrl
            val email = user.email
            val emailVerified = user.isEmailVerified
            val uid = user.uid
            for(userInfo in user.providerData){
                if (name.isNullOrEmpty() && userInfo.displayName != null){
                    name = userInfo.displayName
                }
                if (photoUrl == null && userInfo.photoUrl != null){
                    photoUrl = userInfo.photoUrl
                }
            }
            name?.apply{
                email?.let{
                    val emailArray = email.split("@")
                    name = emailArray[0]
                }
                val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(name).setPhotoUri(photoUrl).build()
                updateProfile(user, profileUpdates)
            }
            Log.d(TAG, "name:$name")
            Log.d(TAG, "email:$email")
            Log.d(TAG, "photoUrl:$photoUrl")
            Log.d(TAG, "emailVerified:$emailVerified")
            Log.d(TAG, "uid:$uid")
        }
        return user
    }

    private fun updateProfile(user: FirebaseUser, profileUpdates: UserProfileChangeRequest) {
        user.updateProfile(profileUpdates)?.addOnCompleteListener{ task ->
            if(task.isSuccessful){
                Log.d(TAG, "User profile updated.")
            }
        }
    }

    private fun fetchOwnCheckinList(user: String){
        val docsRef = db.collection("attendance_user").document(user).collection("date")
        var attendanceArray = arrayOf<String>()
        val listView = findViewById<ListView>(R.id.attendanceList)
        docsRef.get().addOnSuccessListener { docs ->
            for(doc in docs){
                Log.d(TAG, "${doc.id} => ${doc.data}")
                // TODO:use dto
//                val doc.toObject(AttendanceUser::class.java)
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
