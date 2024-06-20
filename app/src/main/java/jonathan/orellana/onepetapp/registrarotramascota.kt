package jonathan.orellana.onepetapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class registrarotramascota : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrarotramascota)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.hide();

        val btnVolver = findViewById<ImageButton>(R.id.btnVolverrom)

        btnVolver.setOnClickListener {
            val pantallaSiguiente = Intent(this, registrarmascotadm::class.java)
            startActivity(pantallaSiguiente)
        }

        val btnRegistrarOtra = findViewById<Button>(R.id.btnRegistrarOtra)

        btnRegistrarOtra.setOnClickListener {
            val pantallaSiguiente = Intent(this, registrarmascotadm::class.java)
            startActivity(pantallaSiguiente)
        }
    }
}