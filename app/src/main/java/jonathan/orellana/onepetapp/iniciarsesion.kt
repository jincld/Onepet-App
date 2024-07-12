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
    companion object variablesLogin {
        lateinit var valorRolUsuario: String
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

        fun hashSHA256(contraescrita: String): String {
            val bytes = MessageDigest.getInstance("SHA-256").digest(contraescrita.toByteArray())
            return bytes.joinToString("") {"%02x".format(it)}
        }

        val txtcorreoiniciar = findViewById<TextView>(R.id.txtcorreoiniciar)
        val txtcontrainiciar = findViewById<TextView>(R.id.txtcontrasenainicio)
        val btnrecuperarcontra = findViewById<TextView>(R.id.btnrecuperarcontra)
        val btninicarsesion = findViewById<TextView>(R.id.btniniciarsesionhome)
        val btnVolver = findViewById<ImageButton>(R.id.btnVolverIS)

        fun obtenerUuidRol(): String? {
            val objConexion = ClaseConexion().cadenaConexion()
            val resulSet = objConexion?.prepareStatement("SELECT rol FROM tbUsuariosOne WHERE correo_usuario = ? ")!!
            resulSet.setString(1, txtcorreoiniciar.text.toString())
            var uuidRol: String? = null
            val resultado = resulSet.executeQuery()

            if (resultado.next()) {
                uuidRol = resultado.getString("UUID_rol")
                println("este es el uuid traido desde el if $uuidRol")
            }

            println("este es el uuid traido desde la funcion $uuidRol")
            return uuidRol
        }

        btnVolver.setOnClickListener {
            val pantallaAnterior = Intent(this, registrarse::class.java)
            startActivity(pantallaAnterior)
        }

        btninicarsesion.setOnClickListener{
            valorRolUsuario = obtenerUuidRol().toString()

            val pantallaprincipal = Intent (this, MainActivity::class.java)

            GlobalScope.launch (Dispatchers.IO) {
                val objconexion = ClaseConexion().cadenaConexion()
                val contraencriptada = hashSHA256(txtcontrainiciar.text.toString())

                val comprobarusuario = objconexion?.prepareStatement("SELECT uuid_usuario FROM tbUsuariosOne where correo_usuario = ? and contra_usuario = ?")!!
                comprobarusuario.setString(1, txtcorreoiniciar.text.toString())
                comprobarusuario.setString(2, contraencriptada)

                val resultado = comprobarusuario.executeQuery()

                println("este es el resultado que traigo con el select $resultado")

                if (resultado.next()){
                    startActivity(pantallaprincipal)

                } else {
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@iniciarsesion, "Usuario o contraseña invalidos", Toast.LENGTH_LONG).show()
                    }
                }
            }
        btnrecuperarcontra.setOnClickListener {

            val recuperar = Intent(this, correoderecuperacion::class.java)
            startActivity(recuperar)
        }

        }

    }
}

