package jonathan.orellana.onepetapp

import RecyclerViewHelper.AdaptadorVet
import RecyclerViewHelper.AdaptadorVetUser
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.dataClassVeterinaria

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Ver_veterinarias_usuario.newInstance] factory method to
 * create an instance of this fragment.
 */
class Ver_veterinarias_usuario : Fragment() {
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
        val root = inflater.inflate(R.layout.fragment_ver_veterinarias_usuario, container, false)




        val rcvVeterinarias = root.findViewById<RecyclerView>(R.id.rcvVeterinariasUser)
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
                val adapter = AdaptadorVetUser(veterinaria, this@Ver_veterinarias_usuario)
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
         * @return A new instance of fragment Ver_veterinarias_usuario.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Ver_veterinarias_usuario().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}