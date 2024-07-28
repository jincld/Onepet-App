package RecyclerViewHelpers

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import jonathan.orellana.onepetapp.R
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.agregar_vet
import jonathan.orellana.onepetapp.agregarempleadodv
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import modelo.ClaseConexion
import modelo.dataClassEmpleado
import java.util.UUID

class Adaptador(var Datos: List<dataClassEmpleado>): RecyclerView.Adapter<ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card, parent, false)
        return ViewHolder(vista)
    }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val empleado = Datos[position]
        holder.txtNombreEmp.text = empleado.nombreEmpleado
        holder.txtCorreoEmp.text = empleado.correoEmpleado
        holder.txtContraEmp.text = empleado.contraEmpleado

        //todo: click icono eliminar
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
    }



   /* fun update(nombreNuevo: String, correoNuevo: String, ContraNueva: String) {

        GlobalScope.launch(Dispatchers.IO) {
            val RolGlobalTraido = agregarempleadodv.VariablesGlobalesEmpleado.RolEmpVG

            ///1 - creo un objeto de la clase conexion
            val objConexion = ClaseConexion().cadenaConexion()

            //2 - Creo una variable que tenga un prepareStatement
            val updateVet =
                objConexion?.prepareStatement(
                    "UPDATE tbUsuariosOne set nombre_usuario = ?, correo_usuario = ?, contra_usuario = ? where UUID_Usuario = ?"
                )!!
            updateVet.setString(1, nombreNuevo)
            updateVet.setString(2, correoNuevo)
            updateVet.setString(3, ContraNueva)
            updateVet.setString(4, traerUUID)

            updateVet.executeUpdate()
        }
    }*/

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