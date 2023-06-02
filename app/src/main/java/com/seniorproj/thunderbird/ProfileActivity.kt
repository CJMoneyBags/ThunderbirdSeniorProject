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

    override fun onCreate(savedInstanceState: Bundle?) = runBlocking {
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

        // ========================================================================================

        val cargoTag = "Database - Cargo"
        val containerTag = "Database - Container"

        // test getting existing cargo
        val getExistingCargo = Database.getCargo("40x40x50")
        if (getExistingCargo != null) {
            Log.d(cargoTag, "Name: ${getExistingCargo.name}")
        }
        else {
            Log.d(cargoTag, "Didn't work. Oof.")
        }

        // test setting new cargo
        val setNewCargo = Database.Cargo(
            "Tester Cargo",
            "Tester Cargo Contents",
            1.23,
            4.56,
            7.89,
            10.11
        )
        if (Database.setCargo(setNewCargo)) {
            Log.d(cargoTag, "${setNewCargo.name} added successfully")
        }
        else {
            Log.d(cargoTag, "Unable to add ${setNewCargo.name}")
        }

        // test getting new cargo
        val getNewCargo = Database.getCargo(setNewCargo.name)
        if (getNewCargo != null) {
            Log.d(cargoTag, "Found ${getNewCargo.name} with length ${getNewCargo.length}")
        }
        else {
            Log.d(cargoTag, "Sorry chief.")
        }

        // ========================================================================================

        // test getting existing container
        val getExistingContainer = Database.getContainer("test")
        if (getExistingContainer != null) {
            Log.d(containerTag, "Name: ${getExistingContainer.name}")
        }
        else {
            Log.d(containerTag, "Didn't work. Oof.")
        }

        // test setting new container
        val setNewContainer = Database.Container(
            "Tester Container",
            1.23,
            4.56,
            7.89,
            10.11
        )
        if (Database.setContainer(setNewContainer)) {
            Log.d(containerTag, "${setNewContainer.name} added successfully")
        }
        else {
            Log.d(containerTag, "Unable to add ${setNewContainer.name}")
        }

        // test getting new container
        val getNewContainer = Database.getContainer(setNewContainer.name)
        if (getNewContainer != null) {
            Log.d(containerTag, "Found ${getNewContainer.name} with weight limit ${getNewContainer.weightLimit}")
        }
        else {
            Log.d(containerTag, "Sorry chief.")
        }

        // ========================================================================================

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