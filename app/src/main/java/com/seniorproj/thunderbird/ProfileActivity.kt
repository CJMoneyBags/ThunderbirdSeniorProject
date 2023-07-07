package com.seniorproj.thunderbird

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.seniorproj.thunderbird.databinding.ActivityProfileBinding
import java.time.LocalDateTime

class ProfileActivity : AppCompatActivity() {
    //ViewBinding
    private lateinit var binding: ActivityProfileBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    @RequiresApi(Build.VERSION_CODES.O)
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

        // greeting
        val greeting: TextView = findViewById(R.id.welcomeText)
        val currentTime: Int = LocalDateTime.now().hour
        greeting.text = when {
            currentTime < 12 -> getString(R.string.good_morning)
            currentTime < 17 -> getString(R.string.good_afternoon)
            currentTime < 24 -> getString(R.string.good_evening)
            else -> getString(R.string.hello)
        }

        // handle click, go to selection screen
        binding.packContainerButton.setOnClickListener {
            startActivity(Intent(this, SelectionActivity::class.java))
        }

        // handle click, go to create cargo screen
        binding.createCargoButton.setOnClickListener {
            startActivity(Intent(this, CreateCargoActivity::class.java))
        }

        // handle click, go to create container screen
        binding.createContainerButton.setOnClickListener {
            startActivity(Intent(this, CreateContainerActivity::class.java))

        }

        // handle click, logout
        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }

        // handle click, change password
        binding.changePasswordBtn.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
    }

    private fun checkUser() {
        // check user is logged in or not
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null) {
            // user is not logged in
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}