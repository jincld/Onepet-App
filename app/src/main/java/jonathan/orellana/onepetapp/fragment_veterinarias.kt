package jonathan.orellana.onepetapp

import RecyclerViewHelper.AdaptadorVet
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
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

   /* override fun onCreateView(
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

            val resulSet = objConexion?.prepareStatement("select * from tbVeterinarias where UUID_veterinaria = ?")!!
            resulSet.setString(1, iniciarsesion.variablesLogin.uuid_Vet_real)
            resulSet.executeQuery()

            val ver_vet = resulSet.executeQuery()
            //crear statement

            val veterinarias = mutableListOf<dataClassVeterinaria>()

            //recorro todos los registos de la base de datos

            while(ver_vet.next()){
                val UUID_Vet = ver_vet.getString("UUID_Veterinaria")
                val ubicacion = ver_vet.getString("Ubicacion_veterinaria")
                val nit = ver_vet.getString("NIT")
                val contacto = ver_vet.getString("contacto_veterinaria")
                val nombre = ver_vet.getString("nombre_veterinaria")
                val correo = ver_vet.getString("correo_veterinaria")
                val descripcion = ver_vet.getString("descripcion_servicio")



                val ValoresJuntos = dataClassVeterinaria(UUID_Vet,nombre, ubicacion, nit,contacto,correo,descripcion)
                veterinarias.add(ValoresJuntos)
            }
            return veterinarias
        }
        CoroutineScope(Dispatchers.IO).launch {
            val veterinaria = obtenerDatos()
            withContext(Dispatchers.Main){
                val adapter = AdaptadorVet(veterinaria, this@fragment_veterinarias)
                rcvVeterinarias.adapter= adapter
            }
        }

        return root
    }*/


    class AdaptadorVet(private val veterinarias: List<dataClassVeterinaria>, private val fragment: Fragment) : RecyclerView.Adapter<AdaptadorVet.VeterinariaViewHolder>() {

        class VeterinariaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val cardView: LinearLayout = itemView.findViewById(R.id.cardVeterinarias)
            val nombreTextView: TextView = itemView.findViewById(R.id.txtNombreVetCard)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VeterinariaViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_cardmv, parent, false)
            return VeterinariaViewHolder(view)
        }

        override fun onBindViewHolder(holder: VeterinariaViewHolder, position: Int) {
            val veterinaria = veterinarias[position]
            holder.nombreTextView.text = veterinaria.nombre_veterinaria

            // Ocultar la CardView si el nombre es "Prueba vet"
            if (veterinaria.nombre_veterinaria == "Prueba vet") {
                holder.cardView.visibility = View.GONE
            } else {
                holder.cardView.visibility = View.VISIBLE
            }

           // Configurar el OnClickListener para la CardView
           holder.cardView.setOnClickListener {
                (fragment as fragment_veterinarias).navigateToEliminar()
            }


        }

        override fun getItemCount(): Int {
            return veterinarias.size
        }
    }



    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflar el diseño para este fragmento
            val root = inflater.inflate(R.layout.fragment_veterinarias, container, false)
            val rcvVeterinarias = root.findViewById<RecyclerView>(R.id.rcvVeterinarias)
            rcvVeterinarias.layoutManager = LinearLayoutManager(context)

            fun obtenerDatos(): List<dataClassVeterinaria> {
                // Crear objeto conexión
                val objConexion = ClaseConexion().cadenaConexion()

                val resulSet = objConexion?.prepareStatement("select * from tbVeterinarias where UUID_veterinaria = ?")!!
                resulSet.setString(1, iniciarsesion.variablesLogin.uuid_Vet_real)
                resulSet.executeQuery()

                val ver_vet = resulSet.executeQuery()
                val veterinarias = mutableListOf<dataClassVeterinaria>()

                // Recorrer todos los registros de la base de datos
                while (ver_vet.next()) {
                    val UUID_Vet = ver_vet.getString("UUID_Veterinaria")
                    val ubicacion = ver_vet.getString("Ubicacion_veterinaria")
                    val nit = ver_vet.getString("NIT")
                    val contacto = ver_vet.getString("contacto_veterinaria")
                    val nombre = ver_vet.getString("nombre_veterinaria")
                    val correo = ver_vet.getString("correo_veterinaria")
                    val descripcion = ver_vet.getString("descripcion_servicio")

                    val ValoresJuntos = dataClassVeterinaria(UUID_Vet, nombre, ubicacion, nit, contacto, correo, descripcion)
                    veterinarias.add(ValoresJuntos)
                }
                return veterinarias
            }

            CoroutineScope(Dispatchers.IO).launch {
                val veterinarias = obtenerDatos()
                withContext(Dispatchers.Main) {
                    val adapter = AdaptadorVet(veterinarias, this@fragment_veterinarias)
                    rcvVeterinarias.adapter = adapter
                }
            }

            return root
        }

   fun navigateToEliminar() {
        val intent = Intent(activity, ActualizarVetActivity::class.java)
        startActivity(intent)
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