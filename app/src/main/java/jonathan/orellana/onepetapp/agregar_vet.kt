package jonathan.orellana.onepetapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import java.time.LocalDate
import java.util.UUID

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [agregar_vet.newInstance] factory method to
 * create an instance of this fragment.
 */
class agregar_vet : Fragment() {
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
    companion object VariablesGlobalesVeterinaria{
        lateinit var NombreVet: String
        var UUIDvet: String = UUID.randomUUID().toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_agregar_vet, container, false)

        val txtNombreVet = root.findViewById<EditText>(R.id.txtNombreVet)
        val txtUbicacionVet = root.findViewById<EditText>(R.id.txtUbicacionVet)
        val txtNitVet = root.findViewById<EditText>(R.id.txtNitVet)
        val txtContactoVet = root.findViewById<EditText>(R.id.txtConctactoVet)
        val txtCorreoVet = root.findViewById<EditText>(R.id.txtCorreoVet)
        val txtDescripcionVet = root.findViewById<EditText>(R.id.txtDescripcionServicios)
        val btnRegistrarVet = root.findViewById<Button>(R.id.btnRegistrarVet)








        btnRegistrarVet.setOnClickListener {
            val nombre = txtNombreVet.text.toString()
            val ubicacion = txtUbicacionVet.text.toString()
            val NIT = txtNitVet.text.toString()
            val Contacto = txtContactoVet.text.toString()
            val Correo = txtCorreoVet.text.toString()
            val descripcion = txtDescripcionVet.text.toString()

            var hayerrores = false;

            if (nombre.isEmpty()) {
                txtNombreVet.error = "El nombre es obligatorio"
                hayerrores = true
            } else {
                txtNombreVet.error = null;
            }

            if (ubicacion.isEmpty()) {
                txtUbicacionVet.error = "La ubicación es obligatoria"
                hayerrores = true
            } else {
                txtUbicacionVet.error = null;
            }


            if (NIT.isEmpty()) {
                txtNitVet.error = "El NIT es obligatorio"
                hayerrores = true
            } else {
                txtNitVet.error = null;
            }

            if (Contacto.isEmpty()) {
                txtContactoVet.error = "El contacto es obligatorio"
                hayerrores = true
            } else {
                txtContactoVet.error = null;
            }


            if (Correo.isEmpty()) {
                txtCorreoVet.error = "El correo es obligatorio"
                hayerrores = true
            } else {
                txtCorreoVet.error = null;
            }

            if (descripcion.isEmpty()) {
                txtCorreoVet.error = "la descripción de servicios es obligatorio"
                hayerrores = true
            } else {
                txtDescripcionVet.error = null;
            }

            if (!Correo.matches(Regex("[a-zA-Z0-9._-]+@[a-z]+[.][a-z]+"))) {
                txtCorreoVet.error = "El correo no tiene el formato válido"
                hayerrores = true

            } else {
                txtCorreoVet.error = null


            }
            if (hayerrores) {
                //
            } else {

                 CoroutineScope(Dispatchers.IO).launch {

                    val objConexion = ClaseConexion().cadenaConexion()
                    val addVet = objConexion?.prepareStatement("Insert into tbveterinarias (uuid_veterinaria,nombre_veterinaria, ubicacion_veterinaria, nit, contacto_veterinaria, correo_veterinaria, descripcion_servicio) values (?,?,?,?,?,?,?)")!!
                    addVet.setString(1, UUIDvet)
                    addVet.setString(2, txtNombreVet.text.toString())
                    addVet.setString( 3, txtUbicacionVet.text.toString())
                    addVet.setString(4, txtNitVet.text.toString())
                    addVet.setString(5, txtContactoVet.text.toString())
                    addVet.setString(6, txtCorreoVet.text.toString())
                    addVet.setString(7, txtDescripcionVet.text.toString())
                    addVet.executeUpdate()


                    val fecha = LocalDate.now().toString()
                    val IngresoAuditoria = objConexion?.prepareStatement("insert into tbAuditoria (UUID_auditoria, usuario, accion, fecha) values (?, ?, ?, ?)")!!
                    IngresoAuditoria.setString(1, UUID.randomUUID().toString())
                    IngresoAuditoria.setString(2,iniciarsesion.variablesLogin.correo_admin)
                    IngresoAuditoria.setString(3, "El usuario ha registrado una veterinaria")
                    IngresoAuditoria.setString(4,fecha )
                    IngresoAuditoria.executeUpdate()

                    NombreVet = txtNombreVet.text.toString()
                    println("este es el nombre de vet que quiero usar ${NombreVet}")

                     val UpdateUser = objConexion?.prepareStatement("Update tbUsuariosOne set vet = ?  where correo_usuario = ?")!!
                     UpdateUser.setString(1, UUIDvet)
                     println("este es la UUID de vet que quiero usar ${UUIDvet}")
                     UpdateUser.setString(2,iniciarsesion.variablesLogin.correo_admin)
                     println("este es el correo que quiero usar ${iniciarsesion.variablesLogin.correo_admin}")
                     UpdateUser.executeUpdate()

                     withContext(Dispatchers.Main){
                    findNavController().navigate(R.id.action_agregar_vet_to_veterinarias)
                    }


                }
            }

        }
/*
           fun obtenerUuidVet(): String? {

               GlobalScope.launch(Dispatchers.IO) {

                   val objConexion = ClaseConexion().cadenaConexion()
                   val resulSet =
                       objConexion?.prepareStatement("SELECT UUID_Veterinaria FROM tbVeterinarias WHERE nombre_veterinaria = ? ")!!
                   resulSet.setString(1, NombreVet)

                   val resultado = resulSet.executeQuery()

                   if (resultado.next()) {

                       UUID_Vet = resultado.getString("UUID_vet")

                       println("este es el uuid traido desde el if $UUID_Vet")
                   }

               }

return UUID_Vet
           }

        btnPrueba.setOnClickListener {
            obtenerUuidVet()
        }
*/
        return root

    }
            }
