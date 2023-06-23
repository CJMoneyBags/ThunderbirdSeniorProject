package com.seniorproj.thunderbird

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
//import com.seniorproj.thunderbird.databinding.ActivityCargoBinding
import kotlinx.coroutines.runBlocking

class CargoActivity : AppCompatActivity() {
    // ViewBinding
//    private lateinit var binding: ActivityCargoBinding

    // ActionBar
    private lateinit var actionBar: ActionBar

    // FirebaseAuth
//    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) = runBlocking { // = runBlocking needed for suspend funs
        super.onCreate(savedInstanceState)
//        binding = ActivityCargoBinding.inflate(layoutInflater)
//        val view = binding.root
//        setContentView(view)
        setContentView(R.layout.activity_cargo)

        // Configure ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Cargo"

        // only need to query once
        val allCargo = Database.getAllCargo()!!
        val userSelection = mutableListOf<Database.Cargo>()

//        addSpinner(allCargo)
//        addSpinner(allCargo)

        // spinner
        val spinner: Spinner = findViewById(R.id.cargoSpinner)
        spinner.adapter = ArrayAdapter(this@CargoActivity, android.R.layout.simple_spinner_item, allCargo)
        var cargo: Database.Cargo
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // You can define you actions as you want
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                cargo = spinner.selectedItem as Database.Cargo
                Toast.makeText(
                    this@CargoActivity,
                    "Added ${cargo.name} to list. Number of items: ${userSelection.size}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // number picker
        val numberPicker: NumberPicker = findViewById(R.id.numberPicker)
        numberPicker.minValue = 0
        numberPicker.maxValue = 50
        numberPicker.wrapSelectorWheel = true
//        numberPicker.display


        // Populate Spinner
//        val allCargo = Database.getAllCargo()!!
//        val spinner: Spinner = findViewById(R.id.cargoSpinner1)
//        spinner.adapter = ArrayAdapter(this@CargoActivity, android.R.layout.simple_spinner_item, allCargo)
//
//        val thing = spinner.selectedItem
//
//        Log.d("Cargo", "Test, $thing")
//
//        var cargo: Database.Cargo
//
//        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//                // You can define you actions as you want
//            }
//
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
//                cargo = spinner.selectedItem as Database.Cargo
//                Toast.makeText(
//                    this@CargoActivity,
//                    "Length: ${cargo.length}, Width: ${cargo.width}, Height: ${cargo.height}, Weight: ${cargo.weight}",
//                    Toast.LENGTH_SHORT
//                ).show()
//
//            }
//        }

    }

    private fun addSpinner(allCargo: List<Database.Cargo>) {
        val spinner = Spinner(this)
        spinner.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, allCargo)
        spinner.adapter = arrayAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val cargo = spinner.selectedItem as Database.Cargo
                Toast.makeText(
                    this@CargoActivity,
                    "Length: ${cargo.length}, Width: ${cargo.width}, Height: ${cargo.height}, Weight: ${cargo.weight}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
//                nothing here yet
            }
        }

//        binding.rootContainer.addView(spinner)
    }

}
