package jonathan.orellana.onepetapp

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.dataClassEspecie
import java.util.Calendar
import java.util.UUID

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [agregarmascotaas.newInstance] factory method to
 * create an instance of this fragment.
 */
class agregarmascotaas : Fragment() {
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
        //Creo la variable root
        val root = inflater.inflate(R.layout.fragment_agregarmascotaas, container, false)
        //Mando a llamar el boton usando la variable root
        val txtNomAddMascota = root.findViewById<EditText>(R.id.txtNombreMascota)
        val spSelectEspecie = root.findViewById<Spinner>(R.id.spEspecie)
        val spGeneroMascota = root.findViewById<Spinner>(R.id.spGenero)
        val txtPesoAddMascota = root.findViewById<EditText>(R.id.txtPesoMascotas)
        val txtAñoAddMascota = root.findViewById<EditText>(R.id.txtAñoMascota)
        val txtEnferCAddMascota = root.findViewById<EditText>(R.id.txtEnfermedadesCM)
        val txtProceMAddMascota = root.findViewById<EditText>(R.id.txtProcedimientosMAscotas)
        val txtRazaMascota = root.findViewById<EditText>(R.id.txtRazaMascota)
        val txtAlerAddMascota = root.findViewById<EditText>(R.id.txtAlergiasMascotas)
        val btnAddFotoMascota = root.findViewById<Button>(R.id.btnAgregarFotoMascota)
        val btnAgregarMascotas = root.findViewById<Button>(R.id.btnAgregarMascotas)

