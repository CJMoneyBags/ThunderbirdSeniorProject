package com.seniorproj.thunderbird

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.seniorproj.thunderbird.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    //ViewBinding
    private lateinit var binding: ActivityProfileBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configure ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Profile"

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        // go to cargo
        // TODO make this button/transition prettier!
        binding.cargoButton.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, SelectionActivity::class.java))
        }

        // handle click, logout
        binding.logoutBtn.setOnClickListener{
            firebaseAuth.signOut()
            checkUser()
        }
    }

    private fun checkUser() {
        //check user is logged in or not
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser != null){
            //user not null, user is logged in, get user ifor
            val email = firebaseUser.email
            // set to text view
            binding.emailTv.text = email
        }
        else{
            //user is null, user is not loggedin
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}