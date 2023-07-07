package com.seniorproj.thunderbird

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.runBlocking

class SelectionActivity : AppCompatActivity() {
    // ActionBar
    private lateinit var actionBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) = runBlocking {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)

        // configure ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Select Container and Cargo"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        // only need to query once
        val allCargo = Database.getAllCargo()!!
        val allContainers = Database.getAllContainers()!!

        // other values we need
        var cargo = Database.Cargo("", 0.0, 0.0, 0.0, 0.0)
        var container = Database.Container("", 0.0, 0.0, 0.0, 0.0)
        val cargoList = arrayListOf<Database.CargoPair>()

        // spinner for container
        val containerSpinner: Spinner = findViewById(R.id.containerSpinner)
        containerSpinner.adapter = ArrayAdapter(this@SelectionActivity, android.R.layout.simple_spinner_item, allContainers)
        containerSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) { }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                container = containerSpinner.selectedItem as Database.Container
                Log.d("Container Spinner", "Selected Container: ${container.name}")
            }
        }

        // spinner for cargo
        val cargoSpinner: Spinner = findViewById(R.id.cargoSpinner)
        cargoSpinner.adapter = ArrayAdapter(this@SelectionActivity, android.R.layout.simple_spinner_item, allCargo)
        cargoSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) { }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                cargo = cargoSpinner.selectedItem as Database.Cargo
                Log.d("Cargo Spinner", "Selected Cargo: ${cargo.name}")
            }
        }

        // number picker
        var numCargo = 1
        val numberPicker: NumberPicker = findViewById(R.id.numberPicker)
        numberPicker.minValue = 1
        numberPicker.maxValue = 24
        numberPicker.wrapSelectorWheel = true
        numberPicker.setOnValueChangedListener { _, _, newValue ->
            numCargo = newValue
            Log.d("Number Picker", "Number is now $numCargo")
        }

        // add cargo button
        val confirmCargoButton: Button = findViewById(R.id.confirmCargo)
        confirmCargoButton.setOnClickListener {
            cargoList.add(Database.CargoPair(cargo, numCargo))
            Toast.makeText(
                this@SelectionActivity,
                "Added x$numCargo of ${cargo.name}",
                Toast.LENGTH_SHORT
            ).show()
            if (cargoList.isNotEmpty()) {
                Log.d("Cargo List", "$cargoList")
            }
        }

        // undo cargo button
        val undoCargoButton: Button = findViewById(R.id.undoCargo)
        undoCargoButton.setOnClickListener {
            // remove and determine what's been removed
            val lastCargo = cargoList.removeLastOrNull()

            // if we removed something, show it in a Toast message
            if (lastCargo != null) {
                Toast.makeText(
                    this@SelectionActivity,
                    "Removed x${lastCargo.number} of ${lastCargo.cargo.name}",
                    Toast.LENGTH_SHORT
                ).show()
                if (cargoList.isNotEmpty()) {
                    Log.d("Cargo List", "$cargoList")
                }
            }
            else {
                Toast.makeText(
                    this@SelectionActivity,
                    "No cargo to undo!",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("Cargo List", "List is empty.")
            }
        }

        // continue button
        val continueButton: Button = findViewById(R.id.continueToNextScreen)
        continueButton.setOnClickListener {
            // send container and cargoList to the next screen
            val bundle = Bundle()
            bundle.putParcelable("container", container)
            bundle.putParcelableArrayList("cargoList", cargoList)

            // set up the next screen
            val intent = Intent(this@SelectionActivity, DisplayActivity::class.java)
            intent.putExtras(bundle)

            // go to the next screen
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // go back to previous activity, when back button of actionbar clicked
        return super.onSupportNavigateUp()
    }
}
