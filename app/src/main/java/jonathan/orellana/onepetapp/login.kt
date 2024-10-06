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
        supportActionBar?.hide();


        //botones para iniciar sesion o registrarse
        val btnregistrarse = findViewById<Button>(R.id.btnregistrarse)

        btnregistrarse.setOnClickListener {
            val pantallaSiguiente = Intent(this, registrarse::class.java)
            startActivity(pantallaSiguiente)
        }

        val btninicarsesion = findViewById<Button>(R.id.btniniciarsesion)

        btninicarsesion.setOnClickListener {
            val pantallaSiguiente = Intent(this, iniciarsesion::class.java)
            startActivity(pantallaSiguiente)
        }
    }
}