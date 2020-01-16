package com.example.tsuandroid.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tsuandroid.R

class LoginActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val setting = "mysettings"

        val editLogin: EditText = findViewById(R.id.login_login)
        val editPassword: EditText = findViewById(R.id.login_password)
        val buttonRegis: Button = findViewById(R.id.login_registration)
        val buttonAuth: Button = findViewById(R.id.login_auth)
        val buttonReset: Button = findViewById(R.id.login_reset_password)

        val sPref = getSharedPreferences(setting, Context.MODE_PRIVATE)
        val editor = sPref.edit()

        buttonAuth.setOnClickListener{
            if (editLogin.text.toString() != "" && editPassword.text.toString() != "") {
                if(sPref.contains(editLogin.text.toString())) {
                    val userPass = sPref.getString(editLogin.text.toString(), "0")
                    if(userPass.equals(editPassword.text.toString())) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Неверный пароль", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this, "Неверный логин", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Заполните поля", Toast.LENGTH_LONG).show()
            }
        }

        buttonRegis.setOnClickListener{
            val intent = Intent(this, SingUpActivity::class.java)
            startActivity(intent)
        }

        buttonReset.setOnClickListener{
            if(editLogin.text.toString() != "") {
                editor.remove(editLogin.text.toString())
                val intent = Intent(this, SingUpActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Введите логин", Toast.LENGTH_LONG).show()
            }
        }
    }
}