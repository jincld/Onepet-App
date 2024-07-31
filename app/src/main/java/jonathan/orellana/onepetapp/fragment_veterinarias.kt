package jonathan.orellana.onepetapp

import RecyclerViewHelper.AdaptadorVet
import RecyclerViewHelpers.Adaptador
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.dataClassEmpleado
import modelo.dataClassVeterinaria

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_veterinarias.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment_veterinarias : Fragment() {
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
       val root = inflater.inflate(R.layout.fragment_veterinarias, container, false)



       val rcvVeterinarias = root.findViewById<RecyclerView>(R.id.rcvVeterinarias)
        rcvVeterinarias.layoutManager = LinearLayoutManager(context)


        fun obtenerDatos(): List<dataClassVeterinaria>{

            //crear objeto conexion

            val objConexion = ClaseConexion().cadenaConexion()

            //crear statement

            val statement = objConexion?.createStatement()
            val resulSet = statement?.executeQuery("select * from tbVeterinarias")!!
            val veterinarias = mutableListOf<dataClassVeterinaria>()

            //recorro todos los registos de la base de datos

            while(resulSet.next()){
                val UUID_Vet = resulSet.getString("UUID_Veterinaria")
                val ubicacion = resulSet.getString("Ubicacion_veterinaria")
                val nit = resulSet.getString("NIT")
                val contacto = resulSet.getString("contacto_veterinaria")
                val nombre = resulSet.getString("nombre_veterinaria")
                val correo = resulSet.getString("correo_veterinaria")
                val descripcion = resulSet.getString("descripcion_servicio")



                val ValoresJuntos = dataClassVeterinaria(UUID_Vet,nombre, ubicacion, nit,contacto,correo,descripcion)
                veterinarias.add(ValoresJuntos)
            }
            return veterinarias
        }
        CoroutineScope(Dispatchers.IO).launch {
            val veterinaria = obtenerDatos()
            withContext(Dispatchers.Main){
                val adapter = AdaptadorVet(veterinaria)
                rcvVeterinarias.adapter= adapter
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
         * @return A new instance of fragment fragment_veterinarias.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fragment_veterinarias().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}