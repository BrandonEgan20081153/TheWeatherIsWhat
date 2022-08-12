package com.example.theweatheriswhat;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.theweatheriswhat.databinding.ActivityCreateAccountBinding;

public class CreateAccount extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityCreateAccountBinding binding;

    Button btnCreateAccount;
    EditText etEnterEmail, etEnterPassword, etEnterAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        btnCreateAccount = findViewById(R.id.btnLogin);
        etEnterEmail = findViewById(R.id.etEnterEmail);
        etEnterPassword = findViewById(R.id.etEnterPassword);
        etEnterAge = findViewById(R.id.etEnterAge);
    }
}