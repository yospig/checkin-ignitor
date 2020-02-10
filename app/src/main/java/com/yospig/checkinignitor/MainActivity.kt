package com.yospig.checkinignitor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


const val EXTRA_MESSAGE = "com.yospig.checkinignitor.MESSAGE"

class MainActivity : AppCompatActivity() {

    // declare Firebase Auth
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val email: EditText = findViewById(R.id.email)
        val password: EditText = findViewById(R.id.password)
        val login: Button = findViewById(R.id.login)

        login.setOnClickListener {
            emailLogin(email.text.toString(), password.text.toString())
        }

        // initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            Log.d(TAG, "Authenticated. login done.")
            val intent = Intent(this, CheckinListActivity::class.java).apply{}
            startActivity(intent)
        } else {
        }
    }

    private fun emailLogin(email: String, password: String): Unit {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "LoginWithEmail:success")
                    Toast.makeText(
                        this,
                        "Authentication success.",
                        Toast.LENGTH_LONG
                    ).show()
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Log.d(TAG, "LoginWithEmail:failed")
                    Toast.makeText(
                        this,
                        "Authentication failed.",
                        Toast.LENGTH_LONG
                    ).show()
                    updateUI(null)
                }
            }
    }

    companion object {
        private const val TAG = "EmailPassword"
    }
}

