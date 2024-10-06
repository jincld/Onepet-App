package jonathan.orellana.onepetapp

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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
//mandamos a llamar los botones
        val txtVerNombreVet =findViewById<TextView>(R.id.txtVerNombreVet)
        val txtVerUbicacionVet =findViewById<TextView>(R.id.txtVerUbicacionVet)
        val txtVerNitVet = findViewById<TextView>(R.id.txtVerNitVet)
        val txtVerContactoVet = findViewById<TextView>(R.id.txtVerContactoVet)
        val txtVerCorreoVet =findViewById<TextView>(R.id.txtVerCorreoVet)
        val txtVerServiciosVet = findViewById<TextView>(R.id.txtVerServiciosVet)
        val btnEditarVet = findViewById<ImageButton>(R.id.btnEditarVet)
        val btnEliminarVet = findViewById<ImageButton>(R.id.btnEliminarVet)
        val btnVolverMV = findViewById<ImageButton>(R.id.btnVolverMV)

        btnVolverMV.setOnClickListener {
            finish()
        }

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

//funcion para actualizar las veterinarias
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

        //validaciones
        fun isValid(vararg editTexts: EditText): Boolean {
            for (editText in editTexts) {
                if (editText.text.toString().isEmpty()) {
                    Toast.makeText(this, "Por favor llene todos los datos", Toast.LENGTH_SHORT).show()
                    return false
                }
            }
            return true
        }

//boton de editar veterinaria
        btnEditarVet.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Editar")
            builder.setMessage("¿Estás seguro que quieres editar?")

            val nombrenuevo = EditText(this)
            nombrenuevo.setText(nombre.toString())

            val nuevaubicacion = EditText(this)
            nuevaubicacion.setText(ubicacion.toString())

            val nuevoNit = EditText(this)
            nuevoNit.setText(nit.toString())
            nuevoNit.inputType = InputType.TYPE_CLASS_NUMBER //

            val nuevoContacto = EditText(this)
            nuevoContacto.setText(contacto.toString())

            val correoNuevo = EditText(this)
            correoNuevo.setText(correo.toString())

            val descripcionNueva = EditText(this)
            descripcionNueva.setText(descripcion.toString())

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
                    println("----------este es el nombre de vet que quiero usar ${nombrenuevo.text.toString()}")

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
                println("Este es el nombre de la vet que quiero eliminar ${nombrevett}")

                //Traer id empleado
                val statement = objConexion?.createStatement()
                val UUID_empleado = statement?.executeQuery("select UUID_ROL from tbRolesUsuarios where Nombre_Rol = 'Empleado'")!!

                //Regresar id de veterinaria al default
                val updateUser = objConexion?.prepareStatement("UPDATE tbUsuariosOne set vet = '1' where correo_usuario = ?")!!
                updateUser.setString(1, iniciarsesion.variablesLogin.valorCorreoUsuario)
                updateUser.executeUpdate()

                //Eliminar empleados
                val deleteEmpleados = objConexion?.prepareStatement("delete from tbUsuariosOne where vet = ? AND rol = ?")!!
                deleteEmpleados.setString(1, iniciarsesion.variablesLogin.uuid_Vet_real)
                deleteEmpleados.setString(2, UUID_empleado.toString())
                deleteEmpleados.executeUpdate()

                //Eliminar citas
                val deleteCitas = objConexion?.prepareStatement("delete from tbCitas where vet = ?")!!
                deleteCitas.setString(1, iniciarsesion.variablesLogin.uuid_Vet_real)
                deleteCitas.executeUpdate()

                //Eliminar reseñas
                val deleteResenas = objConexion?.prepareStatement("delete from tbresenas where vet = ?")!!
                deleteResenas.setString(1, iniciarsesion.variablesLogin.uuid_Vet_real)
                deleteResenas.executeUpdate()

                //Eliminar veterinaria
                val deleteVeterinaria = objConexion?.prepareStatement("delete from tbVeterinarias where UUID_veterinaria = ?")!!
                deleteVeterinaria.setString(1, iniciarsesion.variablesLogin.uuid_Vet_real)
                deleteVeterinaria.executeUpdate()

                val commit = objConexion.prepareStatement("commit")!!
                commit.executeQuery()
            }

        }
        btnEliminarVet.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Eliminar")
            builder.setMessage("¿Está seguro que quiere eliminar su veterinaria? Esto eliminará a sus empleados y datos de citas. Se cerrará sesión también.")

            builder.setPositiveButton("Si") { dialog, which ->
                eliminarVet()
                Toast.makeText(this, "Veterinaria eliminada", Toast.LENGTH_SHORT).show()
                val cerrar = Intent(this, iniciarsesion::class.java)
                startActivity(cerrar)
            }
            builder.setNegativeButton("no") { dialog, which ->
                dialog.dismiss()
            }
            builder.show()
        }

    }
    }
