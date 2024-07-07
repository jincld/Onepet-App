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
import dataclassusuarios
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
        val btnfoto = findViewById<Button>(R.id.btnftdeperfil)
        val btninicarsesionvet =findViewById<TextView>(R.id.btniniciarsesionvet)

        fun obtenerUuidRol(): String? {
            val objConexion = ClaseConexion().cadenaConexion()
            val statement = objConexion?.createStatement()
            val resulSet = statement?.executeQuery("SELECT UUID_rol FROM tbRoles WHERE nombre_rol = 'Due o mascota'")
            var uuidRol: String? = null

            if (resulSet?.next() == true) {
                uuidRol = resulSet.getString("UUID_rol")
                println("este es el uuid traido desde el if $uuidRol")
            }

            println("este es el uuid traido desde la funcion $uuidRol")
            return uuidRol
        }

        btninicarsesionvet.setOnClickListener{

            if (txtnombreadminvet.text.isEmpty() || txtcorreoadminvet.text.isEmpty() || txtcontraadminvet.text.isEmpty()) {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_LONG).show()

            if (txtcorreoadminvet.text.matches("[a-zA-Z0-9._-]+@[a-z]\\\\.+[a-z]+]".toRegex())) {
                Toast.makeText(this, "campo agregado", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "ingrese campos validos", Toast.LENGTH_LONG).show()

            }
            if (txtcontraadminvet.text.length <= 7) {
                Toast.makeText(this, "La contraseña debe tenr mas de 8 digitos", Toast.LENGTH_LONG).show()
            }
            GlobalScope.launch(Dispatchers.IO){
                val objConexion = ClaseConexion().cadenaConexion()
                val contraencriptada = hashSHA256(txtcontraadminvet.text.toString())
                val uuidTraido = obtenerUuidRol()

                val crearusuario = objConexion?.prepareStatement("insert into tbUsuarios (UUID_usuario, nombre_usuario, contra_usuario, correo_usuario, rol) values (?, ?, ?, ?, (Select uuid_rol from tbRoles where nombre_rol = 'Dueno mascota'))")!!
                crearusuario.setString(1, UUID.randomUUID().toString())
                crearusuario.setString(2, txtnombreadminvet.text.toString())
                crearusuario.setString(3,contraencriptada)
                crearusuario.setString(4, txtcorreoadminvet.text.toString())
                crearusuario.setString(5,uuidTraido)
                println("este es el uuid traido antes del execute  $uuidTraido")
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
}

