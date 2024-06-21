package jonathan.orellana.onepetapp

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import modelo.ClaseConexion
import java.util.UUID

class agregarVeterinariadv : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agregar_veterinaria)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val txtNombreVet = findViewById<TextView>(R.id.txtNombreVet)
        val txtUbicacionVet = findViewById<TextView>(R.id.txtUbicacionVet)
        val txtNitVet = findViewById<TextView>(R.id.txtNitVet)
        val txtContactoVet = findViewById<TextView>(R.id.txtConctactoVet)
        val txtPersonalVet = findViewById<TextView>(R.id.txtPersonalVet)
        val btnRegistrarVet = findViewById<TextView>(R.id.btnRegistrarVet)



        btnRegistrarVet.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                //1- Creo un objeto de la clase conexión dentro de la cortina
                val objConexion = ClaseConexion().cadenaConexion()

                //2-Creo una variable que contenga un preparestatement
                val addVeterinaria = objConexion?.prepareStatement("into tbveterinarias (uuid_veterinaria,nombre_veterinaria, ubicacion_veterinaria, nit, contacto_veterinaria, personal) values (?,?,?,?,?,?)")!!
                addVeterinaria.setString(1, UUID.randomUUID().toString())
                addVeterinaria.setString(2 , txtNombreVet.text.toString())
                addVeterinaria.setString(3 , txtUbicacionVet.text.toString())
                addVeterinaria.setInt(4, txtNitVet.text.toString().toInt())
                addVeterinaria.setString(5 , txtContactoVet.text.toString())
                addVeterinaria.setInt(6, txtPersonalVet.text.toString().toInt())
                addVeterinaria.executeUpdate()

        }

    }
}
    }