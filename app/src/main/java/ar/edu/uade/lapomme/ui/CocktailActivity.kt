package ar.edu.uade.lapomme.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import ar.edu.uade.lapomme.R
import ar.edu.uade.lapomme.data.CocktailsDataSource
import com.bumptech.glide.Glide

class CocktailActivity : AppCompatActivity() {

    private lateinit var viewModel: CocktailViewModel
    private lateinit var lnlLogo: LinearLayout
    private lateinit var pb: ProgressBar
    private lateinit var img: ImageView
    private lateinit var idTrago: TextView
    private lateinit var name: TextView
    private lateinit var alc: TextView
    private lateinit var ing1: TextView
    private lateinit var ing2: TextView
    private lateinit var ing3: TextView
    private lateinit var ing4: TextView
    private lateinit var inst: TextView
    private lateinit var add: Button
    private lateinit var del: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cocktail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val id = intent.getStringExtra("id")!!

        lnlLogo = findViewById(R.id.lnlLogo)

        lnlLogo.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        add = findViewById(R.id.btnAdd)
        del = findViewById(R.id.btnDel)

        add.setOnClickListener{
            viewModel.addFav(id)
            Toast.makeText(this, "Se agregó el elemento a favoritos.", Toast.LENGTH_LONG).show()
        }

        del.setOnClickListener{
            viewModel.deleteFav(id)
            Toast.makeText(this, "Se eliminó el elemento de favoritos.", Toast.LENGTH_LONG).show()
        }

        pb = findViewById(R.id.progressbar2)
        img = findViewById(R.id.imgCocktailInfo)
        idTrago = findViewById(R.id.txtId)
        name = findViewById(R.id.txtName)
        alc = findViewById(R.id.txtAlc)
        ing1 = findViewById(R.id.txtIng1)
        ing2 = findViewById(R.id.txtIng2)
        ing3 = findViewById(R.id.txtIng3)
        ing4 = findViewById(R.id.txtIng4)
        inst = findViewById(R.id.txtInst)

        viewModel = ViewModelProvider(this)[CocktailViewModel::class.java]

        viewModel.cocktail.observe(this) { Cocktail ->
            pb.visibility = View.INVISIBLE
            idTrago.text = "Id: " + Cocktail.idDrink
            name.text = Cocktail.strDrink
            alc.text = Cocktail.strAlcoholic
            ing1.text = "Ingredients: " + Cocktail.strIngredient1
            ing2.text = Cocktail.strIngredient2
            ing3.text = Cocktail.strIngredient3
            ing4.text = Cocktail.strIngredient4
            inst.text = "Recipe: " + Cocktail.strInstructions

            Glide.with(this)
                .load(Cocktail.strDrinkThumb)
                .into(img)
        }

        viewModel.init(id, this)
        pb.visibility = View.VISIBLE
    }
}
