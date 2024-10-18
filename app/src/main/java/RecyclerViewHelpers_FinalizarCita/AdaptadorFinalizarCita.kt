package RecyclerViewHelpers_FinalizarCita

import RecyclerViewHelpersMisAsignaciones.ViewHolderMisAsignaciones
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R
import jonathan.orellana.onepetapp.activity_finalizarcita
import jonathan.orellana.onepetapp.rechazarcitadv1
import modelo.dataClassMisAsignaciones

class AdaptadorFinalizarCita(var Datos: List<dataClassMisAsignaciones>) : RecyclerView.Adapter<ViewHolderMisAsignaciones>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMisAsignaciones {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_card_mis_asignaciones, parent, false)
        return ViewHolderMisAsignaciones(vista)
    }



    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolderMisAsignaciones, position: Int) {
        val controlCard = Datos[position]
        holder.txtMotivoCitaMA.text = controlCard.motivo_cita
        holder.txtFechaCitaMA.text = controlCard.fecha_cita
        holder.txtMascotaMA.text = controlCard.mascota
        holder.txtDescripcionMA.text = controlCard.descripcion_motivo

        /*  holder.btnTerminarCitaMA.setOnClickListener {
              val context = holder.itemView.context
              val builder = androidx.appcompat.app.AlertDialog.Builder(context)
              builder.setTitle("Finalizar cita")
              builder.setMessage("¿Desea marcar a esta cita como finalizada?")
              builder.setPositiveButton("Actualizar") { _, _ ->
                  terminarCita(controlCard.UUID_Cita)
              }
              builder.setNegativeButton("Cancelar", null)
              builder.show()
          }*/

        holder.btnTerminarCitaMA.setOnClickListener {
            val context = holder.itemView.context
            val pantallaFinalizar = Intent(context, activity_finalizarcita::class.java)
            pantallaFinalizar.putExtra("motivo_cita", controlCard.motivo_cita)
            pantallaFinalizar.putExtra("fecha_cita", controlCard.fecha_cita)
            pantallaFinalizar.putExtra("mascota", controlCard.mascota)
            pantallaFinalizar.putExtra("descripcion_motivo", controlCard.descripcion_motivo)
            context.startActivity(pantallaFinalizar)
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