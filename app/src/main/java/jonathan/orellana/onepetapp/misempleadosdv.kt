package jonathan.orellana.onepetapp

import RecyclerViewHelpers.Adaptador
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.dataClassEmpleado

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [misempleadosdv.newInstance] factory method to
 * create an instance of this fragment.
 */
class misempleadosdv : Fragment() {
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




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



         val root = inflater.inflate(R.layout.fragment_misempleadosdv, container, false)
         val rcvEmpleado = root.findViewById<RecyclerView>(R.id.rcvEmpleados)

        rcvEmpleado.layoutManager = LinearLayoutManager(context)
        fun obtenerDatos(): List<dataClassEmpleado>{

            //crear objeto conexion

            val objConexion = ClaseConexion().cadenaConexion()

            //crear statement

            fun obtenerUuidRol(): String? {
                val objConexion = ClaseConexion().cadenaConexion()
                val statement = objConexion?.createStatement()
                val resulSet = statement?.executeQuery("select UUID_Rol from tbRolesUsuarios where nombre_rol = 'Empleado'")!!
                var uuidRol: String? = null

                if (resulSet.next()) {
                    uuidRol = resulSet.getString("UUID_rol")
                    println("este es el uuid traido desde el if emp $uuidRol")
                }

                println("este es el uuid traido desde la funcion emp $uuidRol")
                return uuidRol
            }


            val resulSet = objConexion?.prepareStatement("select * from tbUsuariosOne where rol = ? and vet = ?")!!
            resulSet.setString(1, obtenerUuidRol())
            resulSet.setString(2, iniciarsesion.variablesLogin.uuid_Vet_real)
            resulSet.executeQuery()

            var prueba = resulSet.executeQuery()
            val empleados = mutableListOf<dataClassEmpleado>()

            //recorro todos los registos de la base de datos

            while(prueba.next()){
                val uuid = prueba.getString("UUID_usuario")
                val Nombre = prueba.getString("nombre_usuario")
                val Contra = prueba.getString("contra_usuario")
                val Correo = prueba.getString("correo_usuario")
                val ContraSinEncriptar = prueba.getString("contra_usuario")


                val ValoresJuntos = dataClassEmpleado(uuid, Nombre, Contra, Correo)
                empleados.add(ValoresJuntos)
            }
            return empleados
        }
        CoroutineScope(Dispatchers.IO).launch {
            val EmpleadoDB = obtenerDatos()
            withContext(Dispatchers.Main){
                val adapter = Adaptador(EmpleadoDB)
                rcvEmpleado.adapter= adapter
            }
        }



        return root

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment misempleadosdv.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            misempleadosdv().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}