package jonathan.orellana.onepetapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class item_cardSolicitudesC : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_item_card_solicitudes_c)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        val btnRechazarCitaS = findViewById<Button>(R.id.btnRechazarCitaCS)
//        val btnAceptarCitaS = findViewById<Button>(R.id.btnAceptarCS)
//
//        //todo: clic al boton de Rechazar Cita
//
//        btnRechazarCitaS.setOnClickListener {
//            val pantallaRechazar = Intent(this, rechazarcitadv1::class.java)
//            startActivity(pantallaRechazar)
//        }
//
//        //Todo: boton de Aceptar y Asignar
//
//        btnAceptarCitaS.setOnClickListener{
//            val pantallaRechazar = Intent(this, asignarcitadv1::class.java)
//            startActivity(pantallaRechazar)
//        }
    }
}