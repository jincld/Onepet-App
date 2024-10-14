package jonathan.orellana.onepetapp

import RecyclerViewHelpersCitasAtendidas.AdaptadorCitasAtendidas
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
import modelo.dataClassCitasAtendidas

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_citasatendidas.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment_citasatendidas : Fragment() {
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
        val root = inflater.inflate(R.layout.fragment_citasatendidas, container, false)
        val rcvCitasAtendidas = root.findViewById<RecyclerView>(R.id.rcvCitasAtendidas)

        //Agregar un layout al RecyclerView
        rcvCitasAtendidas.layoutManager = LinearLayoutManager(context)


        //TODO: mostrar datos
        fun obtenerCitas(): List<dataClassCitasAtendidas> {
            //1- Crear un objeto de clase conexion
            val objConexion = ClaseConexion().cadenaConexion()

            //2- Crear un Statement
            val resulSet = objConexion?.prepareStatement("SELECT c.uuid_cita, c.fecha_cita, c.motivo_cita, c.descripcion_motivo, c.estado, c.vet,  m.nombre_mascota, u.nombre_usuario FROM tbCitas c RIGHT JOIN tbUsuariosOne u ON c.usuario = u.uuid_usuario INNER JOIN tbMascotas m ON c.mascota = m.uuid_mascota WHERE c.vet = ? AND c.estado NOT LIKE 'Pendiente'")!!
            resulSet.setString(1, iniciarsesion.variablesLogin.uuid_Vet_real)

            var citasAsignadasMostrar = resulSet.executeQuery()
            val listaCitasAsignadas = mutableListOf<dataClassCitasAtendidas>()

            while (citasAsignadasMostrar.next()){
                val UUID_Cita = citasAsignadasMostrar.getString("uuid_cita")
                val fecha_cita = citasAsignadasMostrar.getString("fecha_cita")
                val motivo_cita = citasAsignadasMostrar.getString("motivo_cita")
                val descripcion_cita = citasAsignadasMostrar.getString("descripcion_motivo")
                val estado = citasAsignadasMostrar.getString("Estado")
                val mascota = citasAsignadasMostrar.getString("nombre_mascota")
                val vet = citasAsignadasMostrar.getString("vet")
                val usuario = citasAsignadasMostrar.getString("nombre_usuario")

                //SPINNERS
                val valoresJuntos = dataClassCitasAtendidas(UUID_Cita, fecha_cita, motivo_cita, descripcion_cita, estado, mascota, vet, usuario)

                listaCitasAsignadas.add(valoresJuntos)
            }
            return listaCitasAsignadas
        }

        //Asignarle el adaptador al RecyclerView
        CoroutineScope(Dispatchers.IO).launch {
            val misCitasDB = obtenerCitas()
            withContext(Dispatchers.Main){
                val adapter = AdaptadorCitasAtendidas(misCitasDB)
                rcvCitasAtendidas.adapter = adapter
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
         * @return A new instance of fragment fragment_citasatendidas.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fragment_citasatendidas().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}