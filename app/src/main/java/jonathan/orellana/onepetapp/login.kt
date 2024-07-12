package jonathan.orellana.onepetapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Mandar a llamar a todos los elementos


        val btnregistrarse = findViewById<Button>(R.id.btnregistrarse)

        //Programar al botón
        btnregistrarse.setOnClickListener {
            //Navegar entre pantallas
            //Ir a la siguiente pantalla
            val pantallaSiguiente = Intent(this, registrarse::class.java)
            startActivity(pantallaSiguiente)
        }

        //Mandar a llamar
        val btninicarsesion = findViewById<Button>(R.id.btniniciarsesion)
        //Programar al botón
        btninicarsesion.setOnClickListener {
            //Navegar entre pantallas
            //Ir a la siguiente pantalla
            val pantallaSiguiente = Intent(this, iniciarsesion::class.java)
            startActivity(pantallaSiguiente)
        }
    }
}