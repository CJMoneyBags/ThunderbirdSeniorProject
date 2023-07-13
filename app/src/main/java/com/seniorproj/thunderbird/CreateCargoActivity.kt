package com.seniorproj.thunderbird

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateCargoActivity : AppCompatActivity() {
    // ActionBar
    private lateinit var actionBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_cargo)

        // configure ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Create Cargo"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        // cargo details
        val name: EditText = findViewById(R.id.name)
        val length: EditText = findViewById(R.id.length)
        val width: EditText = findViewById(R.id.width)
        val height: EditText = findViewById(R.id.height)
        val weight: EditText = findViewById(R.id.weight)

        // create cargo button
        val createCargoButton: Button = findViewById(R.id.createCargoButton)
        createCargoButton.setOnClickListener {
            val cargo = Database.Cargo(
                name.text.toString(),
                length.text.toString().toDouble(),
                width.text.toString().toDouble(),
                height.text.toString().toDouble(),
                weight.text.toString().toDouble()
            )
            Toast.makeText(
                this@CreateCargoActivity,
                "Created ${name.text}",
                Toast.LENGTH_SHORT
            ).show()
            CoroutineScope(Dispatchers.IO).launch {
                if (Database.setCargo(cargo)) {
                    Log.d("Create Cargo", "Successfully created $cargo.")
                }
                else {
                    Log.d("Create Cargo", "Something went wrong.")
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // go back to previous activity, when back button of actionbar clicked
        return super.onSupportNavigateUp()
    }
}