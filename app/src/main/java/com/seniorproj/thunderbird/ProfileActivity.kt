package com.seniorproj.thunderbird

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar


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

        testDatabase()

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
        val tag = "Database Test"
        // create Cargo
        val testCargo = Database.Cargo(
            "test cargo", "test contents", 1.0, 2.0, 3.0, 4.0
        )

        // create Container
        val testContainer = Database.Container(
            "test container", 4.0, 3.0, 2.0, 1.0
        )

        // add Cargo to database
        if (Database.setCargo(testCargo)) {
            Log.d(tag, "Set ${testCargo.name}")
        }

        // add Container to database
        if (Database.setContainer(testContainer)) {
            Log.d(tag, "Set ${testContainer.name}")
        }

        // get Cargo
        val gotCargo = Database.getCargo("test cargo")
        if (gotCargo != null) {
            Log.d(tag, "Got ${gotCargo.name} with length ${gotCargo.length}")
        }

        // get Container
        val gotContainer = Database.getContainer("test container")
        if (gotContainer != null) {
            Log.d(tag, "Got ${gotContainer.name} with height ${gotContainer.height}")
        }

        // get all Cargo
        val allCargo = Database.getAllCargo()
        if (allCargo.isNotEmpty()) {
            Log.d(tag, "All Cargo received with size ${allCargo.size}")
        }

        // get all Containers
        val allContainers = Database.getAllContainers()
        if (allContainers.isNotEmpty()) {
            Log.d(tag, "All Containers received with size ${allCargo.size}")
        }

        // get all Cargo names
        val allCargoNames = Database.getAllCargoNames()
        if (allCargoNames.isNotEmpty()) {
            val setCargoNames = allCargo.map { it.name }.toSet()
            if (setCargoNames == allCargoNames.toSet()) {
                Log.d(tag, "All Cargo names received: $allCargoNames")
            }
            else {
                Log.d(tag, "Not all Cargo names received")
            }
        }

        // get all Container names
        val allContainerNames = Database.getAllContainerNames()
        if (allContainerNames.isNotEmpty()) {
            val setContainerNames = allContainers.map { it.name }.toSet()
            if (setContainerNames == allContainerNames.toSet()) {
                Log.d(tag, "All Container names received: $allContainerNames")
            }
            else {
                Log.d(tag, "Not all Container names received")
            }
        }

        // delete test Cargo
        if (Database.deleteCargo(testCargo.name)) {
            if (Database.getCargo(testCargo.name) == null) {
                Log.d(tag, "Deleted ${testCargo.name}")
            }
        }

        // delete test Container
        if (Database.deleteContainer(testContainer.name)) {
            if (Database.getContainer(testContainer.name) == null) {
                Log.d(tag, "Deleted ${testContainer.name}")
            }
        }

    }
}