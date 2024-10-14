package RecyclerViewHelpers

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.dataClassEmpleado
import java.security.MessageDigest

class Adaptador(var Datos: List<dataClassEmpleado>): RecyclerView.Adapter<ViewHolder>(){

    fun hashSHA256(contraescrita: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(contraescrita.toByteArray())
        return bytes.joinToString("") {"%02x".format(it)}
    }
    fun actualicePantalla(uuid: String, nuevoNombre: String, nuevocorreo: String) {
        val index = Datos.indexOfFirst { it.empleadoUUID == uuid }
        if (index != -1) {
            Datos[index].nombreEmpleado = nuevoNombre
            Datos[index].contraEmpleado = nuevocorreo

            //println("Actualizando pantalla para UUID: $uuid, Nombre: $nuevoNombre, Contraseña: $nuevacontra, Correo: $nuevocorreo")
            notifyDataSetChanged()
        } else {
            println("No se encontró el empleado con UUID: $uuid")
        }
    }
    fun actualizarDato(nuevoNombre: String, nuevocorreo: String, uuid: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val objConexion = ClaseConexion().cadenaConexion()

            val updateempleado = objConexion?.prepareStatement("update tbUsuariosOne set nombre_usuario = ?, correo_usuario = ? where UUID_usuario = ?")!!
            updateempleado.setString(1, nuevoNombre)
            updateempleado.setString(2, nuevocorreo)
            updateempleado.setString(3, uuid)
            //println("Actualizando usuario con UUID: $uuid, Nombre: $nuevoNombre, Contraseña: $contraencriptada, Correo: $nuevocorreo")
            updateempleado.executeUpdate()

            withContext(Dispatchers.Main) {
                actualicePantalla(uuid, nuevoNombre, nuevocorreo)
            }
        }
    }

    fun actualicePantallaContra(uuid: String, nuevacontra: String) {
        val index = Datos.indexOfFirst { it.empleadoUUID == uuid }
        if (index != -1) {
            Datos[index].correoEmpleado = nuevacontra
            //println("Actualizando pantalla para UUID: $uuid, Nombre: $nuevoNombre, Contraseña: $nuevacontra, Correo: $nuevocorreo")
            notifyDataSetChanged()
        } else {
            println("No se encontró el empleado con UUID: $uuid")
        }
    }

    fun actualizarContra(nuevacontra: String, uuid: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val objConexion = ClaseConexion().cadenaConexion()
            val contraencriptada = hashSHA256(nuevacontra)
            println("Contraseña encriptada: $contraencriptada")

            val updateempleado = objConexion?.prepareStatement("update tbUsuariosOne set contra_usuario = ? where UUID_usuario = ?")!!
            updateempleado.setString(1, contraencriptada)
            updateempleado.setString(2, uuid)
           // println("Actualizando usuario con UUID: $uuid, Nombre: $nuevoNombre, Contraseña: $contraencriptada, Correo: $nuevocorreo")
            updateempleado.executeUpdate()

            withContext(Dispatchers.Main) {
                actualicePantallaContra(uuid, nuevacontra)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card_empleados, parent, false)
        return ViewHolder(vista)
    }
    override fun getItemCount() = Datos.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val empleado = Datos[position]
        holder.txtNombreEmp.text = empleado.nombreEmpleado
        holder.txtCorreoEmp.text = empleado.contraEmpleado
        holder.txtContraEmp.text = empleado.correoEmpleado

        holder.btnBorrarCard.setOnClickListener() {

            val context = holder.itemView.context

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Eliminar")
            builder.setMessage("¿Quiere eliminar a este empleado?")

            //Botones

            builder.setPositiveButton("Si") { dialog, which ->
                eliminarDatos(empleado.nombreEmpleado, position)
            }

            builder.setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }

        holder.btneditarcard.setOnClickListener{
            //Creamos un Alert Dialog
            val context = holder.itemView.context

            val builder = androidx.appcompat.app.AlertDialog.Builder(context)
            builder.setTitle("Editar Empleado")
            builder.setMessage("¿Quiere editar a este empleado?")

            //Agregarle un cuadro de texto para
            //que el usuario escriba el nuevo nombre
            val cuadroTextoNombre = EditText(context).apply { setText(empleado.nombreEmpleado) }
            //val cuadroTextoContra = EditText(context).apply { setText(empleado.contraEmpleado) }
            val cuadroTextoCorreo = EditText(context).apply { setText(empleado.contraEmpleado) }


            val layout = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                addView(cuadroTextoNombre)
                //addView(cuadroTextoContra)
                addView(cuadroTextoCorreo)
            }

            builder.setView(layout)

            builder.setPositiveButton("Actualizar") { dialog, which ->
                /*val nombre = cuadroTextoNombre.text.toString()
                val correo = cuadroTextoCorreo.text.toString()
                val contra = cuadroTextoContra.text.toString()*/

                val nombre = cuadroTextoNombre.text.toString()
                val correo = cuadroTextoCorreo.text.toString()
                //val contra = cuadroTextoCorreo.text.toString()
                var hayerrores = false

                println("Nombre capturado: $nombre")
               // println("Contraseña capturada SIN ENCRIPTAR: $contra")
                println("Correo capturado: $correo")

                if (nombre.isEmpty()) {
                    cuadroTextoNombre.error = "Debe de completar este campo"
                    hayerrores = true
                } else {
                    cuadroTextoNombre.error = null
                }

                if (!hayerrores) {
                    actualizarDato(nombre, correo, empleado.empleadoUUID)
                }
            }

            builder.setNegativeButton("Cancelar", null)
            builder.show()

        }

        //Editar Contra
        holder.btnEditarContraCard.setOnClickListener{
            //Creamos un Alert Dialog
            val context = holder.itemView.context

            val builder = androidx.appcompat.app.AlertDialog.Builder(context)
            builder.setTitle("Editar Contraseña")
            builder.setMessage("¿Desea editar la contraseña de este empleado?")

            //Agregarle un cuadro de texto para
            //que el usuario escriba el nuevo nombre
            //val cuadroTextoNombre = EditText(context).apply { setText(empleado.nombreEmpleado) }
            val cuadroTextoContra = EditText(context).apply { setText(empleado.correoEmpleado) }
            //val cuadroTextoCorreo = EditText(context).apply { setText(empleado.correoEmpleado) }


            val layout = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                //addView(cuadroTextoNombre)
                addView(cuadroTextoContra)
                //addView(cuadroTextoCorreo)
            }

            builder.setView(layout)

            builder.setPositiveButton("Actualizar") { dialog, which ->
                /*val nombre = cuadroTextoNombre.text.toString()
                val correo = cuadroTextoCorreo.text.toString()
                val contra = cuadroTextoContra.text.toString()*/

                //val nombre = cuadroTextoNombre.text.toString()
                //val correo = cuadroTextoCorreo.text.toString()
                val contra = cuadroTextoContra.text.toString()
                var hayerrores = false

                //println("Nombre capturado: $nombre")
                // println("Contraseña capturada SIN ENCRIPTAR: $contra")
                //println("Correo capturado: $correo")

                if (contra.isEmpty()) {
                    cuadroTextoContra.error = "Debe de completar este campo"
                    hayerrores = true
                } else {
                    cuadroTextoContra.error = null
                }

                if (!hayerrores) {
                    actualizarContra(contra, empleado.empleadoUUID)
                }
            }

            builder.setNegativeButton("Cancelar", null)
            builder.show()

        }
    }
    fun eliminarDatos(NombreEmp: String, position: Int) {

        val listaDatos = Datos.toMutableList()
        listaDatos.removeAt(position)

        GlobalScope.launch(Dispatchers.IO) {
            val objConexion = ClaseConexion().cadenaConexion()

            val borrarEmp = objConexion?.prepareStatement("delete from tbUsuariosOne where nombre_usuario = ?")!!
            borrarEmp.setString(1, NombreEmp)
            borrarEmp.executeUpdate()

            /*val commit = objConexion?.prepareStatement("commit")!!
            commit.executeUpdate()*/
        }

        Datos = listaDatos.toList()
        notifyItemRemoved(position)
        notifyDataSetChanged()

    }

}