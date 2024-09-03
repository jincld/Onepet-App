package RecyclerViewHelpers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jonathan.orellana.onepetapp.R
import modelo.dataClassHistoCitas
import modelo.dataClassSoliC

class AdaptadorHistoCitas (var Datos: List<dataClassHistoCitas>): RecyclerView.Adapter<ViewHolderHistoCitas>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHistoCitas {
        //Unir el RecyclerView con la card
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card_historial_c, parent, false)
        return ViewHolderHistoCitas(vista)
    }

    //Devolver la cantidad de datos que se muestran
    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolderHistoCitas, position: Int) {
        //Controlar a la card
        val controlCard = Datos[position]

        holder.txtMotivoCitaCH.text = controlCard.motivo_cita
        holder.txtFechaCitaCH.text = controlCard.fecha_cita
        holder.txtVeterinariaCH.text = controlCard.vet
        holder.txtMascotaCH.text = controlCard.mascota
        holder.txtMotivoCitaCC2H.text = controlCard.motivo_cita
        holder.txtDescripcionCH.text = controlCard.descripcion_motivo

    }
}