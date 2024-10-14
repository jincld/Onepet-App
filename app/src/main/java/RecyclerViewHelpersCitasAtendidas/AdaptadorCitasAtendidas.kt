package RecyclerViewHelpersCitasAtendidas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R
import modelo.dataClassCitasAtendidas

class AdaptadorCitasAtendidas(var Datos: List<dataClassCitasAtendidas>) : RecyclerView.Adapter<ViewHolderCitasAtendidas>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCitasAtendidas {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card_citasatendidas, parent, false)
        return ViewHolderCitasAtendidas(vista)
    }
    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolderCitasAtendidas, position: Int) {
        val controlCard = Datos[position]
        holder.txtMotivoCitaHC.text = controlCard.motivo_cita
        holder.txtEstadoHc.text = controlCard.estado
        holder.txtUsuarioHC.text = controlCard.usuario
        holder.txtMascotaHC.text = controlCard.mascota
        holder.txtFechaCitaHC.text = controlCard.fecha_cita
        holder.txtMotivoCitaHC2.text = controlCard.motivo_cita
        holder.txtDescripcionCitaHC.text = controlCard.descripcion_motivo

    }
}