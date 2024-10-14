package RecyclerViewHelpersMisAsignaciones

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R
import jonathan.orellana.onepetapp.rechazarcitadv1
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.dataClassMisAsignaciones

class AdaptadorMisAsignaciones(var Datos: List<dataClassMisAsignaciones>) : RecyclerView.Adapter<ViewHolderMisAsignaciones>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMisAsignaciones {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_card_mis_asignaciones, parent, false)
        return ViewHolderMisAsignaciones(vista)
    }

    fun terminarCita(UUID_cita: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val objConexion = ClaseConexion().cadenaConexion()
            val cambiarEstadoEmp = objConexion?.prepareStatement("Update tbCitasEmp set estado ='Finalizada' where UUID_cita = ?")
            cambiarEstadoEmp?.setString(1, UUID_cita)
            cambiarEstadoEmp?.executeUpdate()

            val cambiarEstado = objConexion?.prepareStatement("Update tbCitas set estado ='Finalizada' where UUID_cita = ?")
            cambiarEstado?.setString(1, UUID_cita)
            cambiarEstado?.executeUpdate()

            objConexion?.prepareStatement("commit")?.executeUpdate()

            // Actualizar datos en la UI
            withContext(Dispatchers.Main) {
                // Remueve el item actualizado de la lista
                val listaDatos = Datos.toMutableList()
                val index = listaDatos.indexOfFirst { it.UUID_Cita == UUID_cita }
                if (index != -1) {
                    listaDatos.removeAt(index)
                }
                Datos = listaDatos
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolderMisAsignaciones, position: Int) {
        val controlCard = Datos[position]
        holder.txtMotivoCitaMA.text = controlCard.motivo_cita
        holder.txtFechaCitaMA.text = controlCard.fecha_cita
        holder.txtMascotaMA.text = controlCard.mascota
        holder.txtDescripcionMA.text = controlCard.descripcion_motivo

        holder.btnTerminarCitaMA.setOnClickListener {
            val context = holder.itemView.context
            val builder = androidx.appcompat.app.AlertDialog.Builder(context)
            builder.setTitle("Finalizar cita")
            builder.setMessage("¿Desea marcar a esta cita como finalizada?")
            builder.setPositiveButton("Actualizar") { _, _ ->
                terminarCita(controlCard.UUID_Cita)
            }
            builder.setNegativeButton("Cancelar", null)
            builder.show()
        }

        holder.btnEliminarCitaMA.setOnClickListener {
            val context = holder.itemView.context
            val pantallaRechazar = Intent(context, rechazarcitadv1::class.java)
            pantallaRechazar.putExtra("motivo_cita", controlCard.motivo_cita)
            pantallaRechazar.putExtra("fecha_cita", controlCard.fecha_cita)
            pantallaRechazar.putExtra("usuario", controlCard.usuario)
            pantallaRechazar.putExtra("descripcion_motivo", controlCard.descripcion_motivo)
            context.startActivity(pantallaRechazar)
        }
    }
}
