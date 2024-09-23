package jonathan.orellana.onepetapp

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import modelo.ClaseConexion
import java.security.MessageDigest

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ajustesdv.newInstance] factory method to
 * create an instance of this fragment.
 */
class ajustesdv : Fragment() {
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

        val root = inflater.inflate(R.layout.fragment_ajustesdv, container, false)
//mandamos a llamar los botones
var txtNombreAjustes: TextView =  root.findViewById(R.id.txtNombreAjustes)
        var txtContraAjustes: TextView =  root.findViewById(R.id.txtContraAjustes)
        var txtCorreoAjustes: TextView =  root.findViewById(R.id.txtCorreoAjustes)
        val btnCerrar = root.findViewById<Button>(R.id.btnCerrarSesion)
        val btnActualizarDatos = root.findViewById<Button>(R.id.btnActualizarUser)




        txtCorreoAjustes.text = iniciarsesion.variablesLogin.correo_admin
        txtContraAjustes.text = iniciarsesion.variablesLogin.contra_sinincriptar
 txtNombreAjustes.text =  MainActivity.variablesMainActivity.nombre_user


        fun hashSHA256(contraescrita: String): String {
            val bytes = MessageDigest.getInstance("SHA-256").digest(contraescrita.toByteArray())
            return bytes.joinToString("") { "%02x".format(it)}
            }

        //funcion para actualizar usuario en configuracion
        fun updateUser(nombreNuevoUser: String, contraNuevaUser: String, CorreoNuevoUser: String) {
            GlobalScope.launch(Dispatchers.IO) {

                ///1 - creo un objeto de la clase conexion
                val objConexion = ClaseConexion().cadenaConexion()

                //2 - Creo una variable que tenga un prepareStatement
                val updateUser =
                    objConexion?.prepareStatement(
                        "UPDATE tbUsuariosOne set nombre_usuario = ?, contra_usuario = ?, correo_usuario = ?  where correo_usuario = ?"
                    )!!
                updateUser.setString(1, nombreNuevoUser)
                updateUser.setString(2, contraNuevaUser)
                updateUser.setString(3, CorreoNuevoUser)
                updateUser.setString(4, txtCorreoAjustes.text.toString())
                updateUser.executeUpdate()
            }
        }
        fun isValid(vararg editTexts: EditText): Boolean {
            for (editText in editTexts) {
                if (editText.text.toString().isEmpty()) {
                    Toast.makeText(context, "Por favor llene todos los datos", Toast.LENGTH_SHORT).show()
                    return false
                }
            }
            return true
        }

//cuadro de texto y el editar
        btnActualizarDatos.setOnClickListener{
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Editar")
            builder.setMessage("Estas seguro que quieres editar?")

            val nombrenuevo = EditText(context)
            nombrenuevo.setText(txtNombreAjustes.text.toString())

            val correonuevo = EditText(context)
            correonuevo.setText(txtCorreoAjustes.text.toString())

            val contranueva = EditText(context)
            contranueva.setText(txtContraAjustes.text.toString())

            val layout = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                addView(nombrenuevo)
                addView(correonuevo)
                addView(contranueva)
            }

            builder.setView(layout)

            builder.setPositiveButton("Si") { dialog, which ->
                if (isValid(nombrenuevo, contranueva, correonuevo )) {
                    val contraIncriptada = hashSHA256(contranueva.text.toString())
                    updateUser(
                        nombrenuevo.text.toString(),
                        contraIncriptada,
                        correonuevo.text.toString(),
                    )
                    println(" --------- este es el nombre de vet que quiero usar ${nombrenuevo.text.toString()}")
                    println("---------- este es el nombre de vet que quiero usar ${contraIncriptada}")
                    println("---------- este es el nombre de vet que quiero usar ${correonuevo.text}")

                    Toast.makeText(context, "Datos actualizados", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                    txtNombreAjustes.text = nombrenuevo.text.toString()
                    txtContraAjustes.text = contranueva.text.toString()
                    txtCorreoAjustes.text = correonuevo.text.toString()
                }
            }
            builder.setNegativeButton("no") { dialog, which ->
                dialog.dismiss()
            }
            builder.show()
        }


        btnCerrar.setOnClickListener {
            val cerrar = Intent(context, login::class.java)
            startActivity(cerrar)
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
         * @return A new instance of fragment ajustesdv.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ajustesdv().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}