package jonathan.orellana.onepetapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import jonathan.orellana.onepetapp.ui.detalle_veterinaria
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import modelo.ClaseConexion
import modelo.dataClassVeterinaria
import java.util.UUID

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [agregar_veterinaria.newInstance] factory method to
 * create an instance of this fragment.
 */
class agregar_veterinaria : Fragment() {
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
        val root = inflater.inflate(R.layout.fragment_agregar_veterinaria, container, false)

        val txtNombreVet = root.findViewById<TextView>(R.id.txtNombreVet)
        val txtUbicacionVet = root.findViewById<TextView>(R.id.txtUbicacionVet)
        val txtNitVet = root.findViewById<TextView>(R.id.txtNitVet)
        val txtContactoVet = root.findViewById<TextView>(R.id.txtConctactoVet)
        val txtCorreoVet = root.findViewById<TextView>(R.id.txtCorreoVet)
        val txtDescripcionVet = root.findViewById<TextView>(R.id.txtDescripcionServicios)
        lateinit var veterinaria : dataClassVeterinaria
        val btnRegistrarVet = root.findViewById<Button>(R.id.btnRegistrarVet)

        btnRegistrarVet.setOnClickListener {
            val nombre = txtNombreVet.text.toString()
            val ubicacion = txtUbicacionVet.text.toString()
            val NIT = txtNitVet.text.toString()
            val Contacto = txtContactoVet.text.toString()
            val Correo = txtCorreoVet.text.toString()
            val descripcion = txtDescripcionVet.text.toString()

            var hayerrores = false;

            if (nombre.isEmpty()){
                txtNombreVet.error = "El nombre es obligatorio"
                hayerrores = true
            }else{
                txtNombreVet.error = null;
            }

            if (ubicacion.isEmpty()){
                txtUbicacionVet.error = "La ubicación es obligatoria"
                hayerrores = true }
            else{
                txtUbicacionVet.error = null;
            }


            if (NIT.isEmpty()){
            txtNitVet.error = "El NIT es obligatorio"
            hayerrores = true }
            else {
            txtNitVet.error = null;
        }


            if (Contacto.isEmpty()){
                txtContactoVet.error = "El contacto es obligatorio"
                hayerrores = true }
            else{
                txtContactoVet.error = null;
            }


            if (Correo.isEmpty()){
                txtCorreoVet.error = "El correo es obligatorio"
                hayerrores = true }
            else{
                txtCorreoVet.error = null;
            }

            if (descripcion.isEmpty()){
                txtCorreoVet.error = "la descripción de servicios es obligatorio"
                hayerrores = true }
            else{
                txtDescripcionVet.error = null;
            }

           if (!Correo.matches(Regex( "[a-zA-Z0-9._-]+@[a-z]+[.][a-z+]"))) {
               txtCorreoVet.error = "El correo no tiene el formato válido"
                hayerrores = true
            }
            //else {
               // txtCorreoVet.error = null
            //}

            if (hayerrores)
            {
            //
            }
            else {
                CoroutineScope(Dispatchers.IO).launch {
                    val objConexion = ClaseConexion().cadenaConexion()
                    val addVet =
                        objConexion?.prepareStatement("into tbveterinarias (uuid_veterinaria,nombre_veterinaria, ubicacion_veterinaria, nit, contacto_veterinaria, correo_veterinaria, descripcion_servicio) values (?,?,?,?,?,?,?,?)")!!
                    addVet.setString(1, UUID.randomUUID().toString())
                    addVet.setString(2, txtNombreVet.text.toString())
                    addVet.setString(3, txtUbicacionVet.text.toString())
                    addVet.setString(4, txtNitVet.text.toString())
                    addVet.setString(5, txtContactoVet.text.toString())
                    addVet.setString(6,txtCorreoVet.text.toString())
                    addVet.setString(7, txtDescripcionVet.text.toString())
                    addVet.executeUpdate()


                    val pantallaDetalle = Intent(context, detalle_veterinaria::class.java)
                    pantallaDetalle.putExtra("UUID_veterinaria", veterinaria.UUID_veterinaria)
                    pantallaDetalle.putExtra("nombre_veterinaria", veterinaria.nombre_veterinaria)
                    pantallaDetalle.putExtra("ubicacion_veterinaria", veterinaria.ubicacion_veterinaria)
                    pantallaDetalle.putExtra("NIT", veterinaria.nit)
                    pantallaDetalle.putExtra("contacto_veterinaria", veterinaria.contacto_veterinaria)
                    pantallaDetalle.putExtra("correo_veterinaria", veterinaria.correo_veterinaria)
                    pantallaDetalle.putExtra("descripcion_servicios", veterinaria.descripcion_servicios)
                    requireContext().startActivity(pantallaDetalle)

                }
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
         * @return A new instance of fragment agregar_veterinaria.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            agregar_veterinaria().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}