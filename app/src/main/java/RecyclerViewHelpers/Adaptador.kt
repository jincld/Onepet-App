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

    fun actualizarLista(nuevaLista: List<dataClassEmpleado>) {
        Datos = nuevaLista
        notifyDataSetChanged() // Notificar al adaptador sobre los cambios
    }




    fun actualizarDato( nuevoNombre: String, nuevacontra: String, nuevocorreo:String, uuid: String){
        GlobalScope.launch(Dispatchers.IO){

            //1- Creo un objeto de la clase de conexion
            val objConexion = ClaseConexion().cadenaConexion()
            val contraencriptada = hashSHA256(nuevacontra.toString())
          /*  val correo = nuevocorreo.toString()
            val contra = nuevacontra.toString()
            var hayerrores = false

            if (!correo.matches(Regex("[a-zA-Z0-9._-]+@[a-z]+[.][a-z]+"))){
                nuevocorreo.error = "Ingrese un correo valido"
                hayerrores = true
            } else {
                nuevocorreo.error = null
            }

            if (contra.length <= 8) {
                nuevacontra.error = "La contraseña debe tener más de 8 caracteres"
                hayerrores = true
            } else {
                nuevacontra.error = null
            }

            if (hayerrores){
            } else {*/



            //2- creo una variable que contenga un PrepareStatement
            val updateMascota = objConexion?.prepareStatement("update tbUsuariosOne set nombre_usuario = ?, contra_usuario = ?, correo_usuario = ? where UUID_usuario = ?")!!
            updateMascota.setString(1, nuevoNombre)
            updateMascota.setString(2, contraencriptada)
            updateMascota.setString(3, nuevocorreo)
            updateMascota.setString(4, uuid)
            updateMascota.executeUpdate()

            withContext(Dispatchers.Main){
                actualicePantalla(uuid, nuevoNombre, nuevocorreo,  nuevacontra) // Corregido el orden de los parámetros
            }

           /* }*/
        }
    }

    fun actualicePantalla( uuid: String, nuevoNombre: String, nuevacontra: String, nuevocorreo:String ){
        val index = Datos.indexOfFirst { it.empleadoUUID == uuid }
        Datos[index].nombreEmpleado = nuevoNombre
        Datos[index].contraEmpleado = nuevacontra
        Datos[index].correoEmpleado = nuevocorreo




        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card, parent, false)
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
            builder.setMessage("¿Quiere eliminar el empleado?")

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
       /* holder.btneditarcard.setOnClickListener{
            //Creamos un Alert Dialog
            val context = holder.itemView.context

            val builder = androidx.appcompat.app.AlertDialog.Builder(context)
            builder.setTitle("Editar Empleado")
            builder.setMessage("¿Desea editar Empleado?")

            //Agregarle un cuadro de texto para
            //que el usuario escriba el nuevo nombre
            val cuadroTexto = EditText(context)
            cuadroTexto.setHint(empleado.nombreEmpleado)
            cuadroTexto.setHint(empleado.contraEmpleado)
            cuadroTexto.setHint(empleado.correoEmpleado)
            builder.setView(cuadroTexto)

            //Botones
            builder.setPositiveButton("Actualizar") { dialog, which ->
                actualizarDato(cuadroTexto.text.toString(), empleado.empleadoUUID)
            }

            builder.setNegativeButton("Cancelar"){dialog, which ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }*/

        holder.btneditarcard.setOnClickListener{
            //Creamos un Alert Dialog
            val context = holder.itemView.context

            val builder = androidx.appcompat.app.AlertDialog.Builder(context)
            builder.setTitle("Editar Empleado")
            builder.setMessage("¿Desea editar a este empleado?")

            //Agregarle un cuadro de texto para
            //que el usuario escriba el nuevo nombre
            val cuadroTextoNombre = EditText(context)
            cuadroTextoNombre.setText(empleado.nombreEmpleado)
            val cuadroTextoContra = EditText(context)
            cuadroTextoContra.setText(empleado.contraEmpleado)
            val cuadroTextoCorreo = EditText(context)
            cuadroTextoCorreo.setText(empleado.correoEmpleado)
            val layout = LinearLayout(context)
            layout.orientation = LinearLayout.VERTICAL
            layout.addView(cuadroTextoNombre)
            layout.addView(cuadroTextoCorreo)
            layout.addView(cuadroTextoContra)
            builder.setView(layout)

            //Botones
            builder.setPositiveButton("Actualizar") { dialog, which ->
                actualizarDato(cuadroTextoNombre.text.toString(), cuadroTextoCorreo.text.toString(), cuadroTextoContra.text.toString(), empleado.empleadoUUID)
            }

            builder.setNegativeButton("Cancelar"){dialog, which ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
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

            val commit = objConexion?.prepareStatement("commit")!!
            commit.executeUpdate()
        }

        Datos = listaDatos.toList()
        notifyItemRemoved(position)
        notifyDataSetChanged()

    }

}