        txtAñoAddMascota.setOnClickListener {
            val calendario = Calendar.getInstance()
            val anio = calendario.get(Calendar.YEAR)
            val mes = calendario.get(Calendar.MONTH)
            val dia = calendario.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { view, anioSeleccionado, mesSeleccionado, diaSeleccionado ->
                    val fechaSeleccionada =
                        "$diaSeleccionado/${mesSeleccionado + 1}/$anioSeleccionado"
                    txtAñoAddMascota.setText(fechaSeleccionada)
                },
                anio, mes, dia
            )
            datePickerDialog.show()
        }


        //Obtener UUID de Dueño de Mascota (Usuario)
        fun obtenerUUIDDueno(): String? {

            val correoGlobalEscrito = iniciarsesion.variablesGlobalesLogin.correodelUsuarioGlobal
            val objConexion = ClaseConexion().cadenaConexion()

            val traerUUIDUsuario = objConexion?.prepareStatement("SELECT UUID_usuario FROM tbUsuariosOne WHERE correo_usuario = '?'")!!
            traerUUIDUsuario.setString(1, correoGlobalEscrito)
            val resultSet = traerUUIDUsuario.executeQuery()

            var uuidUsuario: String? = null

            if (resultSet?.next() == true) {
                uuidUsuario = resultSet.getString("UUID_usuario")
                println("este es el uuid traido desde el if $uuidUsuario")
            }

            println("este es el uuid traido desde la funcion $uuidUsuario")
            return uuidUsuario
        }

        //1-Creamos la funcion que haga un select
        fun obtenerEspecie(): List<dataClassEspecie> {
            //Creo un objeto de la clase Conexion
            val objConexion = ClaseConexion().cadenaConexion()

            //Crear un Statement que ejecute el Select
            val statement = objConexion?.createStatement()
            val resultSet = statement?.executeQuery("Select * from tbEspecies")!!

            val listaEspecie = mutableListOf<dataClassEspecie>()

            while (resultSet.next()) {
                val uuidEspecie = resultSet.getString("UUID_especie")
                val nombreEspecie = resultSet.getString("nombre_especie")

                val especieCompleta = dataClassEspecie(uuidEspecie, nombreEspecie)
                listaEspecie.add(especieCompleta)
            }
            return listaEspecie
        }

        //Prgramar al Spinner
        CoroutineScope(Dispatchers.IO).launch {
            //1-Obtengo el listado de datos que quiero mostrar
            val listadoDeEspecie = obtenerEspecie()
            val nombreEspecies = listadoDeEspecie.map { it.nombre_especie }
            withContext(Dispatchers.Main) {
                //2- Creo y configuro el adaptador
                val miAdaptador = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    nombreEspecies
                )
                spSelectEspecie.adapter = miAdaptador
            }
        }

        //Obtener Lista del Spinner de Genero
        val listadoGenero = arrayOf("Masculino", "Femenino")

        //Crear y configurar el adaptador
        //El adaptador solicita tres cosas: el contexto, un layout y los datos
        val miAdaptadorDeLinea = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, listadoGenero)
        spGeneroMascota.adapter = miAdaptadorDeLinea


        btnAgregarMascotas.setOnClickListener{
            //Guardar en una variable los valores que escribio el  usuario
            val nombreMascota = txtNomAddMascota.text.toString()
            val pesoMascota = txtPesoAddMascota.text.toString()
            val añoMascota = txtAñoAddMascota.text.toString()
            val enfermedadesCronicas = txtEnferCAddMascota.text.toString()
            val procedimientoMA = txtProceMAddMascota.text.toString()
            val razaMascota = txtRazaMascota.text.toString()
            val alergiaMascota = txtAlerAddMascota.text.toString()

            //Variable para verificar si hay errores la inicializamos en false
            var hayErrores = false

            //TODO: 1- Validar que los campos no esten vacios
            //Nombre Mascota
            if (nombreMascota.isEmpty()) {
                txtNomAddMascota.error = "El nombre de la Mascota es obligatorio"
                hayErrores = true
            }
            else {
                txtNomAddMascota.error = null
            }
            //Peso Mascota
            if (pesoMascota.isEmpty()) {
                txtPesoAddMascota.error = "El Peso de la Mascota es obligatorio"
                hayErrores = true
            }
            else {
                txtPesoAddMascota.error = null
            }
            //Año de Nacimiento mascota
            if (añoMascota.isEmpty()) {
                txtAñoAddMascota.error = "El Año de Nacimiento de la Mascota es obligatorio"
                hayErrores = true
            }
            else {
                txtAñoAddMascota.error = null
            }
            //Enfermedades Cronicas Mascota
            if (enfermedadesCronicas.isEmpty()) {
                txtEnferCAddMascota.error = "El apartado es obligatorio llenarse"
                hayErrores = true
            }
            else {
                txtEnferCAddMascota.error = null
            }
            //Procedimientos Medicos Mascota Antes
            if (procedimientoMA.isEmpty()) {
                txtProceMAddMascota.error = "El apartado es obligatorio llenarse"
                hayErrores = true
            }
            else {
                txtProceMAddMascota.error = null
            }
            //Raza Mascota
            if (razaMascota.isEmpty()) {
                txtRazaMascota.error = "El apartado es obligatorio llenarse"
                hayErrores = true
            }
            else {
                txtProceMAddMascota.error = null
            }
            //Alergias Mascota
            if (alergiaMascota.isEmpty()) {
                txtAlerAddMascota.error = "El apartado es obligatorio llenarse"
                hayErrores = true
            }
            else {
                txtAlerAddMascota.error = null
            }

            //Si hay errores no procede a guardar los datos
            if(hayErrores) {
                //Hacer algo si hay errores
            }
            else {
                //Si todas las validaciones son correcta, procede a guardar los datos en la base de datos
                CoroutineScope(Dispatchers.IO).launch {
                    val especiee = obtenerEspecie()
                    val especieID = especiee[spSelectEspecie.selectedItemPosition].UUID_especie

                    //Guardar datos
                    //1- Creo un objeto de la clase conexion
                    val claseC = ClaseConexion().cadenaConexion()

                    //Traer el codigo de UUID Usuario
                    val uuidUsuarioTraido = obtenerUUIDDueno()

                    //2- creo una variable que contenga un PrepareStatement
                    val addMascota =
                        claseC?.prepareStatement("into tbMascotas(uuid_mascota, nombre_mascota, raza, sexo, procesos_previos, alergias, enfermedades_cronicas, fecha_nacimiento, peso,  especie, dueno) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )")!!
                    //UUID de usuario no mostrado al dueno, estando solo adentro de la base de datos
                    addMascota.setString(1, UUID.randomUUID().toString())
                    addMascota.setString(2, txtNomAddMascota.text.toString())
                    addMascota.setString(3, txtRazaMascota.text.toString())
                    //Spinner Generado en codigo
                    addMascota.setString(4, spGeneroMascota.selectedItem.toString())
                    addMascota.setString(5, txtProceMAddMascota.text.toString())
                    addMascota.setString(6, txtAlerAddMascota.text.toString())
                    addMascota.setString(7, txtEnferCAddMascota.text.toString())
                    addMascota.setString(8, txtAñoAddMascota.text.toString())
                    addMascota.setInt(9, txtPesoAddMascota.text.toString().toInt())
                    addMascota.setString(10, uuidUsuarioTraido)
                    println("este es el uuid traido antes del execute  $uuidUsuarioTraido")
                    addMascota.setString(11, especieID)
                    addMascota.executeUpdate()

                    //Abro una corrutina para mostrar una alerta y limpiar los campos
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Datos registrados", Toast.LENGTH_SHORT).show()
                        txtNomAddMascota.setText("")
                        txtRazaMascota.setText("")
                        txtProceMAddMascota.setText("")
                        txtAlerAddMascota.setText("")
                        txtEnferCAddMascota.setText("")
                        txtEnferCAddMascota.setText("")
                        txtAñoAddMascota.setText("")
                        txtPesoAddMascota.setText("")
                    }
                }
            }
        }
        //retornar root
        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment agregarmascotaas.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            agregarmascotaas().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}