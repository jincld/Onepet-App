package jonathan.orellana.onepetapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.dataClassVeterinaria
import java.util.UUID


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [resenasdv.newInstance] factory method to
 * create an instance of this fragment.
 */
class resenasdv : Fragment() {
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
        val root = inflater.inflate(R.layout.fragment_resenasdv, container, false)

        val spVetR = root.findViewById<Spinner>(R.id.spVetsR)
        val txtCaliR = root.findViewById<EditText>(R.id.txtCaliR)
        val txtComentR = root.findViewById<EditText>(R.id.txtComentR)
        val btnEnviarResena = root.findViewById<Button>(R.id.btnEnviarResena)

        fun obtenerVets(): List<dataClassVeterinaria> {

            val objConexion = ClaseConexion().cadenaConexion()

            //Creo un statement que me ejecute el select
            val statement = objConexion?.createStatement()
            val resultSet = statement?.executeQuery("select * from tbVeterinarias")!!
            val listaVets = mutableListOf<dataClassVeterinaria>()

            while (resultSet.next()) {
                val UUID_veterinaria = resultSet.getString("UUID_veterinaria")
                val nombre_veterinaria = resultSet.getString("nombre_veterinaria")
                val ubicacion_veterinaria = resultSet.getString("ubicacion_veterinaria")
                val nit = resultSet.getString("nit")
                val contacto_veterinaria = resultSet.getString("contacto_veterinaria")
                val correo_veterinaria = resultSet.getString("correo_veterinaria")
                val descripcion_servicios = resultSet.getString("descripcion_servicio")

                val unaVeterinariaCompleta =
                    dataClassVeterinaria(UUID_veterinaria, nombre_veterinaria, ubicacion_veterinaria, nit, contacto_veterinaria, correo_veterinaria, descripcion_servicios)
                listaVets.add(unaVeterinariaCompleta)
            }
            return listaVets
        }

        CoroutineScope(Dispatchers.IO).launch {
            //1- Obtener el listado de datos que quiero mostrar
            val listadoVets = obtenerVets()
            val nombre_vet = listadoVets.map { it.nombre_veterinaria }

            withContext(Dispatchers.Main) {
                //2 Creo y  configuto el adaptador
                val miAdaptadorr = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    nombre_vet
                )
                spVetR.adapter = miAdaptadorr
            }


        btnEnviarResena.setOnClickListener {
            val txtCaliRd = txtCaliR.text.toString()
            //val txtCaliINT = txtCaliRd.toInt()
            val txtComentRd = txtComentR.text.toString()

            var hayerrores = false



            if (txtComentRd.isEmpty()) {
                txtComentR.error = "Se debe de copletar este campo"
                hayerrores = true
            }else if (txtComentRd.length >= 300) {
                txtComentR.error = "El comentario tiene un límite de 300 carácteres"
                hayerrores = true
            } else {
                txtComentR.error = null
            }

            if (txtCaliRd.toString().matches(Regex("^[1-5]$"))) {
                txtCaliR.error = null
            } else {
                txtCaliR.error = "La calificación debe de ser un número entero entre 1 y 5"
                hayerrores = true
            }

          if (hayerrores){
            } else{
                CoroutineScope(Dispatchers.IO).launch {

                    val objConexion = ClaseConexion().cadenaConexion()
                    val veterinarias = obtenerVets()
                    val vetSelected = veterinarias[spVetR.selectedItemPosition].UUID_veterinaria
                    val hacerResena = objConexion?.prepareStatement("insert into tbResenas (UUID_resena, calificacion, comentarios, resenador, vet) values (?, ?, ?, ?, ?)")!!
                    hacerResena.setString(1, UUID.randomUUID().toString())
                    hacerResena.setString(2, txtCaliRd.toString())
                    hacerResena.setString(3, txtComentR.text.toString())
                    hacerResena.setString(4, iniciarsesion.variablesLogin.UUID_Usuario)
                    hacerResena.setString(5, vetSelected)
                    hacerResena.executeUpdate()

                    withContext(Dispatchers.Main){
                        //mostrar mensaje y limpiar campos
                        Toast.makeText(context, "Reseña enviada", Toast.LENGTH_SHORT).show()
                        txtComentR.setText("")
                        txtCaliR.setText("")
                    }

                }

            }
        }


    }
        return root
}
}

