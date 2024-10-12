package jonathan.orellana.onepetapp

import RecyclerViewHelpersMisAsignaciones.AdaptadorMisAsignaciones
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
import modelo.dataClassMisAsignaciones

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_asignaciones.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment_asignaciones : Fragment() {
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
        val root = inflater.inflate(R.layout.fragment_asignaciones, container, false)
        val rcvMisAsignaciones = root.findViewById<RecyclerView>(R.id.rcvMisAsignaciones)

        //Agregar un layout al RecyclerView
        rcvMisAsignaciones.layoutManager = LinearLayoutManager(context)


                //TODO: mostrar datos
        fun obtenerAsignaciones(): List<dataClassMisAsignaciones> {
            //1- Crear un objeto de clase conexion
            val objConexion = ClaseConexion().cadenaConexion()

            //2- Crear un Statement
            val resulSet = objConexion?.prepareStatement("SELECT c.uuid_cita, c.fecha_cita, c.motivo_cita, c.descripcion_motivo, c.estado,  m.nombre_mascota, v.nombre_veterinaria, u.nombre_usuario FROM tbCitasEmp c RIGHT JOIN tbVeterinarias v ON c.vet = v.uuid_veterinaria LEFT JOIN tbUsuariosOne u ON c.usuario = u.uuid_usuario INNER JOIN tbMascotas m ON c.mascota = m.uuid_mascota WHERE uuid_usuario = ? AND estado = 'Aceptada'")!!
            resulSet.setString(1, iniciarsesion.variablesLogin.UUID_Usuario)

            var misAsignacionesMostrar = resulSet.executeQuery()
            val listaMisCitas = mutableListOf<dataClassMisAsignaciones>()

            while (misAsignacionesMostrar.next()){
                val UUID_Cita = misAsignacionesMostrar.getString("uuid_cita")
                val fecha_cita = misAsignacionesMostrar.getString("fecha_cita")
                val motivo_cita = misAsignacionesMostrar.getString("motivo_cita")
                val descripcion_cita = misAsignacionesMostrar.getString("descripcion_motivo")
                val estado = misAsignacionesMostrar.getString("Estado")
                val mascota = misAsignacionesMostrar.getString("nombre_mascota")
                val vet = misAsignacionesMostrar.getString("nombre_veterinaria")
                val usuario = misAsignacionesMostrar.getString("nombre_usuario")

                //SPINNERS
                val valoresJuntos = dataClassMisAsignaciones(UUID_Cita, fecha_cita, motivo_cita, descripcion_cita, estado, mascota, vet, usuario)

                listaMisCitas.add(valoresJuntos)
            }
            return listaMisCitas
        }

        //Asignarle el adaptador al RecyclerView
        CoroutineScope(Dispatchers.IO).launch {
            val misCitasDB = obtenerAsignaciones()
            withContext(Dispatchers.Main){
                val adapter = AdaptadorMisAsignaciones(misCitasDB)
                rcvMisAsignaciones.adapter = adapter
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
         * @return A new instance of fragment fragment_asignaciones.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fragment_asignaciones().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}