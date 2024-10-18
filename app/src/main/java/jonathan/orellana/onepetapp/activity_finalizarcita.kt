package jonathan.orellana.onepetapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
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

class activity_finalizarcita : AppCompatActivity() {

    //creamos variables globales
    companion object variablesFinalizar {
        lateinit var valor_motivo_cita_terminar: String
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_finalizarcita)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Recibir los valores

        val motivoRecibidoR = intent.getStringExtra("motivo_cita")
        val fechaRecibidoR = intent.getStringExtra("fecha_cita")
        val mascotaRecibidaR = intent.getStringExtra("mascota")
        //val descripcionRecibidoR = intent.getStringExtra("descripcion_motivo")

        valor_motivo_cita_terminar = motivoRecibidoR.toString()
        //Mando a llamar a todos los elementos de la pantalla

        val txtMotivoFinalizar = findViewById<TextView>(R.id.txtMotivoFinalizar)
        val txtFechaFinalizar = findViewById<TextView>(R.id.txtFechaFinalizar)
        val txtDetallesRecibidos = findViewById<TextView>(R.id.txtDetallesFinalizar)
        val txtMascotaFinalizar = findViewById<TextView>(R.id.txtMascotaFinalizar)
        val btnCerrar = findViewById<ImageView>(R.id.btnVolverTC)
        val btnFinalizar = findViewById<Button>(R.id.btnFinalizarCitaACT)

        //Asigarle los datos recibidos a mis TextView
        txtMotivoFinalizar.text = motivoRecibidoR
        txtFechaFinalizar.text = fechaRecibidoR
        txtMascotaFinalizar.text = mascotaRecibidaR
        //txtDetallesRecibidos.text = descripcionRecibidoR

        btnCerrar.setOnClickListener {
            val pantallaprincipal = Intent(this, MainActivity::class.java)
            startActivity(pantallaprincipal)
            finish()
        }


//programamos del boton para rechazar cita
        btnFinalizar.setOnClickListener {
            var hayErrores = false
            //Rechazo Cita
            if (txtDetallesRecibidos.text.isEmpty()) {
                txtDetallesRecibidos.error = "Se debe de explicar los detalles posteriores de haber atendido a la mascota"
                hayErrores = true
            } else if (txtDetallesRecibidos.text.length > 150) {
                txtDetallesRecibidos.error = "El límite de carácteres es 150"
                hayErrores = true
            }else {
                txtDetallesRecibidos.error = null
            }

            if(!hayErrores) {
                CoroutineScope(Dispatchers.IO).launch {

                    //Terminar cita tbcita
                    val objConexion = ClaseConexion().cadenaConexion()
                    val rechazarCita = objConexion?.prepareStatement("Update tbCitas set estado ='Finalizada',  detalle_cita = ? where motivo_cita = ?")!!
                    rechazarCita?.setString(1, txtDetallesRecibidos.text.toString())
                    rechazarCita?.setString(2, valor_motivo_cita_terminar)
                    rechazarCita?.executeUpdate()

                    //Terminar cita tbcitaemp
                    val rechazarCitaEmp = objConexion?.prepareStatement("Update tbCitasEmp set estado = 'Finalizada',  detalle_cita = ? where motivo_cita = ?")!!
                    rechazarCitaEmp?.setString(1, txtDetallesRecibidos.text.toString())
                    rechazarCitaEmp?.setString(2, valor_motivo_cita_terminar)
                    rechazarCitaEmp?.executeUpdate()

                    withContext(Dispatchers.Main) {
                        // Mostrar mensaje y limpiar campos
                        Toast.makeText(this@activity_finalizarcita, "Cita marcada como finalizada", Toast.LENGTH_SHORT).show()
                    }

                }
            }
            else {
            }
        }
    }
}