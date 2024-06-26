package ar.edu.uade.lapomme.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ar.edu.uade.lapomme.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var userName = "prueba"
        var selectedID = 1

        var prefs = getSharedPreferences("ar.edu.uade.lapomme.sharedpref", Context.MODE_PRIVATE)
        prefs.edit().putString("user", userName).apply()

        Handler(Looper.getMainLooper()).postDelayed({
            var intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("ID", selectedID)
            startActivity(intent)
            finish()
        }, 4000)
    }
}