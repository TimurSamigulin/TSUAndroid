package com.example.tsuandroid.activity

import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.hardware.fingerprint.FingerprintManager
import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tsuandroid.R
import com.example.tsuandroid.activity.util.FingerprintHandler
import java.io.IOException
import java.security.*
import java.security.cert.CertificateException
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey

@Suppress("DEPRECATION")
class LoginActivity: AppCompatActivity() {
    private val SETTING = "mysettings"
    private val PASS = "password"

    private lateinit var keyStore: KeyStore
    private val KEY_NAME: String = "ST"
    private lateinit var cipher: Cipher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val keyguardManager: KeyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        val fingerprintManager: FingerprintManager = getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager

        if(!fingerprintManager.isHardwareDetected)
            Toast.makeText(this, "Версия андройд не подходит", Toast.LENGTH_SHORT).show()
        else {
            genKey()

            if(cipherInit()) {
                val cryptoObject: FingerprintManager.CryptoObject = FingerprintManager.CryptoObject(cipher)
                val helper: FingerprintHandler = FingerprintHandler(this)
                helper.startAuthentication(fingerprintManager, cryptoObject)
            }
        }

        val sPref = getSharedPreferences(SETTING, Context.MODE_PRIVATE)
        val editor = sPref.edit()
        if (!sPref.contains(PASS)) {
            val intent = Intent(this, SingUpActivity::class.java)
            startActivity(intent)
        }

        val editPassword: EditText = findViewById(R.id.login_password)
        val buttonAuth: Button = findViewById(R.id.login_auth)
        val buttonReset: Button = findViewById(R.id.login_reset_password)

        buttonAuth.setOnClickListener {
            if (editPassword.text.toString() != "") {
                val userPass = sPref.getString(PASS, "0")
                if (userPass.equals(editPassword.text.toString())) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Неверный пароль", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Заполните поля", Toast.LENGTH_LONG).show()
            }
        }

        buttonReset.setOnClickListener {
            editor.remove(PASS)
            val intent = Intent(this, SingUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun cipherInit(): Boolean {
        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES+"/"+KeyProperties.BLOCK_MODE_CBC+"/"+KeyProperties.ENCRYPTION_PADDING_PKCS7)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: NoSuchPaddingException) {
            e.printStackTrace()
        }
        try {
            keyStore.load(null)
            val key: SecretKey = keyStore.getKey(KEY_NAME, null) as SecretKey
            cipher.init(Cipher.ENCRYPT_MODE, key)
            return true
        } catch (e1: IOException) {
            e1.printStackTrace()
            return false
        } catch (e1: NoSuchAlgorithmException) {
            e1.printStackTrace()
            return false
        } catch (e1: CertificateException) {
            e1.printStackTrace()
            return false
        } catch (e1: UnrecoverableKeyException) {
            e1.printStackTrace()
            return false
        } catch (e1: KeyStoreException) {
            e1.printStackTrace()
            return false
        } catch (e1: InvalidKeyException) {
            e1.printStackTrace()
            return false
        }
    }

    private fun genKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore")
        } catch (e: KeyStoreException) {
            e.printStackTrace()
        }

        var keyGeneratore: KeyGenerator? = null

        try {
            keyGeneratore = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: NoSuchProviderException) {
            e.printStackTrace()
        }

        try {
            keyStore.load(null)
            keyGeneratore?.init(KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setUserAuthenticationRequired(true)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                .build())
            keyGeneratore?.generateKey()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: CertificateException) {
            e.printStackTrace()
        } catch (e: InvalidAlgorithmParameterException) {
            e.printStackTrace()
        }
    }
}