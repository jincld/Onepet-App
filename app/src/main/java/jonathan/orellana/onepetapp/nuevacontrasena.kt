package jonathan.orellana.onepetapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion

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



        val txtnuevacontra = findViewById<EditText>(R.id.txtnuevacontra)
        val btnnuevacontra = findViewById<Button>(R.id.btnnuevacontra)

        fun actualizardatos(nuevacontra: String){
            GlobalScope.launch(Dispatchers.IO) {
                val objConexion = ClaseConexion().cadenaConexion()


                val updatecontra = objConexion?.prepareStatement("update tbUsuariosOne set contra_usuario = ? where correo_usuario = ?")!!
                updatecontra.setString(1, nuevacontra)
                updatecontra.executeUpdate()

                val commit = objConexion.prepareStatement("commit")!!
                commit.executeUpdate()

                withContext(Dispatchers.Main){
                    actualizardatos(nuevacontra)
                }

            }
            btnnuevacontra.setOnClickListener {
            actualizardatos(nuevacontra)
        }
            val recuperar = Intent(this, iniciarsesion::class.java)
           startActivity(recuperar)
        }
   }
}