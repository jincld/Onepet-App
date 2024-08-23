package jonathan.orellana.onepetapp.ui

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import jonathan.orellana.onepetapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import modelo.ClaseConexion

class ActualizarVetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_actualizar_vet)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtVerNombreVet =findViewById<TextView>(R.id.txtVerNombreVet)
        val txtVerUbicacionVet =findViewById<TextView>(R.id.txtVerUbicacionVet)
        val txtVerNitVet = findViewById<TextView>(R.id.txtVerNitVet)
        val txtVerContactoVet = findViewById<TextView>(R.id.txtVerContactoVet)
        val txtVerCorreoVet =findViewById<TextView>(R.id.txtVerCorreoVet)
        val txtVerServiciosVet = findViewById<TextView>(R.id.txtVerServiciosVet)
        val btnEditarVet = findViewById<Button>(R.id.btnEditarVet)
        val btnEliminarVet = findViewById<Button>(R.id.btnEliminarVet)


        //Asignarle los datos recibidos a mis textos
//Segundo = primero

        var nombre = intent.getStringExtra("Nombre")
        val ubicacion = intent.getStringExtra("Ubicacion")
        val nit = intent.getStringExtra("NIT")
        val contacto = intent.getStringExtra("Contacto")
        val correo = intent.getStringExtra("Correo")
        val descripcion = intent.getStringExtra("Descripcion")

        txtVerNombreVet.text = nombre
        txtVerUbicacionVet.text = ubicacion
        txtVerNitVet.text = nit
        txtVerContactoVet.text = contacto
        txtVerCorreoVet.text = correo
        txtVerServiciosVet.text =  descripcion


        fun uodate(nombreNuevo: String, ubicacionNueva: String, NITNuevo: String, ContactoNuevo: String, CorreoNuevo: String, descripcion: String) {
            GlobalScope.launch(Dispatchers.IO) {
                val correoGLobalTraido = correo

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
                nombre = txtVerNombreVet.text.toString()

                updateVet.executeUpdate()
            }
        }
        fun isValid(vararg editTexts: EditText): Boolean {
            for (editText in editTexts) {
                if (editText.text.toString().isEmpty()) {
                    Toast.makeText(this, "Porfavor llene todos los datos", Toast.LENGTH_SHORT).show()
                    return false
                }
            }
            return true
        }


        btnEditarVet.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Editar")
            builder.setMessage("Estas seguro que quieres editar?")

            val nombrenuevo = EditText(this)
            nombrenuevo.setHint("Nombre")

            val nuevaubicacion = EditText(this)
            nuevaubicacion.setHint("Ubicación")

            val nuevoNit = EditText(this)
            nuevoNit.setHint("NIT")
            nuevoNit.inputType = InputType.TYPE_CLASS_NUMBER //

            val nuevoContacto = EditText(this)
            nuevoContacto.setHint("Contacto")

            val correoNuevo = EditText(this)
            correoNuevo.setHint("Correo")

            val descripcionNueva = EditText(this)
            descripcionNueva.setHint("Descripción servicios")

            val layout = LinearLayout(this).apply {
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
                    Toast.makeText(this, "Datos actualizados", Toast.LENGTH_SHORT).show()
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



                val nombrevett = nombre
                println("este es el nombre de la vet que quiero eliminar ${nombrevett}")


                // 2- Crear una variable que contenga un preparestatement (donde se mete el código de sqlserver
                val deleteVeterinaria = objConexion?.prepareStatement("delete from tbVeterinarias where nombre_veterinaria = ?")!!
                deleteVeterinaria.setString(1, nombrevett)
                deleteVeterinaria.executeUpdate()


                val commit = objConexion.prepareStatement("commit")!!
                commit.executeUpdate()
            }

        }
        btnEliminarVet.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Eliminar")
            builder.setMessage("Estas seguro que quieres eliminar tu veterinaria?")

            builder.setPositiveButton("Si") { dialog, which ->
                eliminarVet()
                Toast.makeText(this, "Datos eliminados", Toast.LENGTH_SHORT).show()


            }
            builder.setNegativeButton("no") { dialog, which ->
                dialog.dismiss()
            }
            builder.show()
        }

    }
    }
