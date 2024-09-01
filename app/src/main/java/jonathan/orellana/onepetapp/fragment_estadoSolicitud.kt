package jonathan.orellana.onepetapp

import RecyclerViewHelpers.AdaptadorCitas
import RecyclerViewHelpers.AdaptadorMascotas
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
import modelo.dataClassCitas
import modelo.tbMascotas


class fragment_estadoSolicitud : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_estado_solicitud, container, false)
        val rcvEstadoCitas = root.findViewById<RecyclerView>(R.id.rcvEstadoCitas)

        //Agregar un layout al RecyclerView
        rcvEstadoCitas.layoutManager = LinearLayoutManager(context)

        //TODO: mostrar datos
        fun obtenerCitas(): List<dataClassCitas> {
            //1- Crear un objeto de clase conexion
            val objConexion = ClaseConexion().cadenaConexion()

            //2- Crear un Statement
            val statement = objConexion?.createStatement()
            val resultSet = statement?.executeQuery("SELECT c.uuid_cita, c.fecha_cita, c.motivo_cita, c.descripcion_motivo, m.nombre_mascota, v.nombre_veterinaria, u.nombre_usuario FROM tbCitas c RIGHT JOIN tbVeterinarias v ON c.vet = v.uuid_veterinaria LEFT JOIN tbUsuariosOne u ON c.usuario = u.uuid_usuario INNER JOIN tbMascotas m ON c.mascota = m.uuid_mascota")!!

            val listaMisCitas = mutableListOf<dataClassCitas>()

            while (resultSet.next()){
                val UUID_Cita = resultSet.getString("uuid_cita")
                val fecha_cita = resultSet.getString("fecha_cita")
                val motivo_cita = resultSet.getString("motivo_cita")
                val descripcion_cita = resultSet.getString("descripcion_motivo")
                val mascota = resultSet.getString("nombre_mascota")
                val vet = resultSet.getString("nombre_veterinaria")
                val usuario = resultSet.getString("nombre_usuario")

                //SPINNERS
                val valoresJuntos = dataClassCitas(UUID_Cita, fecha_cita, motivo_cita, descripcion_cita, mascota, vet, usuario)

                listaMisCitas.add(valoresJuntos)
            }
            return listaMisCitas
        }

        //Asignarle el adaptador al RecyclerView
        CoroutineScope(Dispatchers.IO).launch {
            val misCitasDB = obtenerCitas()
            withContext(Dispatchers.Main){
                val adapter = AdaptadorCitas(misCitasDB)
                rcvEstadoCitas.adapter = adapter
            }
        }

        return root
    }


}