package jonathan.orellana.onepetapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion

class rechazarcitadv1 : AppCompatActivity() {

    //creamos variables globales
    companion object variablesRechazar {
        lateinit var valor_motivo_cita_rechazar: String
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rechazarcitadv1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Recibir los valores

        val motivoRecibidoR = intent.getStringExtra("motivo_cita")
        val fechaRecibidoR = intent.getStringExtra("fecha_cita")
        val usuarioRecibidoR = intent.getStringExtra("usuario")
        val motivo2RecibidoR = intent.getStringExtra("motivo_cita")
        val descripcionRecibidoR = intent.getStringExtra("descripcion_motivo")

        valor_motivo_cita_rechazar = motivoRecibidoR.toString()
        //Mando a llamar a todos los elementos de la pantalla

        val txtMotivoRechazar = findViewById<TextView>(R.id.txtMotivoRechazo)
        val txtFechaRechazar = findViewById<TextView>(R.id.txtFechaRechazo)
        val txtUsuarioRechazar = findViewById<TextView>(R.id.txtUsuarioRechazo)
        val txtMotivoRechazar2 = findViewById<TextView>(R.id.txtMotivoRechazo2)
        val txtRazonRechazo = findViewById<EditText>(R.id.txtRazonRechazo)
        val btnRechazar = findViewById<Button>(R.id.btnRechazarCita)
        val txtDescripcionRechazar = findViewById<TextView>(R.id.txtDescRechazo)
        val btnCerrar = findViewById<ImageView>(R.id.btnVolverRC)

        //Asigarle los datos recibidos a mis TextView
        txtMotivoRechazar.text = motivoRecibidoR
        txtFechaRechazar.text = fechaRecibidoR
        txtUsuarioRechazar.text = usuarioRecibidoR
        txtMotivoRechazar2.text = motivo2RecibidoR
        txtDescripcionRechazar.text = descripcionRecibidoR

        btnCerrar.setOnClickListener {
            finish()
        }

//programamos del boton para rechazar cita

        btnRechazar.setOnClickListener {

          /*  var hayErrores = false

            //Rechazo Cita
            if (descripcionRecibidoR!!.isEmpty()) {
                txtRazonRechazo.error = "Se debe de describir con detalle el motivo"
                hayErrores = true
            } else if (descripcionRecibidoR.length > 1) {
                txtRazonRechazo.error = "El límite de carácteres es 250"
                hayErrores = true
            }else {
                txtRazonRechazo.error = null
            }

            if(hayErrores) {
                //Hacer algo si hay errores
            }
            else {*/
            CoroutineScope(Dispatchers.IO).launch {
                val objConexion = ClaseConexion().cadenaConexion()
                println(" --------------este es el motivo de cita que quiero usar ${valor_motivo_cita_rechazar}")
                val rechazarCita =
                    objConexion?.prepareStatement("Update tbCitas set estado ='Rechazada',  descripcion_motivo = ? where motivo_cita = ?")!!
                rechazarCita?.setString(1, txtRazonRechazo.text.toString())
                println(" --------------este es el rechazo que quiero ${txtRazonRechazo.text.toString()}")
                rechazarCita?.setString(2, valor_motivo_cita_rechazar)
                rechazarCita?.executeUpdate()

                withContext(Dispatchers.Main) {
                    // Mostrar mensaje y limpiar campos
                    Toast.makeText(this@rechazarcitadv1, "Cita rechazada correctamente", Toast.LENGTH_SHORT).show()
                }

            }
          //  }
            }
        }
}