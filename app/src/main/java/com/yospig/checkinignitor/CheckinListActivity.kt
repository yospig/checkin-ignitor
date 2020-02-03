package com.yospig.checkinignitor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
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



    private fun getCurrentUser() {
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
