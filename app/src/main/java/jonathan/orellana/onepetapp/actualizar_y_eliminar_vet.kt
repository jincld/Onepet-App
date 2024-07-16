package jonathan.orellana.onepetapp

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import modelo.ClaseConexion

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [actualizar_y_eliminar_vet.newInstance] factory method to
 * create an instance of this fragment.
 */
class actualizar_y_eliminar_vet : Fragment() {
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


        //Asignarle los datos recibidos a mis textos
//Segundo = primero
        txtVerNombreVet.text = agregar_vet.VariablesGlobalesVeterinaria.NombreVet
        txtVerUbicacionVet.text = agregar_vet.VariablesGlobalesVeterinaria.UbicacionVet
        txtVerNitVet.text = agregar_vet.VariablesGlobalesVeterinaria.NitVet
        txtVerContactoVet.text = agregar_vet.VariablesGlobalesVeterinaria.ContactoVet
        txtVerCorreoVet.text = agregar_vet.VariablesGlobalesVeterinaria.CorreoVet
        txtVerServiciosVet.text = agregar_vet.VariablesGlobalesVeterinaria.DescripcionVet



        fun uodate(nombreNuevo: String, ubicacionNueva: String, NITNuevo: String, ContactoNuevo: String, CorreoNuevo: String, descripcion: String) {
            GlobalScope.launch(Dispatchers.IO) {
                val correoGLobalTraido = agregar_vet.VariablesGlobalesVeterinaria.CorreoVet

                ///1 - creo un objeto de la clase conexion
                val objConexion = ClaseConexion().cadenaConexion()

                //2 - Creo una variable que tenga un prepareStatement
                val updateVet =
                    objConexion?.prepareStatement(
                        "UPDATE tbveterinarias set nombre_veterinaria = ?, ubicacion_veterinaria = ?, nit = ?, contacto_veterinaria = ?, correo_veterinaria = ?, descripcion_servicio = ? where correo_veterinaria = ?"
                    )!!
                updateVet.setString(1, nombreNuevo)
                updateVet.setString(2, ubicacionNueva)
                updateVet.setString(3, NITNuevo)
                updateVet.setString(4, ContactoNuevo)
                updateVet.setString(5, CorreoNuevo)
                updateVet.setString(6, descripcion)
                updateVet.setString(7, correoGLobalTraido)
                updateVet.executeUpdate()
            }
        }
        fun isValid(vararg editTexts: EditText): Boolean {
            for (editText in editTexts) {
                if (editText.text.toString().isEmpty()) {
                    Toast.makeText(context, "Porfavor llene todos los datos", Toast.LENGTH_SHORT).show()
                    return false
                }
            }
            return true
        }


        btnEditarVet.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Editar")
            builder.setMessage("Estas seguro que quieres editar?")

            val nombrenuevo = EditText(context)
            nombrenuevo.setHint("Nombre")

            val nuevaubicacion = EditText(context)
            nuevaubicacion.setHint("Ubicación")

            val nuevoNit = EditText(context)
            nuevoNit.setHint("NIT")
            nuevoNit.inputType = InputType.TYPE_CLASS_NUMBER //

            val nuevoContacto = EditText(context)
            nuevoContacto.setHint("Contacto")

            val correoNuevo = EditText(context)
            correoNuevo.setHint("Correo")

            val descripcionNueva = EditText(context)
            descripcionNueva.setHint("Descripción servicios")

            val layout = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.VERTICAL
                addView(nombrenuevo)
                addView(nuevaubicacion)
                addView(nuevoNit)
                addView(nuevoContacto)
                addView(correoNuevo)
                addView(descripcionNueva)
            }

            builder.setView(layout)

            builder.setPositiveButton("Si") { dialog, which ->
                if (isValid(nombrenuevo, nuevaubicacion, nuevoNit, nuevoContacto, correoNuevo, descripcionNueva)) {
                    uodate(
                        nombrenuevo.text.toString(),
                        nuevaubicacion.text.toString(),
                        nuevoNit.text.toString(),
                        nuevoContacto.text.toString(),
                        correoNuevo.text.toString(),
                        descripcionNueva.text.toString()
                    )
                    Toast.makeText(context, "Datos actualizados", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                    txtVerNombreVet.text = nombrenuevo.text.toString()
                    txtVerUbicacionVet.text = nuevaubicacion.text.toString()
                    txtVerNitVet.text = nuevoNit.text.toString()
                    txtVerContactoVet.text = nuevoContacto.text.toString()
                    txtVerCorreoVet.text = correoNuevo.text.toString()
                    txtVerServiciosVet.text = descripcionNueva.text.toString()
                }
            }
            builder.setNegativeButton("no") { dialog, which ->
                dialog.dismiss()
            }
            builder.show()
        }





        fun eliminarVet() {
            GlobalScope.launch(Dispatchers.IO) {
                // creamos un objeto de la clase conexion

                val objConexion = ClaseConexion().cadenaConexion()
                println("estamos dentro de una corrutina")

                // 2- Crear una variable que contenga un preparestatement (donde se mete el código de sqlserver
                val deleteVeterinaria =
                    objConexion?.prepareStatement("delete from tbveterinarias where nombre_veterinaria = ?")!!
                deleteVeterinaria.setString(1, agregar_vet.VariablesGlobalesVeterinaria.NombreVet)
                deleteVeterinaria.executeUpdate()

                println("este es el nombre de la vet que quiero eliminar ${agregar_vet.NombreVet}")

                val commit = objConexion.prepareStatement("commit")!!
                commit.executeUpdate()
            }

        }
        btnEliminarVet.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Eliminar")
            builder.setMessage("Estas seguro que quieres eliminar tu veterinaria?")

            builder.setPositiveButton("Si") { dialog, which ->
                eliminarVet()
                Toast.makeText(context, "Datos eliminados", Toast.LENGTH_SHORT).show()


            }
            builder.setNegativeButton("no") { dialog, which ->
                dialog.dismiss()
            }
            builder.show()
        }
        return  root

    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment actualizar_y_eliminar_vet.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            actualizar_y_eliminar_vet().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}



