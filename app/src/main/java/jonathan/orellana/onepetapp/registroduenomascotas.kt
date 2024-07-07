package jonathan.orellana.onepetapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dataclassusuarios
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import java.security.MessageDigest

import java.util.UUID
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.SecretKeySpec

class registroduenomascotas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registroduenomascotas)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        fun hashSHA256(contraescrita: String): String {
            val bytes = MessageDigest.getInstance("SHA-256").digest(contraescrita.toByteArray())
            return bytes.joinToString("") {"%02x".format(it)}

        }

        val  txtnombreduenomas = findViewById<TextView>(R.id.txtnombreduenomas)
        val  txtcorreoduenomas = findViewById<TextView>(R.id.txtcorreoduenomas)
        val  txtcontraduenomas = findViewById<TextView>(R.id.txtcontraduenomas)
        val  btnftoperfil = findViewById<Button>(R.id.btnagregarimagendueno)
        val  btnsiguiente = findViewById<TextView>(R.id.btnsieguienteduenomascota)


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

        btnsiguiente.setOnClickListener{
         GlobalScope.launch(Dispatchers.IO){

          val objConexion = ClaseConexion().cadenaConexion()
             val contraencriptada = hashSHA256(txtcontraduenomas.text.toString())

             val uuidTraido = obtenerUuidRol()

             val crearusuario = objConexion?.prepareStatement("insert into tbUsuarios (UUID_usuario, nombre_usuario, contra_usuario, correo_usuario, rol) values (?, ?, ?, ?, ?)")!!
             crearusuario.setString(1, UUID.randomUUID().toString())
             crearusuario.setString(2, txtnombreduenomas.text.toString())
             crearusuario.setString(3, contraencriptada)
             crearusuario.setString(4, txtcorreoduenomas.text.toString())
             crearusuario.setString(5, uuidTraido)
             println("este es el uuid traido antes del execute  $uuidTraido")
             crearusuario.executeUpdate()
                withContext(Dispatchers.Main){
                 //mostrar mensaje y limpiar campos
                 Toast.makeText(this@registroduenomascotas, "Usuario registrado", Toast.LENGTH_SHORT).show()
                 txtnombreduenomas.setText("")
                 txtcontraduenomas.setText("")
                txtcorreoduenomas.setText("")
                 val login = Intent(this@registroduenomascotas, iniciarsesion::class.java)
                 startActivity(login)

                }
             }
          }
       }
    }



//enlace iniciar sesion

//