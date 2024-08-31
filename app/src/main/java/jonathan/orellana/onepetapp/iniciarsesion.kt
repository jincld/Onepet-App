package jonathan.orellana.onepetapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import jonathan.orellana.onepetapp.ui.home.HomeFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import java.security.MessageDigest
import java.util.UUID

class iniciarsesion : AppCompatActivity() {

    companion object variablesGlobalesLogin{
        //Prueba
        lateinit var correodelUsuarioGlobal: String
        lateinit var valorRolUsuario: String
        var uuidRol: String? = null

        //MODIFICADO
        //ERROR AQUI PORQUE NO LO INICIALIZO DE ALGUNA FORMA
        lateinit var idDeUsuario: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_iniciarsesion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.hide();

        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        getWindow().setNavigationBarColor(getResources().getColor(R.color.white));

        fun hashSHA256(contraescrita: String): String {
            val bytes = MessageDigest.getInstance("SHA-256").digest(contraescrita.toByteArray())
            return bytes.joinToString("") { "%02x".format(it) }
        }

        val txtcorreoiniciar = findViewById<EditText>(R.id.txtcorreoiniciar)
        val txtcontrainiciar = findViewById<EditText>(R.id.txtcontrasenainicio)
        val btnrecuperarcontra = findViewById<TextView>(R.id.btnrecuperarcontra)
        val btninicarsesion = findViewById<Button>(R.id.btniniciarsesionhome)
        val btnVolver = findViewById<ImageButton>(R.id.btnVolverIS)


        //   fun obtenerUuidRol(): String? {

        GlobalScope.launch(Dispatchers.IO) {

            val objConexion = ClaseConexion().cadenaConexion()
            val resulSet = objConexion?.prepareStatement("SELECT rol FROM tbUsuariosOne WHERE correo_usuario = ? ")!!
            resulSet.setString(1, txtcorreoiniciar.text.toString())

            val resultado = resulSet.executeQuery()

            if (resultado.next()) {

                uuidRol = resultado.getString("rol")

                println("este es el uuid traido desde el if $uuidRol")
            }



            println("este es el uuid traido desde la funcion $uuidRol")

        }

        //MODIFICADO
        //ERROR POR invokeSuspend, no se lo que significa pero no me deja iniciar sesion
        GlobalScope.launch(Dispatchers.IO) {

            val objConexion = ClaseConexion().cadenaConexion()
            val resulSet = objConexion?.prepareStatement("SELECT UUID_usuario FROM tbUsuariosOne WHERE correo_usuario = ? ")!!
            resulSet.setString(1, txtcorreoiniciar.text.toString())

            val resultado = resulSet.executeQuery()

            if (resultado.next()) {

                idDeUsuario = resultado.getString("UUID_usuario")

                println("este es el uuid traido desde el if $idDeUsuario")
            }
            println("este es el uuid traido desde la funcion $idDeUsuario")

        }

        btnVolver.setOnClickListener {
            val pantallaAnterior = Intent(this, login::class.java)
            startActivity(pantallaAnterior)
        }

        btninicarsesion.setOnClickListener{
            valorRolUsuario = uuidRol.toString()
            correodelUsuarioGlobal = txtcorreoiniciar.text.toString()


            val pantallaprincipal = Intent(this, MainActivity::class.java)

                GlobalScope.launch(Dispatchers.IO) {
                    val objConexion = ClaseConexion().cadenaConexion()
                    val contraencriptada = hashSHA256(txtcontrainiciar.text.toString())

                    val resulSet = objConexion?.prepareStatement("SELECT rol FROM tbUsuariosOne where correo_usuario = ? and contra_usuario = ?")!!
                    resulSet.setString(1, txtcorreoiniciar.text.toString())
                    resulSet.setString(2, contraencriptada)
                    val resultado = resulSet.executeQuery()

                    if (resultado.next()) {
                        valorRolUsuario = resultado.getString("ROL")
                        startActivity(pantallaprincipal)

                    }else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@iniciarsesion, "Usuario o contraseña inválidos", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            /*    startActivity(pantallaprincipal)*/

           /* GlobalScope.launch(Dispatchers.IO) {
                val objconexion = ClaseConexion().cadenaConexion()
                val contraencriptada = hashSHA256(txtcontrainiciar.text.toString())

                val comprobarusuario = objconexion?.prepareStatement("SELECT uuid_usuario FROM tbUsuariosOne where correo_usuario = ? and contra_usuario = ?")!!
                comprobarusuario.setString(1, txtcorreoiniciar.text.toString())
                comprobarusuario.setString(2, contraencriptada)

                val resultado = comprobarusuario.executeQuery()

                println("este es el resultado que traigo con el select $resultado")

                if (resultado.next()) {
                    startActivity(pantallaprincipal)

                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@iniciarsesion,
                            "Usuario o contraseña invalidos",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }*/
            println("este es el resultado que traigo con el select $txtcorreoiniciar")
        }
        btnrecuperarcontra.setOnClickListener {
           val recuperar = Intent(this, correoderecuperacion::class.java)
            startActivity(recuperar)
        }
    }
}


