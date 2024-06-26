package ar.edu.uade.lapomme.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.SearchView
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
    lateinit var search : SearchView
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
        search = findViewById(R.id.txtSearch)

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(busqueda: String): Boolean {
                adapter.searchItems(busqueda)
                return true
            }
        })

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        viewModel= ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.cocktails.observe(this) {
            adapter.showItems(it)
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