package jonathan.orellana.onepetapp

import android.content.Intent
import android.os.Bundle
import android.text.InputType
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
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import java.security.MessageDigest

class nuevacontrasena : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_nuevacontrasena)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        fun hashSHA256(contraescrita: String): String {
            val bytes = MessageDigest.getInstance("SHA-256").digest(contraescrita.toByteArray())
            return bytes.joinToString("") {"%02x".format(it)}

        }

        val txtnuevacontra = findViewById<EditText>(R.id.txtnuevacontra)
        val btnnuevacontra = findViewById<Button>(R.id.btnnuevacontra)
        val correoop = correoderecuperacion.globalvariables.correo
        val txtcontraconfirm = findViewById<EditText>(R.id.txtnuevacontraconfirm)
        val btnVolver = findViewById<ImageButton>(R.id.btnVolverCDCC)
        val btnojo1 = findViewById<ImageButton>(R.id.ojorecu1)
        val btnojo2 = findViewById<ImageButton>(R.id.ojoconfirmrecu2)

        btnVolver.setOnClickListener {
            val pantallaAnterior = Intent(this, correoderecuperacion::class.java)
            startActivity(pantallaAnterior)
        }

btnojo1.setOnClickListener{
    if (txtnuevacontra.inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD){
    txtnuevacontra.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
} else {
    txtnuevacontra.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

    }
}

    btnojo2.setOnClickListener{
        if (txtcontraconfirm.inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD){
            txtcontraconfirm.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        } else {
            txtcontraconfirm.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        }

    }



        btnnuevacontra.setOnClickListener {
            val contra = txtnuevacontra.text.toString()
            val nuevacontraconfimr =txtcontraconfirm.text.toString()
            var hayerrores = false


            //validaciones
           /* if (contra.length <= 8) {
                txtnuevacontra.error = "La contraseña debe tener más de 8 carácteres"
                hayerrores = true
            } else {
                txtnuevacontra.error = null
            }*/

            if (contra.isEmpty()) {
                txtnuevacontra.error = "Complete todos los campos"
                hayerrores = true
            }else if (contra.length > 100) {
                txtnuevacontra.error = "El límite de carácteres es 100"
                hayerrores = true
            }else if (contra.length <= 8) {
                txtnuevacontra.error = "La contraseña debe tener más de 8 carácteres"
                hayerrores = true
            } else {
                txtnuevacontra.error = null
            }

            if (nuevacontraconfimr.isEmpty()) {
                txtcontraconfirm.error = "Complete todos los campos"
                hayerrores = true
            } else {
                txtcontraconfirm.error = null
            }

            if (nuevacontraconfimr == txtnuevacontra.text.toString()) {

                txtcontraconfirm.error = null

            } else {
                txtcontraconfirm.error = "Las contraseñas no coinciden"
                hayerrores = true
            }

            if (hayerrores){
            } else {

                //corrutina para actualizar la contraseña
                CoroutineScope(Dispatchers.IO).launch {
                    val contranueva = hashSHA256(txtnuevacontra.text.toString())
                    val objConexion = ClaseConexion().cadenaConexion()

                    val resulSet = objConexion?.prepareStatement("update tbUsuariosOne set contra_usuario = ? where correo_usuario = ? ")!!
                    resulSet.setString(1, contranueva)
                    resulSet.setString(2,correoop )
                    resulSet.executeUpdate()

                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@nuevacontrasena, "Contraseña actualizada correctamente", Toast.LENGTH_SHORT).show()



                    }

        }
                val recuperar = Intent(this@nuevacontrasena, iniciarsesion::class.java)
                startActivity(recuperar)
             }
          }
      }
   }

