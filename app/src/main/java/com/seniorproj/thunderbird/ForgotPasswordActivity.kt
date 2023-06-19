package com.seniorproj.thunderbird

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.seniorproj.thunderbird.databinding.ActivityForgotPasswordBinding



class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

       binding.btnSubmit.setOnClickListener {

           val email: String = binding.etForgotEmail.text.toString().trim{it <= ' '}
           if(email.isEmpty()){
               Toast.makeText(
                   this@ForgotPasswordActivity,
                   "Please enter email address.",
                   Toast.LENGTH_SHORT
               ).show()
           }else{
               FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                   .addOnCompleteListener{task ->
                       if(task.isSuccessful){
                           Toast.makeText(
                               this@ForgotPasswordActivity,
                               "Email sent successfully to reset your password!",
                               Toast.LENGTH_LONG
                           ).show()

                           finish()
                       }else{
                           Toast.makeText(
                               this@ForgotPasswordActivity,
                               task.exception!!.message.toString(),
                               Toast.LENGTH_LONG
                           ).show()
                       }
                   }
           }


       }

    }
}