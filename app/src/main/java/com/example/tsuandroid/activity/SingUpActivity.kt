package com.example.tsuandroid.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tsuandroid.R

class SingUpActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val setting = "mysettings"
        val editLogin: EditText = findViewById(R.id.sign_up_login)
        val editPassword: EditText = findViewById(R.id.sign_up_password)
        val buttonRegis: Button = findViewById(R.id.sign_up_registration)
        val buttonAuth: Button = findViewById(R.id.sign_up_auth)

        val sPref = getSharedPreferences(setting, Context.MODE_PRIVATE)
        val editor = sPref.edit()

        buttonRegis.setOnClickListener{
            if (editLogin.text.toString() != "" && editPassword.text.toString() != "") {
                editor.putString(editLogin.text.toString(), editPassword.text.toString())
                editor.apply()
                Toast.makeText(this, "Регистрация прошла успешно", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Заполните поля", Toast.LENGTH_LONG).show()
            }
        }

        buttonAuth.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}