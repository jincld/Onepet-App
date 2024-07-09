package jonathan.orellana.onepetapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [miveterinariadv.newInstance] factory method to
 * create an instance of this fragment.
 */
class miveterinariadv : Fragment() {
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
        val root = inflater.inflate(R.layout.fragment_miveterinariadv, container, false)



        val txtVerNombreVet = root.findViewById<TextView>(R.id.txtVerNombreVet)
        val txtVerUbicacionVet = root.findViewById<TextView>(R.id.txtVerUbicacionVet)
        val txtVerNitVet = root.findViewById<TextView>(R.id.txtVerNitVet)
        val txtVerContactoVet = root.findViewById<TextView>(R.id.txtVerContactoVet)
        val txtVerCorreoVet = root.findViewById<TextView>(R.id.txtVerCorreoVet)
        val txtVerServiciosVet = root.findViewById<TextView>(R.id.txtVerServiciosVet)
        val btnEditarVet = root.findViewById<Button>(R.id.btnEditarVet)
        val btnEliminarVet = root.findViewById<Button>(R.id.btnEliminarVet)


        val intent = Intent(context,agregar_veterinaria::class.java)

        val txt = intent.getStringExtra("")
        val nombreRecibido= intent.getStringExtra("nombre_veterinaria")
        val ubicacionRecibido = intent.getStringExtra("ubicacion_veterinaria")
        val nitRecibido = intent.getIntExtra("NIT", 0)
        val contactoRecibido = intent.getStringExtra("contacto_veterinaria")
        val correoRecibido = intent.getStringExtra("correo_veterinaria")
        val descripcionRecibida = intent.getStringExtra("descripcion_servicios")



        //Asignarle los datos recibidos a mis textos
//Segundo = primero
        txtVerNombreVet.text = txtVerNombreVet.toString()
        txtVerUbicacionVet.text = txtVerUbicacionVet.toString()
        txtVerNitVet.text = txtVerNitVet.toString()
        txtVerContactoVet.text = txtVerContactoVet.toString()
        txtVerCorreoVet.text = txtVerCorreoVet.toString()
        txtVerServiciosVet.text = txtVerServiciosVet.toString()











        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment miveterinariadv.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            miveterinariadv().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}