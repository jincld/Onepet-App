package jonathan.orellana.onepetapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [agregarmascotas.newInstance] factory method to
 * create an instance of this fragment.
 */
class agregarmascotas : Fragment() {
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

    //1-Mando a llamar todos los elementos de la vista para programarlos


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Creo la variable root
        val root = inflater.inflate(R.layout.fragment_agregarmascotas, container, false)
        //Mando a llamar el boton usando la variable root
        val txtNomAddMascota = root.findViewById<EditText>(R.id.txtNombreMascota)
        val txtAddEspecie = root.findViewById<EditText>(R.id.txtEspecieMascota)
        val txtGenAddMascota = root.findViewById<EditText>(R.id.txtGeneroMascota)
        val txtPesoAddMascota = root.findViewById<EditText>(R.id.txtPesoMascota)
        val txtAñoAddMascota = root.findViewById<EditText>(R.id.txtAñoMascota)
        val txtEnferCAddMascota = root.findViewById<EditText>(R.id.txtEnfermedadesCMascota)
        val txtProceMAddMascota = root.findViewById<EditText>(R.id.txtProcedimientosMAscota)
        val txtAlerAddMascota = root.findViewById<EditText>(R.id.txtAlergiasMascota)
        val btnAddFotoMascota = root.findViewById<Button>(R.id.btnAgregarFotoMascota)
        val btnAgregarMascotas = root.findViewById<Button>(R.id.btnAgregarMascotas)

        btnAgregarMascotas.setOnClickListener{
            //Guardar en una variable los valores que escribio el  usuario
            val nombreMascota = txtNomAddMascota.text.toString()
            val especieMascota = txtAddEspecie.text.toString()
            val generoMascota = txtGenAddMascota.text.toString()
            val pesoMascota = txtPesoAddMascota.text.toString()
            val añoMascota = txtAñoAddMascota.text.toString()
            val enfermedadesCronicas = txtEnferCAddMascota.text.toString()
            val procedimientoMA = txtProceMAddMascota.text.toString()
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
            //Especie Mascota
            if (especieMascota .isEmpty()) {
                txtAddEspecie.error = "La especie de la Mascota es obligatorio"
                hayErrores = true
            }
            else {
                txtAddEspecie.error = null
            }
            //Genero de Mascota
            if (generoMascota.isEmpty()) {
                txtGenAddMascota.error = "El Genero de la Mascota es obligatorio"
                hayErrores = true
            }
            else {
                txtGenAddMascota.error = null
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
            //Alergias Mascota
            if (alergiaMascota.isEmpty()) {
                txtAlerAddMascota.error = "El apartado es obligatorio llenarse"
                hayErrores = true
            }
            else {
                txtAlerAddMascota.error = null
            }

            //TODO: 2- Validacion de Numeros
            if (!pesoMascota.matches(Regex("¨[0-9]+")))  {
                txtPesoAddMascota.error = "El peso solo debe contener numeros"
                hayErrores = true
            }
            else {
                txtPesoAddMascota.error = null
            }

            // Función para limpiar los campos
            fun limpiarCampos() {
                txtNomAddMascota.text.clear()
                txtAddEspecie.text.clear()
                txtGenAddMascota.text.clear()
                txtPesoAddMascota.text.clear()
                txtAñoAddMascota.text.clear()
                txtEnferCAddMascota.text.clear()
                txtProceMAddMascota.text.clear()
                txtAlerAddMascota.text.clear()
            }

            // Función para guardar los datos
            fun guardarDatos(
                nombreMascota: String,
                especieMascota: String,
                generoMascota: String,
                pesoMascota: Int,
                añoMascota: String,
                enfermedadesCronicas: String,
                procedimientoMA: String,
                alergiasMascota: String
            ) {
                // Lógica para guardar los datos
                // Aqui tendría que ir lo de oracle
                Toast.makeText(context, "Datos guardados", Toast.LENGTH_SHORT).show()
            }

            //Si hay errores no procede a guardar los datos
            if(hayErrores) {
                //Hacer algo si hay errores
            }
            else {
                //Si todas las validaciones son correcta, procede a guardar los daots
                guardarDatos(nombreMascota, especieMascota, generoMascota, pesoMascota.toInt(), añoMascota, enfermedadesCronicas, procedimientoMA, alergiaMascota)
                limpiarCampos()
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
         * @return A new instance of fragment agregarmascotas.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            agregarmascotas().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

    }
}