package com.example.theweatheriswhat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var btnGoToCreateAccount: Button
    private lateinit var btnLogin: Button
    private lateinit var etEnterEmail: EditText
    private lateinit var etEnterPassword: EditText
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
        btnLogin = findViewById(R.id.btnLogin)
        btnGoToCreateAccount = findViewById(R.id.btnGoToCreateAccount)
        etEnterEmail = findViewById(R.id.etEnterEmail)
        etEnterPassword = findViewById(R.id.etEnterPassword)
        btnGoToCreateAccount.setOnClickListener {
            val intent = Intent(this@MainActivity, CreateAccount::class.java)
            startActivity(intent)
        }
        btnLogin.setOnClickListener {
            val emailStr = etEnterEmail.text.toString()
            val passStr = etEnterPassword.text.toString()
            if (emailStr.isEmpty()) {
                Toast.makeText(this, "Enter email!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (passStr.isEmpty()) {
                Toast.makeText(this, "Enter password!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            try {
                mAuth!!.signInWithEmailAndPassword(emailStr, passStr)
                    .addOnCompleteListener(this@MainActivity) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(
                                this@MainActivity, "Login successful.",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this@MainActivity, CitiesWeatherActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                this@MainActivity, "Login failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } catch (e: Exception) {
                println(e)
            }
        }
    }
}