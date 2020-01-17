package com.example.tsuandroid.activity.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.fingerprint.FingerprintManager
import android.os.CancellationSignal
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.tsuandroid.activity.MainActivity


@Suppress("DEPRECATION")
class FingerprintHandler(val context: Context): FingerprintManager.AuthenticationCallback() {

    fun startAuthentication(fingerprintManager: FingerprintManager, cryptoObject: FingerprintManager.CryptoObject) {
        val cenCancellationSignal = CancellationSignal()
        if(ActivityCompat.checkSelfPermission(context, android.Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED)
            return

        fingerprintManager.authenticate(cryptoObject, cenCancellationSignal, 0, this, null)
    }

    override fun onAuthenticationFailed() {
        super.onAuthenticationFailed()
        Toast.makeText(context, "Auth Failed", Toast.LENGTH_LONG).show()
    }

    override fun onAuthenticationSucceeded(result: FingerprintManager.AuthenticationResult?) {
        super.onAuthenticationSucceeded(result)
        context.startActivity(Intent(context, MainActivity::class.java))
    }
}
