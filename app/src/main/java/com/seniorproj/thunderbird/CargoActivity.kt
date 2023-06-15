package com.seniorproj.thunderbird

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.seniorproj.thunderbird.databinding.ActivityProfileBinding
import kotlinx.coroutines.runBlocking

class CargoActivity : AppCompatActivity() {
    // ViewBinding
//    private lateinit var binding: ActivityProfileBinding

    // ActionBar
    private lateinit var actionBar: ActionBar

    // FirebaseAuth
//    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) = runBlocking { // = runBlocking needed for suspend funs
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cargo)

//        binding = ActivityProfileBinding.inflate(layoutInflater)
//        setContentView(binding.root)

        // Configure ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Cargo"

        // Populate Spinner
        val cargo = Database.getAllCargo()!!
        val spinner: Spinner = findViewById(R.id.cargoSpinner)
        spinner.adapter = ArrayAdapter(this@CargoActivity, android.R.layout.simple_spinner_item, cargo)


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // You can define you actions as you want
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                val cargo = spinner.selectedItem as Database.Cargo
                Toast.makeText(
                    this@CargoActivity,
                    "Length: ${cargo.length}, Width: ${cargo.width}, Height: ${cargo.height}, Weight: ${cargo.weight}",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }

    }

}

/*package com.seniorproj.thunderbird

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
}*/