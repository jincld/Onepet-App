package RecyclerViewHelpersMisAsignaciones

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R
import modelo.dataClassMisAsignaciones

class AdaptadorMisAsignaciones (var Datos: List<dataClassMisAsignaciones>): RecyclerView.Adapter<ViewHolderMisAsignaciones>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMisAsignaciones {
        //Unir el RecyclerView con la card
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_card_mis_asignaciones, parent, false)
        return ViewHolderMisAsignaciones(vista)
    }

    //Devolver la cantidad de datos que se muestran
    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolderMisAsignaciones, position: Int) {
        //Controlar a la card
        val controlCard = Datos[position]

        holder.txtMotivoCitaMA.text = controlCard.motivo_cita
        holder.txtFechaCitaMA.text = controlCard.fecha_cita
        holder.txtMascotaMA.text = controlCard.mascota
        holder.txtDescripcionMA.text = controlCard.descripcion_motivo


    }
}