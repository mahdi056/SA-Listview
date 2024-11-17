package com.example.myapplicationproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText etusername = findViewById(R.id.et_reg_username);
        EditText etemail = findViewById(R.id.et_reg_email);
        EditText etpassword = findViewById(R.id.et_reg_password);
        EditText etconpassword = findViewById(R.id.et_reg_con_password);
        EditText etphonenumber = findViewById(R.id.et_reg_phn_number);
        Button btnregister = findViewById(R.id.btn_register);
        Button btnlogin = findViewById(R.id.btn_login);

        btnregister.setOnClickListener(v->{
            String username = etusername.getText().toString();
            String email = etemail.getText().toString();
            String password = etpassword.getText().toString();
            String conpassword = etconpassword.getText().toString();
            String phone = etphonenumber.getText().toString();


            if (password.equals(conpassword) && !password.isEmpty() && !username.isEmpty()){
                Toast.makeText(RegisterActivity.this, "Well done!", Toast.LENGTH_SHORT).show();
                Databasehelper dbHelper = new Databasehelper(RegisterActivity.this);
                boolean isinserted = dbHelper.insertUser (username,email,password,phone);
                if (isinserted){
                    Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    Intent intend = new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(intend);

                }
                else{
                    Toast.makeText(RegisterActivity.this, "registration failed", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(RegisterActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
            }

        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });


    }
}
