package RecyclerViewHelpersCitasAsignadas

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R
import jonathan.orellana.onepetapp.asignarcitadv1
import jonathan.orellana.onepetapp.rechazarcitadv1
import modelo.dataClassCitasAsignadas

class AdaptadorCitasAsignadas(var Datos: List<dataClassCitasAsignadas>) : RecyclerView.Adapter<ViewHolderCitasAsignadas>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCitasAsignadas {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card_citas_asignadas, parent, false)
        return ViewHolderCitasAsignadas(vista)
    }
    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolderCitasAsignadas, position: Int) {
        val controlCard = Datos[position]
        holder.txtMotivoCitaCAS.text = controlCard.motivo_cita
        holder.txtEstadoCAS.text = controlCard.estado
        holder.txtUsuarioCAS.text = controlCard.usuario
        holder.txtMascotaCAS.text = controlCard.mascota
        holder.txtFechaCitaCAS.text = controlCard.fecha_cita
        holder.txtMotivoCitaCAS2.text = controlCard.motivo_cita
        holder.txtDescripcionCitaCAS.text = controlCard.descripcion_motivo


        holder.btnRechazarCAS.setOnClickListener {
            val context = holder.itemView.context
            val pantallaRechazar = Intent(context, rechazarcitadv1::class.java)
            pantallaRechazar.putExtra("motivo_cita", controlCard.motivo_cita)
            pantallaRechazar.putExtra("fecha_cita", controlCard.fecha_cita)
            pantallaRechazar.putExtra("usuario", controlCard.usuario)
            pantallaRechazar.putExtra("descripcion_motivo", controlCard.descripcion_motivo)
            context.startActivity(pantallaRechazar)
        }

        holder.btnEditarCAS.setOnClickListener {
            val context = holder.itemView.context
            val pantallaEditar = Intent(context, asignarcitadv1::class.java)
            pantallaEditar.putExtra("motivo_cita", controlCard.motivo_cita)
            pantallaEditar.putExtra("fecha_cita", controlCard.fecha_cita)
            pantallaEditar.putExtra("usuario", controlCard.usuario)
            pantallaEditar.putExtra("descripcion_motivo", controlCard.descripcion_motivo)
            context.startActivity(pantallaEditar)
        }
    }
}