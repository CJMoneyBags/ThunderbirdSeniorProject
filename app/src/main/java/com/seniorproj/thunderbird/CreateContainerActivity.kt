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

class CreateContainerActivity : AppCompatActivity()  {
    // ActionBar
    private lateinit var actionBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_container)

        // configure ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Create Container"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        // container details
        val name: EditText = findViewById(R.id.name)
        val length: EditText = findViewById(R.id.length)
        val width: EditText = findViewById(R.id.width)
        val height: EditText = findViewById(R.id.height)
        val weightLimit: EditText = findViewById(R.id.weightLimit)

        // create cargo button
        val createContainerButton: Button = findViewById(R.id.createContainerButton)
        createContainerButton.setOnClickListener {
            val container = Database.Container(
                name.text.toString(),
                length.text.toString().toDouble(),
                width.text.toString().toDouble(),
                height.text.toString().toDouble(),
                weightLimit.text.toString().toDouble()
            )
            Toast.makeText(
                this@CreateContainerActivity,
                "Created ${name.text}",
                Toast.LENGTH_SHORT
            ).show()
            CoroutineScope(Dispatchers.IO).launch {
                if (Database.setContainer(container)) {
                    Log.d("Create Container", "Successfully created $container.")
                }
                else {
                    Log.d("Create Container", "Something went wrong.")
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // go back to previous activity, when back button of actionbar clicked
        return super.onSupportNavigateUp()
    }
}