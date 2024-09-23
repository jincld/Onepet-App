package RecyclerViewHelpers

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.dataClassCitas

class AdaptadorCitas (var Datos: List<dataClassCitas>): RecyclerView.Adapter<ViewHolderCitas>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCitas {
        //Unir el RecyclerView con la card
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_cardhistorialcitas, parent, false)
        return ViewHolderCitas(vista)
    }

    /////////////////// TODO: Eliminar Card Citas
    fun eliminarCardCitas(MotivoCita: String, posicion: Int){
        val listaDatos = Datos.toMutableList()
        listaDatos.removeAt(posicion)
        GlobalScope.launch(Dispatchers.IO) {
            //1- Creamos un objeto de la clase conexion
            val objConexion = ClaseConexion().cadenaConexion()

            //2- Crear una variable que contenga un PrepareStatement
            val deleteMascota =
                objConexion?.prepareStatement("delete from tbCitas where motivo_cita = ?")!!
            deleteMascota.setString(1, MotivoCita)
            deleteMascota.executeUpdate()

            val commit = objConexion.prepareStatement("commit")!!
            commit.executeUpdate()
        }

        Datos = listaDatos.toList()
        notifyItemRemoved(posicion)
        notifyDataSetChanged()

    }


    fun actualicePantallaC(UUID_Cita: String, nuevaFecha: String, nuevoMotivo: String, nuevaDescripcion: String){
        val index = Datos.indexOfFirst { it.UUID_Cita == UUID_Cita }
        if(index != -1) {
            Datos[index].fecha_cita = nuevaFecha
            Datos[index].motivo_cita = nuevoMotivo
            Datos[index].descripcion_motivo = nuevaDescripcion
            notifyDataSetChanged()
        }
    }

    //////////////////////TODO: Actualizar datos
    fun actualizarCita(nuevaFecha: String, nuevoMotivo: String, nuevaDescripcion: String, UUID_Cita: String) {
        GlobalScope.launch(Dispatchers.IO){
            val objConexion = ClaseConexion().cadenaConexion()

            val updateCita = objConexion?.prepareStatement("Update tbCitas set fecha_cita =  ?, motivo_cita = ? , descripcion_motivo = ? where UUID_Cita = ?")!!
            updateCita.setString(1, nuevaFecha)
            updateCita.setString(2, nuevoMotivo)
            updateCita.setString(3, nuevaDescripcion)
            updateCita.setString(4, UUID_Cita)
            updateCita.executeUpdate()

            val commit = objConexion.prepareStatement("COMMIT")!!
            commit.executeUpdate()

            withContext(Dispatchers.Main){
                actualicePantallaC(UUID_Cita, nuevaFecha, nuevoMotivo, nuevaDescripcion)
            }
        }
    }

    //Devolver la cantidad de datos que se muestran
    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolderCitas, position: Int) {
        //Controlar a la card
        val controlCard = Datos[position]

        holder.txtMotivoCitaC.text = controlCard.motivo_cita
        holder.txtFechaCitaC.text = controlCard.fecha_cita
        holder.txtVeterinariaCC.text = controlCard.vet
        holder.txtMascotaC.text = controlCard.mascota
        holder.txtMotivoCitaCC2.text = controlCard.motivo_cita
        holder.txtDescripcionC.text = controlCard.descripcion_motivo
        holder.txtEstadoCitaC.text = controlCard.estado

        //todo: clic al boton de eliminar
        holder.btnEliminarCitaC.setOnClickListener {

            //Creamos un Alert Dialog
            val context = holder.itemView.context

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Eliminar")
            builder.setMessage("¿Desea cancelar la cita?")

            //Botones
            builder.setPositiveButton("Si") { dialog, which ->
                eliminarCardCitas(controlCard.motivo_cita, position)
            }

            builder.setNegativeButton("No"){dialog, which ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }

        //Todo: boton de Actualizar
        holder.btnEditarC.setOnClickListener{
            //Creamos un Alert Dialog
            val context = holder.itemView.context

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Editar")
            builder.setMessage("¿Desea editar esta Cita?")

            //Agregarle un cuadro de texto para
            //que el usuario escriba cada parametro
            val fecha_cita = EditText(context).apply {
                setText(controlCard.fecha_cita)
            }
            val motivo_cita = EditText(context).apply {
                setText(controlCard.motivo_cita)
            }
            val descripcion_motivo = EditText(context).apply {
                setText(controlCard.descripcion_motivo)
            }

            val layout = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                addView(fecha_cita)
                addView(motivo_cita)
                addView(descripcion_motivo)
            }
            builder.setView(layout)

            //Botones
            builder.setPositiveButton("Actualizar") { dialog, which ->
                actualizarCita(
                    fecha_cita.text.toString(),
                    motivo_cita.text.toString(),
                    descripcion_motivo.text.toString(),
                    controlCard.UUID_Cita
                )
            }

            builder.setNegativeButton("Cancelar"){dialog, which ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
            }
        }
}