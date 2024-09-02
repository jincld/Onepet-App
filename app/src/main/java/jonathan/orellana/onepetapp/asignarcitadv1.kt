package jonathan.orellana.onepetapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class asignarcitadv1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_asignarcitadv1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnVolverAS = findViewById<ImageView>(R.id.btnVolverAS)

        btnVolverAS.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("ir_a_solicitudes_citas", true)
            startActivity(intent)
        }

        //Recibir los valores

        val motivoRecibido = intent.getStringExtra("motivo_cita")
        val fechaRecibido = intent.getStringExtra("fecha_cita")
        val usuarioRecibido = intent.getStringExtra("usuario")
        val motivo2Recibido = intent.getStringExtra("motivo_cita")
        val descripcionRecibido = intent.getStringExtra("descripcion_motivo")

        //Mando a llamar a todos los elementos de la pantalla

        val txtMotivoAsignar = findViewById<TextView>(R.id.txtMotivoAsignacion)
        val txtFechaAsignar = findViewById<TextView>(R.id.txtFechaAsignacion)
        val txtUsuarioAsignar = findViewById<TextView>(R.id.txtUsuarioAsignacion)
        val txtMotivoAsignar2 = findViewById<TextView>(R.id.txtMotivoAsignacion2)
        val txtDescripcionAsignar = findViewById<TextView>(R.id.txtDescAsignacion)

        //Asigarle los datos recibidos a mis TextView
        txtMotivoAsignar.text = motivoRecibido
        txtFechaAsignar.text = fechaRecibido
        txtUsuarioAsignar.text = usuarioRecibido
        txtMotivoAsignar2.text = motivo2Recibido
        txtDescripcionAsignar.text = descripcionRecibido

    }
}