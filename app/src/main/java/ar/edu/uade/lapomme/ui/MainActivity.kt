package ar.edu.uade.lapomme.ui

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.edu.uade.lapomme.R
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var rvCocktails: RecyclerView
    private lateinit var adapter: MainAdapter
    private lateinit var firebaseAuth: FirebaseAuth
    //lateinit var search : EditText
    lateinit var pb: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rvCocktails = findViewById(R.id.rvCocktails)
        rvCocktails.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter()
        rvCocktails.adapter = adapter
        pb = findViewById(R.id.progressbar1)
        // La función de búsqueda aún no funciona, aunque esté la barra en la app
        //search = findViewById(R.id.txtSearch)

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        viewModel= ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.cocktails.observe(this) {
            adapter.Update(it)
            pb.visibility = View.INVISIBLE
        }

        viewModel.init(this)
        pb.visibility = View.VISIBLE
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null)
        {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}