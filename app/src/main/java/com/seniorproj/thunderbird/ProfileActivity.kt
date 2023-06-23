package com.seniorproj.thunderbird

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.seniorproj.thunderbird.databinding.ActivityProfileBinding
import kotlinx.coroutines.runBlocking

class ProfileActivity : AppCompatActivity() {
    //ViewBinding
    private lateinit var binding: ActivityProfileBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) = runBlocking { // = runBlocking needed for suspend funs
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

//        testDatabase()

        // go to cargo
        binding.cargoButton.setOnClickListener {
            //open profile
            startActivity(Intent(this@ProfileActivity, CargoActivity::class.java))
            finish()
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

    private suspend fun testDatabase() {
        // make a bunch of cargo
        var cargoList = mutableListOf<Database.Cargo>()

        // 48x40
        cargoList.add(Database.Cargo(
            "48x40 3000lbs", 48.0, 40.0, 60.0, 3000.0
        ))
        cargoList.add(Database.Cargo(
            "48x40 4000lbs", 48.0, 40.0, 60.0, 4000.0
        ))

        // 42x42
        cargoList.add(Database.Cargo(
            "42x42 1000lbs", 42.0, 42.0, 60.0, 1000.0
        ))
        cargoList.add(Database.Cargo(
            "42x42 2000lbs", 42.0, 42.0, 60.0, 2000.0
        ))
        cargoList.add(Database.Cargo(
            "42x42 3000lbs", 42.0, 42.0, 60.0, 3000.0
        ))
        cargoList.add(Database.Cargo(
            "42x42 4000lbs", 42.0, 42.0, 60.0, 4000.0
        ))

        // 48x48
        cargoList.add(Database.Cargo(
            "48x48 1000lbs", 48.0, 48.0, 60.0, 1000.0
        ))
        cargoList.add(Database.Cargo(
            "48x48 2000lbs", 48.0, 48.0, 60.0, 2000.0
        ))
        cargoList.add(Database.Cargo(
            "48x48 3000lbs", 48.0, 48.0, 60.0, 3000.0
        ))
        cargoList.add(Database.Cargo(
            "48x48 4000lbs", 48.0, 48.0, 60.0, 4000.0
        ))

        // 40x40
        cargoList.add(Database.Cargo(
            "40x40 1000lbs", 40.0, 40.0, 60.0, 1000.0
        ))
        cargoList.add(Database.Cargo(
            "40x40 2000lbs", 40.0, 40.0, 60.0, 2000.0
        ))
        cargoList.add(Database.Cargo(
            "40x40 3000lbs", 40.0, 40.0, 60.0, 3000.0
        ))
        cargoList.add(Database.Cargo(
            "40x40 4000lbs", 40.0, 40.0, 60.0, 4000.0
        ))

        // 48x42
        cargoList.add(Database.Cargo(
            "48x42 1000lbs", 48.0, 42.0, 60.0, 1000.0
        ))
        cargoList.add(Database.Cargo(
            "48x42 2000lbs", 48.0, 42.0, 60.0, 2000.0
        ))
        cargoList.add(Database.Cargo(
            "48x42 3000lbs", 48.0, 42.0, 60.0, 3000.0
        ))
        cargoList.add(Database.Cargo(
            "48x42 4000lbs", 48.0, 42.0, 60.0, 4000.0
        ))

        // 36x36
        cargoList.add(Database.Cargo(
            "36x36 1000lbs", 36.0, 36.0, 60.0, 1000.0
        ))
        cargoList.add(Database.Cargo(
            "36x36 2000lbs", 36.0, 36.0, 60.0, 2000.0
        ))
        cargoList.add(Database.Cargo(
            "36x36 3000lbs", 36.0, 36.0, 60.0, 3000.0
        ))
        cargoList.add(Database.Cargo(
            "36x36 4000lbs", 36.0, 36.0, 60.0, 4000.0
        ))

        cargoList.map { Database.setCargo(it) }

        Log.d("DatabaseTest", "Should be 24 cargo documents: ${Database.getAllCargo()?.size}")
    }
}