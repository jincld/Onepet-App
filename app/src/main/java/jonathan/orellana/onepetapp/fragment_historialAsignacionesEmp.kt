package jonathan.orellana.onepetapp

import RecyclerViewHelpers_HistorialAsignaciones.AdaptadorHistorialAsignaciones
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
import modelo.dataClassHistorialAsignaciones

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_historialAsignacionesEmp.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment_historialAsignacionesEmp : Fragment() {
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
        val root = inflater.inflate(R.layout.fragment_historial_asignaciones_emp, container, false)
        val rcvResenas = root.findViewById<RecyclerView>(R.id.rcvHistorialAsignacionesHAE)

        rcvResenas.layoutManager = LinearLayoutManager(context)
        fun obtenerDatosAsignaciones(): List<dataClassHistorialAsignaciones>{

            //obtenemos los datps
            val objConexion = ClaseConexion().cadenaConexion()
            val resulSet = objConexion?.prepareStatement("SELECT c.uuid_cita, c.fecha_cita, c.motivo_cita, c.descripcion_motivo, c.estado, c.vet,  m.nombre_mascota, u.nombre_usuario, c.detalle_cita FROM tbCitasEmp c RIGHT JOIN tbUsuariosOne u ON c.usuario = u.uuid_usuario INNER JOIN tbMascotas m ON c.mascota = m.uuid_mascota WHERE usuario = ? AND (c.estado NOT LIKE 'Pendiente' AND c.estado NOT LIKE 'Aceptada')")!!
            resulSet.setString(1, iniciarsesion.variablesLogin.UUID_Usuario)
            resulSet.executeQuery()

            var prueba = resulSet.executeQuery()
            val listaAsignaciones = mutableListOf<dataClassHistorialAsignaciones>()

            while(prueba.next()){
                //asignamos valores
                val UUID_Cita = prueba.getString("UUID_Cita")
                val fecha_cita = prueba.getString("fecha_cita")
                val motivo_cita = prueba.getString("motivo_cita")
                val descripcion_motivo = prueba.getString("descripcion_motivo")
                val estado = prueba.getString("estado")
                val mascota = prueba.getString("nombre_mascota")
                val vet = prueba.getString("vet")
                val usuario = prueba.getString("nombre_usuario")
                val detalle_cita = prueba.getString("detalle_cita")

                val ValoresJuntos = dataClassHistorialAsignaciones(UUID_Cita, fecha_cita, motivo_cita, descripcion_motivo, estado, mascota, vet, usuario, detalle_cita)
                listaAsignaciones.add(ValoresJuntos)
            }
            return listaAsignaciones
        }
        CoroutineScope(Dispatchers.IO).launch {
            val asignacionesBD = obtenerDatosAsignaciones()
            withContext(Dispatchers.Main){
                val adapter = AdaptadorHistorialAsignaciones(asignacionesBD)
                rcvResenas.adapter= adapter
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
         * @return A new instance of fragment fragment_historialAsignacionesEmp.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fragment_historialAsignacionesEmp().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}