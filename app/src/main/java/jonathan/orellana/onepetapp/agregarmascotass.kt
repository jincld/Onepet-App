package jonathan.orellana.onepetapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.findViewTreeFullyDrawnReporterOwner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import java.util.UUID

class agregarmascotass : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agregarmascotass)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //1-Mando a llamar todos los elementos de la vista para programarlos
        val txtNomAddMascota = findViewById<EditText>(R.id.txtNombreMascota)
        val txtAddEspecie = findViewById<EditText>(R.id.txtEspecieMascota)
        val txtGenAddMascota = findViewById<EditText>(R.id.txtGeneroMascota)
        val txtPesoAddMascota = findViewById<EditText>(R.id.txtPesoMascota)
        val txtAñoAddMascota = findViewById<EditText>(R.id.txtAñoMascota)
        val txtEnferCAddMascota = findViewById<EditText>(R.id.txtEnfermedadesCMascota)
        val txtProceMAddMascota = findViewById<EditText>(R.id.txtProcedimientosMAscota)
        val txtAlerAddMascota = findViewById<EditText>(R.id.txtAlergiasMascota)
        val btnAddFotoMascota = findViewById<Button>(R.id.btnAgregarFotoMascota)
        val btnAgregarMascotas = findViewById<Button>(R.id.btnAgregarMascotas)

        btnAgregarMascotas.setOnClickListener{
            //Guardar en una variable los valores que escribio el usuario
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
                Toast.makeText(this, "Datos guardados", Toast.LENGTH_SHORT).show()
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

    }
}