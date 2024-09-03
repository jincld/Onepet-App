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
        lateinit var valor_nombre_usuario: String
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
        val motivoRecibido = intent.getStringExtra("motivo_cita")
        val fechaRecibido = intent.getStringExtra("fecha_cita")
        val usuarioRecibido = intent.getStringExtra("usuario")
        val motivo2Recibido = intent.getStringExtra("motivo_cita")
        val descripcionRecibido = intent.getStringExtra("descripcion_motivo")

        valor_motivo_cita = motivoRecibido.toString()



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



        fun obtenerUuidRol(): String? {
            val objConexion = ClaseConexion().cadenaConexion()
            val statement = objConexion?.createStatement()
            val resulSet = statement?.executeQuery("SELECT UUID_rol FROM tbRolesUsuarios WHERE nombre_rol = 'Empleado'")!!
            var uuidRol: String? = null

            if (resulSet.next()) {
                uuidRol = resulSet.getString("UUID_rol")
                println("este es el uuid traido desde el if $uuidRol")
            }

            println("este es el uuid traido desde la funcion $uuidRol")
            return uuidRol

        }

        fun obtenerEmpleado(): List<dataClassUsuarios> {

            val objConexion = ClaseConexion().cadenaConexion()

            val uuidEmpleado = obtenerUuidRol()

            val resulSet = objConexion?.prepareStatement("select * from tbUsuariosOne where rol = ?")!!
            resulSet.setString(1, uuidEmpleado)
            resulSet.executeQuery()

            val ver_vet = resulSet.executeQuery()



            val listaEmpleado = mutableListOf<dataClassUsuarios>()

            while (ver_vet.next()) {
                val uuid = ver_vet.getString("UUID_usuario")
                val nombre = ver_vet.getString("nombre_usuario")
                val contra = ver_vet.getString("contra_usuario")
                val correo = ver_vet.getString("correo_usuario")
                val rol = ver_vet.getString("rol")
                val vet = ver_vet.getString("vet")

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

            val empleado = obtenerEmpleado()
            valor_nombre_usuario = empleado[spEmpleado.selectedItemPosition].nombre_usuario

        }

        fun obtenerUUIDUsuarioC(): String? {

            val valoruuidusuario = valor_nombre_usuario

            val objConexion = ClaseConexion().cadenaConexion()

            val traerUUIDUsuarioC =
                objConexion?.prepareStatement("SELECT UUID_usuario FROM tbUsuariosOne WHERE nombre_usuario = ?")!!
            traerUUIDUsuarioC.setString(1, valoruuidusuario)
            val resultSet = traerUUIDUsuarioC.executeQuery()
            println("------------------------Este es el nombre traido desde el spinner $valor_nombre_usuario")

            var uuidUsuarioC: String? = null

            if (resultSet?.next() == true) {
                uuidUsuarioC = resultSet.getString("UUID_usuario")
                println("------------------------Este es el uuid traido desde el if $uuidUsuarioC")
            }

            println("------------------------Este es el uuid traido desde la funcion $uuidUsuarioC")
            return uuidUsuarioC
        }

        fun obtenerUUIDCita(): String? {

            val valoruuidcita = valor_motivo_cita

            val objConexion = ClaseConexion().cadenaConexion()

            val traerUUIDCita =
                objConexion?.prepareStatement("SELECT UUID_Cita FROM tbCitas WHERE motivo_cita = ?")!!
            traerUUIDCita.setString(1, valoruuidcita)
            val resultSet = traerUUIDCita.executeQuery()

            var uuidCita: String? = null

            if (resultSet?.next() == true) {
                uuidCita = resultSet.getString("UUID_Cita")
                println("------------------------Este es el uuid traido desde el if $uuidCita")
            }

            println("------------------------Este es el uuid traido desde la funcion $uuidCita")
            return uuidCita
        }


btnAsignarCita.setOnClickListener {
    CoroutineScope(Dispatchers.IO).launch {
        //Obtener el codigo de obtener el UUID Cita
        val uuidCitaTraida = obtenerUUIDCita()

        //Obtener el codigo de obtener el UUID Usuario
        val uuidUsuarioTraidoC = obtenerUUIDUsuarioC()

        val objConexion = ClaseConexion().cadenaConexion()
        println(" --------------Este es el uuid de la cita que quiero usar ${uuidCitaTraida}")
        println(" --------------Este es el uuid del usuario que quiero usar ${uuidUsuarioTraidoC}")
        val asignar =
            objConexion?.prepareStatement("Insert into tbAsignaciones (uuid_asignacion,citas, empleado) values (?,?,?)")!!
        asignar.setString(1, UUID.randomUUID().toString())
        asignar.setString(2, uuidCitaTraida)
        println("----------------------Este es el uuid de cita traido antes del execute  $uuidCitaTraida")
        asignar.setString(3, uuidUsuarioTraidoC)
        println("----------------------Este es el uuid de usuario traido antes del execute  $uuidUsuarioTraidoC")
        asignar.executeUpdate()

        println(" --------------este es el nombre de vet que quiero usar ${valor_motivo_cita}")
        val updateCita = objConexion?.prepareStatement("Update tbCitas set estado ='Aceptada' where motivo_cita = ?")!!
        updateCita?.setString(1, valor_motivo_cita)
        updateCita?.executeUpdate()

    }


}


    }
}