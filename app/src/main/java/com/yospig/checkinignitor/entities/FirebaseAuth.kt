package com.yospig.checkinignitor.entities

import android.util.Log
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.yospig.checkinignitor.R

class IgnitorFirebaseAuth() {
    private val TAG = "FirebaseAuth"

    fun getAuthInformation():FirebaseUser {
        getCurrentUser()
        return FirebaseAuth.getInstance().currentUser ?: throw NullPointerException()
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
}