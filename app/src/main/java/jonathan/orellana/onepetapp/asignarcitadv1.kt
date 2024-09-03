package jonathan.orellana.onepetapp

import RecyclerViewHelpers.dataClassUsuarios
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.dataClassEmpleado
import java.util.UUID

class asignarcitadv1 : AppCompatActivity() {


    companion object variablesCitas {
        lateinit var valor_motivo_cita: String
        lateinit var valor_uuid_cita: String
        lateinit var valor_uuid_usuario: String
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_asignarcitadv1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnVolverAS = findViewById<ImageView>(R.id.btnVolverAS)

        btnVolverAS.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("ir_a_solicitudes_citas", true)
            startActivity(intent)
        }

        //Recibir los valores
        val UUID_recibido = intent.getStringExtra("UUID_cita")
        val motivoRecibido = intent.getStringExtra("motivo_cita")
        val fechaRecibido = intent.getStringExtra("fecha_cita")
        val usuarioRecibido = intent.getStringExtra("usuario")
        val motivo2Recibido = intent.getStringExtra("motivo_cita")
        val descripcionRecibido = intent.getStringExtra("descripcion_motivo")



        //Mando a llamar a todos los elementos de la pantalla

        val txtMotivoAsignar = findViewById<TextView>(R.id.txtMotivoAsignacion)
        val txtFechaAsignar = findViewById<TextView>(R.id.txtFechaAsignacion)
        val txtUsuarioAsignar = findViewById<TextView>(R.id.txtUsuarioAsignacion)
        val txtMotivoAsignar2 = findViewById<TextView>(R.id.txtMotivoAsignacion2)
        val btnAsignarCita = findViewById<Button>(R.id.btnAsignarCita)
        val spEmpleado = findViewById<Spinner>(R.id.spEmpleadoC )
        val txtDescripcionAsignar = findViewById<TextView>(R.id.txtDescAsignacion)

        //Asigarle los datos recibidos a mis TextView
        txtMotivoAsignar.text = motivoRecibido
        txtFechaAsignar.text = fechaRecibido
        txtUsuarioAsignar.text = usuarioRecibido
        txtMotivoAsignar2.text = motivo2Recibido
        txtDescripcionAsignar.text = descripcionRecibido



        fun obtenerEmpleado(): List<dataClassUsuarios> {

            val objConexion = ClaseConexion().cadenaConexion()

            //Creo un statement que me ejecute el select
            val statement = objConexion?.createStatement()

            val resultSet = statement?.executeQuery("select * from tbUsuariosOne")!!

            val listaEmpleado = mutableListOf<dataClassUsuarios>()

            while (resultSet.next()) {
                val uuid = resultSet.getString("UUID_usuario")
                val nombre = resultSet.getString("nombre_usuario")
                val contra = resultSet.getString("contra_usuario")
                val correo = resultSet.getString("correo_usuario")
                val rol = resultSet.getString("rol")
                val vet = resultSet.getString("vet")

                val unEmpleadoCompleto =
                    dataClassUsuarios(uuid, nombre, contra, correo,  rol, vet)
                listaEmpleado.add(unEmpleadoCompleto)


            }
            return listaEmpleado
        }

        CoroutineScope(Dispatchers.IO).launch {

            //1- Obtener el listado de datos que quiero mostrar
            val listadoEmp = obtenerEmpleado()
            val nombreEmpleado = listadoEmp.map { it.nombre_usuario }

            withContext(Dispatchers.Main) {
                //2 Creo y  configuto el adaptador
                val miAdaptadorr = ArrayAdapter(
                    this@asignarcitadv1,
                    android.R.layout.simple_spinner_dropdown_item,
                    nombreEmpleado
                )
                spEmpleado.adapter = miAdaptadorr
            }

        }


btnAsignarCita.setOnClickListener {

    val objConexion = ClaseConexion().cadenaConexion()
    println(" --------------este es el uuid de la cita que quiero usar ${valor_uuid_cita}")
    println(" --------------este es el uuid del usuario que quiero usar ${valor_uuid_usuario}")

    val asignar = objConexion?.prepareStatement("Insert into tbAsignaciones (uuid_asignacion,citas, empleado) values (?,?,?)")!!
    asignar.setString(1, UUID.randomUUID().toString())
    asignar.setString(2, valor_uuid_cita)
    asignar.setString( 3, valor_uuid_usuario)
    asignar.executeUpdate()




    /*println(" --------------este es el nombre de vet que quiero usar ${valor_motivo_cita}")
    val objConexion = ClaseConexion().cadenaConexion()
    val updateCita = objConexion?.prepareStatement("Update tbCitas set estado ='Aceptada' where motivo_cita = ?")!!
    updateCita?.setString(1, valor_motivo_cita)
    updateCita?.executeUpdate()*/
}


    }
}