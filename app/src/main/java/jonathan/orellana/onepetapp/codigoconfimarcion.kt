package jonathan.orellana.onepetapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class codigoconfimarcion : AppCompatActivity() {

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
          Toast.makeText(this, "Número incorrecto, intenta de nuevo", Toast.LENGTH_SHORT).show()
        }


       }

    }
}