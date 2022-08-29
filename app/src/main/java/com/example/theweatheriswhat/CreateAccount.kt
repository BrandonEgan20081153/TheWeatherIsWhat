package com.example.theweatheriswhat

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class CreateAccount : AppCompatActivity() {
    private lateinit var btnCreateAccount: Button
    private lateinit var etEnterEmail: EditText
    private lateinit var etEnterPassword: EditText
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        mAuth = FirebaseAuth.getInstance()
        btnCreateAccount = findViewById(R.id.btnCreateAccount)
        etEnterEmail = findViewById(R.id.etEnterEmail)
        etEnterPassword = findViewById(R.id.etEnterPassword)
        btnCreateAccount.setOnClickListener(View.OnClickListener {
            val emailStr = etEnterEmail.text.toString()
            val passStr = etEnterPassword.text.toString()
            if (emailStr.isEmpty()) {
                Toast.makeText(this, "Enter email!", Toast.LENGTH_LONG).show()
                return@OnClickListener
            }
            if (passStr.isEmpty()) {
                Toast.makeText(this, "Enter password!", Toast.LENGTH_LONG).show()
                return@OnClickListener
            }
            mAuth.createUserWithEmailAndPassword(emailStr, passStr)
                .addOnCompleteListener(this@CreateAccount) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
//                                    FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(
                            this@CreateAccount, "Authentication Successful.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(
                            this@CreateAccount, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        })
    }
}