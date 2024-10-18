package jonathan.orellana.onepetapp

import RecyclerViewHelpers.AdaptadorSolicitudCitas
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
import modelo.dataClassSoliC

class solicitudescitadv : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_solicitudescitadv, container, false)
        val rcvSolicitudesCita = root.findViewById<RecyclerView>(R.id.rcvSolicitudesCitaV)

        //Agregar un layout al RecyclerView
        rcvSolicitudesCita.layoutManager = LinearLayoutManager(context)

//        val btnRechazarCitaS = root.findViewById<Button>(R.id.btnRechazarCitaCS)
//        val btnAceptarCitaS = root.findViewById<Button>(R.id.btnAceptarCS)
//
//        //todo: clic al boton de Rechazar Cita
//
//        btnRechazarCitaS.setOnClickListener {
//            val pantallaRechazar = Intent(requireContext(), rechazarcitadv::class.java)
//            startActivity(pantallaRechazar)
//        }
//
//        //Todo: boton de Aceptar y Asignar
//
//        btnAceptarCitaS.setOnClickListener{
//            val pantallaRechazar = Intent(requireContext(), asignarcitadv::class.java)
//            startActivity(pantallaRechazar)
//        }

        //TODO: mostrar datos

        //obtenemos las solicitud de cita
        fun obtenerSoliCitas(): List<dataClassSoliC> {
            //1- Crear un objeto de clase conexion
          /*  val objConexion = ClaseConexion().cadenaConexion()

            //2- Crear un Statement
            val statement = objConexion?.createStatement()
            val resultSet = statement?.executeQuery("SELECT c.uuid_cita, c.fecha_cita, c.motivo_cita, c.descripcion_motivo, m.nombre_mascota, v.nombre_veterinaria, u.nombre_usuario FROM tbCitas c RIGHT JOIN tbVeterinarias v ON c.vet = v.uuid_veterinaria LEFT JOIN tbUsuariosOne u ON c.usuario = u.uuid_usuario INNER JOIN tbMascotas m ON c.mascota = m.uuid_mascota")!!*/

            val objConexion = ClaseConexion().cadenaConexion()
            val resulSet = objConexion?.prepareStatement(        "SELECT c.uuid_cita, c.fecha_cita, c.motivo_cita, c.descripcion_motivo, " +
                    "m.nombre_mascota, v.nombre_veterinaria, u.nombre_usuario, c.detalle_cita " +
                    "FROM tbCitas c " +
                    "RIGHT JOIN tbVeterinarias v ON c.vet = v.uuid_veterinaria " +
                    "LEFT JOIN tbUsuariosOne u ON c.usuario = u.uuid_usuario " +
                    "INNER JOIN tbMascotas m ON c.mascota = m.uuid_mascota " +
                    "WHERE v.uuid_veterinaria = ? AND c.Estado = 'Pendiente'")!!
            resulSet.setString(1, iniciarsesion.variablesLogin.uuid_Vet_real)
            //resulSet.executeQuery()

            var misSolicitudes = resulSet.executeQuery()
            val listaSoliCitas = mutableListOf<dataClassSoliC>()

            while (misSolicitudes.next()){
                val UUID_Cita = misSolicitudes.getString("uuid_cita")
                val fecha_cita = misSolicitudes.getString("fecha_cita")
                val motivo_cita = misSolicitudes.getString("motivo_cita")
                val descripcion_cita = misSolicitudes.getString("descripcion_motivo")
                val mascota = misSolicitudes.getString("nombre_mascota")
                val vet = misSolicitudes.getString("nombre_veterinaria")
                val usuario = misSolicitudes.getString("nombre_usuario")
                val detalle_cita = misSolicitudes.getString("detalle_cita")

                //SPINNERS
                val valoresJuntos = dataClassSoliC(UUID_Cita, fecha_cita, motivo_cita, descripcion_cita, mascota, vet, usuario, detalle_cita)

                listaSoliCitas.add(valoresJuntos)
            }
            return listaSoliCitas
        }

        //Asignarle el adaptador al RecyclerView
        CoroutineScope(Dispatchers.IO).launch {
            val misSoliCitasDB = obtenerSoliCitas()
            withContext(Dispatchers.Main){
                val adapter = AdaptadorSolicitudCitas(misSoliCitasDB)
                rcvSolicitudesCita.adapter = adapter
            }
        }

        return root
        }
}