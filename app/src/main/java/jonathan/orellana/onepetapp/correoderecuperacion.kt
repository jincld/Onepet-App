package jonathan.orellana.onepetapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class correoderecuperacion : AppCompatActivity() {

    companion object globalvariables {
        lateinit var numeroaleatorio : String
        lateinit var correo : String

    }

    private var buttonClickCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_correoderecuperacion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val correorecuperacion = findViewById<EditText>(R.id.txtcorreorecupracion)
        val btnrecupercion = findViewById<Button>(R.id.btncorreorecuperacion)
        val btnVolver = findViewById<ImageButton>(R.id.btnVolverCDR)

        btnVolver.setOnClickListener {
            val pantallaAnterior = Intent(this, iniciarsesion::class.java)
            startActivity(pantallaAnterior)
        }

       btnrecupercion.setOnClickListener {

           correo = correorecuperacion.text.toString()
           var hayerrores = false


               if (buttonClickCount < 3) {
                   buttonClickCount++
               } else {

                   btnrecupercion.isEnabled = false
                   Toast.makeText(this, "No puedes presionar el botón más de 3 veces", Toast.LENGTH_SHORT).show()
                   Handler().postDelayed({
                       btnrecupercion.isEnabled = true
                       buttonClickCount = 0
                   }, 600000)
               }

           if (!correo.matches(Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$"))){

               correorecuperacion.error = "Ingrese un correo válido"
               hayerrores = true
           } else {
               correorecuperacion.error = null
           }
           if (hayerrores){
           } else {
try {


               CoroutineScope(Dispatchers.Main).launch {
                   numeroaleatorio = (1000..10000).random().toString()
                   enviarCorreo("$correo", "Código de recuperación cuenta OnePet!", "Este es su código de recuperación, ingréselo en la aplicación: $numeroaleatorio")

                   println("este es el correo $correo")
               }
}catch (e: Exception){
    println("este es el error $e")
    Toast.makeText(this, "Error $e", Toast.LENGTH_SHORT).show()

           }
               val recuperarcorreo = Intent(this, codigoconfimarcion::class.java)
               startActivity(recuperarcorreo)
           }

         }
      }
   }
