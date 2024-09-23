package jonathan.orellana.onepetapp

import RecyclerViewHelpersResenas.AdaptadorResenas
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
import modelo.dataClassResenas

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [misResenas.newInstance] factory method to
 * create an instance of this fragment.
 */
class misResenas : Fragment() {
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
        val root = inflater.inflate(R.layout.fragment_mis_resenas, container, false)
        val rcvResenas = root.findViewById<RecyclerView>(R.id.rcvMisResenas)

        rcvResenas.layoutManager = LinearLayoutManager(context)
        fun obtenerDatosResena(): List<dataClassResenas>{

            //obtenemos los datps
            val objConexion = ClaseConexion().cadenaConexion()
            val resulSet = objConexion?.prepareStatement("select * from tbResenas where vet = ?")!!
            resulSet.setString(1, iniciarsesion.variablesLogin.uuid_Vet_real)
            resulSet.executeQuery()

            var prueba = resulSet.executeQuery()
            val listaResenas = mutableListOf<dataClassResenas>()

            while(prueba.next()){
                //asignamos valores
                val UUID_resena = prueba.getString("UUID_resena")
                val calificacion = prueba.getInt("calificacion")
                val comentarios = prueba.getString("comentarios")
                val resenador = prueba.getString("resenador")
                val vet = prueba.getString("vet")

                val ValoresJuntos = dataClassResenas(UUID_resena, calificacion, comentarios, resenador, vet)
                listaResenas.add(ValoresJuntos)
            }
            return listaResenas
        }
        CoroutineScope(Dispatchers.IO).launch {
            val resenasBD = obtenerDatosResena()
            withContext(Dispatchers.Main){
                val adapter = AdaptadorResenas(resenasBD)
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
         * @return A new instance of fragment misResenas.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            misResenas().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}