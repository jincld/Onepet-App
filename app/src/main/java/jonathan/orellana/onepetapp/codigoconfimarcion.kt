package jonathan.orellana.onepetapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
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

class codigoconfimarcion : AppCompatActivity() {

    private var buttonClickCount = 0
    lateinit var numeroAleatorio1 : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_codigoconfimarcion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val txtcodigoconfimacion = findViewById<EditText>(R.id.txtcodigorecuperacion)
        val btncodigoconfirmacion = findViewById<Button>(R.id.btncodigorecuperacion)
        val numeroTraido = correoderecuperacion.globalvariables.numeroaleatorio
        val txtEnviarDeNuevo = findViewById<TextView>(R.id.txtEnviarDeNuevo)
        val btnVolver = findViewById<ImageButton>(R.id.btnVolverCCR)

        btnVolver.setOnClickListener {
            val pantallaAnterior = Intent(this, correoderecuperacion::class.java)
            startActivity(pantallaAnterior)
        }

      btncodigoconfirmacion.setOnClickListener{
          val numerobtenido = txtcodigoconfimacion.text.toString().toIntOrNull()

         if (numerobtenido==null){
              Toast.makeText(this, "Ingrese un numero valido", Toast.LENGTH_SHORT).show()
              return@setOnClickListener
       }
         if (numerobtenido.toString() == numeroTraido){
             Toast.makeText(this, "Codigo de confimacion correcto", Toast.LENGTH_SHORT).show()
             val recuperar = Intent(this, nuevacontrasena::class.java)
             startActivity(recuperar)
         } else {
          Toast.makeText(this, "Número incorrecto, intente de nuevo", Toast.LENGTH_SHORT).show()
        }


       }

        txtEnviarDeNuevo.setOnClickListener {

            if (buttonClickCount < 3) {
                buttonClickCount++
            } else {

                txtEnviarDeNuevo.isEnabled = false
                Toast.makeText(this, "No puedes presionar el botón más de 3 veces", Toast.LENGTH_SHORT).show()
                Handler().postDelayed({
                    txtEnviarDeNuevo.isEnabled = true
                    buttonClickCount = 0
                }, 600000)
            }
                try {


                    CoroutineScope(Dispatchers.Main).launch {
                        correoderecuperacion.globalvariables.numeroaleatorio = (1000..10000).random().toString()
                        enviarCorreo("${correoderecuperacion.correo}", "Código de recuperación cuenta OnePet!", "Este es su código de recuperación, ingréselo en la aplicación: ${correoderecuperacion.numeroaleatorio}")

                        println("este es el correo ${correoderecuperacion.correo}")

                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@codigoconfimarcion, "Código enviado nuevamente", Toast.LENGTH_LONG).show()
                        }
                    }
                }catch (e: Exception){
                    println("este es el error $e")
                    Toast.makeText(this, "Error $e", Toast.LENGTH_SHORT).show()

                }


            }

        }
}
