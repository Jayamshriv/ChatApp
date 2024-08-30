package com.example.chatapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

    private lateinit var appusername: TextInputLayout
    private lateinit var appemail: TextInputLayout
    private lateinit var apppassword: TextInputLayout
    private lateinit var appsignup: Button
    private lateinit var mDbRef: DatabaseReference

    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)

        val topAppBar: MaterialToolbar = findViewById(R.id.topAppBar)
        setSupportActionBar(topAppBar)

        mAuth = FirebaseAuth.getInstance()


        mAuth = FirebaseAuth.getInstance()

        appusername = findViewById(R.id.app_username)
        appemail = findViewById(R.id.app_mail)
        apppassword = findViewById(R.id.app_password)
        appsignup = findViewById(R.id.app_signup)

        appsignup.setOnClickListener(){
            val name = appusername.editText?.text.toString()
            val email = appemail.editText?.text.toString()  // Accessing the EditText inside TextInputLayout
            val password = apppassword.editText?.text.toString()
            signup(name, email, password)

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun signup(name: String, email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    addUserToDatabase(name, email, mAuth.currentUser?.uid!!)
                    val intent = Intent(this@SignUp, MainActivity::class.java )
                    startActivity(intent)
                    finish()

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@SignUp, "Some Error Occured", Toast.LENGTH_LONG).show()

                }
            }

    }

    private fun addUserToDatabase(name: String, email:String, uid: String){
        mDbRef = FirebaseDatabase.getInstance().getReference()

        mDbRef.child("user").child(uid).setValue(User(name, email, uid))

    }
}