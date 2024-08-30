package com.example.chatapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var appemail: TextInputLayout
    private lateinit var apppassword: TextInputLayout
    private lateinit var applogin: Button
    private lateinit var appsignup: Button


    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login)

        mAuth = FirebaseAuth.getInstance()

        appemail = findViewById(R.id.app_mail)
        apppassword = findViewById(R.id.app_password)
        applogin = findViewById(R.id.app_login)
        appsignup = findViewById(R.id.app_signup)

        val topAppBar: MaterialToolbar = findViewById(R.id.topAppBar)
        setSupportActionBar(topAppBar)

        appsignup.setOnClickListener() {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
            finish()
        }

        applogin.setOnClickListener() {
            val email =
                appemail.editText?.text.toString()  // Accessing the EditText inside TextInputLayout
            val password = apppassword.editText?.text.toString()

            login(email, password);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this@Login, MainActivity::class.java )
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@Login, "No User Found!!", Toast.LENGTH_SHORT).show()
                }
            }
    }
}

