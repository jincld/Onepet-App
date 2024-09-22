package jonathan.orellana.onepetapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class vet_para_asignar_cita : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_asignar_cita)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtVerNombreVetUser = findViewById<TextView>(R.id.txtVerNombreVetUser)
        val txtVerUbicacionVetUser = findViewById<TextView>(R.id.txtVerUbicacionVetUser)
        val txtVerNitVetUser = findViewById<TextView>(R.id.txtVerNitVetUser)
        val txtVerContactoVetUser = findViewById<TextView>(R.id.txtVerContactoVetUser)
        val txtVerCorreoVetUser = findViewById<TextView>(R.id.txtVerCorreoVetUser)
        val txtVerServiciosVetUser = findViewById<TextView>(R.id.txtVerServiciosVetUser)
        val btnCerrar = findViewById<Button>(R.id.btnAsignarCita)
        val btnVolverVet = findViewById<ImageButton>(R.id.btnVolverAGIF)

        btnVolverVet.setOnClickListener {
            finish()
        }

        //Asignarle los datos recibidos a mis textos
//Segundo = primero

        var nombre = intent.getStringExtra("Nombre")
        val ubicacion = intent.getStringExtra("Ubicacion")
        val nit = intent.getStringExtra("NIT")
        val contacto = intent.getStringExtra("Contacto")
        val correo = intent.getStringExtra("Correo")
        val descripcion = intent.getStringExtra("Descripcion")

        txtVerNombreVetUser.text = nombre
        txtVerUbicacionVetUser.text = ubicacion
        txtVerNitVetUser.text = nit
        txtVerContactoVetUser.text = contacto
        txtVerCorreoVetUser.text = correo
        txtVerServiciosVetUser.text = descripcion

        btnCerrar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("ir_a_agregar_Cita", true)
            startActivity(intent)
        }
    }
}