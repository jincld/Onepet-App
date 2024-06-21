package jonathan.orellana.onepetapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import modelo.ClaseConexion
import modelo.dataClassEmpleado
import modelo.dataClassEtiqueta
import java.util.UUID
import kotlin.coroutines.coroutineContext

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
        val spEtiqueta = root.findViewById<Spinner>(R.id.spEtiqueta)
        val btnAgregarEmpleado = root.findViewById<Button>(R.id.btnAgregarEmpleado)



        fun obtenerEtiquetas(): List<dataClassEtiqueta> {


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
        }



        btnAgregarEmpleado.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {

                val Conexion = ClaseConexion().cadenaConexion()
                val addEmpleado = Conexion?.prepareStatement("Insert into tbEmpleados (uuid_empleado, contrasena_empleado, nombre_empleado,correo_empleado, etiqueta_empleado, adminvet) values (?,?,?,?,?,?)")!!
                addEmpleado.setString(1, UUID.randomUUID().toString())
                addEmpleado.setString(2, txtContra_empleado.text.toString())
                addEmpleado.setString(3, txtNombre_empleado.text.toString())
                addEmpleado.setString(4, txtCorreoEmpleado.text.toString())
               // addEmpleado.setString(5, val uuid_admin)
                //addEmpleado.setString(6, 'variable que contenga uuid de la etiqueta proviniente del spinner')




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
    }
}