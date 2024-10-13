package jonathan.orellana.onepetapp

import RecyclerViewHelpersMisMascotas.AdaptadorMisMascotas
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
import modelo.dataClassMisMascotas

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
        val root = inflater.inflate(R.layout.fragment_mis_mascotas, container, false)
        val rcvMascotas = root.findViewById<RecyclerView>(R.id.rcvMisMascotas)

        rcvMascotas.layoutManager = LinearLayoutManager(context)
        fun obtenerDatos(): List<dataClassMisMascotas>{

            //crear objeto conexion

           val objConexion = ClaseConexion().cadenaConexion()
            val resulSet = objConexion?.prepareStatement("SELECT  tbMascotas.uuid_mascota, tbMascotas.nombre_mascota, tbMascotas.raza, tbMascotas.sexo, tbMascotas.procesos_previos, tbMascotas.alergias, tbMascotas.enfermedades_cronicas, tbMascotas.fecha_nacimiento, tbMascotas.peso,tbEspecies.nombre_especie, tbMascotas.dueno FROM tbMascotas Inner JOIN tbEspecies ON tbMascotas.especie = tbEspecies.uuid_especie where dueno = ?")!!
            resulSet.setString(1, iniciarsesion.variablesLogin.UUID_Usuario)
            //resulSet.executeQuery()

            var prueba = resulSet.executeQuery()
            val mascotas = mutableListOf<dataClassMisMascotas>()

            //recorro todos los registos de la base de datos

            while(prueba.next()){
                val uuid = prueba.getString("UUID_mascota")
                val Nombre = prueba.getString("nombre_mascota")
                val Raza = prueba.getString("raza")
                val Sexo = prueba.getString("sexo")
                val Procesos = prueba.getString("procesos_previos")
                val Alergias = prueba.getString("alergias")
                val Enfermedades = prueba.getString("enfermedades_cronicas")
                val Fecha = prueba.getString("fecha_nacimiento")
                val Peso = prueba.getInt("peso")
                val Especie = prueba.getString("Nombre_especie")
                //val Foto = prueba.getString("foto_perfil")
                val Dueno = prueba.getString("dueno")

                //println("Este es el uuid MIS MASCOTAS: $uuid")
                println("Este es el nombre MIS MASCOTAS: $Nombre")
                println("Este es el raza MIS MASCOTAS: $Raza")
                println("Este es el sexo MIS MASCOTAS: $Sexo")
                println("Este es el procesos MIS MASCOTAS: $Procesos")
                println("Este es el alergias MIS MASCOTAS: $Alergias")
                println("Este es el enfermedades MIS MASCOTAS: $Enfermedades")
                println("Este es el fecha MIS MASCOTAS: $Fecha")
                println("Este es el peso MIS MASCOTAS: $Peso")
                println("Este es el especie MIS MASCOTAS: $Especie")
                //println("Este es el foto MIS MASCOTAS: $Foto")
                //println("Este es el dueno MIS MASCOTAS: $Dueno")

                val ValoresJuntos = dataClassMisMascotas(uuid, Nombre, Raza, Sexo, Procesos, Alergias, Enfermedades, Fecha, Peso, Especie, Dueno)
                mascotas.add(ValoresJuntos)
            }

            return mascotas
        }
               /* if (uuid != null && Nombre != null && Raza != null && Sexo != null &&
                    Procesos != null && Alergias != null && Enfermedades != null &&
                    Fecha != null && Especie != null && Foto != null && Dueno != null) {
                    val valoresJuntos = dataClassMisMascotas(
                        uuid, Nombre, Raza, Sexo, Procesos, Alergias,
                        Enfermedades, Fecha, Peso, Especie, Foto, Dueno
                    )
                    mascotas.add(valoresJuntos)
                }
            }
            return mascotas
        }*/
        CoroutineScope(Dispatchers.IO).launch {
            val mascotaDB = obtenerDatos()
            withContext(Dispatchers.Main){
                val adapter = AdaptadorMisMascotas(mascotaDB)
                rcvMascotas.adapter= adapter
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