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

        val txtnombreadminvet =findViewById<EditText>(R.id.txtnombreadminvet)
        val txtcorreoadminvet =findViewById<EditText>(R.id.txtcorreodminvet)
        val txtcontraadminvet =findViewById<EditText>(R.id.txtcontraadminvet)
        val btnfoto = findViewById<Button>(R.id.btnftdeperfil)
        val btninicarsesionvet = findViewById<TextView>(R.id.btniniciarsesionvet)

        fun obtenerUuidRol(): String? {
            val objConexion = ClaseConexion().cadenaConexion()
            val statement = objConexion?.createStatement()
            val resulSet = statement?.executeQuery("Select UUID_rol from tbRolesUsuarios where nombre_rol = 'Admin Vet'")!!
            val usuarios = mutableListOf<dataclassusuarios>()
            var uuidRol: String? = null

            if (resulSet?.next() == true) {
                uuidRol = resulSet.getString("UUID_rol")
                println("este es el uuid traido desde el if $uuidRol")
            }

            println("este es el uuid traido desde la funcion $uuidRol")
            return uuidRol
        }

        btninicarsesionvet.setOnClickListener{

            val nombre = txtnombreadminvet.text.toString()
            val correo = txtcorreoadminvet.text.toString()
            val contra = txtcontraadminvet.text.toString()
            var hayerrores = false

            if (nombre.isEmpty()) {
               txtnombreadminvet.error = "Complete todos lo campos"
                hayerrores = true
            } else {
                txtnombreadminvet.error = null
            }

            if (correo.isEmpty()) {
                txtcorreoadminvet.error = "Complete todos lo campos"
                hayerrores = true
            } else {
                txtcorreoadminvet.error = null
            }

            if (contra.isEmpty()) {
                txtcontraadminvet.error = "Complete todos lo campos"
                hayerrores = true
            } else {
                txtcontraadminvet.error = null
            }

            if (!correo.matches(Regex("[a-zA-Z0-9._-]+@[a-z]+[.][a-z]+"))){

                txtcorreoadminvet.error = "Ingrese un correo valido"
                hayerrores = true
            } else {
                txtcorreoadminvet.error = null
            }

            if (contra.length <= 8) {
                txtcontraadminvet.error = "La contraseña debe tener más de 8 caracteres"
                hayerrores = true
            } else {
               txtcontraadminvet.error = null
            }

            if (hayerrores){
            } else {

                GlobalScope.launch(Dispatchers.IO){
                    val objConexion = ClaseConexion().cadenaConexion()
                    val contraencriptada = hashSHA256(txtcontraadminvet.text.toString())
                    val uuidTraido = obtenerUuidRol()

                    val crearusuario = objConexion?.prepareStatement("insert into tbUsuariosOne (UUID_usuario, nombre_usuario, contra_usuario, correo_usuario, rol) values (?, ?, ?, ?, ?)")!!
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

