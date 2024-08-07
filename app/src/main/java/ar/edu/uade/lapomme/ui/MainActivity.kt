package ar.edu.uade.lapomme.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
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
import ar.edu.uade.lapomme.model.Cocktail
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var rvCocktails: RecyclerView
    private lateinit var adapter: MainAdapter
    private lateinit var lnLogo: LinearLayout
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var search : SearchView
    private lateinit var pb: ProgressBar
    private lateinit var fav: Button

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

        viewModel= ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.cocktails.observe(this) {
            adapter.showItems(it)
            pb.visibility = View.INVISIBLE
        }

        fav = findViewById(R.id.btnFav)

        fav.setOnClickListener{
            viewModel.fav = true
            viewModel.init(this)
        }

        lnLogo = findViewById(R.id.lnLogo)

        lnLogo.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

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