package com.yospig.checkinignitor

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

import kotlinx.android.synthetic.main.activity_checkin_list.*

class CheckinListActivity : AppCompatActivity() {

    private val TAG = "CheckinListActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkin_list)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onStart() {
        super.onStart()
        getCurrentUser()
    }

    fun getCurrentUser() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let{
            val name = user.displayName
            val email = user.email
            val photoUrl = user.photoUrl
            val emailVerified = user.isEmailVerified
            val uid = user.uid
            Log.d(TAG, "name:$name")
            Log.d(TAG, "email:$email")
            Log.d(TAG, "photoUrl:$photoUrl")
            Log.d(TAG, "emailVerified:$emailVerified")
            Log.d(TAG, "uid:$uid")
        }
    }
}
