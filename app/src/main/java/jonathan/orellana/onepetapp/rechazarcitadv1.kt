package jonathan.orellana.onepetapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class rechazarcitadv1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rechazarcitadv1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnVolverS = findViewById<ImageView>(R.id.btnVolverS)

        btnVolverS.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("ir_a_solicitudes1_citas", true)
            startActivity(intent)
        }

        //Recibir los valores

        val motivoRecibidoR = intent.getStringExtra("motivo_cita")
        val fechaRecibidoR = intent.getStringExtra("fecha_cita")
        val usuarioRecibidoR = intent.getStringExtra("usuario")
        val motivo2RecibidoR = intent.getStringExtra("motivo_cita")
        val descripcionRecibidoR = intent.getStringExtra("descripcion_motivo")

        //Mando a llamar a todos los elementos de la pantalla

        val txtMotivoRechazar = findViewById<TextView>(R.id.txtMotivoRechazo)
        val txtFechaRechazar = findViewById<TextView>(R.id.txtFechaRechazo)
        val txtUsuarioRechazar = findViewById<TextView>(R.id.txtUsuarioRechazo)
        val txtMotivoRechazar2 = findViewById<TextView>(R.id.txtMotivoRechazo2)
        val txtDescripcionRechazar = findViewById<TextView>(R.id.txtDescRechazo)

        //Asigarle los datos recibidos a mis TextView
        txtMotivoRechazar.text = motivoRecibidoR
        txtFechaRechazar.text = fechaRecibidoR
        txtUsuarioRechazar.text = usuarioRecibidoR
        txtMotivoRechazar2.text = motivo2RecibidoR
        txtDescripcionRechazar.text = descripcionRecibidoR
    }
}