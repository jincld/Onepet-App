package jonathan.orellana.onepetapp

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
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
        val root = inflater.inflate(R.layout.fragment_actualizar_y_eliminar_vet, container, false)



        val txtVerNombreVet = root.findViewById<TextView>(R.id.txtVerNombreVet)
        val txtVerUbicacionVet = root.findViewById<TextView>(R.id.txtVerUbicacionVet)
        val txtVerNitVet = root.findViewById<TextView>(R.id.txtVerNitVet)
        val txtVerContactoVet = root.findViewById<TextView>(R.id.txtVerContactoVet)
        val txtVerCorreoVet = root.findViewById<TextView>(R.id.txtVerCorreoVet)
        val txtVerServiciosVet = root.findViewById<TextView>(R.id.txtVerServiciosVet)
        val btnEditarVet = root.findViewById<Button>(R.id.btnEditarVet)
        val btnEliminarVet = root.findViewById<Button>(R.id.btnEliminarVet)



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


        fun uodate(nombreNuevo: String, ubicacionNueva:String, NITNuevo:String ,ContactoNuevo:String, CorreoNuevo: String ) {
            val correoGLobalTraido =
                registroduenovet.VariablesGlobalesRegistroDuenio.txtcorreoadminvetGlobal

            ///1 - creo un objeto de la clase conexion
            val objConexion = ClaseConexion().cadenaConexion()

            //2 - Creo una variable que tenga un prepareStatement
            val updateVet =
                objConexion?.prepareStatement("UPDATE tbveterinarias set nombre_veterinaria = '?', ubicacion_veterinaria = '?', nit = '?', contacto_veterinaria = '?', correo_veterinaria = '?' descripcion_servicio = '?' where correo_usuario = '';")!!
            updateVet.setString(1, nombreNuevo)
            updateVet.setString(2, ubicacionNueva)
            updateVet.setString(3, NITNuevo)
            updateVet.setString(4, ContactoNuevo)
            updateVet.setString(5, CorreoNuevo)
            updateVet.setString(6, correoGLobalTraido)
            updateVet.executeUpdate()
        }

        btnEditarVet.setOnClickListener {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Editar")
                builder.setMessage("Estas seguro que quieres editar?")

                val nombrenuevo = EditText(context)
                nombrenuevo.setHint(nombreRecibido)
                 builder.setView(nombrenuevo)

            val nuevaubicacion = EditText(context)
            nuevaubicacion.setHint(ubicacionRecibido)
            builder.setView(nuevaubicacion)

            val nuevoNit = EditText(context)
            nombrenuevo.setHint(nitRecibido!!.toInt())
            builder.setView(nuevoNit)


            val nuevoContacto = EditText(context)
            nombrenuevo.setHint(contactoRecibido)
            builder.setView(nuevoContacto)

            val correoNuevo = EditText(context)
            nombrenuevo.setHint(correoRecibido)
            builder.setView(correoNuevo)


            builder.setPositiveButton("Si") { dialog, which ->
             uodate(nombrenuevo.text.toString(), nuevaubicacion.text.toString(), nuevoNit.text.toString(), nuevoContacto.text.toString(), correoNuevo.text.toString())
                }
                builder.setNegativeButton("no") { dialog, which ->
                    dialog.dismiss()
                }




            }

        btnEliminarVet.setOnClickListener {
        }
            GlobalScope.launch(Dispatchers.IO) {
                // creamos un objeto de la clase conexion

                val objConexion = ClaseConexion().cadenaConexion()

                // 2- Crear una variable que contenga un preparestatement (donde se mete el código de sqlserver
                val deleteVeterinaria = objConexion?.prepareStatement( "delete from tbveterinarias where nombre_veterinaria = ?")!!
                deleteVeterinaria.setString(1, nombreRecibido)



                val builder = AlertDialog.Builder(context)
                builder.setTitle("Eliminar")
                builder.setMessage("Estas seguro que quieres eliminar tu veterinaria?")

                builder.setPositiveButton("Si") { dialog, which ->
                    deleteVeterinaria.executeUpdate()
                    val commit = objConexion.prepareStatement("commit")!!
                    commit.executeUpdate()


                }
                builder.setNegativeButton("no") { dialog, which ->
                    dialog.dismiss()
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