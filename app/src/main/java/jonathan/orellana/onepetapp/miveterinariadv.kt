package jonathan.orellana.onepetapp

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion

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



        val txt = activity?.intent?.getStringExtra("")
        val nombreRecibido= activity?.intent?.getStringExtra("nombre_veterinaria")
        val ubicacionRecibido = activity?.intent?.getStringExtra("ubicacion_veterinaria")
        val nitRecibido = activity?.intent?.getIntExtra("NIT", 0)
        val contactoRecibido = activity?.intent?.getStringExtra("contacto_veterinaria")
        val correoRecibido = activity?.intent?.getStringExtra("correo_veterinaria")
        val descripcionRecibida = activity?.intent?.getStringExtra("descripcion_servicios")



        //Asignarle los datos recibidos a mis textos
//Segundo = primero
        txtVerNombreVet.text = txtVerNombreVet.toString()
        txtVerUbicacionVet.text = txtVerUbicacionVet.toString()
        txtVerNitVet.text = txtVerNitVet.toString()
        txtVerContactoVet.text = txtVerContactoVet.toString()
        txtVerCorreoVet.text = txtVerCorreoVet.toString()
        txtVerServiciosVet.text = txtVerServiciosVet.toString()




        btnEditarVet.setOnClickListener {
            val nombreNuevo = txtVerNombreVet.text.toString()
            val ubicacionNueva = txtVerUbicacionVet.text.toString()
            val NITNuevo = txtVerNitVet.text.toString()
            val ContactoNuevo = txtVerContactoVet.text.toString()
            val CorreoNuevo = txtVerCorreoVet.text.toString()
            val descripcionNueva = txtVerServiciosVet.text.toString()

            var hayerrores = false;

            if (nombreNuevo.isEmpty()){
                txtVerNombreVet.error = "El nombre es obligatorio"
                hayerrores = true
            }else{
                txtVerNombreVet.error = null;
            }

            if (ubicacionNueva.isEmpty()){
                txtVerUbicacionVet.error = "La ubicación es obligatoria"
                hayerrores = true }
            else{
                txtVerUbicacionVet.error = null;
            }


            if (NITNuevo.isEmpty()){
                txtVerNitVet.error = "El NIT es obligatorio"
                hayerrores = true }
            else {
                txtVerNitVet.error = null;
            }


            if (ContactoNuevo.isEmpty()){
                txtVerContactoVet.error = "El contacto es obligatorio"
                hayerrores = true }
            else{
                txtVerContactoVet.error = null;
            }


            if (CorreoNuevo.isEmpty()){
                txtVerCorreoVet.error = "El correo es obligatorio"
                hayerrores = true }
            else{
                txtVerCorreoVet.error = null;
            }

            if (descripcionNueva.isEmpty()){
                txtVerServiciosVet.error = "la descripción de servicios es obligatorio"
                hayerrores = true }
            else{
                txtVerServiciosVet.error = null;
            }

            if (!CorreoNuevo.matches(Regex( "[a-zA-Z0-9._-]+@[a-z]+[.][a-z+]"))) {
                txtVerCorreoVet.error = "El correo no tiene el formato válido"
                hayerrores = true
            }
            else {


                ///1 - creo un objeto de la clase conexion
                val objConexion = ClaseConexion().cadenaConexion()

                //2 - Creo una variable que tenga un prepareStatement
                val updateVet = objConexion?.prepareStatement("UPDATE tbveterinarias set nombre_veterinaria = '?', ubicacion_veterinaria = '?', nit = '?', contacto_veterinaria = '?', correo_veterinaria = '?' descripcion_servicio = '?' where uuid_veterinaria = '';")!!
                updateVet.setString(1, nombreNuevo)
                updateVet.setString(2, ubicacionNueva)
                updateVet.setString(3, NITNuevo)
                updateVet.setString(4, ContactoNuevo)
                updateVet.setString(5, CorreoNuevo)
                updateVet.setString(6, uuid_traido)

                updateVet.executeUpdate()

                val builder = AlertDialog.Builder(context)
                builder.setTitle("Editar")
                builder.setMessage("Estas seguro que quieres editar?")




            }

        btnEliminarVet.setOnClickListener {
        }
            GlobalScope.launch(Dispatchers.IO) {
                // creamos un objeto de la clase conexion

                val objConexion = ClaseConexion().cadenaConexion()

                // 2- Crear una variable que contenga un preparestatement (donde se mete el código de sqlserver
                val deleteVeterinaria = objConexion?.prepareStatement( "delete from tbveterinarias where nombre_veterinaria = ?")!!
                deleteVeterinaria.setString(1, nombreRecibido)
                deleteVeterinaria.executeUpdate()

                val commit = objConexion.prepareStatement("commit")!!
                commit.executeUpdate()
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