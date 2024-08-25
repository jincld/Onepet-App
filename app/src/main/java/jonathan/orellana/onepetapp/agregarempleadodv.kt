package jonathan.orellana.onepetapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import java.security.MessageDigest
import java.util.UUID

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [agregarempleadodv.newInstance] factory method to
 * create an instance of this fragment.
 */
class agregarempleadodv : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    companion object VariablesGlobalesEmpleado{
        lateinit var NombreEmpVG: String
        lateinit var CorreoEmVG: String
        lateinit var ContraEmpVG: String
        lateinit var RolEmpVG: String
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val root = inflater.inflate(R.layout.fragment_agregarempleadodv, container, false)


     // val uuid_admin = 'Codigo para que mande a llamar el uuid del admin de veterinaria'

        val txtNombre_empleado = root.findViewById<TextView>(R.id.txtNombre_empleado)
        val txtContra_empleado = root.findViewById<TextView>(R.id.txtContra_empleado)
        val txtCorreoEmpleado = root.findViewById<TextView>(R.id.txtCorreo_empleado)
        val btnAgregarEmpleado = root.findViewById<Button>(R.id.btnAgregarEmpleado)

        fun hashSHA256(contraescrita: String): String {
            val bytes = MessageDigest.getInstance("SHA-256").digest(contraescrita.toByteArray())
            return bytes.joinToString("") {"%02x".format(it)}

        }

        /*fun obtenerEtiquetas(): List<dataClassEtiqueta> {


            val conexion = ClaseConexion().cadenaConexion()

            //Creo un statement que me ejecute el select
            val statement = conexion?.createStatement()

            val resultSet = statement?.executeQuery("select * from tbEtiquetas")!!

            val listaEtiqueta = mutableListOf<dataClassEtiqueta>()

            while (resultSet.next()) {
                val uuidEtiqueta = resultSet.getString("UUID_etiqueta")
                val nombreEtiqueta = resultSet.getString("nombre_etiqueta")

                val unaEtiquetaCompleta =
                    dataClassEtiqueta(uuidEtiqueta, nombreEtiqueta, )
                listaEtiqueta.add(unaEtiquetaCompleta)

            }
            return listaEtiqueta
        }*/

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

        btnAgregarEmpleado.setOnClickListener {
            val correo = txtCorreoEmpleado.text.toString()
            val contra = txtContra_empleado.text.toString()
            val nombre = txtNombre_empleado.text.toString()
            var hayerrores = false

            if (!correo.matches(Regex("[a-zA-Z0-9._-]+@[a-z]+[.][a-z]+"))){
                txtCorreoEmpleado.error = "Ingrese un correo válido"
                hayerrores = true
            } else {
                txtCorreoEmpleado.error = null
            }

            if (contra.length <= 8) {
                txtContra_empleado.error = "La contraseña debe tener más de 8 carácteres"
                hayerrores = true
            } else {
                txtContra_empleado.error = null
            }

            if (hayerrores){
            } else{
            CoroutineScope(Dispatchers.IO).launch {

                val objConexion = ClaseConexion().cadenaConexion()
                val contraencriptada = hashSHA256(txtContra_empleado.text.toString())

                val uuidTraido = obtenerUuidRol()

                val crearEmpleado = objConexion?.prepareStatement("insert into tbUsuariosOne (UUID_usuario, nombre_usuario, contra_usuario, correo_usuario, rol) values (?, ?, ?, ?, ?)")!!
                crearEmpleado.setString(1, UUID.randomUUID().toString())
                crearEmpleado.setString(2, txtNombre_empleado.text.toString())
                crearEmpleado.setString(3, contraencriptada)
                crearEmpleado.setString(4, txtCorreoEmpleado.text.toString())
                crearEmpleado.setString(5, uuidTraido)
                println("este es el uuid traido antes del execute  $uuidTraido")
                crearEmpleado.executeUpdate()

                withContext(Dispatchers.Main){
                    //mostrar mensaje y limpiar campos
                    Toast.makeText(context, "Empleado registrado", Toast.LENGTH_SHORT).show()
                    txtNombre_empleado.setText("")
                    txtCorreoEmpleado.setText("")
                    txtContra_empleado.setText("")

                }

            }


        }
        }

        return root

    }

    /*companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment agregarempleadodv.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            agregarempleadodv().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }*/
}