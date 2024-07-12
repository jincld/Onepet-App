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

class iniciarsesion : AppCompatActivity() {

    companion object variablesGlobalesLogin{
        lateinit var correodelUsuarioGlobal: String
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



        fun hashSHA256(contraescrita: String): String {
            val bytes = MessageDigest.getInstance("SHA-256").digest(contraescrita.toByteArray())
            return bytes.joinToString("") {"%02x".format(it)}

        }



        val txtcorreoiniciar = findViewById<TextView>(R.id.txtcorreoiniciar)
        val txtcontrainiciar = findViewById<TextView>(R.id.txtcontrasenainicio)
        val btnrecuperarcontra = findViewById<TextView>(R.id.btnrecuperarcontra)
        val btninicarsesion = findViewById<TextView>(R.id.btniniciarsesionhome)




        btninicarsesion.setOnClickListener{
            val pantallaprincipal = Intent (this, MainActivity::class.java)

            GlobalScope.launch (Dispatchers.IO) {

                val objconexion = ClaseConexion().cadenaConexion()

                val contraencriptada = hashSHA256(txtcontrainiciar.text.toString())

                val comprobarusuario = objconexion?.prepareStatement("SELECT uuid_usuario FROM tbUsuariosOne where correo_usuario = ? and contra_usuario = ?")!!
                comprobarusuario.setString(1, txtcorreoiniciar.text.toString())
                comprobarusuario.setString(2, contraencriptada)

                val resultado = comprobarusuario.executeQuery()

                if (resultado.next()){
                    startActivity(pantallaprincipal)
                } else {
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@iniciarsesion, "Usuario o contraseña invalidos", Toast.LENGTH_LONG).show()
                    }
                }

                }
            }
        btnrecuperarcontra.setOnClickListener {

            val recuperar = Intent(this, correoderecuperacion::class.java)
            startActivity(recuperar)
        }

        }

    }
