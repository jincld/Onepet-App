package jonathan.orellana.onepetapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
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
        //Mandar a llamar a todos los elementos
        val btnduenomas = findViewById<Button>(R.id.btnduenomascota)

        //Programar al botón
        btnduenomas.setOnClickListener {
            //Navegar entre pantallas
            //Ir a la siguiente pantalla
            val pantallaSiguiente = Intent(this, registroduenomascotas::class.java)
            startActivity(pantallaSiguiente)
        }

        //Mandar a llamar
        val btnclinicvet = findViewById<Button>(R.id.btnclinicaveterinaria)
        //Programar al botón
        btnclinicvet.setOnClickListener {
            //Navegar entre pantallas
            //Ir a la siguiente pantalla
            val pantallaSiguiente = Intent(this, registroduenovet::class.java)
            startActivity(pantallaSiguiente)
        }
    }
}