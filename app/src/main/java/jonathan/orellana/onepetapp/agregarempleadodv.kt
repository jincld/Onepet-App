package jonathan.orellana.onepetapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import java.util.UUID

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class agregarempleadodv : Fragment() {
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
        return inflater.inflate(R.layout.fragment_agregarempleadodv, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val txtNombreEmpleado = view.findViewById<EditText>(R.id.txtNombreEmpleado)
        val txtCorreoEmpleado = view.findViewById<EditText>(R.id.txtCorreoEmpleado)
        val txtContraEmpleado = view.findViewById<EditText>(R.id.txtContraEmpleado)
        val spnRolEmpleado = view.findViewById<Spinner>(R.id.spnRolEmpleado)
        val spnEtiquetaEmpleado = view.findViewById<Spinner>(R.id.spnEtiquetaEmpleado)
        val btnCrearEmpleado = view.findViewById<Button>(R.id.btnCrearEmpleado)

        // Llenar los Spinners
        CoroutineScope(Dispatchers.IO).launch {
            val roles = obtenerRoles()
            val etiquetas = obtenerEtiquetas()

            withContext(Dispatchers.Main) {
                spnRolEmpleado.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, roles).apply {
                    setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                }
                spnEtiquetaEmpleado.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, etiquetas).apply {
                    setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                }
            }
        }

        // Programación del botón "Crear Empleado"
        btnCrearEmpleado.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val nombreEmpleado = txtNombreEmpleado.text.toString()
                val correoEmpleado = txtCorreoEmpleado.text.toString()
                val contraEmpleado = txtContraEmpleado.text.toString()
                val rolEmpleado = spnRolEmpleado.selectedItem.toString()
                val etiquetaEmpleado = spnEtiquetaEmpleado.selectedItem.toString()

                if (nombreEmpleado.isNotEmpty() && correoEmpleado.isNotEmpty() && contraEmpleado.isNotEmpty() && rolEmpleado.isNotEmpty() && etiquetaEmpleado.isNotEmpty()) {
                    val resultado = crearEmpleado(nombreEmpleado, correoEmpleado, contraEmpleado, rolEmpleado)
                    withContext(Dispatchers.Main) {
                        // Aquí puedes manejar la respuesta, como mostrar un mensaje de éxito o error
                    }
                }
            }
        }
    }

    private suspend fun obtenerRoles(): List<String> {
        val roles = mutableListOf<String>()
        val objConexion = ClaseConexion().cadenaConexion()
        val consulta = objConexion?.prepareStatement("SELECT nombre_rol FROM tbRolesUsuarios")
        val resultSet = consulta?.executeQuery()
        while (resultSet?.next() == true) {
            roles.add(resultSet.getString("nombre_rol"))
        }
        return roles
    }

    private suspend fun obtenerEtiquetas(): List<String> {
        val etiquetas = mutableListOf<String>()
        // Aquí debes realizar la consulta a la base de datos para obtener las etiquetas
        // Similar a obtenerRoles()
        return etiquetas
    }

    private suspend fun crearEmpleado(nombre: String, correo: String, contra: String, rol: String): Boolean {
        return try {
            val objConexion = ClaseConexion().cadenaConexion()
            val addEmpleado = objConexion?.prepareStatement(
                "INSERT INTO tbUsuariosOne (UUID_usuario, nombre_usuario, contra_usuario, correo_usuario, rol) VALUES (?, ?, ?, ?, ?)"
            )
            val uuidEmpleado = UUID.randomUUID().toString()
            addEmpleado?.setString(1, uuidEmpleado)
            addEmpleado?.setString(2, nombre)
            addEmpleado?.setString(3, contra)
            addEmpleado?.