package jonathan.orellana.onepetapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class registrarse : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrarse)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.hide();

        val btnduenomas = findViewById<Button>(R.id.btnduenomascota)
        val btnVolverComo = findViewById<ImageButton>(R.id.btnVolverComo)

        //definimos a que pantalla nnos lleva cada boton
        btnVolverComo.setOnClickListener {
            val pantallaAnterior = Intent(this, login::class.java)
            startActivity(pantallaAnterior)
        }

        btnduenomas.setOnClickListener {
            val pantallaSiguiente = Intent(this, registroduenomascotas::class.java)
            startActivity(pantallaSiguiente)
        }

        val btnclinicvet = findViewById<Button>(R.id.btnclinicaveterinaria)
        btnclinicvet.setOnClickListener {
            val pantallaSiguiente = Intent(this, registroduenovet::class.java)
            startActivity(pantallaSiguiente)
        }
    }
}