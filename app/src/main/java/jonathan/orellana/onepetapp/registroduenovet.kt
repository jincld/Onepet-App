package jonathan.orellana.onepetapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import java.security.MessageDigest

import java.util.UUID

class registroduenovet : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registroduenovet)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fun hashSHA256(contraescrita: String): String {
            val bytes = MessageDigest.getInstance("SHA-256").digest(contraescrita.toByteArray())
            return bytes.joinToString("") {"%02x".format(it)}

        }

        val txtnombreadminvet =findViewById<TextView>(R.id.txtnombreadminvet)
        val txtcorreoadminvet =findViewById<TextView>(R.id.txtcorreodminvet)
        val txtcontraadminvet =findViewById<TextView>(R.id.txtcontraadminvet)
        val btninicarsesionvet =findViewById<TextView>(R.id.btniniciarsesionvet)

        btninicarsesionvet.setOnClickListener{
            GlobalScope.launch(Dispatchers.IO){
                val objConexion = ClaseConexion().cadenaConexion()
                val contraencriptada = hashSHA256(txtcontraadminvet.text.toString())

                val crearusuario = objConexion?.prepareStatement("insert into tbUsuarios (UUID_usuario, nombre_usuario, contra_usuario, correo_usuario, rol) values (?, ?, ?, ?, (Select uuid_rol from tbRoles where nombre_rol = 'Dueño mascota'))")!!
                crearusuario.setString(1, UUID.randomUUID().toString())
                crearusuario.setString(2, txtnombreadminvet.text.toString())
                crearusuario.setString(3,contraencriptada)
                crearusuario.setString(4, txtcorreoadminvet.text.toString())
                crearusuario.executeUpdate()

                withContext(Dispatchers.Main){
                    //mostrar mensaje y limpiar campos
                    Toast.makeText(this@registroduenovet, "Usuario registrado", Toast.LENGTH_SHORT).show()
                    txtnombreadminvet.setText("")
                    txtcontraadminvet.setText("")
                    txtcorreoadminvet.setText("")

                    val login = Intent(this@registroduenovet, iniciarsesion::class.java)
                    startActivity(login)


                }
            }
        }
    }
}

