package jonathan.orellana.onepetapp

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
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

class iniciarsesion : AppCompatActivity() {
    companion object variablesLogin {
        lateinit var valorRolUsuario: String
        lateinit var valorCorreoUsuario: String
        lateinit var uuid_vet_real: String
        var uuidRol: String? = null
        lateinit  var correo_admin: String
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
        val ojolog = findViewById<ImageButton>(R.id.btnojolog)
        val btnVolver = findViewById<ImageButton>(R.id.btnVolverIS)
        val btnNoCuenta = findViewById<TextView>(R.id.txtNoCuenta)


        //   fun obtenerUuidRol(): String? {



        btnVolver.setOnClickListener {
            val pantallaAnterior = Intent(this, login::class.java)
            startActivity(pantallaAnterior)
        }


        ojolog.setOnClickListener{
            if (txtcontrainiciar.inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD){
                txtcontrainiciar.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
              txtcontrainiciar.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

            }

        }


        btninicarsesion.setOnClickListener {
            valorCorreoUsuario = txtcorreoiniciar.text.toString()

            val pantallaprincipal = Intent(this, MainActivity::class.java)

            val contra = txtcontrainiciar.text.toString()
            val correo = txtcorreoiniciar.text.toString()
            var hayerrores = false

            if (correo.isEmpty()) {
                txtcorreoiniciar.error = "Complete todos lo campos"
                hayerrores = true
            } else {
                txtcorreoiniciar.error = null
            }

            if (!correo.matches(Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$"))){

             txtcorreoiniciar.error = "Correo invalido"
                hayerrores = true
            } else {
                txtcorreoiniciar.error = null
            }

            if (contra.isEmpty()) {
              txtcontrainiciar.error = "Complete todos lo campos"
                hayerrores = true
            } else {
                txtcontrainiciar.error = null
            }

            if (contra.length < 7) {
                txtcontrainiciar.error = "Contraseña invalida"
                hayerrores = true
            } else {
               txtcontrainiciar.error = null
            }

            if (hayerrores){
            } else {

                GlobalScope.launch(Dispatchers.IO) {
                    val objConexion = ClaseConexion().cadenaConexion()
                    val contraencriptada = hashSHA256(txtcontrainiciar.text.toString())

                    val resulSet = objConexion?.prepareStatement("SELECT rol FROM tbUsuariosOne where correo_usuario = ? and contra_usuario = ?")!!
                    resulSet.setString(1, txtcorreoiniciar.text.toString())
                    resulSet.setString(2, contraencriptada)
                    correo_admin = txtcorreoiniciar.text.toString()
                    println("este es el correo que quiero usar ${correo_admin}")

                    val resultado = resulSet.executeQuery()

                    val UUID_vet = objConexion?.prepareStatement("select vet from tbUsuariosOne where correo_usuario = ?")!!
                    UUID_vet.setString(1, correo_admin)
                    UUID_vet.executeQuery()
                    var uuid_vet_global = UUID_vet.executeQuery();

                    if (uuid_vet_global.next()) {
                        uuid_vet_real = uuid_vet_global.getString("vet")
                        println("este es la UUID de vet que quiero usar ${uuid_vet_real}")
                    }
                    else {

                    }

                    if (resultado.next()) {
                        valorRolUsuario = resultado.getString("ROL")
                        println("--*--*-*ESTE ES EL ROL ${valorRolUsuario}")
                        startActivity(pantallaprincipal)


                    }else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@iniciarsesion, "Usuario o contraseña inválidos", Toast.LENGTH_LONG).show()
                        }
                    }
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

        btnrecuperarcontra.setOnClickListener {
            val recuperar = Intent(this, correoderecuperacion::class.java)
            startActivity(recuperar)
        }

        btnNoCuenta.setOnClickListener {
            val noCuenta = Intent(this, registrarse::class.java)
            startActivity(noCuenta)
        }
            println("este es el resultado que traigo con el select $txtcorreoiniciar")
        }

    }



