package jonathan.orellana.onepetapp

import RecyclerViewHelpers.Adaptador
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
import modelo.tbMascotas

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_misMascotas.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment_misMascotas : Fragment() {
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
        //Creo la variable root
        val root = inflater.inflate(R.layout.fragment_mismascotas, container, false)
        val rcvMisMascotas = root.findViewById<RecyclerView>(R.id.rcvMisMascotas)

        //Agregar un layout al RecyclerView
        rcvMisMascotas.layoutManager = LinearLayoutManager(context)

        //TODO: mostrar datos
        fun obtenerMascotas(): List<tbMascotas> {
            //1- Crear un objeto de clase conexion
            val objConexion = ClaseConexion().cadenaConexion()

            //2- Crear un Statement
            val statement = objConexion?.createStatement()
            val resultSet = statement?.executeQuery("SELECT * FROM tbMascotas ")!!

            val listaMisMascotas = mutableListOf<tbMascotas>()

            while (resultSet.next()){
                val UUID_mascota = resultSet.getString("UUID_mascota")
                val nombre_mascota = resultSet.getString("nombre_mascota")
                val raza = resultSet.getString("raza")
                val procesos_previos = resultSet.getString("procesos_previos")
                val alergias = resultSet.getString("alergias")
                val enfermedades_cronicas = resultSet.getString("enfermedades_cronicas")
                val fecha_nacimiento = resultSet.getString("fecha_nacimiento")
                val peso = resultSet.getInt("peso")

                //SPINNERS
                val sexo = resultSet.getString("sexo")
                val especie = resultSet.getString("especie")

                val valoresJuntos = tbMascotas(UUID_mascota, nombre_mascota, raza, procesos_previos, alergias, enfermedades_cronicas, fecha_nacimiento, peso.toString(), sexo, especie)

                listaMisMascotas.add(valoresJuntos)
            }
            return listaMisMascotas
        }

        //Asignarle el adaptador al RecyclerView
        CoroutineScope(Dispatchers.IO).launch {
            val misMascotasDB = obtenerMascotas()
            withContext(Dispatchers.Main){
                val adapter = Adaptador(misMascotasDB)
                rcvMisMascotas.adapter = adapter
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
         * @return A new instance of fragment fragment_misMascotas.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fragment_misMascotas().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}