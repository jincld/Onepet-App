package jonathan.orellana.onepetapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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
    }
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



       btnrecupercion.setOnClickListener {

           val correo = correorecuperacion.text.toString()
           var hayerrores = false

           if (!correo.matches(Regex("[a-zA-Z0-9._-]+@[a-z]+[.][a-z]+"))){

               correorecuperacion.error = "Ingrese un correo valido"
               hayerrores = true
           } else {
               correorecuperacion.error = null
           }
           if (hayerrores){
           } else {
try {


               CoroutineScope(Dispatchers.Main).launch {
                   numeroaleatorio = (1000..10000).random().toString()
                   enviarCorreo("$correo", "Codigo de recuperacion", "Codigo de recuperacion, No olvide su contrasña $numeroaleatorio")

                   println("este es el coresrasfasdf $correo")
               }
}catch (e: Exception){
    println("este es el eroaeroasdf $e")
    Toast.makeText(this, "aasdfdsf $e", Toast.LENGTH_SHORT).show()
              // val recuperarcorreo = Intent(this, codigoconfimarcion::class.java)
              // startActivity(recuperarcorreo)
           }
           }
        }
    }
}