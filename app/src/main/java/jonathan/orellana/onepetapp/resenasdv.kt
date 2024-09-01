package jonathan.orellana.onepetapp

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment


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


        btnEnviarResena.setOnClickListener {
            val txtCaliR = txtCaliR.text.toString()
            val txtCaliINT = txtCaliR.toInt()
            val txtComentRd = txtComentR.text.toString()

            var hayerrores = false

            if (txtCaliINT !in 1..5) {
                val alertDialog = AlertDialog.Builder(requireContext())
                alertDialog.setTitle("Error")
                alertDialog.setMessage("La calificación debe de estar entre 1 y 5")
                alertDialog.setPositiveButton("Aceptar") { _, _ -> }
                alertDialog.show()
                hayerrores = true
            }else {
                hayerrores = false
            }

           if (txtComentRd.length <= 300) {
                 txtComentR.error = "La contraseña debe tener más de 8 carácteres"
                 hayerrores = true
             } else {
                 txtComentR.error = null
             }

          /* if (hayerrores){
            } else{
                CoroutineScope(Dispatchers.IO).launch {

                    val objConexion = ClaseConexion().cadenaConexion()

                    val hacerResena = objConexion?.prepareStatement("insert into tbResenas (UUID_resena, calificacion, comentarios, resenador, vet) values (?, ?, ?, ?, ?)")!!
                    hacerResena.setString(1, UUID.randomUUID().toString())
                    hacerResena.setString(2, txtCaliR.toString())
                    hacerResena.setString(3, txtComentR.text.toString())
                    hacerResena.setString(4, iniciarsesion.variablesLogin.valorCorreoUsuario)
                    hacerResena.setString(5, uuidTraido)
                    hacerResena.executeUpdate()

                    withContext(Dispatchers.Main){
                        //mostrar mensaje y limpiar campos
                        Toast.makeText(context, "Empleado registrado", Toast.LENGTH_SHORT).show()
                        txtNombre_empleado.setText("")
                        txtCorreoEmpleado.setText("")
                        txtContra_empleado.setText("")

                    }

                }

            }*/
        }



        return root
    }

}