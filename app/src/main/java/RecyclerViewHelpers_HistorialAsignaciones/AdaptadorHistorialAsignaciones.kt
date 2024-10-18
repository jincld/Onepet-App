package RecyclerViewHelpers_HistorialAsignaciones

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R
import modelo.dataClassHistorialAsignaciones

class AdaptadorHistorialAsignaciones(var Datos: List<dataClassHistorialAsignaciones>) : RecyclerView.Adapter<ViewHolderHistorialAsignaciones>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHistorialAsignaciones {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_card_historial_asignaciones_emp, parent, false)
        return ViewHolderHistorialAsignaciones(vista)
    }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolderHistorialAsignaciones, position: Int) {
        val controlCard = Datos[position]
        holder.txtMotivoCitaHAE.text = controlCard.motivo_cita
        holder.txtEstadoHAE.text = controlCard.estado
        holder.txtMascotaHAE.text = controlCard.mascota
        holder.txtFechaCitaHAE.text = controlCard.fecha_cita
        holder.txtDescripcionCitaHAE.text = controlCard.descripcion_motivo
        holder.txtDetalleHA.text = controlCard.detalle_cita
    }
}