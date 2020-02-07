package com.yospig.checkinignitor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
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
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
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
    }

    private fun getCurrentUser(): FirebaseUser? {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let{
            var name = it.displayName
            var photoUrl = it.photoUrl
            val email = it.email
            val emailVerified = it.isEmailVerified
            val uid = it.uid
            for(userInfo in user.providerData){
                if (name.isNullOrEmpty() && userInfo.displayName != null){
                    name = userInfo.displayName
                }
                if (photoUrl == null && userInfo.photoUrl != null){
                    photoUrl = userInfo.photoUrl
                }
            }
            val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(name).setPhotoUri(photoUrl).build()
            updateProfile(user, profileUpdates)
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
        val docRef = db.collection("attendance_user").document(user)
        val source = Source.CACHE

        docRef.get(source).addOnCompleteListener { task ->
            if(task.isSuccessful){
                // offline cache
                val document = task.result
                Log.d(TAG, "Cached document data: ${document?.data}")
           }else{
                Log.d(TAG, "Cached get failed: ", task.exception)
            }
        }
    }
}
